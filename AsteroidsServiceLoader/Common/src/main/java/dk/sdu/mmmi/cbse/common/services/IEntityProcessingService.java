package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface has a method meant for processing entities every game tick.
 *
 * @author Lasse
 */
public interface IEntityProcessingService {

	void process(GameData gameData, World world);

}
