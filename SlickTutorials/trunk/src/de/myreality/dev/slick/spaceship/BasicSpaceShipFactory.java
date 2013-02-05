package de.myreality.dev.slick.spaceship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BasicSpaceShipFactory implements SpaceShipFactory {
	
	private String seed;
	
	private Image shipSprite;
	
	private static final int MAX_SIZE = 100;
	private static final int MIN_SIZE = 30;
	private static final int AMPLITUDE = 10;
	private static final float SIZE_DIVIDER = 1.4f;
	private static Image radialGradient;
	
	static {
		try {
			radialGradient = new Image("res/black-gradient.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public BasicSpaceShipFactory(String seed) {
		setSeed(seed);
	}
	
	public String getSeed() {
		return seed;
	}
	
	private void calculateSprite() {
		shipSprite = generateShipTexture(seed.hashCode(), 10);
	}

	@Override
	public SpaceShip getNewSpaceShip(float x, float y) {
		
		if (shipSprite != null) {
			SpaceShip ship = new SpaceShip(seed, shipSprite, null, null, null);
			ship.setBounds(x - (shipSprite.getWidth() / 2), y - (shipSprite.getHeight() / 2), shipSprite.getWidth(), shipSprite.getHeight());
			return ship;
		}
		return null;
	}
	
	class ImageComperator implements Comparator<Image> {

		@Override
		public int compare(Image o1, Image o2) {
			int size1 = o1.getWidth() * o1.getHeight();
			int size2 = o2.getWidth() * o2.getHeight();
			
			if (size1 > size2) {
				return -1;
			} else if (size1 < size2) {
				return 1;
			} else {
				return 0;
			}
		}
		
	}
	
	private Image generateShipTexture(int hash, int layers) {
		
		int maxWidth = 0;
		int maxHeight = 0;
		ImageComperator imageComperator = new ImageComperator();
		ArrayList<Image> imageList = new ArrayList<Image>();
		for (int i = 0; i < layers; ++i) {
			Image generatedImage = generateImage(hash);
			if (generatedImage.getWidth() > maxWidth) {
				maxWidth = generatedImage.getWidth();
			}
			
			if (generatedImage.getHeight() > maxHeight) {
				maxHeight = generatedImage.getHeight();
			}
			imageList.add(generatedImage);
			hash /= 1.5;
		}
		Image resultImage = null;
		try {
			resultImage = Image.createOffscreenImage(maxWidth, maxHeight);
			Graphics g = resultImage.getGraphics();
			int centerX = maxWidth / 2;
			int centerY = maxHeight / 2;
			// Sort the list by size
			//Collections.sort(imageList, imageComperator);
			for (Image image : imageList) {
				g.drawImage(image, centerX - (image.getWidth() / 2), centerY - (image.getHeight() / 2));
			}
			g.flush();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return resultImage;
	}
	
	private Image generateImage(int hash) {
		int width = calculateWidth(hash);
		int height = calculateHeight(hash);
		Image texture = null;
		try {
			GLContext.useContext(this);
			texture = Image.createOffscreenImage(width, height, Image.FILTER_LINEAR);
			Graphics g = texture.getGraphics();
			Graphics.setCurrent(g);
			g.setAntiAlias(true);
			radialGradient.draw(-width, -height, width * 3, height * 3, getHashColor());
			g.flush();
		} catch (SlickException e) {
			e.printStackTrace();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texture;
		
	}
	
	public void setSeed(String seed) {
		this.seed = seed;
		calculateSprite();
	}
	
	private float calculateDifference(int min, int max) {
		return (float)(max - min) / 2;
	}
	
	private int calculateSize(int hash, int minSize, int maxSize, int amplitude) {
		return (int) (calculateDifference(minSize, maxSize) * Math.sin(amplitude * hash) + minSize + calculateDifference(minSize, maxSize));
	}
	
	private int calculateWidth(int hash) {
		int width = calculateSize(hash, MIN_SIZE, MAX_SIZE, AMPLITUDE);
		if (hash % 2 == 0) {
			int minWidth = 0;
			float sizeDividing = SIZE_DIVIDER;
			if (sizeDividing < 1) {
				sizeDividing = 1.0f;
			}
			int maxWidth = (int) (width / sizeDividing);
			width -= calculateSize(hash, minWidth, maxWidth, AMPLITUDE);
		}
		return width;
	}
	
	private Color getHashColor() {
		int grey = calculateSize(seed.hashCode(), 90, 150, AMPLITUDE / 2);
		return new Color(grey, grey, grey, 255);
	}
	
	private int calculateHeight(int hash) {
		int height = calculateSize(hash, MIN_SIZE, MAX_SIZE, AMPLITUDE);
		if (hash % 2 != 0) {
			int minHeight = 0;
			float sizeDividing = SIZE_DIVIDER;
			if (sizeDividing < 1) {
				sizeDividing = 1.0f;
			}
			int maxHeight = (int) (height / sizeDividing);
			height -= calculateSize(hash, minHeight, maxHeight, AMPLITUDE);
		}
		return height;
	}
}
