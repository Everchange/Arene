

public abstract class Weapon {
	
	private int bonusToucher;
	private Damage degats;
	private int critMultiplicateur;
	
	public Weapon(String nom) {
		
		if (nom == "hache") {
			
			bonusToucher = 42;
			degats = new Damage(1,2,3);
			critMultiplicateur = 14;
			
		}
		
	}
	
//	public static int attaque(int bt, int bd) {
//		
//		if 
//		
//	}

}
