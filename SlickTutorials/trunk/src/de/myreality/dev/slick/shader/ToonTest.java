package de.myreality.dev.slick.shader;

import org.lwjgl.Sys;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.shader.ShaderProgram;

import de.myreality.dev.chronos.toolkit.resource.Resource;
import de.myreality.dev.chronos.toolkit.resource.ResourceManager;
import de.myreality.dev.chronos.toolkit.slick.ImageLoader;
import de.myreality.dev.chronos.toolkit.slick.ImageRenderComponent;
import de.myreality.dev.chronos.toolkit.slick.SlickEntity;

public class ToonTest extends BasicGame {
	
	private ResourceManager manager = ResourceManager.getInstance();
	private Resource<Image> background, ball;
	private ShaderProgram program;
	private boolean shaderWorks;

	public ToonTest() {
		super("Chronos - Pixelation Shader Test");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		Image image = background.get();
		Image ballImage = ball.get();
		//start using our program
	    if (shaderWorks)
	        program.bind();
		
	    program.setUniform1f("alpha", 1.0f);
	    program.setUniform1f("edge", 0.1f);
	    program.setUniform1f("mid", 0.6f);
	    
		image.draw(0, 0, gc.getWidth(), gc.getHeight());
		ballImage.draw(100, 100, 100, 100);
		
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
		
		if (!ShaderProgram.isSupported()) {
	        // Sys is part of LWJGL -- it's a handy way to show an alert
	        Sys.alert("Error", "Your graphics card doesn't support OpenGL shaders.");
	        container.exit();
	        return;
	    }
		
		// load our shader program
	    try {
	        // load our vertex and fragment shaders
	        final String VERT = "res/shaders/toon.vert";
	        final String FRAG = "res/shaders/toon.frag";
	        program = ShaderProgram.loadProgram(VERT, FRAG);
	        shaderWorks = true;
	    } catch (SlickException e) {
	        // there was a problem compiling our source! show the log
	        e.printStackTrace();
	    }
		
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}
	

	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new ToonTest());
		game.setDisplayMode(800, 600, false);
		game.start();
	}

}
