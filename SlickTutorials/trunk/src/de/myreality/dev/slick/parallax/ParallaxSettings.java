package de.myreality.dev.slick.parallax;

import org.newdawn.slick.Image;

import de.myreality.dev.chronos.toolkit.Vector2f;
import de.myreality.dev.chronos.toolkit.resource.Resource;
import de.myreality.dev.chronos.toolkit.resource.ResourceManager;

public class ParallaxSettings {
	
	private String tileID;
	
	private float distance;
	
	private int width;
	
	private int height;
	
	private Vector2f velocity;
	
	public ParallaxSettings(String tileID, float distance) {
		this.tileID = tileID;			
		Resource<Image> tile = ResourceManager.getInstance().getResource(tileID, Image.class);
		width = tile.get().getWidth();
		height = tile.get().getHeight();
		this.distance = distance;
		velocity = new Vector2f();
	}
	
	public ParallaxSettings(Image tile, float distance) {
		
	}

	public String getTileID() {
		return tileID;
	}

	public ParallaxSettings setTileID(String tileID) {
		this.tileID = tileID;
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

	public ParallaxSettings setVelocity(Vector2f velocity) {
		this.velocity = velocity;
		return this;
	}
	
	
	
}