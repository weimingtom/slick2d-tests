package de.myreality.dev.slick.spaceship;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import de.myreality.dev.chronos.util.Point2f;

/**
 * Factory that creates layer based space ships from a
 * given seed
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @version 1.0
 * @since 1.0
 */
public abstract class BasicSpaceShipFactory implements SpaceShipFactory {
	
	/* ====== DEFINITIONS ====== */
	
	
	// Seed for ship generation
	private String seed;	
	
	// Current ship sprite that the seed represents
	private Image shipSprite;
	
	// Maximum width in pixels
	private int maxWidth;
	
	// Maximum height in pixels
	private int maxHeight;
	
	// Minimum width in pixels
	private int minWidth;
	
	// Minimum height in pixels
	private int minHeight;
	
	
	/* ====== CONSTRUCTORS ====== */
	
	/**
	 * Constructor that calculates the texture
	 * 
	 * @param seed game seed
	 */
	public BasicSpaceShipFactory(String seed) {
		setSeed(seed);
	}
	
	
	
	/* ====== GETTERS ====== */
	
	

	@Override
	public SpaceShip getNewSpaceShip(float x, float y) {
		
		if (shipSprite != null) {
			SpaceShip ship = new SpaceShip(seed, shipSprite, getEdges(), getWeaponPoints(), getBoostPoints());
			ship.setBounds(x - (shipSprite.getWidth() / 2), y - (shipSprite.getHeight() / 2), shipSprite.getWidth(), shipSprite.getHeight());
			return ship;
		}
		return null;
	}	
	
	
	/**
	 * @return the current game seed
	 */
	@Override
	public String getSeed() {
		return seed;
	}
	
	
	/**
	 * @return the maxWidth
	 */
	public int getMaxWidth() {
		return maxWidth;
	}



	/**
	 * @return the maxHeight
	 */
	public int getMaxHeight() {
		return maxHeight;
	}



	/**
	 * @return the minWidth
	 */
	public int getMinWidth() {
		return minWidth;
	}



	/**
	 * @return the minHeight
	 */
	public int getMinHeight() {
		return minHeight;
	}
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see de.myreality.dev.slick.spaceship.SpaceShipFactory#getSpaceShipWidth()
	 */
	@Override
	public int getSpaceShipWidth() {
		return getTextureWidth(seed);
	}



	/* (non-Javadoc)
	 * @see de.myreality.dev.slick.spaceship.SpaceShipFactory#getSpaceShipHeight()
	 */
	@Override
	public int getSpaceShipHeight() {
		return getTextureHeight(seed);
	}



	/* (non-Javadoc)
	 * @see de.myreality.dev.slick.spaceship.SpaceShipFactory#getEdges()
	 */
	@Override
	public Point2f[] getEdges() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see de.myreality.dev.slick.spaceship.SpaceShipFactory#getWeaponPoints()
	 */
	@Override
	public Point2f[] getWeaponPoints() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see de.myreality.dev.slick.spaceship.SpaceShipFactory#getBoostPoints()
	 */
	@Override
	public Point2f[] getBoostPoints() {
		// TODO Auto-generated method stub
		return null;
	}


	
	

	
	/* ====== SETTERS ====== */


	@Override
	public void setSeed(String seed) {
		this.seed = seed;
		try {
			calculateSprite();
		} catch (SlickException e) {
			Log.error("Can't create the ship sprite: " + e.getMessage());
		}
	}

	

	/**
	 * @param maxWidth the maxWidth to set
	 */
	protected void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}



	/**
	 * @param maxHeight the maxHeight to set
	 */
	protected void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}



	/**
	 * @param minWidth the minWidth to set
	 */
	protected void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}



	/**
	 * @param minHeight the minHeight to set
	 */
	protected void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}
	
	
	
	/* ====== PRIVATE METHODS ====== */
	
	
	
	private Image generateTexture() throws SlickException {
		
		int textureWidth = getTextureWidth(seed);
		int textureHeight = getTextureHeight(seed);
		
		// Load a new texture
		Image texture = Image.createOffscreenImage(textureWidth, textureHeight);
		
		// Init the layers
		Graphics g = texture.getGraphics();		
		List<ShipLayer> layers = getLayers();
		ShipLayer lastLayer = null;		
		Image layerTexture = null;
		
		// Spaceship color
		int colorValue = getColorValue(seed);
		Color color = new Color(colorValue, colorValue, colorValue);
		
		// Draw on the texture
		for (int i = 0; i < layers.size(); ++i) {
			ShipLayer layer = layers.get(i);
			// Each layer has to know all bottom layers
			layerTexture = layer.build(textureWidth, textureHeight, lastLayer, color);			
			g.drawImage(layerTexture, 0, 0, color);			
			lastLayer = layer;
		}
		
		g.flush();		
		
		return texture;
	}
	
	private void calculateSprite() throws SlickException {
		shipSprite = generateTexture();
	}
	
	
	
	
	/* ====== ABSTRACT METHODS ====== */
	
	protected abstract int getTextureWidth(String seed);
	
	protected abstract int getTextureHeight(String seed);
	
	protected abstract int getColorValue(String seed);
	
	protected abstract List<ShipLayer> getLayers();	
	
}
