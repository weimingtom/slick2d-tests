package de.myreality.dev.slick.lightning.wizardgame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.toolkit.models.Entity;

/**
 * Renderer for light targets
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 */
public class LightTargetRenderer implements Renderer {	
	
	private TileWorld world;
	
	public LightTargetRenderer(TileWorld world) {
		this.world = world;
	}
	
	private Point[] getCornersFromObject(RenderObject object) {

		Point[] corners = new Point[4];
		
		corners[Image.TOP_LEFT] = new Point(object.getGlobalX(), object.getGlobalY());
		corners[Image.TOP_RIGHT] = new Point(object.getGlobalX() + object.getWidth(), object.getGlobalY());
		corners[Image.BOTTOM_LEFT] = new Point(object.getGlobalX(), object.getGlobalY() + object.getHeight());
		corners[Image.BOTTOM_RIGHT] = new Point( object.getGlobalX() + object.getWidth(), object.getGlobalY() + object.getHeight());
		
		return corners;
	}
	
	private Color getCornerColor(Point corner) {
		float colorRed = 0, colorGreen = 0, colorBlue = 0;
		
		for (Entity entity : world.getActiveLightSources()) {

			if (entity instanceof Light) {
				Light light = (Light)entity;
				// Calculate the distance
				float deltaX = light.getGlobalCenterX() - corner.getX();
				float deltaY = light.getGlobalCenterY() - corner.getY();
				float distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
				
				float shadowAmount = distance /  (light.getSize() * 0.5f);	
				if (shadowAmount > 1) {
					shadowAmount = 1;
				}
				
				Color ambientColor = world.getSettings().getAmbientColor();
				
				Color resultColor = light.blend(ambientColor.r / world.getActiveLightSources().size(), ambientColor.g / world.getActiveLightSources().size(), ambientColor.b / world.getActiveLightSources().size(),
										  light.getColor().r, light.getColor().g, light.getColor().b, shadowAmount);
				colorRed += resultColor.r;
				colorGreen += resultColor.g;
				colorBlue += resultColor.b;
			}
		}
		
		return new Color(colorRed, colorGreen, colorBlue);
	}
	
	private void calculateCornerShadow(RenderObject object) {
		if (object instanceof LightingTarget) {
			
			LightingTarget lightingTarget = (LightingTarget)object;
			
			Point[] corners = getCornersFromObject(object);
			
			
			for (int i = 0; i < corners.length;++i) {					

				Color cornerColor = getCornerColor(corners[i]);
				
				lightingTarget.getSprite().setColor(i, cornerColor.r, cornerColor.g, cornerColor.b);
			}
		
		}
	}
	
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		// 4. Render Game Objects
		for (Entity entity : world.getRenderObjects()) {
			
			if (entity instanceof RenderObject) {
				RenderObject object = (RenderObject)entity;
				calculateCornerShadow(object);			
				object.render(gc, sbg, g);
			}
		}
	}

	
}
