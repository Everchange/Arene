

public class CS {
	
	// 0 - FOR, 1 - DEX, 2 - CON, 3 - INT, 4 - SAG, 5 - CHA
	private int[] attributs = new int[6];
	private int[] mods = new int[6];
	private Weapon arme;
	private Armor armure;
	private Race race;
	
	public int getDexMod() {
		return this.mods[1];
	}
	
	public CS(int[] att, int armr) {
		
		for (int k=0 ; k<6 ; k++) {
			
			this.attributs[k] = att[k];
			
		}
		
		this.mods = getMods(this.attributs);
		
		this.armure = new Armor(armr);
		
	}
	
	private int[] getMods(int[] att) {
		
		int[] res = new int[6];
		
		for (int i=0 ; i<6 ; i++) {
		
			res[i] = Math.floorDiv((att[i]-10), 2);
			
		}
		
		return res;
		
	}
		
	

}
