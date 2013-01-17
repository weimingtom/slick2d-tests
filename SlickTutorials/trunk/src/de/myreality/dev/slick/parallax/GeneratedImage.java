package de.myreality.dev.slick.parallax;

import java.util.Collection;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.shader.ShaderProgram;

public class GeneratedImage extends Image {
	
	private float time;

	public GeneratedImage(int width, int height, Collection<ShaderProgram> programs, float time) throws SlickException {
		super(width, height);
		this.time = time;
		
		try {
			Image buffer = Image.createOffscreenImage(width, height, Image.FILTER_LINEAR);
			applyShaders(buffer, programs);
			buffer.getGraphics().copyArea(this, 0, 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	
	public GeneratedImage(int width, int height, Collection<ShaderProgram> programs) throws SlickException {
		this(width, height, programs, 0);
	}

	private void applyShaders(Image target, Collection<ShaderProgram> programs) throws SlickException {
		for (ShaderProgram program : programs) {
			Graphics g = target.getGraphics();
			program.bind();
			
			if (program.hasUniform("time")) {
				program.setUniform1f("time", time);
			}
			
			target.draw();
			program.unbind();
			g.flush();
		}
	}
}
