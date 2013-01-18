package de.myreality.dev.slick.lightning.wizardgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.models.Entity;

public class BumpMapRenderer implements Renderer {
	
	private TileWorld world;
	
	public BumpMapRenderer(TileWorld world) {
		this.world = world;
	}
	
	private void renderBackground(Image tile, Color color) {
		
		WorldSettings settings = world.getSettings();
		Camera camera = world.getCamera();
		
		int tileWidth = settings.getTileSizeX();
		int tileHeight = settings.getTileSizeY();
		int lastY = (int) (camera.getGlobalY() - camera.getGlobalY() % tileHeight);
		final int TILES_X = (int) (camera.getWidth() / tileWidth + 2);
		final int TILES_Y = (int) (camera.getHeight() / tileHeight + 2);

		for (int y = 0; y < TILES_Y; ++y) {
			int lastX = (int) (camera.getGlobalX() - camera.getGlobalX() % tileWidth);
			for (int x = 0; x < TILES_X; ++x) {
				tile.draw(lastX - (int)camera.getGlobalX(), lastY - (int)camera.getGlobalY(), tileWidth, tileHeight, color);
				lastX += tileWidth;
			}
			lastY += tileHeight;
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {

		WorldSettings settings = world.getSettings();
		
		// 1. Render Background
		renderBackground(settings.getBackgroundTile(), settings.getAmbientColor());
		
		
		// 2. Render Bump Map
		for (Entity entity : world.getActiveLightSources()) {	
			
			if (entity instanceof Light) {
				Light light = (Light)entity;
				light.render(gc, null, g);
				
				renderBackground(settings.getBumpTile(), light.getColor());
				g.clearClip();
			}
		}
		
		g.clearAlphaMap();
		g.setDrawMode(Graphics.MODE_NORMAL);
	}
}
