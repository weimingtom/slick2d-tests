package de.myreality.dev.slick.lightning.wizardgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface Renderer {

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g);
}
