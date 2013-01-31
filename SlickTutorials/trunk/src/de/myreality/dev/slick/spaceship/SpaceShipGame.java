package de.myreality.dev.slick.spaceship;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
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
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.opengl.shader.ShaderProgram;

import de.myreality.dev.chronos.resource.ResourceManager;
import de.myreality.dev.chronos.slick.ImageLoader;

/**
 * Test case for parallex scolling
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 */
public class SpaceShipGame extends BasicGame {
	
	private ArrayList<SpaceShip> ships;
	
	private SpaceShip currentSelection;
	
	private BasicSpaceShipFactory shipFactory;
	
	private SessionIdentifierGenerator generator;

	public SpaceShipGame() {
		super("Slick2D - Space Ship Generation");
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		ships = new ArrayList<SpaceShip>();
		generator = new SessionIdentifierGenerator();
		shipFactory = new BasicSpaceShipFactory(generator.nextSessionId());
		Input input = gc.getInput();
		currentSelection = shipFactory.getNewSpaceShip(input.getMouseX(), input.getMouseY());
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (SpaceShip ship : ships) {
			ship.render(gc, null, g);
		}
		currentSelection.render(gc, null, g);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		for (SpaceShip ship : ships) {
			ship.update(gc, null, delta);
		}
		
		Input input = gc.getInput();

		currentSelection.setGlobalCenterPosition(input.getMouseX(), input.getMouseY());
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			addRandomShip(input.getMouseX(), input.getMouseY());
		}
		
		if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON )) {
			shipFactory.setSeed(generator.nextSessionId());
			currentSelection = shipFactory.getNewSpaceShip(input.getMouseX(), input.getMouseY());
		}
	}
	
	private void addRandomShip(int x, int y) {
		SpaceShip ship = shipFactory.getNewSpaceShip(x, y);
		if (ship != null) {
			ships.add(ship);
		}
	}

	public static void main(String[] args) throws SlickException {

		AppGameContainer game = new AppGameContainer(new SpaceShipGame());
		//game.setVSync(true);
		game.setDisplayMode(800,600,false);
		game.setAlwaysRender(false);
		game.start();
	}
	
	class SessionIdentifierGenerator
	{

	  private SecureRandom random = new SecureRandom();
	  private Random subRandom = new Random();

	  public String nextSessionId()
	  {
	    return new BigInteger(subRandom.nextInt(200), random).toString(subRandom.nextInt(50));
	  }

	}
}
