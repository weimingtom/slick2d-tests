package de.myreality.dev.slick.spaceship;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

/**
 * Factory that creates layer based space ships from a
 * given seed
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @version 1.0
 * @since 1.0
 */
public abstract class BasicSpaceShipFactory implements SpaceShipFactory,
													   Comparator<ShipLayer> {
	
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
	
	/* ====== STATIC DEFINITIONS ====== */

	/*// Gradient texture
	private static Image gradientTexture;
	
	
	
	
	static {
		try {
			// Load the gradient
			gradientTexture = new Image("res/black-gradient.png");
		} catch (SlickException e) {
			Log.error("Can't load the gradient texture for the spaceship factory.");
		}
	}*/
	
	
	
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
			SpaceShip ship = new SpaceShip(seed, shipSprite, null, null, null);
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
		// Load a new texture
		Image texture = Image.createOffscreenImage(getTextureWidth(seed), 
												   getTextureHeight(seed));
		
		// Init the layers
		Graphics g = texture.getGraphics();		
		List<ShipLayer> layers = getLayers();
		Collections.sort(layers, this);
		ShipLayer lastLayer = null;
		
		// Draw on the texture
		for (ShipLayer layer : layers) {
			layer.draw(seed, g, lastLayer);
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



	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(ShipLayer layer1, ShipLayer layer2) {
		if (layer1.getID() > layer2.getID()) {
			return 1;
		} else if (layer1.getID() < layer2.getID()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	
	
	/* ======= INTERFACE METHODS ====== */
	
	
}
