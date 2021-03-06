package de.myreality.dev.slick.parallax;

import org.newdawn.slick.GameContainer;

import de.myreality.dev.chronos.slick.SlickEntity;

public class Camera extends SlickEntity {

	private static final long serialVersionUID = 1841379845703100912L;
	
	private GameContainer container;
	
	public Camera(GameContainer gc) {
		this.container = gc;
		setDimension(gc.getWidth(), gc.getHeight(), 0);
	}

	@Override
	public int getWidth() {
		if (container != null) {
			return (int) (container.getWidth() / getScale());
		} else {
			return super.getWidth();
		}
	}

	@Override
	public int getHeight() {
		if (container != null) {
			return (int) (container.getHeight() / getScale());
		} else {
			return super.getHeight();
		}
	}
}
