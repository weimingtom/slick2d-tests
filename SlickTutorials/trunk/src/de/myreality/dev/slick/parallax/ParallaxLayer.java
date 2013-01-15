package de.myreality.dev.slick.parallax;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.toolkit.slick.SlickEntity;

/**
 * Implementation of the Parallax Layer
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 */
public class ParallaxLayer extends SlickEntity implements Comparable<ParallaxLayer> {

	private static final long serialVersionUID = 1L;
	private ParallaxSettings settings;
	
	public ParallaxLayer(ParallaxMapper mapper, ParallaxSettings settings) {
		this.settings = settings;
		attachTo(mapper);
	}
	
	private int getTargetX() {
		return  (int) (-getGlobalX() / settings.getDistance());
	}
	
	private int getTargetY() {
		return (int) (-getGlobalY() / settings.getDistance());
	}
	
	private int getXClip() {
		return getTargetX() % settings.getWidth();
	}
	
	private int getYClip() {
		return getTargetY() % settings.getHeight();
	}
	
	private int getStartX() {
		int startX = 0;
		if (getTargetX() > 0) {
			startX = -settings.getWidth();
		} 
		return startX;
	}
	
	private int getStartY() {
		int startY = 0;
		if (getTargetY() > 0) {
			startY = -settings.getWidth();
		} 
		return startY;
	}
	
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		float localX = getLocalX();
		float localY = getLocalY();
		
		localX -= settings.getVelocity().x * delta;
		localY -= settings.getVelocity().y * delta;
		
		setLocalX(localX);
		setLocalY(localY);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {		
		Image tile = settings.getTileImage();
		
		if (tile != null) {
			
			g.setClip(0, 0, gc.getWidth(), gc.getHeight());
			
			for (int x = getStartX(); x < gc.getWidth() + settings.getWidth(); x += settings.getWidth()) {
				for (int y = getStartY(); y < gc.getHeight() + settings.getHeight(); y += settings.getHeight()) {
					tile.draw(x + getXClip(), y + getYClip(), settings.getWidth(), settings.getHeight(), settings.getFilter());
				}
			}
			
			g.clearClip();
		}
	}

	@Override
	public int compareTo(ParallaxLayer otherMap) {
		float distance = settings.getDistance();
		float otherDistance = otherMap.settings.getDistance();
		
		if (distance > otherDistance) {
			return -1;
		} else if (distance < otherDistance) {
			return 1;
		} else {
			return 0;
		}
	}
}
