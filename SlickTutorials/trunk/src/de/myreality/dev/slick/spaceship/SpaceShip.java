package de.myreality.dev.slick.spaceship;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.models.Entity;
import de.myreality.dev.chronos.slick.SlickEntity;
import de.myreality.dev.chronos.slick.SlickRenderComponent;
import de.myreality.dev.chronos.util.Point2f;

public class SpaceShip extends SlickEntity {

	private static final long serialVersionUID = -4379004569425756652L;
	
	private Point2f[] bounds;
	
	private Point2f[] weaponPoints;
	
	private Point2f[] boostPoints;
	
	private String seed;
	
	public SpaceShip(String seed, Image texture, Point2f[] bounds, Point2f[] weaponPoints, Point2f[] boostPoints) {
		this.seed = seed;
		this.boostPoints = boostPoints;
		this.bounds = bounds;
		this.weaponPoints = weaponPoints;
		addComponent(new TextureRenderComponent(texture));
	}
	
	public String getSeed() {
		return seed;
	}
	
	class TextureRenderComponent extends SlickRenderComponent {

		private static final long serialVersionUID = -3197836235693205120L;
		private Image texture;		
	
		public TextureRenderComponent(Image texture) {
			this.texture = texture;
		}

		@Override
		public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
			if (texture != null) {
				Entity parent = getOwner();
				texture.setRotation(parent.getRotation());
				texture.draw(parent.getGlobalX(), parent.getGlobalY(), parent.getWidth(), parent.getHeight());		
				texture.setRotation(0.0f);
			}
		}
		
		
	}
}
