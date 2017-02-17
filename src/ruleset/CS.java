package ruleset;

import java.util.Random;


public class CS {
	
	// 0 - FOR, 1 - DEX, 2 - CON, 3 - INT, 4 - SAG, 5 - CHA
	protected int[] attributs = new int[6];
	protected int[] mods = new int[6];
	protected int BBA=1, reductionDegats;
	protected int[] vitesse;									// La première valeur est la vitesse, la deuxième un booléen indiquant si les armures réduisent la vitesse ou non
	protected Weapon arme;
	protected Armor armure;
	protected Race race;
	protected int pvMax = 10;
	protected int initiativeBonus = 0;
	protected int feinteBonus = 0;
	protected int feinteDebufff = 0;
	protected int racialHatred;
	protected int[] racialHatredbonus = new int[2];
	protected boolean enTenaille = false;
	protected Random RNG;
	protected Talent talents;
	public int DEFENSIVE = 1;
	public int DEFENSIVE_ATMALUS;
	public int CHARGE = 2;
	public int CHARGE_ARREMOVAL = -2;
	public int DEFENSIVE_ARBONUS = 2;
	
	int[] RACIALBONUS1 = {1,0};
	int[] RACIALBONUS2 = {2,2};
	
	public CS(int[] att, int armr, int ra, int arm, int armrenchant, int armenchant, int rH, int[] tal) {
		
		this.attributs = att;
		this.racialHatred = rH;
		
		this.talents = new Talent(tal);
		
		this.racialHatredbonus = (this.talents.possedeTalent(Talent.TALENT_ENNEMI_FAVORI))? this.RACIALBONUS2 : this.RACIALBONUS2 ;
		
		this.race = new Race(ra);
		this.vitesse = race.getSpeed();
		this.attributs = race.changeStats(this.attributs);
		this.mods = getMods(this.attributs);																	//On obtient les modificateurs des attributs
		pvMax += this.mods[2];
		pvMax += (this.talents.possedeTalent(Talent.TALENT_ROBUSTESSE))? mods[2]+1 : 0;
		
		this.armure = new Armor(armr, this.BBA, this.mods[1], armrenchant, this.talents);					//On créée l'armure
		this.vitesse = this.armure.getSpeed(this.vitesse);													//On calcule la vitesse après ralentissement ou non de l'armure
		this.vitesse[0] += (this.talents.possedeTalent(Talent.TALENT_RAPIDITE))? 1 : 0;
		
		this.arme = new Weapon(arm, this.BBA, this.mods, armenchant, this.talents);
		
		this.initiativeBonus = this.mods[1];
		this.initiativeBonus += (this.talents.possedeTalent(Talent.TALENT_RAPIDITE))? 20 : 0;
		this.reductionDegats += (this.talents.possedeTalent(Talent.TALENT_DUR_A_CUIRE))? 1 : 0;
		
	}
	
	/* Prend en argument les attributs du personnage et renvoie les modificateurs associés dans un tableau d'entiers de taille 6 */
	private int[] getMods(int[] att) {
		
		int[] res = new int[6];
		
		for (int i=0 ; i<6 ; i++) {
		
			res[i] = Math.floorDiv((att[i]-10), 2);
			
		}
		
		return res;
		
	}
	
	public void switchEnTenaille() {
		
		this.enTenaille = !this.enTenaille && !this.talents.possedeTalent(Talent.TALENT_ESQUIVE_INSTINCTIVE);
		
	}
	
	public boolean getEnTenaille(){
		
		return this.enTenaille;
		
	}
	
	public int getRace() {
		
		return this.race.getRaceID();
		
	}
	
	public Armor getArmor() {
		
		return this.armure;
		
	}
	
	public int getVitesse() {
		
		return this.vitesse[0];
		
	}
	
	public int getPVMax() {
		
		return this.pvMax;
		
	}
	
	public int rollInitiative() {
		
		return (RNG.nextInt(20)+1+this.initiativeBonus);
		
	}

}
