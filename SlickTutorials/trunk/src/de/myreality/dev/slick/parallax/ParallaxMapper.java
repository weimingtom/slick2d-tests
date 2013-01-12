package de.myreality.dev.slick.parallax;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.toolkit.slick.SlickEntity;

public class ParallaxMapper extends SlickEntity {
	
	private static final long serialVersionUID = 1L;

	private ArrayList<ParallaxMap> layers;
	
	public ParallaxMapper() {
		layers = new ArrayList<ParallaxMap>();
	}
	
	public void addLayer(ParallaxMap mapLayer) {
		layers.add(mapLayer);
		Collections.sort(layers);
	}
	
	public void addLayer(ParallaxSettings settings) {
		addLayer(new ParallaxMap(this, settings));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		super.update(gc, sbg, delta);
		for (ParallaxMap layer : layers) {
			layer.update(gc, sbg, delta);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		super.render(gc, sbg, g);
		for (ParallaxMap layer : layers) {
			layer.render(gc, sbg, g);
		}
	}
	
	

}
