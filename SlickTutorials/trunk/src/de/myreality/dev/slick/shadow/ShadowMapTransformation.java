package de.myreality.dev.slick.shadow;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class ShadowMapTransformation extends BasicGame {
	
	private Image shadowImage;
	
	public ShadowMapTransformation() {
		super("Slick Tutorials - Shadow Map Transformation");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		Input input = gc.getInput();
		g.setAntiAlias(true);
		g.setColor(Color.white);
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		
		shadowImage.setCenterOfRotation(0, 0);

		float x1 = 100;
		float y1 = 100;
		float x2 = input.getMouseX();
		float y2 = input.getMouseY();
		float x3 = 200;
		float y3 = 200;
		float x4 = 200;
		float y4 = 100;
		
		shadowImage.drawWarped(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		shadowImage = new Image("res/wall.png");
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new ShadowMapTransformation());
		game.setDisplayMode(800, 600, false);
		game.start();
	}

}
