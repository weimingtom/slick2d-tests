package de.myreality.dev.slick.lighting.core;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.models.Entity;
import de.myreality.dev.chronos.resource.Resource;
import de.myreality.dev.chronos.resource.ResourceManager;
import de.myreality.dev.chronos.slick.ColorLoader;
import de.myreality.dev.chronos.slick.ImageLoader;
import de.myreality.dev.chronos.slick.SlickComponent;
import de.myreality.dev.chronos.slick.SlickEntity;
import de.myreality.dev.chronos.util.Point2f;

public class LightingGame extends BasicGame {
	
	private World world;
	
	private Light basicLight;
	
	private static final int ENTITY_COUNT = 100;
	private static final int ENTITY_SIZE = 60;
	private static final int LIGHT_SIZE = 550;

	public LightingGame(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		world.render(gc, null, g);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
		ResourceManager manager = ResourceManager.getInstance();
		manager.addResourceLoader(ImageLoader.getInstance());
		manager.addResourceLoader(ColorLoader.getInstance());
		
		manager.fromXML("resources.xml");		
		
		world = new GameWorld();
		world.init(gc, null);
		
		// Add some random entities
		for (int i = 0; i < ENTITY_COUNT; ++i) {
			world.addEntity(createRandomEntity(gc));
		}
		
		// Set another ambient color
		Resource<Color> ambientNight = manager.getResource("NIGHTBLUE", Color.class);
		world.setAmbientColor(ambientNight.get());
		
		basicLight = new Light(new Point2f(0, 0), 600);
		
		world.addEntity(basicLight);
	}
	
	private Entity createRandomEntity(GameContainer gc) {
		SlickEntity entity = new LightingEntity("BALL");
		
		float randomX = (float) (Math.random() * gc.getWidth());
		float randomY = (float) (Math.random() * gc.getHeight());
		
		entity.setBounds(randomX, randomY, ENTITY_SIZE, ENTITY_SIZE);
		entity.addComponent(new RandomRotationComponent());
		
		return entity;
	}

	
	public Light createRandomLight(float x, float y) {
		Light light = new Light(new Point2f(x, y), LIGHT_SIZE);
		Random random = new Random();
		light.setColor(new Color(random.nextFloat(), random.nextFloat(), random.nextFloat()));		
		return light;
	}
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		Input input = gc.getInput();
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			world.addEntity(createRandomLight(input.getMouseX(), input.getMouseY()));
		}
		
		if (input.isKeyDown(Input.KEY_DELETE)) {
			world.setLightingSystem(null);
		}
		
		
		basicLight.setGlobalCenterX(input.getMouseX());
		basicLight.setGlobalCenterY(input.getMouseY());
		
		world.update(gc, null, delta);
	}
	
	
	public static void main(String[] args) throws SlickException {
		LightingGame game = new LightingGame("Slick2D: Lighting Engine Game");
		AppGameContainer gameContainer = new AppGameContainer(game);
		gameContainer.setDisplayMode(800, 600, false);
		gameContainer.setMinimumLogicUpdateInterval(20);
		gameContainer.start();		
	}
	
	
	
	class RandomRotationComponent extends SlickComponent {

		private static final long serialVersionUID = 818250457131210035L;
		
		private float rotation;
		
		private final Random random = new Random();
		
		public RandomRotationComponent() {
			rotation = random.nextFloat() * random.nextFloat();
		}

		@Override
		public void update(GameContainer gc, StateBasedGame sbg, int delta) {
			super.update(gc, sbg, delta);
			if (hasOwner()) {
				getOwner().rotate(0.5f * delta);
			}
		}
	}
}
