package de.myreality.dev.slick.spaceship;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.myreality.dev.chronos.util.Point2f;


/**
 * Contains data about a single layer and calculates it
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @version 1.0
 * @since 1.0
 */
public interface ShipLayer {
	
	int getTextureWidth();
	
	int getTextureHeight();
	
	int getTextureX();
	
	int getTextureY();
	
	Point2f[] getEdges();
	
	SpaceShipFactory getFactory();
	
	Point2f[] getWeaponPoints();
	
	Point2f[] getBoostPoints();
	
	boolean isShadingEnabled();
	
	Point2f[] calculateTotalEdges(List<ShipLayer> otherLayers);
	
	Image build(int width, int height, ShipLayer bottom, Color color) throws SlickException;
}
