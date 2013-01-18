package de.myreality.dev.slick.lighting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
 
/**
 * davedes' Tutorials
 * Alpha Map Lighting
 * http://slick.cokeandcode.com/wiki/doku.php?id=alpha_maps
 * 
 * @author davedes
 */
public class AlphaMaps extends BasicGame {
 
	//number of tiles in our simple horizontal sprite sheet
	public static final int TILE_COUNT = 5;
 
	//width/height of tile in pixels
	public static final int TILE_SIZE = 40;
 
	//size of alpha map (for use with sprite sheet)
	public static final int ALPHA_MAP_SIZE = 256;
 
	//space after tile, before next tile
	public static final int TILE_SPACING = 2;
 
	//the "sprite sheet" or texture atlas image
	private Image spriteSheet;
 
	//the sub-images of our sprite sheet
	private Image[] tileSprites;
 
	//our 2D map array
	private Image[][] tileMap;
 
	//map size in tiles
	private int mapWidth, mapHeight;
 
	//our alpha map image; just a feathered black circle on a transparent background
	private Image alphaMap;
 
	private Random random = new Random();
 
	//our lights
	private List<Light> lights = new ArrayList<Light>();
 
	//a timer used for simple light scaling effect
	private long elapsed;
 
	//a shared instance of Color so we don't need to create a new one each frame
	private Color sharedColor = new Color(1f, 1f, 1f, 1f);
 
	/** Describes a single point light. */
	public static class Light {
		/** The position of the light */
		float x, y;
		/** The RGB tint of the light, alpha is ignored */
		Color tint; 
		/** The alpha value of the light, default 1.0 (100%) */
		float alpha;
		/** The amount to scale the light (use 1.0 for default size). */
		private float scale;
		//original scale
		private float scaleOrig;
 
		public Light(float x, float y, float scale, Color tint) {
			this.x = x;
			this.y = y;
			this.scale = scaleOrig = scale;
			this.alpha = 1f;
			this.tint = tint;
		}
 
		public Light(float x, float y, float scale) {
			this(x, y, scale, Color.white);
		}
 
		public void update(float time) {

			//effect: scale the light slowly using a sin func
			scale = scaleOrig + 1f + .1f*(float)Math.sin(time);
		}
	}
 
	public AlphaMaps() {
		super("Alpha Map Lighting");
	}
 
	public void init(GameContainer container) throws SlickException {
//		container.setVSync(true);
//		container.setTargetFrameRate(60);
 
		//To reduce texture binds, our alpha map and tilesheet will be in the same texture
		//Most games will implement their own SpriteSheet class, but for simplicity's sake:
			//map tiles are in a horizontal row starting at (0, 0)
			//alpha map is located below the tiles, at (0, TILE_SIZE+TILE_SPACING)
		spriteSheet = new Image("res/fetch.png", false, Image.FILTER_NEAREST);
 
		//grab the tiles
		tileSprites = new Image[TILE_COUNT];
		for (int i = 0; i < tileSprites.length; i++) {
			tileSprites[i] = spriteSheet.getSubImage(i * (TILE_SIZE + TILE_SPACING), 0, TILE_SIZE, TILE_SIZE);
		}
 
		//grab the alpha map
		alphaMap = spriteSheet.getSubImage(0, TILE_SIZE + TILE_SPACING, ALPHA_MAP_SIZE, ALPHA_MAP_SIZE);
 
		//randomize our map
		randomizeMap(container);
 
		//reset the lighting
		resetLights(container);
	}
 
	Image randomTile() {
		int r = random.nextInt(100);
		if (r < 5) 
			return tileSprites[1 + random.nextInt(4) ];
		else
			return tileSprites[0];
	}
 
	void randomizeMap(GameContainer container) {
		// create the map
		mapWidth = container.getWidth() / TILE_SIZE + 1;
		mapHeight = container.getHeight() / TILE_SIZE + 1;
		tileMap = new Image[mapWidth][mapHeight];
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				tileMap[x][y] = randomTile();
			}
		}
	}
 
	public void resetLights(GameContainer container) {
		//clear the lights and add a new one with default scale
		lights.clear();
		lights.add(new Light(container.getInput().getMouseX(), container.getInput().getMouseY(), 1f));
	}
 
	public void render(GameContainer gc, Graphics g)
			throws SlickException {
		//each light requires a new pass to blend with the previous lights
		//FPS will be affected with too many lights at once
		for (int i=0; i<lights.size(); i++) {
			Light light = lights.get(i);
 
			//set up our alpha map for the light
			g.setDrawMode(Graphics.MODE_ALPHA_MAP);
			//clear the alpha map before we draw to it...
			g.clearAlphaMap();
 
			//centre the light
			int alphaW = (int)(alphaMap.getWidth() * light.scale);
			int alphaH = (int)(alphaMap.getHeight() * light.scale);
			int alphaX = (int)(light.x - alphaW/2f);
			int alphaY = (int)(light.y - alphaH/2f);
 
			//we apply the light alpha here; RGB will be ignored
			sharedColor.a = light.alpha;

			//draw the alpha map
			alphaMap.draw(alphaX, alphaY, alphaW, alphaH, sharedColor);
			
			//start blending in our tiles
			g.setDrawMode(Graphics.MODE_ALPHA_BLEND);

			//we'll clip to the alpha rectangle, since anything outside of it will be transparent
			g.setClip(0, 0, gc.getWidth(), gc.getHeight());

			//we'll use startUse/drawEmbedded/endUse for efficiency
			spriteSheet.startUse();
			//after startUse, we can apply a new tint like so..
			//since we're in MODE_ALPHA_BLEND, the alpha value will be ignored (hence applying it above)
			light.tint.bind(); 
			for (int x = 0; x < mapWidth; x++) {
				for (int y = 0; y < mapHeight; y++) {
					tileMap[x][y].drawEmbedded(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				}
			}
			spriteSheet.endUse();
			g.clearClip();
		}
 
		//reset the mode to normal before continuing..
		g.setDrawMode(Graphics.MODE_NORMAL);
 
		g.setColor(Color.white);
		g.drawString("Mouse click to add a light (total count: "+lights.size()+")", 10, 25);
		g.drawString("Press R to randomize the map tiles", 10, 40);
		g.drawString("Press SPACE to reset the lights", 10, 55);
	}
 
	public void update(GameContainer container, int delta)
			throws SlickException {
		if (container.getInput().isKeyPressed(Input.KEY_R))
			randomizeMap(container);
		if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
			resetLights(container);
		}
		elapsed += delta;
 
		//update all lights to have them smoothly scale
		for (int i=0; i<lights.size(); i++) {
			lights.get(i).update(elapsed / 1000f);
		}
 
		//the last-added light will be the one under the mouse
		if (lights.size()>0) {
			Light l = lights.get(lights.size()-1);
			l.x = container.getInput().getMouseX();
			l.y = container.getInput().getMouseY();
		}
	}
 
	//adds a new light
	public void mousePressed(int button, int x, int y) {
		float randSize = random.nextInt(15)*.1f+.5f;
		Color randColor = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
		lights.add(new Light(x, y, randSize, randColor));
	}
 
	public static void main(String[] args) {
		try {
			new AppGameContainer(new AlphaMaps(), 800, 600, false).start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}