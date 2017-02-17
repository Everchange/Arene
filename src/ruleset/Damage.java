package ruleset;

import java.util.Random;

public class Damage {
	
	private int bonus, nombreDes, nombreFaces, buff;
	private Random RNG;
	
	public Damage(int b, int nd, int nf) {
		
		this.bonus = b;
		this.nombreFaces = nf;
		this.nombreDes = nd;
		this.buff = 0;
		
	}
	
	public int degats() {
		
		int resultat = 0;
		for (int i=0 ; i<this.nombreDes ; i++) {
			
			resultat += RNG.nextInt(this.nombreFaces)+1;
			
		}
		resultat += this.bonus;
		resultat += this.buff;
		this.buff = 0;
		return resultat;
				
	}
	
	public int degats_crit() {
		
		int resultat = 0;
		for (int i=0 ; i<this.nombreDes ; i++) {
			
			resultat += RNG.nextInt(this.nombreFaces)+1;
			
		}
		resultat += this.bonus;
		resultat += this.buff;
		return resultat;
				
	}
	
	public int crit(int multiplicateur){
		
		int resultat = 0;
		for (int i = 0 ; i<multiplicateur ; i++) {
			
			resultat += this.degats_crit();
			
		}

		this.buff = 0;
		
		return resultat;
		
	}
	
	public void buff (int b) {

		this.buff += b;
		
	}
}
