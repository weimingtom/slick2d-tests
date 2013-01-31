package de.myreality.dev.slick.spaceship;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BasicSpaceShipFactory implements SpaceShipFactory {
	
	private String seed;
	
	private static final int MAX_SIZE = 180;
	private static final int MIN_SIZE = 20;
	private static final int AMPLITUDE = 800;
	private static final float SIZE_DIVIDER = 1.8f;
	
	public BasicSpaceShipFactory(String seed) {
		this.seed = seed;
	}

	@Override
	public SpaceShip getNewSpaceShip(float x, float y) {
		
		Image texture = generateShipTexture(seed.hashCode(), 10);

		System.out.println("Size: " +texture.getWidth() + "|" + texture.getHeight() + ", Seed: " + seed);
		
		if (texture != null) {
			SpaceShip ship = new SpaceShip(seed, texture, null, null, null);
			ship.setBounds(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), texture.getWidth(), texture.getHeight());
			return ship;
		}
		return null;
	}
	
	private Image generateShipTexture(int hash, int layers) {
		
		int maxWidth = 0;
		int maxHeight = 0;
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
			hash /= 3;
		}
		Image resultImage = null;
		try {
			resultImage = Image.createOffscreenImage(maxWidth, maxHeight);
			Graphics g = resultImage.getGraphics();
			int centerX = maxWidth / 2;
			int centerY = maxHeight / 2;
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
			texture = Image.createOffscreenImage(width, height);
			Graphics g = texture.getGraphics();
			g.setColor(getHashColor(hash));
			g.fillRect(0, 0, width, height);
			g.flush();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texture;
		
	}
	
	public void setSeed(String seed) {
		this.seed = seed;
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
	
	private Color getHashColor(int hash) {
		int grey = calculateSize(hash, 30, 80, AMPLITUDE / 2);
		return new Color(grey, grey, grey);
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
