package de.myreality.dev.slick.spaceship;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.SharedDrawable;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import de.myreality.dev.chronos.util.Point2f;
import de.myreality.dev.chronos.util.Vector2f;

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
	
	private Image background;

	private SharedDrawable sharedDrawable;
	
	public SpaceShipGame() {
		super("Slick2D - Space Ship Generation");
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		
		background = new Image("res/backgrounds/space-far.png");
		ships = new ArrayList<SpaceShip>();
		generator = new SessionIdentifierGenerator();
		shipFactory = new BasicSpaceShipFactory(generator.nextSessionId());
		Input input = gc.getInput();
		currentSelection = shipFactory.getNewSpaceShip(input.getMouseX(), input.getMouseY());
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		background.draw(0, 0, gc.getWidth(), gc.getHeight());
		for (SpaceShip ship : ships) {
			ship.render(gc, null, g);
		}
		currentSelection.render(gc, null, g);
		g.setColor(Color.green);
		g.drawString("Current Seed: " + shipFactory.getSeed(), 10, 30);
		g.setColor(Color.magenta);
		g.drawString("Render objects: " + ships.size(), 10, 50);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {		
		
		final Input input = gc.getInput();
		
		for (SpaceShip ship : ships) {
			ship.update(gc, null, delta);
			Vector2f direction = new Vector2f(ship.getGlobalCenterPosition(), new Point2f(input.getMouseX(), input.getMouseY()));
			
			if (input.getMouseY() < ship.getGlobalCenterY()) {
				ship.setRotation(-direction.getAngle() -90);
			} else {
				ship.setRotation(direction.getAngle() - 90);
			}
		}

		currentSelection.setGlobalCenterPosition(input.getMouseX(), input.getMouseY());
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			addRandomShip(input.getMouseX(), input.getMouseY());
		}
		
		if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON )) {
			//Thread thread = new Thread(new Runnable() {

			//	@Override
			//	public void run() {

					//for (int i = 0; i < 50; ++i) {
						shipFactory.setSeed(generator.nextSessionId());
						currentSelection = shipFactory.getNewSpaceShip(input.getMouseX(), input.getMouseY());
					//}
			//	}
			//	
			//});
			//thread.start();			
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
	    return new BigInteger(128, random).toString(subRandom.nextInt(200));
	  }

	}
}
