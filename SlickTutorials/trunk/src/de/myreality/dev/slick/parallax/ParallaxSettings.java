package de.myreality.dev.slick.parallax;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 * Implementation of ParallaxSettings
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 *
 */
public class ParallaxSettings {
	
	// Image tile
	private Image tile;
	
	// Distance of the layer
	private float distance;
	
	// Target tile width
	private int width;
	
	// Target tile height
	private int height;
	
	// Velocity of the layer
	private Vector2f velocity;
	
	// Color filter (default is white)
	private Color filter;
	
	/**
	 * Constructor of the setting class
	 * 
	 * @param tile Tile image
	 * @param distance Distance of the layer
	 */
	public ParallaxSettings(Image tile, float distance) {
		this.tile = tile;
		width = tile.getWidth();
		height = tile.getHeight();
		this.distance = distance;
		velocity = new Vector2f();
		filter = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public Image getTileImage() {
		return tile;
	}

	public ParallaxSettings setTileImage(Image tile) {
		this.tile = tile;
		return this;
	}

	public float getDistance() {
		return distance;
	}

	public ParallaxSettings setDistance(float distance) {
		this.distance = distance;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public ParallaxSettings setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public ParallaxSettings setHeight(int height) {
		this.height = height;
		return this;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public ParallaxSettings setVelocity(Vector2f vector2f) {
		this.velocity = vector2f;
		return this;
	}

	public Color getFilter() {
		return filter;
	}

	public ParallaxSettings setFilter(Color filter) {
		this.filter = filter;
		return this;
	}
	
	
	
}