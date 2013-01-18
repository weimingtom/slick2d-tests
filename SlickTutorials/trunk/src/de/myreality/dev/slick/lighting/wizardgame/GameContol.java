package de.myreality.dev.slick.lighting.wizardgame;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.newdawn.slick.CanvasGameContainer;

public class GameContol extends Container {
	private static final long serialVersionUID = 1L;
	
	private JButton addLight = new JButton("Add Light");
	
	private JButton addWizard = new JButton("Add Wizard");
	
	private JButton resetWorld = new JButton("Reset world");

	private SwingGame game;
	
	private CanvasGameContainer container;
	
	public GameContol(final SwingGame game, final CanvasGameContainer container) {
		setLayout(new GridLayout(0, 3));
		add(addLight);
		add(addWizard);
		add(resetWorld);
		this.game = game;
		this.container = container;
		
		resetWorld.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (game.getWorld() != null) {
					game.getWorld().reset();
				}
				
				if (container != null) {
					container.requestFocusInWindow();
				}
			}
			
		});
	}
	
	public void init() {
		
	}
}
