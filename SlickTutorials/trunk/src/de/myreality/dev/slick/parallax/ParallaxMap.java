package de.myreality.dev.slick.parallax;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import de.myreality.dev.chronos.toolkit.resource.Resource;
import de.myreality.dev.chronos.toolkit.resource.ResourceManager;

public class ParallaxMap {
	
	private ParallaxSettings settings;
	private ParallaxMapper parentMapper;
	
	public ParallaxMap(ParallaxMapper mapper, ParallaxSettings settings) {
		this.settings = settings;
		this.parentMapper = mapper;
	}

	public void update(GameContainer gc, int delta) {
		
	}
	
	public void render(GameContainer gc, Graphics g) {
		Resource<Image> tileResource = ResourceManager.getInstance().getResource(settings.getTileID(), Image.class);
		Image tile = tileResource.get();
		
		if (tile != null) {
			int xResult = (int) (-parentMapper.getGlobalX() * 1.0f / settings.getDistance());
			int yResult = (int) (-parentMapper.getGlobalY() * 1.0f / settings.getDistance());
			
			g.setClip(0, 0, gc.getWidth(), gc.getHeight());
			
			for (int x = 0; x < gc.getWidth() + settings.getWidth(); x += settings.getWidth()) {
				for (int y = 0; y < gc.getHeight() + settings.getHeight(); y += settings.getHeight()) {
					tile.draw(xResult + x, yResult + y, settings.getWidth(), settings.getHeight());
				}
			}
			
			g.clearClip();
		}
	}
}
