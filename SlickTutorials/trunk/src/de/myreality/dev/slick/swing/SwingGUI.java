package de.myreality.dev.slick.swing;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.newdawn.slick.SlickException;

public class SwingGUI extends JFrame {

	SlickGame game;
	
	private static final long serialVersionUID = 1L;
	
		
	
	public SwingGUI() throws HeadlessException {
		super("Swing - Slick 2D Test");
	}



	public void display() {
		pack();
		setVisible(true);
		this.game = new SlickGame();	
		setBounds(300, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = getContentPane();
		try {
			final SlickPanel canvas = new SlickPanel(game, true);
			pane.add(canvas, BorderLayout.CENTER);
			
			JButton button = new JButton("Change color");
			
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					game.changeColor();
					canvas.requestFocusInWindow();
				}
				
			});
			
			pane.add(button, BorderLayout.PAGE_START);
			canvas.requestFocusInWindow();
			canvas.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}		
	}

}
