package de.myreality.dev.slick.parallax;

import org.newdawn.slick.GameContainer;

import de.myreality.dev.chronos.toolkit.slick.SlickEntity;

public class Camera extends SlickEntity {

	private static final long serialVersionUID = 1841379845703100912L;
	
	private GameContainer container;
	
	public Camera(GameContainer gc) {
		this.container = gc;
		setDimensions(gc.getWidth(), gc.getHeight(), 0);
	}

	@Override
	public int getWidth() {
		return (int) (container.getWidth() / getScale());
	}

	@Override
	public int getHeight() {
		return (int) (container.getHeight() / getScale());
	}
}
