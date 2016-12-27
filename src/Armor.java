public class Armor {

	private int CA, maxDex, dexBonus, weight;
	private String nom;
	
	protected String[] noms = {"Aucune armure", "Armure matelassée", "Armure de cuir", "Armure de peau", "Armure de cuir cloute", "Chemise de mailles", "Cotte de mailles", "Cuirasse", "Armure de plaques"};
	private int[] CAs = {10,11,12,13,14,15,16,17,18};
	private int[] mDs = {6,4,4,3,3,2,2,1,0};
	private int[] weights = {0,0,0,0,1,1,1,2,2};
	
	public Armor(int modele, int dxmod) {
		
		this.CA = CAs[modele];
		this.maxDex = mDs[modele];
		this.weight = weights[modele];
		this.nom = noms[modele];

		this.dexBonus = Math.min(dxmod, this.maxDex);
		
	}
	
	public boolean hit (int touche) {
		
		return (touche > (this.CA+this.dexBonus));
		
	}
	
	public String getNom() {
		
		return this.nom;
		
	}
	
	public int getCA(){
		
		return (this.CA+this.dexBonus);
		
	}
	
	public int[] getSpeed(int[] v){
		
		int[] res = {Math.max((v[0]-(this.weight*v[1])),0) , v[1]};
		return  res;	
		
	}
	
}
