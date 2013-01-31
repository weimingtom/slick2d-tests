package de.myreality.dev.slick.lighting.core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.models.Entity;
import de.myreality.dev.chronos.models.EntitySystem;
import de.myreality.dev.chronos.slick.SlickEntity;
import de.myreality.dev.chronos.util.Point2f;

/**
 * Implementation of a world based render system
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class WorldRenderSystem implements RenderSystem {
	
	// Lighting system
	private LightingSystem lightingSystem;
	
	private EntitySystem renderSystem;

	private Entity camera;
	
	private Color ambientColor;
	
	public WorldRenderSystem(Entity camera, LightingSystem lightingSystem) {
		setLightingSystem(lightingSystem);
		setCamera(camera);
		renderSystem = new EntitySystem("RENDER_SYSTEM");
	}
	
	public WorldRenderSystem(LightingSystem lightingSystem) {
		this(null, lightingSystem);
	}
	
	public WorldRenderSystem(Entity camera) {
		this(camera, null);
	}
	
	public WorldRenderSystem() {
		this(null, null);
	}	

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
		if (camera != null) {
			g.setClip(0, 0, camera.getWidth(), camera.getHeight());
		} else {
			g.setClip(0, 0, gc.getWidth(), gc.getHeight());
		}
		
		for (Entity entity : renderSystem.getAllEntities()) {

			if (entity instanceof LightingTarget) {
				if (lightingSystem != null) {
					lightingSystem.render((LightingTarget)entity, ambientColor);
				} else {
					((LightingTarget) entity).resetLighting();
				}
			}
			
			if (entity instanceof SlickEntity) {		
				SlickEntity slickEntity = ((SlickEntity)entity);
				slickEntity.setColor(ambientColor);
				slickEntity.render(gc, sbg, g);
				
				//for (Point2f bound : slickEntity.getBounds()) {
				//	g.setColor(Color.green);
				//	g.fillRect(bound.x, bound.y, 2, 2);
				//}
			}
		}

		g.clearClip();
	}

	@Override
	public void update(Entity entity) {
		
		if (entity.collidateWith(camera)) {
			renderSystem.addEntity(entity);
		} else {
			renderSystem.removeEntity(entity);
		}
		
	}

	@Override
	public void setLightingSystem(LightingSystem lightingSystem) {
		this.lightingSystem = lightingSystem;
		// Clear the current render setting
		if (renderSystem != null) {
			renderSystem.clear();
		}
	}

	@Override
	public LightingSystem getLightingSystem() {
		return lightingSystem;
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
	public void setAmbientColor(Color color) {
		this.ambientColor = color;
	}

	@Override
	public Color getAmbientColor() {
		return ambientColor;
	}
}
