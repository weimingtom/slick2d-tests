package de.myreality.dev.slick.lighting.core;

import org.newdawn.slick.Image;

import de.myreality.dev.chronos.resource.Resource;
import de.myreality.dev.chronos.resource.ResourceManager;
import de.myreality.dev.chronos.slick.ImageRenderComponent;
import de.myreality.dev.chronos.slick.SlickEntity;

public class LightingEntity extends SlickEntity implements LightingTarget {
	
	private static final long serialVersionUID = -5693112430377346306L;
	private Resource<Image> spriteResource;
	
	public LightingEntity(String spriteID) {
		spriteResource = ResourceManager.getInstance().getResource(spriteID, Image.class);
		ImageRenderComponent spriteComponent = new ImageRenderComponent(spriteID);
		addComponent(spriteComponent);
	}

	@Override
	public Image getSprite() {
		return spriteResource.get();
	}

	@Override
	public void resetLighting() {
		Image sprite = getSprite();
		
		if (sprite != null) {
			sprite.setColor(Image.TOP_LEFT, 1.0f, 11.0f, 1.0f);
			sprite.setColor(Image.TOP_RIGHT, 1.0f, 11.0f, 1.0f);
			sprite.setColor(Image.BOTTOM_LEFT, 1.0f, 11.0f, 1.0f);
			sprite.setColor(Image.BOTTOM_RIGHT, 1.0f, 11.0f, 1.0f);
		}
	}

}
