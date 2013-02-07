package de.myreality.dev.slick.spaceship;

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
	
	
	
	/**
	 * @return The current generation seed
	 */
	String getSeed();
}
