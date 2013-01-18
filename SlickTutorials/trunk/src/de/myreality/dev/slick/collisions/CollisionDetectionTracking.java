package de.myreality.dev.slick.collisions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.models.Entity;
import de.myreality.dev.chronos.models.EntityManager;
import de.myreality.dev.chronos.models.EntitySystem;
import de.myreality.dev.chronos.resource.ResourceManager;
import de.myreality.dev.chronos.slick.ImageLoader;
import de.myreality.dev.chronos.slick.ImageRenderComponent;
import de.myreality.dev.chronos.slick.SlickComponent;
import de.myreality.dev.chronos.slick.SlickEntity;
import de.myreality.dev.chronos.util.Quadtree;
import de.myreality.dev.chronos.util.Vector2f;

public class CollisionDetectionTracking extends BasicGame {
	
	private boolean useQuadTree = true;
	private EntitySystem renderSystem;
	private static int ENTITIES =  10000;
	private static int STEPS = 1000;
	private ResourceManager manager = ResourceManager.getInstance();
	private Quadtree collisionTree;
	private OutputStreamWriter quadFile, normalFile;
	private long elapsedTime = 0;
	private static int INIT_TIME = 2000;
	
	public CollisionDetectionTracking() {
		super("Slick2D - Collision Detection Tracking");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		g.setAntiAlias(true);
		
		for (Entity entity : renderSystem.getAllEntities()) {
			if (entity instanceof SlickEntity) {
				((SlickEntity)entity).render(gc, null, g);
			}
		}
		
		g.setColor(Color.white);
		g.drawString("Render Objects: " + renderSystem.size(), 10, 30);
		
		String collisionMethod = "Tracking normal collision...";
		
		if (useQuadTree) {
			collisionMethod = "Tracking quad tree collision...";
		}
		
		float percentage = Math.round(((float)(renderSystem.size()) / (float)(ENTITIES)) * 100);
		
		g.drawString("Collision Method: " + collisionMethod + " (" + percentage + "%)", 10, 50);
		
		
		
	}

	
	
	private void addSteppedEntities(GameContainer gc) {
		for (int i = 0; i < ENTITIES / STEPS; ++i) {
			addRandomEntity(gc);
		}
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		manager.addResourceLoader(ImageLoader.getInstance());
		manager.fromXML("resources.xml");
		renderSystem = EntityManager.getInstance().addSystem("RENDER_SYSTEM");
		
		collisionTree = new Quadtree(0, new Rectangle(0, 0, gc.getWidth(), gc.getHeight()));
		
		
		try {
			quadFile = new OutputStreamWriter(new FileOutputStream(new File("tracking/quad_collision.txt")));
			normalFile = new OutputStreamWriter(new FileOutputStream(new File("tracking/normal_collision.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	private void addRandomEntity(GameContainer gc) {		
		int randX = (int) (gc.getWidth() * Math.random());
		int randY = (int) (gc.getHeight() * Math.random());	
		addRandomEntity(gc, randX, randY);		
	}
	
	public void addRandomEntity(GameContainer gc, int x, int y) {
		SlickEntity entity = new SlickEntity();
		int size = (int) (0.1f + Math.random() * 15);
		entity.setBounds(x, y, size, size);
		ImageRenderComponent component = new ImageRenderComponent("BALL");
		entity.addComponent(component);
		RandomMovingComponent moving = new RandomMovingComponent();
		entity.addComponent(moving);
		entity.setEntitySystem(renderSystem);
	}
	
	
	private void writeData(OutputStreamWriter writer, GameContainer gc) {
		try {
			writer.write("" + renderSystem.size() + "," + gc.getFPS() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer gc, int delta)
			throws SlickException {
		elapsedTime += delta;	
		
		if (INIT_TIME > 0) {
			if (elapsedTime >= INIT_TIME) {
				INIT_TIME = -1;
				elapsedTime = 0;
			}
		} else {
		
			if (useQuadTree) {
				//if (elapsedTime >= INTERVAL) {
					addSteppedEntities(gc);
									
					writeData(quadFile, gc);
					
					if (gc.getFPS() <= 1) {
						try {
							quadFile.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						useQuadTree = false;
						elapsedTime = 0;
						renderSystem.clear();
						INIT_TIME = 3000;
					}
					
					elapsedTime = 0;
				//}
			} else {
				//if (elapsedTime >= INTERVAL) {
					addSteppedEntities(gc);
					
					writeData(normalFile, gc);
					
					if (gc.getFPS() <= 1) {
						try {
							normalFile.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						gc.exit();
					}
					
					elapsedTime = 0;
				//}
			}
			
			collisionTree.clear();
			for (Entity entity : renderSystem.getAllEntities()) {
				collisionTree.insert(entity);
			}
			
			for (Entity entity : renderSystem.getAllEntities()) {
				
				
				
				if (entity instanceof SlickEntity) {
					((SlickEntity)entity).update(gc, null, delta);
					
					boolean onScreen = entity.getGlobalX() + entity.getWidth() >= 0 &&
							           entity.getGlobalY() + entity.getHeight() >= 0 &&
							           entity.getGlobalX() <= gc.getWidth() &&
							           entity.getGlobalY()<= gc.getHeight();
					
					if (!onScreen) {
						renderSystem.removeEntity(entity);
						addRandomEntity(gc);
						continue;
					}
					
					if (checkCollision(entity)) {
						((SlickEntity) entity).setColor(Color.red);
					} else  {
						((SlickEntity) entity).setColor(Color.green);
					}
				}
			}
		}
	}
	
	

	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new CollisionDetectionTracking());
		game.setDisplayMode(800, 600, false);
		game.start();
	}
	
	
	
	private boolean checkCollision(Entity entity) {
		if (useQuadTree) {
			List<Entity> returnObjects = new ArrayList<Entity>();
			collisionTree.retrieve(returnObjects, entity);
			 
			for (Entity other : returnObjects) {
			    if (other != entity && other.collidateWidth((SlickEntity) entity)) {
			    	return true;
			    }
			}
			
			return false;
		} else {
			for (Entity other : renderSystem.getAllEntities()) {
				if (other != entity && other.collidateWidth((SlickEntity) entity)) {
			    	return true;
			    }
			}
			
			return false;
		}
	} 
	
	
	
	class RandomMovingComponent extends SlickComponent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private Vector2f direction;
		
		private final float MAX_SPEED = 0.1f;
		
		public RandomMovingComponent() {
			direction = generateDirection();
		}
		
		private Vector2f generateDirection() {
			Vector2f result = new Vector2f(0, 0);
			
			result.x = (float) (MAX_SPEED * Math.random() + 0.0f);
			result.y = (float) (MAX_SPEED * Math.random() + 0.0f);
			
			if (Math.random() * 100 < 50) {
				result.x = -result.x;
			}
			
			if (Math.random() * 100 < 50) {
				result.y = -result.y;
			}
			
			return result;
		}

		@Override
		public void update(GameContainer gc, StateBasedGame sbg, int delta) {
			super.update(gc, sbg, delta);
			Entity entity = getOwner();
			
			float x = entity.getGlobalX() + direction.x * delta;
			float y = entity.getGlobalY() + direction.y * delta;
			
			entity.setGlobalX(x);
			entity.setGlobalY(y);
			
			entity.rotate(0.5f * delta);
		}
		
		
		
	}

}
