package de.myreality.dev.slick.lightning;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import de.myreality.dev.chronos.resource.Freeable;
import de.myreality.dev.chronos.resource.ResourceLoader;

public class SpriteSheetLoader extends ResourceLoader<SpriteSheet> {

	@Override
	protected SpriteSheet loadResource(Freeable freeable, ResourceDefinition definition) {
		try {
			return new SpriteSheet(definition.getContent(), 32, 48);
		} catch (SlickException e) {
			return null;
		}
	}

}
