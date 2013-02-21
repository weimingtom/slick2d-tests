package de.myreality.dev.slick.parallax;

import java.io.File;

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

import de.myreality.dev.chronos.resource.ResourceManager;
import de.myreality.dev.chronos.slick.ImageLoader;

/**
 * Test case for parallex scolling
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 */
public class ParallaxScrollingTest extends BasicGame {
	
	private ResourceManager manager;
	
	private ParallaxMapper mapper;
	
	private Image screenBuffer;
	
	private Image overlay;
	
	private Camera camera;
	
	private ShaderProgram lineShader;
	
	public ParallaxScrollingTest() {
		super("Slick2D - Parallax Scrolling Test");
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		// Load the resources
		manager = ResourceManager.getInstance();
		manager.addResourceLoader(ImageLoader.getInstance());
		manager.fromXML("space.xml");
		
		//lineShader = ShaderProgram.loadProgram("res/shaders/scanline-medium.vert", "res/shaders/scanline-medium.frag");
		//lineShader.setUniform2f("rubyTextureSize", new Vector2f(1.0f, 1.0f));
		//lineShader.setUniform2f("rubyInputSize", new Vector2f(0.5f, 0.5f));
		//lineShader.setUniform2f("rubyOutputSize", new Vector2f(0.5f, 0.5f));
		mapper = new ParallaxMapper(gc.getWidth(), gc.getHeight());
		
		ParallaxSettings farSetting = new ParallaxSettings(manager.getResource("SPACE_FAR", Image.class).get(), 100);		
		ParallaxSettings middleSetting = new ParallaxSettings(manager.getResource("SPACE_MIDDLE", Image.class).get(), 45);
		ParallaxSettings stars1 = new ParallaxSettings(manager.getResource("SPACE_STARS_1", Image.class).get(), 30);
		ParallaxSettings stars2 = new ParallaxSettings(manager.getResource("SPACE_STARS_1", Image.class).get(), 60);
		ParallaxSettings middleSetting2 = new ParallaxSettings(manager.getResource("SPACE_MIDDLE2", Image.class).get(), 20);
		ParallaxSettings nearSetting = new ParallaxSettings(manager.getResource("SPACE_NEAR", Image.class).get(), 7);
		ParallaxSettings farFogSetting = new ParallaxSettings(manager.getResource("SPACE_NEAR", Image.class).get(), 45);
		
		stars2.setWidth(250).setHeight(250);
		
		mapper.addLayer(farSetting);
		mapper.addLayer(stars2);
		mapper.addLayer(middleSetting);
		mapper.addLayer(farFogSetting);
		mapper.addLayer(stars1);
		mapper.addLayer(middleSetting2);
		mapper.addLayer(nearSetting);
		
		camera = new Camera(gc);
		camera.setBounds(0, 0, 0, gc.getWidth(), gc.getHeight(), 0);
		mapper.attachTo(camera);
		
		
		overlay = getStripedLineImage(gc);
	}
	
	private Image getStripedLineImage(GameContainer gc) throws SlickException {
		Image buffer = Image.createOffscreenImage(gc.getWidth(), gc.getHeight());
		
		Graphics g = buffer.getGraphics();
		
		g.setColor(new Color(250, 250, 250, 60));
		for (int y = 0; y < buffer.getHeight(); y += 5) {
			g.fillRect(0, y, buffer.getWidth(), 2);
		}
		
		g.flush();
		
		return buffer;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.scale(camera.getScale(), camera.getScale());
		//lineShader.bind();
		mapper.render(gc, null, g);
		//lineShader.unbind();
		overlay.draw(0, 0, gc.getWidth() / camera.getScale(), gc.getHeight() / camera.getScale());
		
		g.scale(-camera.getScale(), -camera.getScale());
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
		
		float speed = (1 / camera.getScale() * 4) * delta;
		
		int zoomOffsetX = camera.getWidth() - gc.getWidth();
		int zoomOffsetY = camera.getHeight() - gc.getHeight();
		camera.setGlobalX(camera.getGlobalX() + normalDirection.x * speed);
		camera.setGlobalY(camera.getGlobalY() + normalDirection.y * speed);
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			gc.exit();
		}
		
		if (input.isKeyDown(Input.KEY_F12)) {
			takeScreenshot(gc, ".png");
		}
		
		float scaleFactor = 0.001f * delta;
		if (input.isKeyDown(Input.KEY_S)) {
			camera.setScale(camera.getScale() + scaleFactor);
		}
		
		if (input.isKeyDown(Input.KEY_D)) {
			camera.setScale(camera.getScale() - scaleFactor);
		}
		
		if (camera.getScale() > 1.5f) {
			camera.setScale(1.5f);
		}
		
		if (camera.getScale() < 0.1f) {
			camera.setScale(0.1f);
		}
		
		
		mapper.setDimension((int)(gc.getWidth() / camera.getScale()), (int)(gc.getHeight() / camera.getScale()), 0);
	}

	public static void main(String[] args) throws SlickException {

		AppGameContainer game = new AppGameContainer(new ParallaxScrollingTest());
		//game.setVSync(true);
		game.setDisplayMode(800,600,true);
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
