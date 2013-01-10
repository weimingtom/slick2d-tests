package de.myreality.dev.slick.parallax;

import java.util.ArrayList;

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
	}
	
	public void addLayer(ParallaxSettings settings) {
		layers.add(new ParallaxMap(this, settings));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		super.update(gc, sbg, delta);
		for (ParallaxMap layer : layers) {
			layer.update(gc, delta);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		super.render(gc, sbg, g);
		for (ParallaxMap layer : layers) {
			layer.render(gc, g);
		}
	}
	
	

}
