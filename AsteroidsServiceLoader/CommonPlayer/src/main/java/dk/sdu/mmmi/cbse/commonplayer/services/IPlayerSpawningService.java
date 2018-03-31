package dk.sdu.mmmi.cbse.commonplayer.services;

import dk.sdu.mmmi.cbse.commonplayer.data.Player;

public interface IPlayerSpawningService {

	public Player createPlayer(float x, float y, float radians);

}
