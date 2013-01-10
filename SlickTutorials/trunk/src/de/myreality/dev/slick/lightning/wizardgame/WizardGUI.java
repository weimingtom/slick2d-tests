package de.myreality.dev.slick.lightning.wizardgame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

public class WizardGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public WizardGUI() throws HeadlessException {
		super();
	}

	public WizardGUI(GraphicsConfiguration gc) {
		super(gc);
	}

	public WizardGUI(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

	public WizardGUI(String title) throws HeadlessException {
		super(title);
	}

	public void display() throws SlickException {
		setVisible(true);
		setBounds(300, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SwingGame game = new SwingGame();
		GameCanvas canvas = new GameCanvas(game);
		canvas.start();
		
		// Add the GUI Elements
		Container contentPane = getContentPane();
		contentPane.add(new GameContol(game, canvas), BorderLayout.PAGE_END);
		contentPane.add(canvas, BorderLayout.CENTER);
	}	
	
	static public class GameCanvas extends CanvasGameContainer {

		private static final long serialVersionUID = 1L;

		public GameCanvas(Game game) throws SlickException {
			super(game);
		}
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				WizardGUI gui = new WizardGUI("Wizard World Test");
				try {
					gui.display();
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			
		});
	}

}
