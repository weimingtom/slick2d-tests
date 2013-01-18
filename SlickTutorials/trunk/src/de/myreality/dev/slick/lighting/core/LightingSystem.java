package de.myreality.dev.slick.lighting.core;

import org.newdawn.slick.Color;

/**
 * Renderer in order to render lights on objects
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface LightingSystem {

	/**
	 * Calculates lighting for the given object
	 */
	void render(LightingTarget target, Color ambientColor);
}
