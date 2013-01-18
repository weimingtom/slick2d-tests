package de.myreality.dev.slick.lighting.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.models.Entity;
import de.myreality.dev.chronos.models.EntitySystem;

public interface World {

	EntitySystem getEntitySystem();
	
	Entity getCamera();
	
	void setCamera(Entity camera);
	
	void setRenderSystem(RenderSystem renderSystem);
	
	void update(GameContainer gc, StateBasedGame sbg, int delta);
	
	void render(GameContainer gc, StateBasedGame sbg, Graphics g);
	
	void init(GameContainer gc, StateBasedGame sbg);
	
	void addEntity(Entity entity);
	
	void removeEntity(Entity entity);
	
	void setAmbientColor(Color color);
	
	Color getAmbientColor();
}
