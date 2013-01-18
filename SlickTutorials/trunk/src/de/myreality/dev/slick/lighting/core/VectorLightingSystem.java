package de.myreality.dev.slick.lighting.core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import de.myreality.dev.chronos.models.Bounding;
import de.myreality.dev.chronos.models.Entity;
import de.myreality.dev.chronos.models.EntitySystem;
import de.myreality.dev.chronos.util.Point2f;
import de.myreality.dev.chronos.util.Quadtree;

/**
 * Vector based lighting renderer including adaptive lighting
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class VectorLightingSystem implements LightingSystem {
	
	// List of all visible lights
	private EntitySystem activeLights;
	
	// Near lights
	private ArrayList<Bounding> nearLights;
	
	// Quad tree in order to compare only near lights
	private Quadtree collisionTree;
	
	private static final int LEVEL = 10;
	
	/**
	 * Basic constructor of the renderer
	 */
	public VectorLightingSystem(EntitySystem activeLights, Rectangle area) {
		this.activeLights = activeLights;
		this.collisionTree = new Quadtree(LEVEL, area);
		nearLights = new ArrayList<Bounding>();
		
	}

	@Override
	public void render(LightingTarget target, Color ambientColor) {
		
		// Calculate the near lights
		calculateNearLights(target);

		for (int index = 0; index < target.getBounds().length; index++) {
			
			// Initiate new bound color
			Color boundColor = new Color(0, 0, 0);
			Point2f bound = target.getBounds()[index];
			
			for (Bounding bounding : nearLights) {
				if (bounding instanceof Light) {
					Color lightColor = calculateBoundLighting((Light)bounding, bound, ambientColor, nearLights.size());
					boundColor.r += lightColor.r;
					boundColor.g += lightColor.g;
					boundColor.b += lightColor.b;
				}
			}
			
			// Sets the new color
			target.getSprite().setColor(index, boundColor.r, boundColor.g, boundColor.b);
		}
	}
	
	private void calculateNearLights(LightingTarget target) {
		// Calculate the quad tree
		collisionTree.clear();
		nearLights.clear();
		
		for (Entity light : activeLights.getAllEntities()) {
			collisionTree.insert(light);
		}

		collisionTree.retrieve(nearLights, target);	
	}
	
	
	private Color calculateBoundLighting(Light light, Point2f bound, Color ambientColor, int lightCount) {
		// Calculate the distance
		float deltaX = light.getGlobalCenterX() - bound.x;
		float deltaY = light.getGlobalCenterY() - bound.y;
		float distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		
		float shadowAmount = distance /  light.getRadius() * 2;
		
		if (shadowAmount > 1) {
			shadowAmount = 1;
		}

		return blend(ambientColor.r / lightCount, ambientColor.g / lightCount, ambientColor.b / lightCount,
								  light.getColor().r, light.getColor().g, light.getColor().b, shadowAmount);
	}
	
	
	
	private Color blend(float r1, float g1, float b1, float r2, float g2, float b2, float ratio) {

		float ir = (float) 1.0 - ratio;

		return new Color((float)(r1 * ratio + r2 * ir), 
						 (float)(g1 * ratio + g2 * ir),
						 (float)(b1 * ratio + b2 * ir));          
	}
}
