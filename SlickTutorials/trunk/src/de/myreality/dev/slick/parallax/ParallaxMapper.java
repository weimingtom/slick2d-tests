package de.myreality.dev.slick.parallax;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.slick.SlickEntity;

/**
 * Mapper in order to manage all layers
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 */
public class ParallaxMapper extends SlickEntity {
	
	private static final long serialVersionUID = 1L;

	// Ordered layers
	private ArrayList<ParallaxLayer> layers;
	
	/**
	 * Basic Constructor
	 */
	public ParallaxMapper(int width, int height) {
		layers = new ArrayList<ParallaxLayer>();
		setDimensions(width, height, 0);
	}
	
	/**
	 * Add a new layer to the mapper
	 * 
	 * @param mapLayer target layer
	 */
	public void addLayer(ParallaxLayer mapLayer) {
		layers.add(mapLayer);
	}
	
	/**
	 * Add a new layer to the mapper with the given settings
	 * 
	 * @param settings Layer settings
	 */
	public void addLayer(ParallaxSettings settings) {
		addLayer(new ParallaxLayer(this, settings));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		super.update(gc, sbg, delta);
		
		for (ParallaxLayer layer : layers) {
			layer.update(gc, sbg, delta);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		super.render(gc, sbg, g);
		for (ParallaxLayer layer : layers) {
			layer.render(gc, sbg, g);
		}
	}
}
