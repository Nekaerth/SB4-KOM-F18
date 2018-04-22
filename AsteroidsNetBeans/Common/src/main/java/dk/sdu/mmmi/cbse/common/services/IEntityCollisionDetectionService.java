package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface has a method meant for detecting collision between entities
 * every game tick before IEntityProcessingServices and after
 * IPostEntityProcessingService have processed.
 *
 * @author Lasse
 */
public interface IEntityCollisionDetectionService {

	void collisionDetection(GameData gameData, World world);

}
