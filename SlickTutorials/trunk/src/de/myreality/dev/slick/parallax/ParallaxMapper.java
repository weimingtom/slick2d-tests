package de.myreality.dev.slick.parallax;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import de.myreality.dev.chronos.toolkit.Vector2f;

public class ParallaxMapper {
	
	private ArrayList<ParallaxMap> layers;
	
	private Vector2f position;
	
	public ParallaxMapper() {
		layers = new ArrayList<ParallaxMap>();
		position = new Vector2f();
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public void addLayer(ParallaxMap mapLayer) {
		layers.add(mapLayer);
	}
	
	public void addLayer(ParallaxSettings settings) {
		layers.add(new ParallaxMap(this, settings));
	}

	public void update(GameContainer gc, int delta) {
		for (ParallaxMap layer : layers) {
			layer.update(gc, delta);
		}
	}
	
	public void render(GameContainer gc, Graphics g) {
		for (ParallaxMap layer : layers) {
			layer.render(gc, g);
		}
	}

}
