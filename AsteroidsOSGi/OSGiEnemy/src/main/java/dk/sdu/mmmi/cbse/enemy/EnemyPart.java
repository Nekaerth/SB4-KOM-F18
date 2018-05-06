package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;

/**
 * A part for containing all attributes that only the enemysystem i using
 *
 * @author Lasse
 */
public class EnemyPart implements EntityPart {

	private float turnTimer;

	public EnemyPart() {
		this.turnTimer = 0;
	}

	public float getTurnTimer() {
		return turnTimer;
	}

	public void changeTurnTime(float t) {
		turnTimer += t;
	}

	@Override
	public void process(GameData gd, Entity entity) {

	}

}
