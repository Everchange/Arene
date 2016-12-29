import java.util.Random;

public class Damage {
	
	private int bonus, nombreDes, nombreFaces;
	private Random RNG;
	
	public Damage(int b, int nd, int nf) {
		
		this.bonus = b;
		this.nombreFaces = nf;
		this.nombreDes = nd;
		
	}
	
	public int degats(int aAjouter) {
		
		int resultat = 0;
		for (int i=0 ; i<this.nombreDes ; i++) {
			
			resultat += RNG.nextInt(this.nombreFaces)+1;
			
		}
		resultat += this.bonus;
		resultat += aAjouter;
		return resultat;
				
		
	}
	
	public int crit(int aAjouter, int multiplicateur){
		
		int resultat = 0;
		for (int i = 0 ; i<multiplicateur ; i++) {
			
			resultat += this.degats(aAjouter);
			
		}
		
		return resultat;
		
	}
}
