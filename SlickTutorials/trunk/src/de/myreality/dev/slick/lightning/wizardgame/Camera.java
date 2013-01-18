package de.myreality.dev.slick.lightning.wizardgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.slick.SlickEntity;

public class Camera extends SlickEntity {

	public Camera(String id, GameContainer gc, TileWorld world) {
		super(null);
		setWidth(gc.getWidth());
		setHeight(gc.getHeight());
		addComponent(new WorldBoundComponent(world));
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		super.render(gc, sbg, g);
		g.setAntiAlias(true);
		g.scale(getScale(), getScale());
	}
	
	
}
