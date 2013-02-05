package de.myreality.dev.slick.chunksystem;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Basic test for a basic chunk system
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 *
 */
public class ChunkTest extends BasicGame {

	public ChunkTest(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {

	}

	@Override
	public void init(GameContainer gc) throws SlickException {

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new ChunkTest("Slick2D: Chunk Test"));
		game.start();
	}

}
