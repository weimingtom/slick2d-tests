package de.myreality.dev.slick.lightning.wizardgame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import de.myreality.dev.chronos.toolkit.resource.ResourceManager;
import de.myreality.dev.chronos.toolkit.slick.ColorLoader;
import de.myreality.dev.chronos.toolkit.slick.ImageLoader;

/**
 * Example for rendering a wizard game that includes dynamic shadows,
 * dynamic lighting and bump mapping at the same time.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 */
public class WizardGame extends BasicGame {
	
	// Target render world
	private TileWorld world;
	
	private Wizard wizard;
	
	// Different world settings
	private WorldSettings citySetting, desertSetting, snowSetting;

	public WizardGame() {
		super("Slick Example - Wizard Game");
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		
		// Load the resources
		ResourceManager manager = ResourceManager.getInstance();
		manager.addResourceLoader(ImageLoader.getInstance());
		manager.addResourceLoader(ColorLoader.getInstance());
		manager.fromXML("wizardgame.xml");
		
		// Set up world settings
		citySetting = new CitySetting();
		citySetting.setWidth(10000)
		           .setHeight(10000);
		world = new TileWorld("WizardWorld", container, citySetting);
		
		// Add some houses
		for (int i = 0; i < 100; i++) {
			House house = new House(world);
			//house.setPosition(0f, 0f);
			house.setGlobalPosition((float)(Math.random() * world.getWidth()), (float)(Math.random() * world.getHeight()));
			world.addWorldEntity(house);
			Light light = new Light((int) (Math.random() * world.getWidth()), (int) (Math.random() * world.getHeight()), 650, new Color(50, 120, 255), world);
			world.addWorldEntity(light);
		}
		
		// Add the wizard
		wizard = new Wizard("Wizard", world);
		WizardMovingComponent moving = new WizardMovingComponent(wizard, world);
		wizard.addComponent(moving);
		wizard.setGlobalPosition(1000, 1000);
		wizard.addToWorld(world);
		
		// Add some more wizards
		for (int i = 0; i < 0; ++i) {
			Wizard tmp = new Wizard("Wizard", world);
			int randX = (int) (Math.random() * world.getWidth());
			int randY = (int) (Math.random() * world.getHeight());
			tmp.setGlobalPosition(randX, randY);
			tmp.addToWorld(world);
		}
		
		// Align the camera to the wizard
		Camera camera = world.getCamera();
		camera.attachTo(wizard);
		camera.setLocalX(wizard.getWidth() - camera.getWidth() / 2);
		camera.setLocalY(wizard.getHeight() - camera.getHeight() / 2);
		
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		Camera camera = world.getCamera();
		
		camera.setLocalX(wizard.getWidth() - camera.getWidth() / 2);
		camera.setLocalY(wizard.getHeight() - camera.getHeight() / 2);
		
		
		Input input = container.getInput();
		
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			container.exit();
		}
		
		world.update(container, null, delta);		
	}	

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		world.render(container, null, g);
		Camera camera = world.getCamera();
		g.setColor(Color.white);
		g.drawString("Camera-Position: " + (int)camera.getGlobalX() + " | " +  (int)camera.getGlobalY(), 10, 50);
		g.drawString("Wizard-Position: " + (int)wizard.getGlobalX() + " | " +  (int)wizard.getGlobalY(), 10, 30);
		g.drawString("Delta: " + (int)(wizard.getGlobalX() - camera.getGlobalX()) + " | " + (int)(wizard.getGlobalY() - camera.getGlobalY()) ,10, 70);
		//g.setColor(Color.black);
		//g.fillRect(camera.getGlobalX(), camera.getGlobalY(), camera.getWidth(), camera.getHeight());
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new WizardGame());
		game.setAlwaysRender(false);
		game.setVSync(true);
		game.setDisplayMode(800, 600, false);
		game.start();
	}

}
