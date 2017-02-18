package ruleset;

import java.util.Random;

public class Weapon {
	
	private int bonusToucher;
	private int bonusDegats;
	private Random RNG;
	private int critMultiplicateur;
	private int critRange;
	private boolean ranged;
	private String nom;
	private Damage degats;
	private boolean heavy;
	private int sneakDamage;
	private boolean boutPortant;
	
	private int[] critMultiplicateurs = {2,3,2,2,2,3,2,3,2,3,3,2};
	private int[] critRanges = {2,1,2,1,2,1,2,1,1,1,1,1};
	private String[] nomsArmes = {"Epee à 2 mains", "Grande hache", "Fléau lourd", "Massue", "Epee longue", "Hache d'armes", "Epee courte", "Hachette", "Marteau léger", "Arc court", "Arc long", "Griffes"};
	private boolean[] ranges = {false, false, false, false, false, false, false, false, false, true, true, false};
	private int[] nombreDes = {2,1,1,1,1,1,1,1,1,1,2};
	private int[] nombreFaces = {6,12,10,10,8,8,6,6,4,6,8,4};
	private boolean[] heavys = {true, true, false, false, false, false, false, false, false, false, false, false};
	
	public Weapon(int pos, int bba, int[] mods, int enchant, Talent talents) {
		
		this.critMultiplicateur = this.critMultiplicateurs[pos];
		this.critMultiplicateur += (talents.possedeTalent(Talent.TALENT_MAITRISE_DU_CRITIQUE))? 1 : 0;
		
		this.critRange = this.critRanges[pos];
		this.critRange += (talents.possedeTalent(Talent.TALENT_SCIENCE_DU_CRITIQUE))? 1 : 0;
		
		this.ranged = this.ranges[pos];
		this.nom = this.nomsArmes[pos];
		this.heavy = this.heavys[pos];
		this.boutPortant = talents.possedeTalent(Talent.TALENT_TIR_A_BOUT_PORTANT);
		
		this.bonusToucher = bba + enchant;
		this.bonusToucher += (this.ranged || talents.possedeTalent(Talent.TALENT_ATTAQUE_EN_FINESSE))? mods[1] : mods[2];
		this.bonusToucher += (talents.possedeTalent(Talent.TALENT_ARME_DE_PREDILECTION))? 1 : 0;
		this.bonusToucher += (talents.possedeTalent(Talent.TALENT_ATTAQUE_PUISSANTE))? -1 : 0;
		this.bonusToucher -= (talents.possedeTalent(Talent.TALENT_EXPERTISE_AU_COMBAT))? bba : 0;
		this.bonusToucher -= (talents.possedeTalent(Talent.TALENT_TIR_PRECIS))? 1 : 0;
		
		this.bonusDegats = enchant;
		this.bonusDegats += (this.heavy)? mods[0]*1.5 : mods[0];
		this.bonusDegats += (talents.possedeTalent(Talent.TALENT_ATTAQUE_PUISSANTE))? 2 : 0;
		this.bonusDegats += (talents.possedeTalent(Talent.TALENT_ATTAQUE_INTELLIGENTE))? Math.floorDiv(mods[3], 2) : 0;
		this.bonusDegats += (talents.possedeTalent(Talent.TALENT_ARC_COMPOSITE) && this.ranged)? Math.floorDiv(mods[0], 2) : 0;
		this.bonusDegats += (talents.possedeTalent(Talent.TALENT_TIR_PRECIS) && this.ranged)? 2 : 0;
		
		this.degats = new Damage(this.bonusDegats, this.nombreDes[pos], this.nombreFaces[pos]);
		
	}
	
	public String getName() {
		
		return this.nom;
		
	}
	
	public boolean aDistance(){
		
		return this.ranged;
		
	}
	
	public int attaque(CS ennemy, boolean CaC, int bonustoucher, int bonusdegats, boolean sneaky) {
		
		boolean[] resultat;
		resultat = this.jetTouche(ennemy.getArmor(), this.bonusToucher+bonustoucher);
		
		this.degats.buff(bonusdegats);
		
		if (resultat[0] && !(this.ranged && CaC && !this.boutPortant)) {
			
			if (sneaky)
				sneakDamage = RNG.nextInt(6)+1;
			else
				sneakDamage = 0;
			
			if (resultat[1])
				return Math.max(this.degats.crit(this.critMultiplicateur)+sneakDamage, 1);		// Les attaques réussies font un minimum de 1 dégât
			else
				return Math.max(this.degats.degats()+sneakDamage,1);
		}
		
		return 0;
		
	}
	
	public boolean[] jetTouche(Armor armure, int bonus){
		
		int resultat = 0;
		resultat += RNG.nextInt(20)+1;
		resultat += this.bonusToucher;
		resultat += bonus;
		
		if (resultat >= 21-this.critRange) {
			
			return new boolean[] {true , armure.hit(RNG.nextInt(20)+1 + this.bonusToucher)};
			
		}
		
		return new boolean[] {armure.hit(resultat),false};
		
	}		
}