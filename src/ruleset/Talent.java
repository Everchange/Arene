package ruleset;


public class Talent {
	
	private int[] talentsChoisis;
	private int nombreTalents;
	
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
