package de.myreality.dev.slick.lighting.core;

import org.newdawn.slick.Image;

import de.myreality.dev.chronos.models.Bounding;
import de.myreality.dev.chronos.util.Point2f;


/**
 * Lighting target interface for the LightingRenderer.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface LightingTarget extends Bounding {
	
	/**
	 * Returns the current sprite of the lighting target
	 */
	Image getSprite();
	
	
	void resetLighting();
}
