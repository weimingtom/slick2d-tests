package de.myreality.dev.slick.chunksystem;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import de.myreality.dev.chronos.slick.SlickEntity;

/**
 * Manager to handle single chunks
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @version 1.0
 * @since 1.0
 */
public interface ChunkManager {
	
	void setSeed(String seed);
	
	String getSeed();
	
	void setRenderSize(int renderSize);
	
	void setCacheSize(int cacheSize);
	
	void setPreCacheSize(int preCacheSize);
	
	int getRenderSize();
	
	int getCacheSize();
	
	int getPreCacheSize();

	void render(GameContainer gc, Graphics g);
	
	void update(GameContainer gc, SlickEntity camera, int delta);
}
