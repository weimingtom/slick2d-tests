package de.myreality.dev.slick.lightning;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.toolkit.resource.Resource;
import de.myreality.dev.chronos.toolkit.resource.ResourceManager;
import de.myreality.dev.chronos.toolkit.slick.ImageLoader;
import de.myreality.dev.chronos.toolkit.slick.SlickEntity;
import de.myreality.dev.chronos.toolkit.slick.SlickRenderComponent;
 
/**
 * davedes' Tutorials
 * Alpha Map Lighting
 * http://slick.cokeandcode.com/wiki/doku.php?id=alpha_maps
 * 
 * @author davedes
 */
public class AlphaBlending extends BasicGame {
	
	private Resource<Image> image, shadow, background;
	
	private Point point;
	
	private static final int TILE_SIZE = 200;
	
	private float time, size, startSize;
	
	private static final Color AMBIENT_COLOR = new Color(10, 0, 40);
	
	private static final Color LIGHT_COLOR = new Color(255, 200, 140);
	
	private Wizard wizard;
	
	public AlphaBlending() {
		super("Slick Tutorials - Alpha Blending");
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.setAntiAlias(true);
		int xFactor = container.getWidth() / TILE_SIZE + 1;
		int yFactor = container.getHeight() / TILE_SIZE + 1;
		
		int xPos = 0;
		for (int x = 0; x < xFactor; x++) {
			int yPos = 0;
			for (int y = 0; y < yFactor; y++) {
				background.get().draw(xPos, yPos, TILE_SIZE, TILE_SIZE, AMBIENT_COLOR);
				yPos += TILE_SIZE;
			}
			xPos += TILE_SIZE;
		}
		g.setDrawMode(Graphics.MODE_ALPHA_MAP);
		shadow.get().draw(point.getX(), point.getY(), size, size, Color.black);
		
		g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
		
		g.setClip((int)point.getX(), (int)point.getY(), (int)size, (int)size);
		xPos = 0;
		for (int x = 0; x < xFactor; x++) {
			int yPos = 0;
			for (int y = 0; y < yFactor; y++) {
				image.get().draw(xPos, yPos, TILE_SIZE, TILE_SIZE, LIGHT_COLOR);
				yPos += TILE_SIZE;
			}
			xPos += TILE_SIZE;
		}
		
		g.clearClip();
		g.setDrawMode(Graphics.MODE_NORMAL);
		wizard.render(container, null, g);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		point = new Point(0, 0);
		// Initialize the resource manager
		ResourceManager manager = ResourceManager.getInstance();
		manager.addResourceLoader(ImageLoader.getInstance());
		manager.addResourceLoader(new SpriteSheetLoader());
		manager.fromXML("spritesheets.xml");
		image = manager.getResource("WALL_BUMP", Image.class);
		shadow = manager.getResource("SHADOW", Image.class);
		background = manager.getResource("WALL", Image.class);
		size = startSize = 600;
		wizard = new Wizard(point);
		wizard.setGlobalPosition(300, 300);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		time += Math.random() * (delta * 100);
		wizard.update(container, null, delta);
		
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			container.exit();
		}

		point.setX((int)(wizard.getGlobalCenterX() - size / 2));
		point.setY((int)(wizard.getGlobalCenterY() - size / 2));

		size = startSize + 1f + 8.5f*(float)Math.sin(time / 3000.0f);
	}
	
	
	private class Wizard extends SlickEntity {

		public Wizard(Point lightPosition) {
			super(null);
			this.addComponent(new WizardRenderComponent(lightPosition));
			setWidth(32);
			setHeight(48);
		}
		
	}
	

	private class WizardRenderComponent extends SlickRenderComponent {

		private Point lightPosition;
		
		private int direction;
		
		private Animation[] spriteAnimation;
		
		private static final int TOP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
		
		public WizardRenderComponent(Point lightPosition) {
			super();
			this.lightPosition = lightPosition;
			ResourceManager manager = ResourceManager.getInstance();
			Resource<Image> sheet = manager.getResource("CRYUS", Image.class);
			
			SpriteSheet downSheet = new SpriteSheet(sheet.get().getSubImage(0, 0, 128, 48), 32, 48);
			SpriteSheet leftSheet = new SpriteSheet(sheet.get().getSubImage(0, 48, 128, 48), 32, 48);
			SpriteSheet rightSheet = new SpriteSheet(sheet.get().getSubImage(0, 96, 128, 48), 32, 48);
			SpriteSheet upSheet = new SpriteSheet(sheet.get().getSubImage(0, 144, 128, 48), 32, 48);
			spriteAnimation = new Animation[4];
			spriteAnimation[TOP] = new Animation(upSheet, 400);
			spriteAnimation[DOWN] = new Animation(downSheet, 400);
			spriteAnimation[LEFT] = new Animation(leftSheet, 400);
			spriteAnimation[RIGHT] = new Animation(rightSheet, 400);
			direction = DOWN;
		}

		@Override
		public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
			SlickEntity slickOwner = (SlickEntity)getOwner();			
			spriteAnimation[direction].draw(slickOwner.getGlobalX(), slickOwner.getGlobalY(), LIGHT_COLOR);
		}

		@Override
		public void update(GameContainer gc, StateBasedGame sbg, int delta) {
			Input input = gc.getInput();
			float speed = 0.15f;
			SlickEntity entity = (SlickEntity)owner;
			if (input.isKeyDown(Input.KEY_W)) {
				spriteAnimation[direction].start();
				direction = TOP;
				entity.setGlobalY(entity.getGlobalY() - speed * delta);
			} else if (input.isKeyDown(Input.KEY_S)) {
				spriteAnimation[direction].start();
				direction = DOWN;
				entity.setGlobalY(entity.getGlobalY() + speed * delta);
			} else if (input.isKeyDown(Input.KEY_A)) {
				spriteAnimation[direction].start();
				direction = LEFT;
				entity.setGlobalX(entity.getGlobalX() - speed * delta);
			} else if (input.isKeyDown(Input.KEY_D)) {
				spriteAnimation[direction].start();
				direction = RIGHT;
				entity.setGlobalX(entity.getGlobalX() + speed * delta);
			} else {
				spriteAnimation[direction].restart();
			}
			
			spriteAnimation[direction].update(delta);
		}
		
	}
	
	

	public static void main(String[] args) {
		try {
			new AppGameContainer(new AlphaBlending(), 800, 600, false).start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}