package de.myreality.dev.slick.spaceship.layers;

import java.util.List;

import org.newdawn.slick.Graphics;

import de.myreality.dev.chronos.util.Point2f;
import de.myreality.dev.slick.spaceship.BasicLayer;
import de.myreality.dev.slick.spaceship.ShipLayer;
import de.myreality.dev.slick.spaceship.SpaceShipFactory;

public class BodyLayer extends BasicLayer {
	
	private int additionalHash;
	
	public BodyLayer(SpaceShipFactory factory, int additionalHash) {
		super(factory);
		this.additionalHash = additionalHash;
	}
	
	public BodyLayer(SpaceShipFactory factory) {
		this(factory, 1);
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
	protected void draw(Graphics g, int totalWidth, int totalHeight, ShipLayer lastLayer) {		
		Point2f posSegment1 = drawFirstSegment(g, totalWidth, totalHeight);
		Point2f posSegment2 = drawSecondSegment(g, totalWidth, totalHeight);
		
		calculateTexturePosition(posSegment1, posSegment2);
	}

	@Override
	public boolean isShadingEnabled() {
		return true;
	}
	
	private float getValue(float min, float max) {
		float difference = max - min;
		return (float) (difference * Math.cos(Math.pow(getFactory().getSeed().hashCode() * additionalHash, 10))+ difference + min);
	}
	
	private void drawCentered(Graphics g, int xCenter, int yCenter, int width, int height) {
		g.fillRect(xCenter - width / 2, yCenter - height / 2, width, height);
	}
	
	private Point2f drawFirstSegment(Graphics g, int totalWidth, int totalHeight) {
		
		float shifting = getValue(2.0f, 2.5f);
		int x = totalWidth / 2;
		int y = (int) getValue(totalHeight / shifting, totalHeight - totalHeight / shifting);
		int width = (int) getValue(totalWidth / 2, totalWidth / 2.5f);
		int height = (int) getValue(totalHeight / 1.1f, totalHeight / 1.2f);
		
		drawCentered(g, x, y, width, height);
		
		alignTextureSize(width, height);
		
		return new Point2f(x, y);
	}
	
	private Point2f drawSecondSegment(Graphics g, int totalWidth, int totalHeight) {
		
		float shifting = getValue(1.0f, 1.5f);
		int x = totalWidth / 2;
		int y = (int) getValue(totalHeight / shifting, totalHeight - totalHeight / shifting);
		int width = (int) getValue(totalWidth / 1.1f, totalWidth / 1.4f);
		int height = (int) getValue(totalHeight / 2f, totalHeight / 2.5f);
		
		drawCentered(g, x, y, width, height);
		
		alignTextureSize(width, height);
		
		return new Point2f(x, y);
	}
	
	private void calculateTexturePosition(Point2f segment1, Point2f segment2) {
		setTextureX((int) segment2.x);
		setTextureY((int) segment1.y);
	}
}
