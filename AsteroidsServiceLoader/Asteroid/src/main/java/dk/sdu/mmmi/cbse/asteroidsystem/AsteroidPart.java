package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;

public class AsteroidPart implements EntityPart {

	private int size;

	public AsteroidPart(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	@Override
	public void process(GameData gd, Entity entity) {

	}

}
