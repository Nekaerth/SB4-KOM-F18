package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface used for when a module is plugged in or out.
 *
 * @author Lasse
 */
public interface IGamePluginService {

	void start(GameData gameData, World world);

	void stop(GameData gameData, World world);
}
