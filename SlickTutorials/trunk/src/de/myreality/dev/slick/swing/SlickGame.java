package de.myreality.dev.slick.swing;

import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SlickGame extends BasicGame {
	
	private Color color;
	
	private Random random;

	public SlickGame() {
		super("Sick Game");
		random = new Random();
	}
	
	public void changeColor() {
		color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(color);
		g.fillRect(0, 0, 100, 100);
    }

	@Override
	public void init(GameContainer g) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub

	}

}
