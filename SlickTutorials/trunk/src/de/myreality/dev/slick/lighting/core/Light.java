package de.myreality.dev.slick.lighting.core;

import de.myreality.dev.chronos.slick.SlickEntity;
import de.myreality.dev.chronos.util.Point2f;

/**
 * Light class for dynamic or alpha lighting
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Light extends SlickEntity {

	// Version id for serialization
	private static final long serialVersionUID = -7459101510322129684L;
	
	/**
	 * Creates a new light object at the given position
	 * 
	 * @param position position
	 */
	public Light(Point2f position) {
		setGlobalCenterPosition(position.x, position.y);	
	}
	
	
	/**
	 * Creates a new light object at the given position
	 * with the given radius
	 * 
	 * @param position position
	 * @param radius radius of the light
	 */
	public Light(Point2f position, int radius) {		
		this(position);
		setRadius(radius);
		
		System.out.println(position.x + "|" + position.y + "  " + getWidth() + "|" + getHeight() + "  " + getGlobalCenterX() + "|" + getGlobalCenterY());
	}

	
	/**
	 * Width and height should be always equal
	 */
	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		if (getHeight() != width) {
			setHeight(width);
		}
	}


	/**
	 * Width and height should be always equal
	 */
	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		if (getWidth() != height) {
			setWidth(height);
		}
	}
	
	
	
	/**
	 * Returns the radius
	 */
	public int getRadius() {
		return getWidth();
	}
	
	
	public void setRadius(int radius) {
		setWidth(radius);
	}
	
}
