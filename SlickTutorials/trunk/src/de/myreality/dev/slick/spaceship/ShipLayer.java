package de.myreality.dev.slick.spaceship;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import de.myreality.dev.chronos.util.Point2f;


/**
 * Contains data about a single layer and calculates it
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @version 1.0
 * @since 1.0
 */
public interface ShipLayer {

	int getWidth();
	
	int getHeight();
	
	Image getTexture();
	
	Point2f[] getEdges();
	
	void draw(String seed, Graphics g, ShipLayer last);
	
	int getID();
	
	int getPadding();
}
