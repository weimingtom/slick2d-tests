package de.myreality.dev.slick.lighting.wizardgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.resource.Resource;
import de.myreality.dev.chronos.resource.ResourceManager;

public class House extends RenderObject implements LightingTarget {

	private Resource<Image> sprite;
	
	private TileWorld world;
	
	public House(TileWorld world) {
		super("House");
		this.world = world;
		ResourceManager manager = ResourceManager.getInstance();
		sprite = manager.getResource("HOUSE", Image.class);
		setWidth(sprite.get().getWidth() / 4);
		setHeight(sprite.get().getHeight() / 4);
	}
	
	

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		super.render(gc, sbg, g);
		Camera camera = world.getCamera();
		getSprite().draw(getGlobalX() - camera.getGlobalX(), getGlobalY() - camera.getGlobalY(), getWidth(), getHeight());
	}



	@Override
	public Image getSprite() {
		return sprite.get();
	}

}
