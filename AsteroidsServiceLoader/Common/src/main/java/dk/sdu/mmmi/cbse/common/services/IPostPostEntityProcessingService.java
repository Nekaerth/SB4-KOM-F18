package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Like IEntityProcessingService and IPostEntityProcessing this interface has a
 * method meant for processing entities every game tick, but after all
 * IEntityProcessingServices and IPostEntityProcessing have processed.
 *
 * @author Lasse
 */
public interface IPostPostEntityProcessingService {

	void postPostProcess(GameData gameData, World world);

}
