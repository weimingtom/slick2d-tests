package de.myreality.dev.slick.lighting.wizardgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class WorldSettings {

	private Image backgroundTile;
	private Image bumpTile;
	private int width;
	private int height;
	private int tileSizeX;
	private int tileSizeY;
	private Color ambientColor;
	
	public WorldSettings() {
		// Default world settings
		width = 2000;
		height = 2000;
		tileSizeX = 32;
		tileSizeY = 32;
		try {
			backgroundTile = new Image(tileSizeX, tileSizeY);
			bumpTile = new Image(tileSizeX, tileSizeY);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	public Image getBackgroundTile() {
		return backgroundTile;
	}
	
	public WorldSettings setBackgroundTile(Image backgroundTile) {
		this.backgroundTile = backgroundTile;
		return this;
	}
	public Image getBumpTile() {
		return bumpTile;
	}
	public WorldSettings setBumpTile(Image bumpTile) {
		this.bumpTile = bumpTile;
		return this;
	}
	public int getWidth() {
		return width;
	}
	public WorldSettings setWidth(int width) {
		this.width = width;
		return this;
	}
	public int getHeight() {
		return height;
	}
	public WorldSettings setHeight(int height) {
		this.height = height;
		return this;
	}
	public int getTileSizeX() {
		return tileSizeX;
	}
	public WorldSettings setTileSizeX(int tileSizeX) {
		this.tileSizeX = tileSizeX;
		return this;
	}
	public int getTileSizeY() {
		return tileSizeY;
	}
	public WorldSettings setTileSizeY(int tileSizeY) {
		this.tileSizeY = tileSizeY;
		return this;
	}
	
	public WorldSettings setTileSize(int size) {
		return setTileSizeX(size).setTileSizeY(size);		
	}

	public Color getAmbientColor() {
		return ambientColor;
	}

	public WorldSettings setAmbientColor(Color ambientColor) {
		this.ambientColor = ambientColor;
		return this;
	}
	
}
