package de.myreality.dev.slick.lightning.wizardgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public interface LightingTarget {

	public Image getSprite();
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g);
}
