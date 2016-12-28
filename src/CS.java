

public class CS {
	
	// 0 - FOR, 1 - DEX, 2 - CON, 3 - INT, 4 - SAG, 5 - CHA
	private int[] attributs = new int[6];
	private int[] mods = new int[6];
	private int BBA=1, reductionDegats;
	private int[] vitesse;		// La première valeur est la vitesse, la deuxième un booléen indiquant si les armures réduisent la vitesse ou non
	private Weapon arme;
	private Armor armure;
	private Race race;
	
	public CS(int[] att, int armr, int ra) {
		
		for (int k=0 ; k<6 ; k++) {
			
			this.attributs[k] = att[k];
			
		}	
		
		this.race = new Race(ra);
		
		this.vitesse = race.getSpeed();
		
		this.attributs = race.changeStats(this.attributs);
		
		this.mods = getMods(this.attributs);						//On obtient les modificateurs des attributs
		
		this.armure = new Armor(armr,this.mods[1]);					//On créée l'armure
		
		this.vitesse = this.armure.getSpeed(this.vitesse);			//On calcule la vitesse après ralentissement ou non de l'armure
		
	}
	
	private int[] getMods(int[] att) {
		
		int[] res = new int[6];
		
		for (int i=0 ; i<6 ; i++) {
		
			res[i] = Math.floorDiv((att[i]-10), 2);
			
		}
		
		return res;
		
	}
		
	

}
