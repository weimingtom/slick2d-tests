package de.myreality.dev.slick.spaceship;

import de.myreality.dev.chronos.util.Point2f;

/**
 * Basic factory for creating space ships
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @version 1.0
 * @since 1.0
 */
public interface SpaceShipFactory {

	/**
	 * Generates a new space ship
	 * 
	 * @param x horizontal position on screen
	 * @param y vertical position on screen
	 * @return new spaceship object
	 */
	SpaceShip getNewSpaceShip(float x, float y);
	
	
	
	/**
	 * When setting up a new game seed, the internal
	 * ship texture will be reloaded
	 */
	void setSeed(String seed);
	
	
	int getSpaceShipWidth();
	
	int getSpaceShipHeight();
	
	
	
	/**
	 * @return The current generation seed
	 */
	String getSeed();
	
	
	Point2f[] getEdges();
	
	Point2f[] getWeaponPoints();
	
	Point2f[] getBoostPoints();
}
