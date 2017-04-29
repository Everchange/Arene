package ruleset;


public class Talent {
	
	private int[] talentsChoisis;
	private int nombreTalents;
	
	public final static int TALENT_ARME_DE_PREDILECTION = 0;
	public final static int TALENT_ATTAQUE_PUISSANTE = 1;
	public final static int TALENT_ATTAQUE_EN_FINESSE = 2;
	public final static int TALENT_ATTAQUE_SOURNOISE = 3;
	public final static int TALENT_ATTAQUE_INTELLIGENTE = 4;
	public final static int TALENT_BOUCLIER = 5;
	public final static int TALENT_CHARGE_PUISSANTE = 6;
	public final static int TALENT_ENNEMI_FAVORI = 7;
	public final static int TALENT_ESQUIVE = 8;
	public final static int TALENT_ESQUIVE_INSTINCTIVE = 9;
	public final static int TALENT_EXPERTISE_AU_COMBAT = 10;
	public final static int TALENT_FEINTE = 11;
	public final static int TALENT_RAPIDITE = 12;
	public final static int TALENT_ROBUSTESSE = 13;
	public final static int TALENT_DUR_A_CUIRE = 14;
	public final static int TALENT_MAITRISE_DU_CRITIQUE = 15;
	public final static int TALENT_SCIENCE_DU_CRITIQUE = 16;
	public final static int TALENT_ARC_COMPOSITE = 17;
	public final static int TALENT_TIR_PRECIS = 18;
	public final static int TALENT_TIR_A_BOUT_PORTANT = 19;
	public final static int TALENT_TIR_D_OBSTACLES = 20;
	
	public Talent(int[] args){
		
		this.talentsChoisis = args;
		this.nombreTalents = this.talentsChoisis.length;
		
	}
	
	public boolean possedeTalent(int talentID) {
		
		boolean flag = false;
		int i = 0;
		
		while (!flag && (i < this.nombreTalents)) {
			
			flag = (this.talentsChoisis[i] == talentID);
			i++;
			
		}
		
		return flag;
		
	}

}
