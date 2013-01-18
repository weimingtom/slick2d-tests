package de.myreality.dev.slick.lighting.wizardgame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.slick.SlickEntity;
import de.myreality.dev.chronos.slick.SlickRenderComponent;

public abstract class AnimatedSprite extends RenderObject implements LightingTarget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int direction;
	
	private boolean moving;
	
	private float speed;	
	
	private AnimatedSpriteRenderComponent renderComponent;
	
	private TileWorld world;
	
	public static final int TOP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	
	public AnimatedSprite(String id, TileWorld world) {
		super(id);
		this.world = world;
		renderComponent = new AnimatedSpriteRenderComponent(getAnimations());
		addComponent(renderComponent);
		addComponent(new WorldBoundComponent(world));
		direction = DOWN;

	}
	
	protected abstract Animation[] getAnimations(); 

	@Override
	public Image getSprite() {
		return renderComponent.getCurrentFrame();
	}
	
	public void addToWorld(TileWorld world) {
		world.addWorldEntity(this);
	}
	
	public int getDirection() {
		return direction;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		super.update(gc, sb, delta);
		
		if (isMoving()) {
			switch (direction) {
				case TOP:
					setGlobalY(getGlobalY() - speed * delta);
					break;
				case DOWN:
					setGlobalY(getGlobalY() + speed * delta);
					break;
				case LEFT:
					setGlobalX(getGlobalX() - speed * delta);
					break;
				case RIGHT:
					setGlobalX(getGlobalX() + speed * delta);
					break;
			}
		}
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void move(int direction, int delta) {
		setDirection(direction);
		moving = true;		
	}
	
	public void stop() {
		moving = false;
	}
	
	public boolean isMoving() {
		return moving;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}



	private class AnimatedSpriteRenderComponent extends SlickRenderComponent {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Animation[] spriteAnimation;
		
		public AnimatedSpriteRenderComponent(Animation[] spriteAnimations) {
			super();
			this.spriteAnimation = spriteAnimations;
			
			
			for (Animation animation : spriteAnimation) {
				animation.setAutoUpdate(false);
			}
			
		}

		@Override
		public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
			SlickEntity slickOwner = (SlickEntity)getOwner();			
			getCurrentAnimation().draw(slickOwner.getGlobalX() - world.getCamera().getGlobalX(), slickOwner.getGlobalY() - world.getCamera().getGlobalY());
		}
		
		
		private Animation getCurrentAnimation() {
			return spriteAnimation[direction];
		}
		
		public Image getCurrentFrame() {
			return getCurrentAnimation().getCurrentFrame();
		}

		@Override
		public void update(GameContainer gc, StateBasedGame sbg, int delta) {

			if (isMoving()) {
				if (getCurrentAnimation().isStopped()) {
					getCurrentAnimation().start();		
				}
			} else {
				getCurrentAnimation().restart();
				getCurrentAnimation().stop();
			}
			
			getCurrentAnimation().update(delta);
		}
		
	}
}
