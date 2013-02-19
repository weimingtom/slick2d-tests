package de.myreality.dev.slick.spaceship.layers;

import java.util.List;

import org.newdawn.slick.Graphics;

import de.myreality.dev.chronos.util.Point2f;
import de.myreality.dev.slick.spaceship.BasicLayer;
import de.myreality.dev.slick.spaceship.ShipLayer;
import de.myreality.dev.slick.spaceship.SpaceShipFactory;

public class StripeLayer extends BasicLayer {

	public StripeLayer(SpaceShipFactory factory) {
		super(factory);
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
	public boolean isShadingEnabled() {
		return false;
	}

	@Override
	protected void draw(Graphics g, int totalWidth, int totalHeight,
			List<ShipLayer> otherLayers) {
		int padding = 500;
		ShipLayer bottom = getBiggestLayer(otherLayers);
		//g.fillRect(bottom.getTextureX() + padding, bottom.getTextureY() + padding, bottom.getTextureWidth() - padding * 2, bottom.getTextureHeight() - padding * 2);
	}

}
