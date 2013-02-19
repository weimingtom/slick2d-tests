package de.myreality.dev.slick.spaceship.layers;

import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import de.myreality.dev.chronos.util.Point2f;
import de.myreality.dev.slick.spaceship.BasicLayer;
import de.myreality.dev.slick.spaceship.ShipLayer;
import de.myreality.dev.slick.spaceship.SpaceShipFactory;

public class BodyLayer extends BasicLayer {
	
	private int additionalHash;
	
	private static final int DEFAULT_ADDITIONAL_HASH     = 1;
	
	private static final int VALUE_EXPONENT              = 2;
	
	private static final float SHIFTING_MIN              = 2.1f;
	
	private static final float SHIFTING_MAX              = 2.5f;
	
	private static final float MIN_FIRST_WIDTH_DIVIDER   = 2f;
	
	private static final float MAX_FIRST_WIDTH_DIVIDER   = 2.5f;
	
	private static final float MIN_SECOND_WIDTH_DIVIDER  = 1.1f;
	
	private static final float MAX_SECOND_WIDTH_DIVIDER  = 1.2f;
	
	private static final float MIN_FIRST_HEIGHT_DIVIDER  = 1.1f;
	
	private static final float MAX_FIRST_HEIGHT_DIVIDER  = 1.2f;
	
	private static final float MIN_SECOND_HEIGHT_DIVIDER = 2.2f;
	
	private static final float MAX_SECOND_HEIGHT_DIVIDER = 2.6f;
	
	public BodyLayer(SpaceShipFactory factory, int additionalHash) {
		super(factory);
		this.additionalHash = additionalHash;
	}
	
	public BodyLayer(SpaceShipFactory factory) {
		this(factory, DEFAULT_ADDITIONAL_HASH);
	}

	@Override
	public Point2f[] getWeaponPoints() {
		return null;
	}

	@Override
	public Point2f[] getBoostPoints() {
		return null;
	}

	@Override
	protected void draw(Graphics g, int totalWidth, int totalHeight, List<ShipLayer> otherLayers) {		
		Rectangle posSegment1 = drawFirstSegment(g, totalWidth, totalHeight);
		Rectangle posSegment2 = drawSecondSegment(g, totalWidth, totalHeight);
		
		// Post calculation of the element
		calculateTexturePosition(posSegment1, posSegment2);
		correctTextureSize(posSegment1, posSegment2, totalWidth, totalHeight);
	}

	@Override
	public boolean isShadingEnabled() {
		return true;
	}
	
	private float getValue(float min, float max) {
		float difference = (max - min) / 2.0f;
		return (float) (difference * Math.cos(Math.pow(getFactory().getSeed().hashCode() * additionalHash, VALUE_EXPONENT))+ max - difference);
	}
	
	private Point2f drawCentered(Graphics g, int xCenter, int yCenter, int width, int height) {
		int x = xCenter - width / 2;
		int y = yCenter - height / 2;
		g.fillRect(x, y, width, height);
		
		return new Point2f(x, y);
	}
	
	private Rectangle drawFirstSegment(Graphics g, int totalWidth, int totalHeight) {
		
		float shifting = getValue(SHIFTING_MIN, SHIFTING_MAX);
		int x = totalWidth / 2;
		int y = (int) getValue(totalHeight / shifting, totalHeight - totalHeight / shifting);
		int width = (int) getValue(totalWidth / MIN_FIRST_WIDTH_DIVIDER, totalWidth / MAX_FIRST_WIDTH_DIVIDER);
		int height = (int) getValue(totalHeight / MIN_FIRST_HEIGHT_DIVIDER, totalHeight / MAX_FIRST_HEIGHT_DIVIDER);
		
		Point2f point = drawCentered(g, x, y, width, height);
		alignTextureSize(width, height);
		return new Rectangle(point.x, point.y, width, height);
	}
	
	private Rectangle drawSecondSegment(Graphics g, int totalWidth, int totalHeight) {
		
		float shifting = getValue(1.0f, 1.5f);
		int x = totalWidth / 2;
		int y = (int) getValue(totalHeight / shifting, totalHeight - totalHeight / shifting);
		int width = (int) getValue(totalWidth / MIN_SECOND_WIDTH_DIVIDER, totalWidth / MAX_SECOND_WIDTH_DIVIDER);
		int height = (int) getValue(totalHeight / MIN_SECOND_HEIGHT_DIVIDER, totalHeight / MAX_SECOND_HEIGHT_DIVIDER);
		
		Point2f point = drawCentered(g, x, y, width, height);
		alignTextureSize(width, height);
		return new Rectangle(point.x, point.y, width, height);
	}
	
	private void correctTextureSize(Rectangle segment1, Rectangle segment2, int totalWidth, int totalHeight) {
		
		float segmentTop = 0.0f;
		float segmentBottom = 0.0f;
		if (segment1.getY() > segment2.getY()) {
			segmentTop = segment2.getY();
			segmentBottom = segment1.getY() + segment1.getHeight();
		} else {
			
			segmentTop = segment1.getY();
			segmentBottom = segment2.getY() + segment2.getHeight();

		}		
		
		if (segmentTop < 0) {
			segmentTop *= -1;
		}
		
		int newHeight = (int) (segmentBottom + segmentTop);
		
		if (newHeight > getTextureHeight()) {
			setTextureHeight(newHeight);
		}
		
		if (getTextureHeight() > totalHeight) {
			setTextureHeight(totalHeight);
		}
	}
	
	private void calculateTexturePosition(Rectangle segment1, Rectangle segment2) {
		if (segment1.getX() < segment2.getX()) {
			setTextureX((int) segment1.getX());
		} else {
			setTextureX((int) segment2.getX());
		}
		if (segment1.getY() < segment2.getY()) {
			setTextureY((int) segment1.getY());
		} else {
			setTextureY((int) segment2.getY());
		}
	}
}
