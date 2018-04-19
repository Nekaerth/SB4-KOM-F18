package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Like IEntityProcessingService this interface has a method meant for
 * processing entities every game tick, but after all IEntityProcessingServices
 * have processed.
 *
 * @author Lasse
 */
public interface IPostEntityProcessingService {

	void postProcess(GameData gameData, World world);

}
