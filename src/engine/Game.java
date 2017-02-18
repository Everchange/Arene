package engine;

import ruleset.CS;
import character.ArenaCharacter;

public class Game {
	
	private ArenaCharacter[] players;
	private TurnChecker turn;
	
	public Game(CS[] playerList, String[] names, String[] imgPaths, int[][] positions) {
		
		int nombreJoueurs = playerList.length;
		
		this.players = new ArenaCharacter[nombreJoueurs];
		
		for (int i = 0; i < nombreJoueurs ; i++) {
			
			//this.players[i] = new ArenaCharacter(playerList[i], names[i], imgPaths[i], positions[i]);
			
		}
		
		turn = new TurnChecker(this.players);
		
	}

}