package dk.sdu.mmmi.cbse.commonbullet.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;

public class ShootingPart implements EntityPart {

	private float shootGap;
	private float shootTimer;

	public ShootingPart(float shootGap) {
		this.shootGap = shootGap;
		this.shootTimer = shootGap;
	}

	public float getShootGap() {
		return shootGap;
	}

	public float getShootTimer() {
		return shootTimer;
	}

	public void addShootTimer(float t) {
		shootTimer += t;
	}

	public void resetTimer() {
		shootTimer = shootGap;
	}

	@Override
	public void process(GameData gameData, Entity entity) {

	}

}
