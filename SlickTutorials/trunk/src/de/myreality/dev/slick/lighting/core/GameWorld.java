package de.myreality.dev.slick.lighting.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.models.Entity;
import de.myreality.dev.chronos.models.EntitySystem;
import de.myreality.dev.chronos.slick.SlickEntity;

public class GameWorld implements World {
	
	private EntitySystem entitySystem;
	
	private Entity camera;
	
	private RenderSystem renderSystem;
	
	private EntitySystem activeLights;

	@Override
	public EntitySystem getEntitySystem() {
		return entitySystem;
	}

	@Override
	public Entity getCamera() {
		return camera;
	}

	@Override
	public void setCamera(Entity camera) {
		this.camera = camera;

	}

	@Override
	public void setRenderSystem(RenderSystem renderSystem) {
		this.renderSystem = renderSystem;

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		
		for (Entity entity : entitySystem.getAllEntities()) {
			
			if (entity instanceof SlickEntity) {
				((SlickEntity) entity).update(gc, sbg, delta);
			}			
			
			if (renderSystem != null) {
				renderSystem.update(entity);
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		if (renderSystem != null) {
			renderSystem.render(gc, sbg, g);
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) {
		camera = new SlickEntity();
		entitySystem = new EntitySystem("ENTITY_SYSTEM");
		activeLights = new EntitySystem("LIGHT_SYSTEM");
		camera.setBounds(0, 0, 0, gc.getWidth(), gc.getHeight(), 0);
		LightingSystem lightingSystem = new VectorLightingSystem(activeLights, new Rectangle(0, 0, gc.getWidth(), gc.getHeight()));
		renderSystem = new WorldRenderSystem(camera, lightingSystem);
	}

	@Override
	public void addEntity(Entity entity) {
		if (entity instanceof Light) {
			activeLights.addEntity(entity);
		} else {
			entitySystem.addEntity(entity);		
		}
	}

	@Override
	public void removeEntity(Entity entity) {
		if (entity instanceof Light) {
			activeLights.removeEntity(entity);
		} else {
			entitySystem.removeEntity(entity);		
		}
	}

	@Override
	public void setAmbientColor(Color color) {
		if (renderSystem != null) {
			renderSystem.setAmbientColor(color);
		}
	}

	@Override
	public Color getAmbientColor() {
		if (renderSystem != null) {
			return renderSystem.getAmbientColor();
		} else {
			return null;
		}
	}

}
