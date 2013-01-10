package de.myreality.dev.slick.lightning.wizardgame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import de.myreality.dev.chronos.toolkit.slick.SlickComponent;

public class WizardMovingComponent extends SlickComponent {
	
	private Wizard wizard;
	
	private TileWorld world;

	public WizardMovingComponent(Wizard wizard, TileWorld world) {
		super();
		this.wizard = wizard;
		this.world = world;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		Input input = gc.getInput();
		
		if (input.isKeyDown(Input.KEY_W)) {
			wizard.move(Wizard.TOP, delta);
		} else if (input.isKeyDown(Input.KEY_S)) {
			wizard.move(Wizard.DOWN, delta);
		} else if (input.isKeyDown(Input.KEY_A)) {
			wizard.move(Wizard.LEFT, delta);
		} else if (input.isKeyDown(Input.KEY_D)) {
			wizard.move(Wizard.RIGHT, delta);
		} else {
			wizard.stop();
		}
		
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			AnimatedCastComponent cast = new AnimatedCastComponent(wizard, world);
			world.addWorldEntity(cast);
		}
	}

}
