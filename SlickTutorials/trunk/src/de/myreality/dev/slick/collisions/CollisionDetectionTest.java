package de.myreality.dev.slick.collisions;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.toolkit.Vector2f;
import de.myreality.dev.chronos.toolkit.models.Entity;
import de.myreality.dev.chronos.toolkit.models.EntityManager;
import de.myreality.dev.chronos.toolkit.models.EntitySystem;
import de.myreality.dev.chronos.toolkit.resource.ResourceManager;
import de.myreality.dev.chronos.toolkit.slick.ImageLoader;
import de.myreality.dev.chronos.toolkit.slick.ImageRenderComponent;
import de.myreality.dev.chronos.toolkit.slick.SlickComponent;
import de.myreality.dev.chronos.toolkit.slick.SlickEntity;

public class CollisionDetectionTest extends BasicGame {
	
	private boolean useQuadTree = true;
	private EntitySystem renderSystem;
	private static int ENTITIES = 10;
	private ResourceManager manager = ResourceManager.getInstance();
	private Quadtree collisionTree;
	
	public CollisionDetectionTest() {
		super("Slick2D - Collision Detection Test");
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
		
		String collisionMethod = "Complete comparison";
		
		if (useQuadTree) {
			collisionMethod = "Quad Tree comparison";
		}
		
		g.drawString("Collision Method: " + collisionMethod + " [F5]", 10, 50);
		
		
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		manager.addResourceLoader(ImageLoader.getInstance());
		manager.fromXML("resources.xml");
		renderSystem = EntityManager.getInstance().addSystem("RENDER_SYSTEM");
		
		for (int i = 0; i < ENTITIES; ++i) {
			addRandomEntity(gc);
		}
		
		collisionTree = new Quadtree(0, new Rectangle(0, 0, gc.getWidth(), gc.getHeight()));
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

	@Override
	public void update(GameContainer gc, int delta)
			throws SlickException {
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
					// Continue for more performance
					continue;
				}
				
				if (checkCollision(entity)) {
					((SlickEntity) entity).setColor(Color.red);
				} else  {
					((SlickEntity) entity).setColor(Color.green);
				}
			}
		}
		
		
		
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_F5)) {
			useQuadTree = !useQuadTree;
		}
		
		if (input.isKeyDown(Input.KEY_F1)) {
			for (int i = 0; i < 10; ++i)
				addRandomEntity(gc);
		}		
		
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			for (int i = 0; i < 5; ++i)
					addRandomEntity(gc, input.getMouseX(), input.getMouseY());
		}
		
	}
	
	

	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new CollisionDetectionTest());
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
		
		private final float MAX_SPEED = 0.25f;
		
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
