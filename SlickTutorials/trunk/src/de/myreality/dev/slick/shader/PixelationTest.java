package de.myreality.dev.slick.shader;

import org.lwjgl.Sys;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.shader.ShaderProgram;

import de.myreality.dev.chronos.toolkit.resource.Resource;
import de.myreality.dev.chronos.toolkit.resource.ResourceManager;
import de.myreality.dev.chronos.toolkit.slick.ImageLoader;

public class PixelationTest extends BasicGame {
	
	private ResourceManager manager = ResourceManager.getInstance();
	private Resource<Image> background, ball;
	private ShaderProgram program;
	private boolean shaderWorks;
	private float pixelSize;
	private Image buffer;
	private int x, y;
	
	private static final float INIT_SIZE = 1.0f;

	public PixelationTest() {
		super("Chronos - Pixelation Shader Test");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		Image image = background.get();
		Image ballImage = ball.get();
		
		Graphics graphics = buffer.getGraphics();
		Graphics.setCurrent(graphics);
		
		image.draw(0, 0, gc.getWidth(), gc.getHeight());
		ballImage.draw(x, y, 100, 100);
		
		Graphics.setCurrent(g);
		
		//start using our program
	    if (shaderWorks)
	        program.bind();
	    
	    program.setUniform1f("pixel", pixelSize);
	    
		program.setUniform2f("size", gc.getWidth(), gc.getHeight());
		buffer.drawFlash(0, 0, gc.getWidth(), gc.getHeight(), new Color(50, 100, 20, 50));
		
		//start using our program
	    if (shaderWorks)
	        program.unbind();
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		
		manager.addResourceLoader(ImageLoader.getInstance());
		manager.fromXML("resources.xml");
		background = manager.getResource("WALL", Image.class);
		ball = manager.getResource("BALL", Image.class); 
		pixelSize = INIT_SIZE;
		if (!ShaderProgram.isSupported()) {
	        // Sys is part of LWJGL -- it's a handy way to show an alert
	        Sys.alert("Error", "Your graphics card doesn't support OpenGL shaders.");
	        container.exit();
	        return;
	    }
		
		// load our shader program
	    try {
	        // load our vertex and fragment shaders
	        final String VERT = "res/shaders/pixelate.vert";
	        final String FRAG = "res/shaders/pixelate.frag";
	        program = ShaderProgram.loadProgram(VERT, FRAG);
	        shaderWorks = true;
	    } catch (SlickException e) {
	        // there was a problem compiling our source! show the log
	        e.printStackTrace();
	    }
		buffer = Image.createOffscreenImage(container.getWidth(), container.getHeight());
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		pixelSize += 0.0005f * delta;
		
		Input input = container.getInput();
		
		if (input.isKeyDown(Input.KEY_W)) {
			y--;
		} else if (input.isKeyDown(Input.KEY_S)) {
			y++;
		}
		
		if (input.isKeyDown(Input.KEY_A)) {
			x--;
		} else if (input.isKeyDown(Input.KEY_D)) {
			x++;
		}
	}
	

	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new PixelationTest());
		game.setDisplayMode(800, 600, false);
		game.start();
	}

}
