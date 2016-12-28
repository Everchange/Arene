/**
 * 
 *@author Clément
 *@version 1.0
 */

public class Race {
	
	// 0 - FOR, 1 - DEX, 2 - CON, 3 - INT, 4 - SAG, 5 - CHA
	private String nom;
	private int[] mods;
	private int[] celerite;
	private static String[] noms={"Humain","Elfe","Nain","Orque","Gnome","Zombie","Halfelin","Homme lézard", "Elfe noir", "Squelette", "Gobelin", "Hobgobelin"};
	// pour les modificateurs la premiere valeur correspond à l'indice du bonus, la deuxième du malus
	private int[][] modificateurs={{0,0},{1,2},{2,1},{0,3},{3,0},{0,1},{1,0},{2,3},{3,2},{0,2},{2,0},{0,0}};
	private int[][] vitesses={{6,1},{6,1},{4,0},{6,1},{4,1},{4,1},{4,1},{6,0},{6,1},{6,1},{4,1},{6,1}};
	
	public Race(int pRace){

		this.nom = noms[pRace];
		this.mods = modificateurs[pRace];
		this.celerite = vitesses[pRace];
		
	}
	
	public String getRaceName(){
		return this.nom;
	}
	
	public int[] changeStats(int[] stats){
		
		int[] res = stats.clone();
		res[mods[0]] += 2;
		res[mods[1]] -= 2;
		return res;
		
	}
	
	public int[] getSpeed(){
		
		return this.celerite;
		
	}
	
	public String getNom(){
		
		return this.nom;
		
	}

}
