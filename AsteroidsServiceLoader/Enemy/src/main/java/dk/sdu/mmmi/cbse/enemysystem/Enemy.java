package dk.sdu.mmmi.cbse.enemysystem;

public class Enemy extends dk.sdu.mmmi.cbse.commonenemy.data.Enemy {

	private float turnTimer;
	private float shootTimer;

	public float getTurnTimer() {
		return turnTimer;
	}

	void addTurnTimer(float t) {
		turnTimer += t;
	}

	public float getShootTimer() {
		return shootTimer;
	}

	void addShootTimer(float t) {
		shootTimer += t;
	}

}
