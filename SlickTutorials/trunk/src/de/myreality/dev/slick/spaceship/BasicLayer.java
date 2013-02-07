package de.myreality.dev.slick.spaceship;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import de.myreality.dev.chronos.util.Point2f;

/**
 * 
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 *
 */
public abstract class BasicLayer implements ShipLayer {
	
	private Point2f[] edges;
	
	private SpaceShipFactory factory;
	
	private static Image gradientTexture;
	
	private int textureWidth, textureHeight;
	
	private int textureX, textureY;
	
	static {
		try {
			// Load the gradient
			gradientTexture = new Image("res/black-gradient.png");
		} catch (SlickException e) {
			Log.error("Can't load the gradient texture for the spaceship factory.");
		}
	}
	
	public BasicLayer(SpaceShipFactory factory) {
		this.factory = factory;
	}

	@Override
	public Point2f[] getEdges() {
		return edges;
	}
	

	@Override
	public SpaceShipFactory getFactory() {
		return factory;
	}

	@Override
	public Image build(int width, int height, ShipLayer bottom, Color color) throws SlickException {

		// Initialize the base texture
		Image layerTexture = Image.createOffscreenImage(width, height, Image.FILTER_NEAREST);
		
		// Draw stuff on base texture
		Graphics g = layerTexture.getGraphics();
		g.setColor(color);
		draw(g, width, height, bottom);
		g.flush();
		
		// Shading via alpha mapping
		if (isShadingEnabled()) {
			layerTexture = createGradientLayer(layerTexture, color);
		}
		
		return layerTexture;
	}
	
	
	private Image createGradientLayer(Image texture, Color color) throws SlickException {
		
		Image result = Image.createOffscreenImage(texture.getWidth(), texture.getHeight(), Image.FILTER_NEAREST);
		Graphics g = result.getGraphics();
		Graphics.setCurrent(g);
		g.setDrawMode(Graphics.MODE_ADD_ALPHA);
		texture.draw(0, 0, result.getWidth(), result.getHeight());
		g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
		
		int width = result.getWidth();
		int height = result.getHeight();
		
		if (width > height) {
			gradientTexture.draw(0, -(width - height) / 2, width, width, color);
		} else {
			gradientTexture.draw(-(height - width) / 2, 0, height, height, color);
		}
				
		g.flush();
		return result;
	}	
	

	@Override
	public Point2f[] calculateTotalEdges(List<ShipLayer> otherLayers) {
		return null;
	}


	/* (non-Javadoc)
	 * @see de.myreality.dev.slick.spaceship.ShipLayer#getTextureWidth()
	 */
	@Override
	public int getTextureWidth() {
		return textureWidth;
	}


	/* (non-Javadoc)
	 * @see de.myreality.dev.slick.spaceship.ShipLayer#getTextureHeight()
	 */
	@Override
	public int getTextureHeight() {
		return textureHeight;
	}
	
	
	protected void setTextureWidth(int width) {
		this.textureWidth = width;
	}
	
	protected void setTextureHeight(int height) {
		this.textureHeight = height;
	}


	protected abstract void draw(Graphics g, int totalWidth, int totalHeight, ShipLayer bottom);

	protected void alignTextureSize(int newWidth, int newHeight) {
		if (newWidth > getTextureWidth()) {
			setTextureWidth(newWidth);
		}
		
		if (newHeight > getTextureHeight()) {
			setTextureWidth(newHeight);
		}
	}

	/* (non-Javadoc)
	 * @see de.myreality.dev.slick.spaceship.ShipLayer#getTextureX()
	 */
	@Override
	public int getTextureX() {
		return textureX;
	}

	/* (non-Javadoc)
	 * @see de.myreality.dev.slick.spaceship.ShipLayer#getTextureY()
	 */
	@Override
	public int getTextureY() {
		return textureY;
	}
	
	protected void setTextureX(int x) {
		this.textureX = x;
	}
	
	protected void setTextureY(int y) {
		this.textureY = y;
	}
	
	
}
