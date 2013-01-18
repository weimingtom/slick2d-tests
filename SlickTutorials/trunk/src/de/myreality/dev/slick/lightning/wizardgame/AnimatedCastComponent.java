package de.myreality.dev.slick.lightning.wizardgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.resource.Resource;
import de.myreality.dev.chronos.resource.ResourceManager;

public class AnimatedCastComponent extends RenderObject {
	
	private Resource<Image> sprite;
	
	private int direction;
	
	private TileWorld world;
	
	private Torch torch;

	public AnimatedCastComponent(AnimatedSprite parent, TileWorld world) {
		super("");
		ResourceManager manager = ResourceManager.getInstance();
		sprite = manager.getResource("GREEN_SPELL", Image.class);
		
		setWidth(10);
		setHeight(10);
		
		attachTo(parent);
		
		direction = parent.getDirection();
		
		torch = new Torch(0, 0, world);
		torch.attachTo(this);
		world.addWorldEntity(torch);
		torch.setUpperColor(manager.getResource("UPPER_COLOR", Color.class));
		torch.setUpperColor(manager.getResource("LOWER_COLOR", Color.class));
		torch.setStartSize(300);
		torch.setLocalX(getWidth() - torch.getWidth() / 2);
		torch.setLocalY(getHeight() - torch.getHeight() / 2);
		this.world = world;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		Camera camera = world.getCamera();
		sprite.get().draw(getGlobalX() - camera.getGlobalX(), getGlobalY() - camera.getGlobalY(), getWidth(), getHeight());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		super.update(gc, sb, delta);
		
		float speed = 0.5f;
		
		switch (direction) {
		case AnimatedSprite.TOP:
			setGlobalY(getGlobalY() - speed * delta);
			break;
		case AnimatedSprite.DOWN:
			setGlobalY(getGlobalY() + speed * delta);
			break;
		case AnimatedSprite.LEFT:
			setGlobalX(getGlobalX() - speed * delta);
			break;
		case AnimatedSprite.RIGHT:
			setGlobalX(getGlobalX() + speed * delta);
			break;			
		}
		
		torch.setLocalX(getWidth() - torch.getWidth() / 2);
		torch.setLocalY(getHeight() - torch.getHeight() / 2);
	}

	@Override
	public Image getSprite() {
		return sprite.get();
	}
	
	
}
