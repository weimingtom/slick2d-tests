package de.myreality.dev.slick.parallax;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.opengl.shader.ShaderProgram;

import de.myreality.dev.chronos.models.Entity;
import de.myreality.dev.chronos.resource.ResourceManager;
import de.myreality.dev.chronos.slick.ImageLoader;

/**
 * Test case for parallex scolling
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 */
public class ProceduralGeneratedSpace extends BasicGame {
	
	private ResourceManager manager;
	
	private ParallaxMapper mapper;
	
	private Image screenBuffer;
	
	private Entity camera;

	public ProceduralGeneratedSpace() {
		super("Slick2D - Procedural Generated Space");
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		// Load the resources
		manager = ResourceManager.getInstance();
		manager.addResourceLoader(ImageLoader.getInstance());
		manager.fromXML("space.xml");
		
		mapper = new ParallaxMapper(gc.getWidth(), gc.getHeight());		
		
		GeneratedImage backgroundSpace = new GeneratedImage(800, 800, getSpaceShaders(), 14500);
		
		ParallaxSettings farSetting = new ParallaxSettings(backgroundSpace, 100);		
		farSetting.setFilter(new Color(30, 0, 70));
		ParallaxSettings middleSetting = new ParallaxSettings(manager.getResource("SPACE_MIDDLE", Image.class).get(), 45);
		ParallaxSettings stars1 = new ParallaxSettings(manager.getResource("SPACE_STARS_1", Image.class).get(), 30);
		ParallaxSettings stars2 = new ParallaxSettings(manager.getResource("SPACE_STARS_1", Image.class).get(), 60);
		ParallaxSettings middleSetting2 = new ParallaxSettings(manager.getResource("SPACE_MIDDLE2", Image.class).get(), 20);
		ParallaxSettings nearSetting = new ParallaxSettings(manager.getResource("SPACE_NEAR", Image.class).get(), 10);
		ParallaxSettings farFogSetting = new ParallaxSettings(manager.getResource("SPACE_NEAR", Image.class).get(), 45);
		
		stars2.setWidth(250).setHeight(250);
		
		mapper.addLayer(farSetting);
		mapper.addLayer(stars2);
		mapper.addLayer(middleSetting);
		mapper.addLayer(farFogSetting);
		mapper.addLayer(stars1);
		mapper.addLayer(middleSetting2);
		mapper.addLayer(nearSetting);
		
		camera = new Entity();
		camera.setBounds(0, 0, 0, gc.getWidth(), gc.getHeight(), 0);
		mapper.attachTo(camera);
	}
	
	private ArrayList<ShaderProgram> getSpaceShaders() {
		ArrayList<ShaderProgram> programs = new ArrayList<ShaderProgram>();
		
		try {
			programs.add(ShaderProgram.loadProgram("res/shaders/perlin.vert", "res/shaders/perlin.frag"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		return programs;
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
		AppGameContainer game = new AppGameContainer(new ProceduralGeneratedSpace());
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
