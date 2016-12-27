public class Armor {

	private int CA, maxDex, weight;
	protected String RIEN="Aucune armure", MATELASSEE="Armure matelassée", CUIR="Armure de cuir", PEAU="Armure de peau", CLOUTE="Armure de cuir cloute", CHEMISE="Chemise de mailles", COTTE="Cotte de mailles", CUIRASSE="Cuirasse", PLAQUE="Armure de plaques";
	
	public Armor(int nom, int dxmod) {
		
		if (nom == 0){
			
			this.CA = 10;
			this.maxDex = -1;
			this.weight = 0;
			
		}
		else if (nom == 1) {
			
			this.CA = 11;
			this.maxDex = 4;
			this.weight = 1;
			
		}
		else if (nom == 2) {
			
			this.CA = 12;
			this.maxDex = 4;
			this.weight = 1;
			
		}
		else if (nom == 3) {
			
			this.CA = 13;
			this.maxDex = 3;
			this.weight = 1;
			
		}
		else if (nom == 4) {
			
			this.CA = 14;
			this.maxDex = 3;
			this.weight = 2;
			
		}
		else if (nom == 5) {
			
			this.CA = 15;
			this.maxDex = 2;
			this.weight = 2;
			
		}
		else if (nom == 6) {
			
			this.CA = 16;
			this.maxDex = 2;
			this.weight = 2;
			
		}
		else if (nom == 7) {
			
			this.CA = 17;
			this.maxDex = 1;
			this.weight = 3;
			
		}
		else {
			
			this.CA = 18;
			this.maxDex = 0;
			this.weight = 3;
			
		}
		
		this.maxDex = Math.min(dxmod, this.maxDex);
		
		
	}
	
	public boolean hit (int touche) {
		
		return (touche > (CA+this.maxDex));
		
	}
	
}
