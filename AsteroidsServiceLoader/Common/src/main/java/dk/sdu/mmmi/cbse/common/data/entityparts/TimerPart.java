package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * Add any amount of float timers, that should increase with dt
 *
 * @author Lasse
 */
public class TimerPart implements EntityPart {

	private float dt;
	private float[] timers;

	public TimerPart(float[] timers) {
		this.timers = timers;
	}

	@Override
	public void process(GameData gameData, Entity entity) {
		dt = gameData.getDelta();
		for (int i = 0; i < timers.length; i++) {
			timers[i] += dt;
		}
	}

	public float[] getTimers() {
		return timers;
	}

}
