package dk.sdu.mmmi.cbse.commonenemy.services;

import dk.sdu.mmmi.cbse.commonenemy.data.Enemy;

public interface IEnemySpawningService {

	public Enemy createEnemy(float x, float y, float radians);

}
