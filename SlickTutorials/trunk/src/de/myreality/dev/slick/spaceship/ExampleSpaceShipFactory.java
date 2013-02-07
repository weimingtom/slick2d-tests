package de.myreality.dev.slick.spaceship;

import java.util.ArrayList;
import java.util.List;

import de.myreality.dev.slick.spaceship.layers.BodyLayer;

public class ExampleSpaceShipFactory extends BasicSpaceShipFactory {
	
	private ArrayList<ShipLayer> layers;

	public ExampleSpaceShipFactory(String seed) {
		super(seed);
		setMinWidth(50);
		setMaxWidth(70);
		setMinHeight(80);
		setMaxHeight(100);
		setSeed(seed);
	}
	
	private void buildLayers() {
		if (layers == null) {
			layers = new ArrayList<ShipLayer>();
			layers.add(new BodyLayer(this));
			
			int compexity = getValue(1, 3, getSeed().hashCode());
			
			int lastCode = getSeed().hashCode() * 2;
			for (int i = 0; i < compexity; ++i) {
				layers.add(new BodyLayer(this, lastCode));
				lastCode += lastCode;
			}
			//layers.add(new BodyLayer(this, 239847));
			//layers.add(new BodyLayer(this, 6848));
		}
	}

	@Override
	protected int getTextureWidth(String seed) {
		return getValue(getMinWidth(), getMaxWidth(), seed.hashCode());
	}

	@Override
	protected int getTextureHeight(String seed) {
		return getValue(getMinHeight(), getMaxHeight(), seed.hashCode());
	}

	@Override
	protected int getColorValue(String seed) {
		return getValue(80, 90, seed.hashCode());
	}

	@Override
	protected List<ShipLayer> getLayers() {
		buildLayers();
		return layers;
	}
	
	
	private int getValue(int min, int max, int amplitude) {
		int difference = max - min;
		return (int) (difference * Math.sin(amplitude)+ difference + min);
	}

}
