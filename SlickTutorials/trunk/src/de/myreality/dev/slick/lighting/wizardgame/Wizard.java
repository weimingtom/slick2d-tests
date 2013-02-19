package de.myreality.dev.slick.lighting.wizardgame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.resource.Resource;
import de.myreality.dev.chronos.resource.ResourceManager;

public class Wizard extends AnimatedSprite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3685245763462393527L;
	private Light light;	

	public Wizard(String id, TileWorld world) {
		super(id, world);
		setWidth(32);
		setHeight(48);
		
		setSpeed(0.1f);
		
		light = new Torch(0, 0, world);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		super.update(gc, sb, delta);
		light.setGlobalX(getGlobalX() - light.getWidth() / 2);
		light.setGlobalY(getGlobalY() - light.getHeight() / 2);
	}
	
	

	@Override
	public void addToWorld(TileWorld world) {
		super.addToWorld(world);
		world.addWorldEntity(light);
	}

	@Override
	protected Animation[] getAnimations() {
		ResourceManager manager = ResourceManager.getInstance();
		Resource<Image> sheet = manager.getResource("CRYUS", Image.class);
		
		SpriteSheet downSheet = new SpriteSheet(sheet.get().getSubImage(0, 0, 128, 48), 32, 48);
		SpriteSheet leftSheet = new SpriteSheet(sheet.get().getSubImage(0, 48, 128, 48), 32, 48);
		SpriteSheet rightSheet = new SpriteSheet(sheet.get().getSubImage(0, 96, 128, 48), 32, 48);
		SpriteSheet upSheet = new SpriteSheet(sheet.get().getSubImage(0, 144, 128, 48), 32, 48);
		Animation[] spriteAnimation = new Animation[4];
		spriteAnimation[TOP] = new Animation(upSheet, 200);
		spriteAnimation[DOWN] = new Animation(downSheet, 200);
		spriteAnimation[LEFT] = new Animation(leftSheet, 200);
		spriteAnimation[RIGHT] = new Animation(rightSheet, 200);
		
		return spriteAnimation;
	}
	
	

}
