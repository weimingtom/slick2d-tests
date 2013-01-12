package de.myreality.dev.slick.parallax;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.imageout.ImageOut;

import de.myreality.dev.chronos.toolkit.models.Entity;
import de.myreality.dev.chronos.toolkit.resource.ResourceManager;
import de.myreality.dev.chronos.toolkit.slick.ImageLoader;

/**
 * Test case for parallex scolling
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 */
public class ParallaxScrollingTest extends BasicGame {
	
	private ResourceManager manager;
	
	private ParallaxMapper mapper;
	
	private Image screenBuffer;
	
	private Entity camera;

	public ParallaxScrollingTest() {
		super("Slick2D - Parallax Scrolling Test");
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		// Load the resources
		manager = ResourceManager.getInstance();
		manager.addResourceLoader(ImageLoader.getInstance());
		manager.fromXML("space.xml");
		
		mapper = new ParallaxMapper();
		
		ParallaxSettings farSetting = new ParallaxSettings("SPACE_FAR", 100);		
		ParallaxSettings middleSetting = new ParallaxSettings("SPACE_MIDDLE", 45);
		ParallaxSettings stars1 = new ParallaxSettings("SPACE_STARS_1", 30);
		ParallaxSettings stars2 = new ParallaxSettings("SPACE_STARS_1", 60);
		ParallaxSettings middleSetting2 = new ParallaxSettings("SPACE_MIDDLE2", 20);
		ParallaxSettings nearSetting = new ParallaxSettings("SPACE_NEAR", 5);
		
		stars2.setWidth(250).setHeight(250);
		
		mapper.addLayer(farSetting);
		mapper.addLayer(stars2);
		mapper.addLayer(middleSetting);
		mapper.addLayer(stars1);
		mapper.addLayer(middleSetting2);
		mapper.addLayer(nearSetting);
		
		camera = new Entity();
		camera.setBounds(0, 0, 0, gc.getWidth(), gc.getHeight(), 0);
		mapper.attachTo(camera);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		mapper.render(gc, null, g);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		mapper.update(gc, null, delta);
		Input input = gc.getInput();
		
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		int centerX = gc.getWidth() / 2;
		int centerY = gc.getHeight() / 2;
		
		Vector2f direction = new Vector2f(mouseX - centerX, mouseY - centerY);
		Vector2f normalDirection = direction.getNormal();
		
		float speed = 2.2f * delta;
		
		camera.setGlobalX(camera.getGlobalX() + normalDirection.x * speed);
		camera.setGlobalY(camera.getGlobalY() + normalDirection.y * speed);
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			gc.exit();
		}
		
		if (input.isKeyDown(Input.KEY_F12)) {
			takeScreenshot(gc, ".png");
		}
		
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new ParallaxScrollingTest());
		//game.setVSync(true);
		game.setDisplayMode(800,600,false);
		game.setAlwaysRender(false);
		game.start();
	}
	
	
	/** 
	 * Takes a screenshot, fileExt can be .png, .gif, .tga, .jpg or .bmp 
	 */
   public void takeScreenshot(GameContainer container, String fileExt) {

      try {
         File FileSSDir = new File("screenshots");
         if (!FileSSDir.exists()) {
            FileSSDir.mkdirs();
         }

         int number = 0;
         String screenShotFileName = "screenshots/" + "screenshot_" + number + fileExt;
         File screenShot = new File(screenShotFileName);

         while (screenShot.exists()) {
            number++;
            screenShotFileName = "screenshots/" + "screenshot_" + number + fileExt;
            screenShot = new File(screenShotFileName);
         }	         

         screenBuffer = new Image(container.getWidth(), container.getHeight());

         Graphics g = container.getGraphics();
         g.copyArea(screenBuffer, 0, 0);
         ImageOut.write(screenBuffer, screenShotFileName);

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
