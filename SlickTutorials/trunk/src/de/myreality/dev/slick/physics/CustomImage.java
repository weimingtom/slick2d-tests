package de.myreality.dev.slick.physics;

import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.renderer.SGL;

public class CustomImage extends Image {
	
	

	public CustomImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomImage(Image other) {
		super(other);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(ImageData data, int f) {
		super(data, f);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(ImageData data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(InputStream in, String ref, boolean flipped, int filter)
			throws SlickException {
		super(in, ref, flipped, filter);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(InputStream in, String ref, boolean flipped)
			throws SlickException {
		super(in, ref, flipped);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(int width, int height, int f) throws SlickException {
		super(width, height, f);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(int width, int height) throws SlickException {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(String ref, boolean flipped, int f, Color transparent)
			throws SlickException {
		super(ref, flipped, f, transparent);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(String ref, boolean flipped, int filter)
			throws SlickException {
		super(ref, flipped, filter);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(String ref, boolean flipped) throws SlickException {
		super(ref, flipped);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(String ref, Color trans) throws SlickException {
		super(ref, trans);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(String ref, int filter) throws SlickException {
		super(ref, filter);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(String ref) throws SlickException {
		super(ref);
		// TODO Auto-generated constructor stub
	}

	public CustomImage(Texture texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}

	public void drawWarped(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Color color) {
    	if (corners == null) {
		color.bind();
		}
		texture.bind();
	
		GL.glTranslatef(x1, y1, 0);
		if (angle != 0) {
		    GL.glTranslatef(centerX, centerY, 0.0f);
		    GL.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		    GL.glTranslatef(-centerX, -centerY, 0.0f);
		}
	
		GL.glBegin(SGL.GL_QUADS);
		init();
	
		if (corners != null) {
			corners[TOP_LEFT].bind();
		}
		GL.glTexCoord2f(textureOffsetX, textureOffsetY);
		GL.glVertex3f(0, 0, 0);
		if (corners != null) {
			corners[BOTTOM_LEFT].bind();
		}
		GL.glTexCoord2f(textureOffsetX, textureOffsetY + textureHeight);
		GL.glVertex3f(x2 - x1, y2 - y1, 0);
		if (corners != null) {
			corners[BOTTOM_RIGHT].bind();
		}
		GL.glTexCoord2f(textureOffsetX + textureWidth, textureOffsetY
		        + textureHeight);
		GL.glVertex3f(x3 - x1, y3 - y1, 0);
		if (corners != null) {
			corners[TOP_RIGHT].bind();
		}
		GL.glTexCoord2f(textureOffsetX + textureWidth, textureOffsetY);
		GL.glVertex3f(x4 - x1, y4 - y1, 0);
		GL.glEnd();
	
		if (angle != 0) {
		    GL.glTranslatef(centerX, centerY, 0.0f);
		    GL.glRotatef(-angle, 0.0f, 0.0f, 1.0f);
		    GL.glTranslatef(-centerX, -centerY, 0.0f);
		}
		GL.glTranslatef(-x1, -y1, 0);
	}
}
