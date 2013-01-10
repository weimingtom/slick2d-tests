package de.myreality.dev.slick.lightning.wizardgame;

import java.util.ArrayList;
import java.util.Collection;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.toolkit.models.Entity;
import de.myreality.dev.chronos.toolkit.models.EntityManager;
import de.myreality.dev.chronos.toolkit.models.EntitySystem;
import de.myreality.dev.chronos.toolkit.slick.SlickEntity;

/**
 * Basic tile world with bump mapping
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 *
 */
public class TileWorld extends SlickEntity {
	
	private static final long serialVersionUID = 1L;

	private WorldSettings settings;
	
	private Camera camera;	
	
	private EntitySystem renderObjects;
	
	private EntitySystem entities;
	
	private EntitySystem activeLightSources;
	
	private ArrayList<Renderer> renderers;

	public TileWorld(String id, GameContainer gc, WorldSettings settings) {
		super(null);
		setWidth(settings.getWidth());
		setHeight(settings.getHeight());
		this.settings = settings;
		this.camera = new Camera("GameCamera", gc, this);
		entities = EntityManager.getInstance().addSystem("ENTITIES");
		renderObjects = EntityManager.getInstance().addSystem("RENDER_OBJECTS");
		activeLightSources = EntityManager.getInstance().addSystem("ACTIVE_LIGHT_SOURCES");
		renderers = new ArrayList<Renderer>();
		
		// Add renderers
		renderers.add(new BumpMapRenderer(this));
		renderers.add(new LightTargetRenderer(this));
		
	}
	
	public EntitySystem getEntitySystem() {
		return entities;
	}
	
	public EntitySystem getLightSystem() {
		return activeLightSources;
	}
	
	public EntitySystem getRenderSystem() {
		return renderObjects;
	}
	
	public Collection<Entity> getActiveLightSources() {
		return activeLightSources.getAllEntities();
	}
	
	public WorldSettings getSettings() {
		return settings;
	}
	
	public Collection<Entity> getRenderObjects() {
		return renderObjects.getAllEntities();
	}
	
	public void addWorldEntity(SlickEntity object) {
		entities.addEntity(object);
		object.setEntitySystem(entities);
	}
	
	public void removeWorldObject(RenderObject object) {
		renderObjects.removeEntity(object);
		entities.removeEntity(object);
	}
	
	public void reset() {
		renderObjects.clear();
		entities.clear();
		activeLightSources.clear();
		camera.setGlobalPosition(0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		super.update(gc, sb, delta);
		camera.update(gc, sb, delta);
		
		for (Entity entity : entities.getAllEntities()) {
			if (entity instanceof SlickEntity) {
				((SlickEntity) entity).update(gc, sb, delta);
				
				if (((SlickEntity) entity).collidateWidth(camera)) {
					if (entity instanceof Light) {
						if (!activeLightSources.contains(entity)) {
							activeLightSources.addEntity(entity);
						}
					} else {
						if (!renderObjects.contains(entity)) {
							renderObjects.addEntity(entity);
						}
					}
				} else {
					if (entity instanceof Light) {
						activeLightSources.removeEntity(entity);
					} else {
						renderObjects.removeEntity(entity);
					}
				}
			}
		}
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		super.render(gc, sbg, g);
		
		camera.render(gc, sbg, g);
		
		for (Renderer renderer : renderers) {
			renderer.render(gc, sbg, g);
		}
	}
	
	public Camera getCamera() {
		return camera;
	}
	
}
