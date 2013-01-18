package de.myreality.dev.slick.lightning.wizardgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.resource.Resource;
import de.myreality.dev.chronos.resource.ResourceManager;
import de.myreality.dev.chronos.slick.SlickEntity;
import de.myreality.dev.chronos.util.Vector3f;

public class Light extends SlickEntity {
	
	private Color color;
	
	private int size;
	
	private Resource<Image> lightMap;
	
	private TileWorld world;
	
	private ShadowMapCalculator shadowMapCalculator;

	public Light(int x, int y, int size, Color color, TileWorld world) {
		super(null);
		ResourceManager manager = ResourceManager.getInstance();
		lightMap = manager.getResource("LIGHTMAP", Image.class);
		this.color = color;
		this.size = size;
		setDimensions(size, size, 0);
		setGlobalPosition(new Vector3f(x, y, 0));
		this.world = world;
		shadowMapCalculator = new ShadowMapCalculator(world, lightMap.get());
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
		setDimensions(size, size, 0);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		super.render(gc, sbg, g);
		Camera camera = world.getCamera();
		
		g.setDrawMode(Graphics.MODE_ALPHA_MAP);
		int positionX = (int)(getGlobalX() - camera.getScale() - camera.getGlobalX());
		int positionY = (int)(getGlobalY() - camera.getScale() - camera.getGlobalY());
		
		Image calculatedMap = shadowMapCalculator.getCalculatedMap();
		calculatedMap.draw(positionX, positionY, (int)size * camera.getScale(), (int)size * camera.getScale(), getColor());
		g.setDrawMode(Graphics.MODE_ALPHA_BLEND);		
		g.setClip(positionX, positionY, (int)(size * camera.getScale()), (int)(size * camera.getScale()));
	}
	
	
	public Color blend(float r1, float g1, float b1, float r2, float g2, float b2, float ratio) {

		float ir = (float) 1.0 - ratio;

		return new Color((float)(r1 * ratio + r2 * ir), 
						 (float)(g1 * ratio + g2 * ir),
						 (float)(b1 * ratio + b2 * ir));          
	}
	
	
	private class ShadowMapCalculator {
	
		private Image lightMap;
		
		private TileWorld world;
		
		public ShadowMapCalculator(TileWorld world, Image lightMap) {
			this.world = world;
			this.lightMap = lightMap;
		}
		
		public Image getCalculatedMap() {
			Image result = lightMap.copy();
			
			return result;
		}
	}
	
	
	public boolean isOnScreen() {
		Camera camera = world.getCamera();
		
		float size = getSize() / 2;
		
		return (getGlobalX() + size >= camera.getGlobalX() && getGlobalY() + size > camera.getGlobalY() &&
			getGlobalX() - size <= camera.getGlobalX() + camera.getWidth() &&
			getGlobalY() - size <= camera.getGlobalY() + camera.getHeight());
	}
}
