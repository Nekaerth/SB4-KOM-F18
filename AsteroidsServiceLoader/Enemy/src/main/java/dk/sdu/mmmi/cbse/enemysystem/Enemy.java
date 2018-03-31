package dk.sdu.mmmi.cbse.enemysystem;

public class Enemy extends dk.sdu.mmmi.cbse.commonenemy.data.Enemy {

	private float turnTimer;

	public float getTurnTimer() {
		return turnTimer;
	}

	void addTurnTimer(float t) {
		turnTimer += t;
	}

}
