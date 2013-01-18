package de.myreality.dev.slick.lighting.wizardgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.slick.AfterUpdateComponent;
import de.myreality.dev.chronos.slick.SlickComponent;
import de.myreality.dev.chronos.slick.SlickEntity;

public class WorldBoundComponent extends SlickComponent implements AfterUpdateComponent {
	
	private TileWorld world;

	public WorldBoundComponent(TileWorld world) {
		super();
		this.world = world;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		
		
	}

	@Override
	public void afterUpdate(GameContainer gc, StateBasedGame sbg, int delta) {
		if (getOwner() instanceof SlickEntity) {
			
			SlickEntity entity = (SlickEntity)getOwner();
		
			// Correct position: Left world clip
			if (entity.getGlobalX() < 0) {
				entity.setGlobalX(0);
			}
			
			// Correct position: Top world clip
			if (entity.getGlobalY() < 0) {
				entity.setGlobalY(0);
			}
			
			// Correct position: Right world clip
			if (entity.getGlobalX() + entity.getWidth() > world.getWidth()) {
				entity.setGlobalX(world.getWidth() - entity.getWidth());
			}
			
			// Correct position: Bottom world clip
			if (entity.getGlobalY() + entity.getHeight() > world.getHeight()) {
				entity.setGlobalY(world.getHeight() - entity.getHeight());
			}

		}
		
	}

}
