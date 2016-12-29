

public abstract class Weapon {
	
	private int bonusToucher;
	private Damage degats;
	private int critMultiplicateur;
	private int critRange;
	private boolean ranged;
	private String nom;
	
	private int[] critMultiplicateurs = {2,3,2,2,2,3,2,3,2,3,3};
	private int[] critRanges = {2,1,2,1,2,1,2,1,1,1,1};
	private String[] nomsArmes = {"Epee à 2 mains", "Grande hache", "Fléau lourd", "Massue", "Epee longue", "Hache d'armes", "Epee courte", "Hachette", "Marteau léger", "Arc court", "Arc long"};
	private boolean[] ranges = {false, false, false, false, false, false, false, false, false, true, true};
			
	public Weapon(int pos) {
		
		this.critMultiplicateur = this.critMultiplicateurs[pos];
		this.critRange = this.critRanges[pos];
		this.ranged = this.ranges[pos];
		this.nom = this.nomsArmes[pos];
		
	}
	
	public String getName() {
		
		return this.nom;
		
	}
	
	public boolean aDistance(){
		
		return this.ranged;
		
	}
	
	
	
}
