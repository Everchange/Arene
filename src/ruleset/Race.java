package ruleset;

public class Race {
	
	// 0 - FOR, 1 - DEX, 2 - CON, 3 - INT, 4 - SAG, 5 - CHA
	private String nom;
	private int[] mods;
	private int[] celerite;
	private int raceID;
	private static final String[] noms={"Humain","Elfe","Nain","Orque","Gnome","Zombie","Halfelin","Homme l�zard", "Elfe noir", "Squelette", "Gobelin", "Hobgobelin"};
	// pour les modificateurs la premiere valeur correspond � l'indice du bonus, la deuxi�me du malus
	private int[][] modificateurs={{0,0},{1,2},{2,1},{0,3},{3,0},{0,1},{1,0},{2,3},{3,2},{0,2},{2,0},{0,0}};
	private int[][] vitesses={{6,1},{6,1},{4,0},{6,1},{4,1},{4,1},{4,1},{6,0},{6,1},{6,1},{4,1},{6,1}};
	
	public Race(int pRace){

		this.nom = noms[pRace];
		this.mods = modificateurs[pRace];
		this.celerite = vitesses[pRace];
		this.raceID = pRace;
		
	}
	
	public int getRaceID() {
		
		return this.raceID;
		
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
	/**
	 * return the average size of a character that belong to the specified race
	 * 
	 * @param index an integer that represent the race of a character
	 * @return a double array of two elements, the width (index 0) and the height (index 1)
	 */
	public static double[] getRaceSize(int index){
		switch(index){
		case(0):
			return new double[] {0.4,1.5};
		default:
			return new double[] {1,1};
		}
	}

}
