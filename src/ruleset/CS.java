package ruleset;

import java.util.Random;
import java.util.Stack;

import character.ArenaCharacter;


public class CS {
	
	// 0 - FOR, 1 - DEX, 2 - CON, 3 - INT, 4 - SAG, 5 - CHA
	protected int[] attributs = new int[6];
	protected int[] mods = new int[6];
	protected int BBA=1, reductionDegats;
	
	protected int[] vitesse;									// La premi�re valeur est la vitesse, la deuxi�me un bool�en indiquant si les armures r�duisent la vitesse ou non
	
	protected int nombreArmes;
	protected int armeActive = 0;
	
	protected Weapon[] arme;
	protected Damage attaqueSournoise = new Damage(0,1,6,1);
	protected Armor armure;
	protected Race race;
	
	protected int pvMax = 10;
	protected int initiativeBonus = 0;
	protected int feinteBonus = 0;
	protected int feinteDebufff = 0;
	
	protected int racialHatred;
	protected int[] racialHatredBonus = new int[2];
	
	protected Random RNG;
	protected Talent talents;
	
	public static  int[] ARMOR_MODIFS = {0, 2, -2};
	public static  int[] ATTACK_MODIFS = {0, -1, 2};
	public static  int[] DAMAGE_MODIFS = {0, 0, 0};

	int[] RACIALBONUS1 = {1,0};
	int[] RACIALBONUS2 = {2,2};
	
	public CS(){
	}
	
	public CS(int[] att, int armr, int ra, int arm[], int armrenchant, int armenchant, int rH, int[] tal) {
		
		this.attributs = att;
		this.racialHatred = rH;
		
		this.talents = new Talent(tal);
		
		this.racialHatredBonus = (this.talents.possedeTalent(Talent.TALENT_ENNEMI_FAVORI))? this.RACIALBONUS2 : this.RACIALBONUS2 ;
		
		this.race = new Race(ra);
		this.vitesse = race.getSpeed();
		this.attributs = race.changeStats(this.attributs);
		this.mods = getMods(this.attributs);																	//On obtient les modificateurs des attributs
		pvMax += this.mods[2];
		pvMax += (this.talents.possedeTalent(Talent.TALENT_ROBUSTESSE))? mods[2]+1 : 0;
		
		this.armure = new Armor(armr, this.BBA, this.mods[1], armrenchant, this.talents);					//On cr��e l'armure
		this.vitesse = this.armure.getSpeed(this.vitesse);													//On calcule la vitesse apr�s ralentissement ou non de l'armure
		this.vitesse[0] += (this.talents.possedeTalent(Talent.TALENT_RAPIDITE))? 1 : 0;
		
		//this.arme = new Weapon[] {new Weapon(arm, this.BBA, this.mods, armenchant, this.talents)};
		System.out.println(this.arme[0]);
	
		this.nombreArmes = arm.length;
		this.arme = new Weapon[this.nombreArmes];
		for (int i = 0 ; i < this.nombreArmes ; i++) {
			
			arme[i] = new Weapon(arm[i], this.BBA, this.mods, armenchant, this.talents);
			
		}
		
		this.initiativeBonus = this.mods[1];
		this.initiativeBonus += (this.talents.possedeTalent(Talent.TALENT_RAPIDITE))? 20 : 0;
		this.reductionDegats += (this.talents.possedeTalent(Talent.TALENT_DUR_A_CUIRE))? 1 : 0;
		
		ATTACK_MODIFS[2] = (this.talents.possedeTalent(ruleset.Talent.TALENT_CHARGE_PUISSANTE))? 0 : 2;
		DAMAGE_MODIFS[2] = (this.talents.possedeTalent(ruleset.Talent.TALENT_CHARGE_PUISSANTE))? 2 : 0;
		
	}
	
	/* Prend en argument les attributs du personnage et renvoie les modificateurs associ�s dans un tableau d'entiers de taille 6 */
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
	
	public Weapon[] getWeapon() {
		
		return this.arme;
		
	}
	
	public Weapon getActiveWeapon() {
		
		return this.arme[this.armeActive];
		
	}
	
	public void setActiveWeapon (int rang) {
		
		this.armeActive = rang;
		
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
	
	public boolean possedeTalent(int talentID) {
		
		return this.talents.possedeTalent(talentID);
		
	}
	
	public int getFeinteBonus() {
		
		return this.feinteBonus;
		
	}
	
	public int getFeinteDebuff() {
		
		return this.feinteDebufff;
		
	}
	
	public int[] getMods() {
		
		return this.mods;
		
	}
	
	public int getBBA() {
		
		return this.BBA;
		
	}
	
	public int getRacialHatred() {
		
		return this.racialHatred;
		
	}
	
	public int[] getRacialHatredBonus() {
		
		return this.racialHatredBonus;
		
	}
	
	public int getReductionDegats() {
		
		return this.reductionDegats;
		
	}
	
	public int[] attaque(CS ennemi, int special){
		
		int crit = 0;
		
		// prend en argument la fiche de l'ennemi, et un entier qui indique si l'attaque est sp�ciale (0 si normal , 1 si defensive, 2 si charge)
		// renvoie un array [r�sultat d20, bonus au toucher, CA de l'ennemi, bonus aux d�g�ts � appliquer si l'attaque touche, (1 si critique, 0 sinon)]
		
		
		// On applique les bonus de toucher �ventuels
		
		//Bonus de haine raciale
		int bonusToucherCirconstanciel = (ennemi.getRace() == this.racialHatred)? this.racialHatredBonus[0] : 0;  //bonus de toucher circonstanciel li� � la haine raciale
		//Bonus li� au type d'attaque
		bonusToucherCirconstanciel += ATTACK_MODIFS[special];
		
		
		// On applique les bonus de d�g�ts �ventuels
		
		//Bonus de haine raciale
		int bonusDegatsCirconstanciel = (ennemi.getRace() == this.racialHatred)? this.racialHatredBonus[1] : 0;
		//Bonus li� au type d'attaque
		bonusDegatsCirconstanciel += DAMAGE_MODIFS[special];
		
		//On applique les modifications temporaires � la CA li�es au type d'attaque
		this.armure.tempbonus(ARMOR_MODIFS[special]);
		
		//On lance le d�
		int lancer = RNG.nextInt(19)+1;
		
		//On calcule le bonus de toucher total
		int bonusToucher = bonusToucherCirconstanciel + this.arme[this.armeActive].getBonusToucher();
		
		//On v�rifie s'il y a critique ou pas
		if (lancer >= 21-this.arme[this.armeActive].getCritRange())
			crit = 1;
		
		
		return new int[]{lancer, bonusToucher, ennemi.getArmor().getCA(), bonusDegatsCirconstanciel, crit};
		
	}
	
	public Damage[] rollDamage(int crit, ArenaCharacter ennemy) {
		
		//Retourne un array qui contient un objet Damage par source de d�g�ts (un pour l'arme, l'autre pour l'attaque sournoise par ex)
		
		int nombreSourceDegats = 1;
		Stack<Damage> pile = new Stack<Damage>();
		
		//On v�rifie s'il y a attaque sournoise
		if (ennemy.enTenaille() && (this.talents.possedeTalent(Talent.TALENT_ATTAQUE_SOURNOISE))) {
			
			pile.push(attaqueSournoise);
			
		}
		
		this.arme[this.armeActive].getDamage().damage(crit);
		pile.push(this.arme[this.armeActive].getDamage());
		
		Damage[] resultat = new Damage[nombreSourceDegats];
		
		
		for (int i = 0 ; i < nombreSourceDegats ; i++) {
			
			resultat[i] = pile.pop();
			
		}		
		
		return resultat;
		
	}
	


	
}
