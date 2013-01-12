package de.myreality.dev.slick.shader;

import org.lwjgl.Sys;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.shader.ShaderProgram;

public class PerlinNoiseTest extends BasicGame {
	
	private Image background;
	private ShaderProgram program;
	
	public PerlinNoiseTest() {
		super("Chronos - Perlin Noise Shader Test");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		background.draw(0, 0, gc.getWidth(), gc.getHeight());
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		
		if (!ShaderProgram.isSupported()) {
	        // Sys is part of LWJGL -- it's a handy way to show an alert
	        Sys.alert("Error", "Your graphics card doesn't support OpenGL shaders.");
	        container.exit();
	        return;
	    }
		
		background = Image.createOffscreenImage(container.getWidth(), container.getHeight(), Image.FILTER_LINEAR);
		
		// load our shader program
	    try {
	    	Graphics g = background.getGraphics();
	        // load our vertex and fragment shaders
	        final String VERT = "res/shaders/perlin.vert";
	        final String FRAG = "res/shaders/perlin.frag";
	        program = ShaderProgram.loadProgram(VERT, FRAG);
	        
		    program.bind();
		    program.setUniform1f("time", (int) (Math.random() * 10000));
		    background.draw();
		    program.unbind();
		    g.flush();
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
		AppGameContainer game = new AppGameContainer(new PerlinNoiseTest());
		game.setDisplayMode(800, 600, false);
		game.start();
	}

}
