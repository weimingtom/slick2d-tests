package de.myreality.dev.slick.physics;

import net.phys2d.math.ROVector2f;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.BodyList;
import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.CollisionListener;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.AABox;
import net.phys2d.raw.shapes.Circle;
import net.phys2d.raw.shapes.Polygon;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class JBox2DTest extends BasicGame implements CollisionListener {
	
	private World world;
	
	private CustomImage image, planetImage;

	private Body planet;
	
	private Body customBox;

	public JBox2DTest(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

		BodyList list = world.getBodies();
		
		for (int i = 0; i < list.size(); ++i) {
			Body body = list.get(i);
			if (planet != body) {
				Polygon box = (Polygon) body.getShape();
				
	            Vector2f[] pts = box.getVertices(body.getPosition(), body.getRotation());
	            
	            Vector2f v1 = pts[0];
	            Vector2f v2 = pts[1];
	            Vector2f v3 = pts[2];
	            Vector2f v4 = pts[3];            
            
            	image.drawWarped(v1.x, v1.y, v4.x, v4.y, v3.x, v3.y, v2.x, v2.y);
            } else {
            	Circle box = (Circle) body.getShape();
	            AABox bounds = box.getBounds();
	            ROVector2f pos = planet.getPosition();
            	planetImage.draw(pos.getX() - box.getRadius(), pos.getY() - box.getRadius(), bounds.getWidth(), bounds.getHeight());
            }
		}
		
		g.setColor(Color.green);
		g.drawString("Elements: " + world.getBodies().size(), 10, 30);
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		world = new World(new Vector2f(0.0f, 0.0002f), 50);
		world.addListener(this);
		image = new CustomImage("res/test.png");
		//image.setColor(Image.BOTTOM_LEFT, 0.1f, 0.1f, 0.1f);
		planetImage = new CustomImage("res/planet.png");
		
		planet = new Body(new Circle(100), 10000000);
		planet.setPosition(400,  300);
		world.add(planet);
		planet.setGravityEffected(false);
		
		float size = 30;
		Vector2f[] pts = {
				new Vector2f(0, 0),
				new Vector2f(size, 0),
				new Vector2f(size, size),
				new Vector2f(0, size)
				
		};
		customBox = new Body("MyBody_" + world.getBodies().size(), new Polygon(pts), 2000f);
		world.add(customBox);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		Input input = gc.getInput();
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			float x = input.getMouseX();
			float y = input.getMouseY();
			
			for (int i = 0; i < 20; ++i) {
				addBody(x, y);
			}
			
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			gc.exit();
		}
		
		BodyList list = world.getBodies();
		
		for (int i = 0; i < list.size(); ++i) {
			Body body = list.get(i);
			computeIfOnScreen(gc, body);
		}
		
		customBox.setPosition(input.getMouseX(), input.getMouseY());
		

		world.step(delta);
	}
	
	
	private void addBody(float x, float y) {
		float size = 30;
		Vector2f[] pts = {
				new Vector2f(0, 0),
				new Vector2f(size, 0),
				new Vector2f(size, size),
				new Vector2f(0, size)
				
		};
		Body body = new Body("MyBody_" + world.getBodies().size(), new Polygon(pts), 15f);
		
		float xForce = (float)(Math.random() * 0.2f);
		float yForce = (float)(Math.random() * 0.2f);
		
		if (Math.random() * 100 < 50) {
			xForce = -xForce;
		}
		
		if (Math.random() * 100 < 50) {
			yForce = -yForce;
		}
		
		body.setForce(xForce, yForce);
		body.setPosition(x, y);
		body.setFriction(100);
		body.setRestitution(0.1f);
		body.setTorque(0.01f);
		world.add(body);
	}
	
	
	private void computeIfOnScreen(GameContainer gc, Body body) {
		if (body != planet) {
			Polygon box = (Polygon) body.getShape();
	        Vector2f[] pts = box.getVertices(body.getPosition(), body.getRotation());
	        
	        boolean allIn = false;
	        for (Vector2f point : pts) {
	        	if (point.x < gc.getWidth() && point.x > 0 && point.y < gc.getHeight() && point.y > 0) {
	        		allIn = true;
	        		break;
	        	}
	        }
	        
	        if (!allIn) {
	        	world.remove(body);
	        }
		}
	}
	
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new JBox2DTest("Slick2D: Game Physics with JBox2D"));
		game.setDisplayMode(800, 600, false);
		game.start();
	}

	@Override
	public void collisionOccured(CollisionEvent event) {
		
	}

}
