package de.myreality.dev.slick.lightning.wizardgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import de.myreality.dev.chronos.resource.Resource;
import de.myreality.dev.chronos.resource.ResourceManager;

public class CitySetting extends WorldSettings {

	public CitySetting() {
		ResourceManager manager = ResourceManager.getInstance();
		
		// Load the world settings
		Resource<Color> ambientColor = manager.getResource("NIGHTBLUE", Color.class);
		Resource<Image> background = manager.getResource("WALL", Image.class);
		Resource<Image> bump = manager.getResource("WALL_BUMP", Image.class);
		setTileSize(150);
		setBackgroundTile(background.get());
		setBumpTile(bump.get());
		setAmbientColor(ambientColor.get());
	}
}
