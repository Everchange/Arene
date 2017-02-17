package engine;

import character.ArenaCharacter;

public class TurnChecker{
	
	ArenaCharacter[] joueurs;
	int current = 0;
	
	public TurnChecker(ArenaCharacter[] players) {
		
		int nombreJoueurs = players.length;
		int[] rolls = new int[nombreJoueurs];
		
		for (int i  = 0 ; i < nombreJoueurs ; i++) {
			
			rolls[i] = players[i].getInitiative();
			
		}
		
		
		int j;
		for (int i = 0; i < nombreJoueurs ; i++) {
			
			j = this.getMax(rolls);
			this.joueurs[i] = players[j];
			rolls[j] = -1;
			
		}
			
		
	}
	
	
	private int getMax (int[] liste) {
		
		int max = liste[0];
		int rang = 0;
		
		for (int i = 1 ; i < liste.length ; i++) {
			
			if (liste[i] > max){
				
				max = liste[i];
				rang = i;
				
			}
		}
		
		return rang;
		
	}
	
	public ArenaCharacter getCurrentPlayer() {
		
		return this.joueurs[this.current];
		
	}
	
	public void nexTurn() {
		
		this.current++;
		if (this.current == this.joueurs.length)
			this.current = 0;
		
	}

}
