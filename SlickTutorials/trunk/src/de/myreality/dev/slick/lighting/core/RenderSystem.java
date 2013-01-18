package de.myreality.dev.slick.lighting.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.models.Entity;

/**
 * Render system that renders slick entities on the screen
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface RenderSystem {

	void render(GameContainer gc, StateBasedGame sbg, Graphics g);
	
	void update(Entity renderEntity);
	
	void setLightingSystem(LightingSystem lightingSystem);
	
	LightingSystem getLightingSystem();
	
	Entity getCamera();
	
	void setCamera(Entity camera);
	
	void setAmbientColor(Color color);
	
	Color getAmbientColor();
}
