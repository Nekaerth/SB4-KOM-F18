package dk.sdu.mmmi.cbse.commonbullet.services;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IBulletService {

	public void shoot(GameData gameData, World world, float x, float y, float radians, Entity owner);

}
