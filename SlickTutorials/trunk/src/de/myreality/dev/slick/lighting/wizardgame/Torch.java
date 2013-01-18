package de.myreality.dev.slick.lighting.wizardgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.resource.Resource;
import de.myreality.dev.chronos.resource.ResourceManager;

public class Torch extends Light {
	
	private float time;
	
	private Resource<Color> lowerColor, upperColor;
	
	private float factor;
	
	private float originalSize;

	public Torch(int x, int y, TileWorld world) {
		super(x, y, 500, Color.white, world);
		ResourceManager manager = ResourceManager.getInstance();
		time = 0.0f;
		lowerColor = manager.getResource("TORCH1", Color.class);
		upperColor = manager.getResource("TORCH2", Color.class);
		factor = 0.5f;
		originalSize = getSize();
	}
	
	
	
	@Override
	public void setSize(int size) {
		super.setSize(size);
	}

	public void setStartSize(int size) {
		originalSize = size;
		setSize(size);
	}

	public void setLowerColor(Resource<Color> color) {
		lowerColor = color;
	}
	
	public void setUpperColor(Resource<Color> color) {
		upperColor = color;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		super.update(gc, sb, delta);
		// Set the size
		time += Math.random() * (delta * 100);
		setSize((int)(originalSize + 1f + 8.5f*(float)Math.sin(time / 3000.0f)));
		
		// Increase or decrease the factor by an random amount
		if (Math.random() * 100 < 50) {
			factor += 0.02f;
		} else {
			factor -= 0.02f;
		}
		
		if (factor < 0) {
			factor = 0;
		}
		
		if (factor > 1) {
			factor = 1;
		}
		
		setColor(blend(lowerColor.get().r, 
							   lowerColor.get().g,
							   lowerColor.get().b,
							   upperColor.get().r,
							   upperColor.get().g,
							   upperColor.get().b,
							   factor));		
	}
	
	
	

}
