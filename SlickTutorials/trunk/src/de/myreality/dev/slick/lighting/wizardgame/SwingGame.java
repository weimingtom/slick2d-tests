package de.myreality.dev.slick.lighting.wizardgame;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.resource.ResourceManager;
import de.myreality.dev.chronos.slick.ColorLoader;
import de.myreality.dev.chronos.slick.ImageLoader;
import de.myreality.dev.chronos.slick.SlickComponent;

public class SwingGame extends BasicGame {
	
	private TileWorld world;
	
	private Torch cursorTorch;

	protected SwingGame() {
		super("Swing Game");
	}
	
	public TileWorld getWorld() {
		return world;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		world.render(gc, null, g);
		g.setColor(Color.white);
		g.setColor(Color.green);
		g.drawString("Render Objects: " + world.getRenderObjects().size(), 10, 30);
		g.setColor(Color.cyan);
		g.drawString("Active Lights: " + world.getActiveLightSources().size(), 10, 50);
		g.setColor(Color.white);
		g.drawString("Camera Scale: " + world.getCamera().getScale() * 100 + "%", 10, 70);
		
		int worldX = (int) (world.getCamera().getGlobalX() + gc.getInput().getMouseX());
		int worldY = (int) (world.getCamera().getGlobalY() + gc.getInput().getMouseY());
		g.drawString("World position: " + worldX + "," + worldY, 10, 90);
		g.drawString("Torch Center: " + cursorTorch.getGlobalCenterX() + " | " + cursorTorch.getGlobalCenterY(), 300, 100);
		g.setColor(Color.gray);
		g.drawString("World: " + world.getId() + ", Size: " + 
		  world.getWidth() + "x" + world.getHeight(), 10, gc.getHeight() - 30);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
		// Load the resources
		ResourceManager manager = ResourceManager.getInstance();
		manager.addResourceLoader(ImageLoader.getInstance());
		manager.addResourceLoader(ColorLoader.getInstance());
		manager.fromXML("wizardgame.xml");
				
		CitySetting setting = new CitySetting();
		setting.setWidth(10000);
		setting.setHeight(10000);
		
		world = new TileWorld("TileWorld", gc, setting);
		
		Camera camera = world.getCamera();
		
		camera.addComponent(new WorldBoundComponent(world));
		camera.addComponent(new CameraMovementComponent());
		
		cursorTorch = new Torch(0, 0, world);
		world.addWorldEntity(cursorTorch);
		
		// Add some houses
		for (int i = 0; i < 300; i++) {
			House house = new House(world);
			house.setGlobalPosition((float)(Math.random() * world.getWidth()), (float)(Math.random() * world.getHeight()));
			world.addWorldEntity(house);
			Light light = new Light((int) (Math.random() * world.getWidth()), (int) (Math.random() * world.getHeight()), 650, new Color(50, 120, 255), world);
			world.addWorldEntity(light);
		}
	}
	
	
	class CameraMovementComponent extends SlickComponent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CameraMovementComponent() {
			super();
		}

		@Override
		public void update(GameContainer gc, StateBasedGame sbg, int delta) {
			super.update(gc, sbg, delta);
			
			int padding = 80;
			
			if (getOwner() instanceof Camera) {
				Camera camera = (Camera)getOwner();
				
				Input input = gc.getInput();
				
				float speed = 1.5f * delta;
				
				if (input.getMouseX() < padding || input.isKeyDown(Input.KEY_A)) {
					camera.setGlobalX(camera.getGlobalX()-speed);
				}
				
				if (input.getMouseY() < padding || input.isKeyDown(Input.KEY_W)) {
					camera.setGlobalY(camera.getGlobalY()-speed);
				}
				
				if (input.isKeyDown(Input.KEY_S) || input.getMouseY() > gc.getHeight() - padding &&
						input.getMouseY() < gc.getHeight() - 10) {
					camera.setGlobalY(camera.getGlobalY()+speed);
				}
				
				if (input.isKeyDown(Input.KEY_D) || input.getMouseX() > gc.getWidth() - padding &&
					input.getMouseX() < gc.getWidth() - 10) {
					camera.setGlobalX(camera.getGlobalX()+speed);
				}
			}
		}	
		
		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		world.update(gc, null, delta);
		Camera camera = world.getCamera();
		Input input = gc.getInput();
		cursorTorch.setGlobalCenterX(input.getMouseX() + camera.getGlobalX());
		cursorTorch.setGlobalCenterY(input.getMouseY() + camera.getGlobalY());
	}

}
