package ruleset;

public class Armor {

	private int CA, maxDex, dexBonus, weight;
	private String nom;
	
	protected String[] noms = {"Aucune armure", "Armure matelassée", "Armure de cuir", "Armure de peau", "Armure de cuir cloute", "Chemise de mailles", "Cotte de mailles", "Cuirasse", "Armure de plaques"};
	private int[] CAs = {10,11,12,13,14,15,16,17,18};
	private int[] mDs = {6,4,4,3,3,2,2,1,0};
	private int[] weights = {0,0,0,0,1,1,1,2,2};
	private boolean removeDex;
	private int tempBonus = 0;
	public int TOTALDEF = 4;
	
	public Armor(int modele, int bba, int dxmod, int enchant, Talent talents) {
		
		this.CA = CAs[modele]+enchant;
		this.CA += (talents.possedeTalent(Talent.TALENT_BOUCLIER))? 1 : 0;
		this.CA += (talents.possedeTalent(Talent.TALENT_ESQUIVE))? 1 : 0;
		this.CA += (talents.possedeTalent(Talent.TALENT_EXPERTISE_AU_COMBAT))? bba : 0;
		this.maxDex = mDs[modele];
		this.weight = weights[modele];
		this.nom = noms[modele];

		this.dexBonus = Math.min(dxmod, this.maxDex);
		
	}
	
	public boolean hit (int touche) {
		
		int AC = this.CA;
		AC += (this.removeDex)? 0 : this.dexBonus;
		AC += this.tempBonus;
		return (touche > AC || (touche == 20));
		
	}
	
	public String getNom() {
		
		return this.nom;
		
	}
	
	public int getCA(){
		
		int armure = this.CA;
		armure += (this.removeDex)? 0 : this.dexBonus;
		armure += this.tempBonus;
		
		return armure;
		
	}
	
	public int[] getSpeed(int[] v){
		
		int[] res = {Math.max((v[0]-(this.weight*v[1])),0) , v[1]};
		return  res;	
		
	}
	
	public void endTurn() {
		
		this.removeDex = false;
		this.tempBonus = 0;
		
	}
	
	public boolean dexRemove() {
		
		this.removeDex = true;
		return true;
		
	}
	
	public void totaldef() {
		
		this.tempBonus += TOTALDEF;
		
	}
	
	public void tempbonus(int b) {
		
		this.tempBonus += b;
		
	}
	
}
