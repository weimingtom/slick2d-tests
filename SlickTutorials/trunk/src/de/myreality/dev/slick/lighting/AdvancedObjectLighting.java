package de.myreality.dev.slick.lighting;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.resource.Resource;
import de.myreality.dev.chronos.resource.ResourceManager;
import de.myreality.dev.chronos.slick.ImageLoader;
import de.myreality.dev.chronos.slick.ImageRenderComponent;
import de.myreality.dev.chronos.slick.SlickEntity;
import de.myreality.dev.chronos.util.Point2f;

public class AdvancedObjectLighting extends BasicGame {
	
	private ArrayList<SlickEntity> entities;
	
	private ArrayList<Light> lights;

	private final static int TILE_SIZE = 256;
	private Resource<Image> background;
	private Ball ball;
	private Light light;
	
	private final static Color AMBIENT_COLOR = new Color(0.1f, 0.1f, 0.1f);

	public AdvancedObjectLighting() {
		super("Slick Tutorial - Object Lighting");
		entities = new ArrayList<SlickEntity>();
		lights = new ArrayList<Light>();
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		
		int lastY = 0;
		final int TILES_X = container.getWidth() / TILE_SIZE + 1;
		final int TILES_Y = container.getHeight() / TILE_SIZE + 1;
		for (int y = 0; y < TILES_Y; ++y) {
			int lastX = 0;
			for (int x = 0; x < TILES_X; ++x) {
				background.get().draw(lastX, lastY, TILE_SIZE, TILE_SIZE, AMBIENT_COLOR);
				
				//backgroundBumped.draw(lastX, lastY, TILE_SIZE, TILE_SIZE);
				lastX += TILE_SIZE;
			}
			lastY += TILE_SIZE;
		}
		
		
		for (SlickEntity entity : entities) {
			
			entity.render(container, null, g);
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		
		// Init the resources
		ResourceManager manager = ResourceManager.getInstance();
		manager.addResourceLoader(ImageLoader.getInstance());
		manager.fromXML("resources.xml");		
		
		// Load the background
		background = manager.getResource("WALL", Image.class);		
		
		light = new Light("Light3", 200, 200, 100, Color.white);
		lights.add(light);
		// Add a ball object
		ball = new Ball("Ball", lights);
		ball.setGlobalPosition(400, 300);
		entities.add(ball);
		
		for (int i = 0; i < 900; i++) {
			Ball tempBall = new Ball("Ball", lights);
			tempBall.setGlobalPosition((float)(Math.random() * container.getWidth()), (float)(Math.random() * container.getHeight()));
			entities.add(tempBall);
		}
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		Input input = container.getInput();
		light.setGlobalCenterPosition(input.getMouseX(), input.getMouseY(), 0);
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			Random random = new Random();
			float randSize = random.nextInt(50)+150f;
			Color randColor = new Color(random.nextInt(200) + 50, random.nextInt(200) + 50, random.nextInt(200) + 50);
			lights.add(new Light("LIGHT", (int)randSize, input.getMouseX(), input.getMouseY(), randColor));
		}
		
		for (SlickEntity entity : entities) {
			entity.rotate(0.1f * delta);
			entity.update(container, null, delta);
			
		}
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer container = new AppGameContainer(new AdvancedObjectLighting());
		container.setDisplayMode(800, 600, false);
		container.start();
	}
	
	
	private class Ball extends SlickEntity {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Ball(String id, ArrayList<Light> lights) {
			super(null);
			Resource<Image> sprite = ResourceManager.getInstance().getResource("BALL", Image.class);
			this.addComponent(new LightRenderComponent("BALL", sprite.get(), lights));
			this.setDimension(20, 20, 0);
		}		
	}
	
	private class LightRenderComponent extends ImageRenderComponent {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ArrayList<Light> lights;

		public LightRenderComponent(String id, Image image, ArrayList<Light> lights) {
			super(id);
			this.lights = lights;
		}

		@Override
		public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {			

			if (getOwner() instanceof SlickEntity) {
				
				SlickEntity slickOwner = (SlickEntity)getOwner();				
				
				for (int i = 0; i < slickOwner.getBounds().length; ++i) {					

					float colorRed = 0, colorGreen = 0, colorBlue = 0;
					
					for (Light light : lights) {						
						Point2f bound = slickOwner.getBounds()[i];

						// Calculate the distance
						float deltaX = light.getGlobalCenterX() - bound.x;
						float deltaY = light.getGlobalCenterY() - bound.y;
						float distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
						//g.setColor(Color.green);
						//g.drawLine(corner.getX(), corner.getY(), light.getX(), light.getY());
						
						float shadowAmount = distance /  light.getSize() * 2;	
						if (shadowAmount > 1) {
							shadowAmount = 1;
						}
						
						Color resultColor = blend(AMBIENT_COLOR.r / lights.size(), AMBIENT_COLOR.g / lights.size(), AMBIENT_COLOR.b / lights.size(),
												  light.getColor().r, light.getColor().g, light.getColor().b, shadowAmount);
						colorRed += resultColor.r;
						colorGreen += resultColor.g;
						colorBlue += resultColor.b;
					}
					
					getImage().setColor(i, colorRed, colorGreen, colorBlue);
				}
				
				super.render(gc, sbg, g);
			}
		}
		
		
		public Color blend(float r1, float g1, float b1, float r2, float g2, float b2, float ratio) {

			float ir = (float) 1.0 - ratio;

			return new Color((float)(r1 * ratio + r2 * ir), 
							 (float)(g1 * ratio + g2 * ir),
							 (float)(b1 * ratio + b2 * ir));          
		}
		
	}
	
	private class Light extends SlickEntity {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private int size;
		
		private Color color;

		public Light(String id, int size, int x, int y, Color color) {
			super(null);
			setGlobalCenterPosition(x, y, 0);
			this.size = size;
			this.color = color;
		}		
		
		public Color getColor() {
			return color;
		}
		
		public int getSize() {
			return size;
		}

		@Override
		public int getWidth() {
			return getSize();
		}

		@Override
		public int getHeight() {
			return getSize();
		}
		
		
	}
}
