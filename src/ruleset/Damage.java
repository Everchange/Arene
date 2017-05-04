package ruleset;

import java.util.Random;

public class Damage {
	
	private int bonusDegats, nombreDes, nombreFaces, buff, multiplicateur;
	private Random RNG;
	
	public Damage(int b, int nd, int nf, int mult) {
		
		this.bonusDegats = b;
		this.nombreFaces = nf;
		this.nombreDes = nd;
		this.buff = 0;
		this.multiplicateur = mult;
		
	}
	
	public int[] damage(int crit){
		
		int multiplicateurEffectif = (this.multiplicateur*crit) + (1-crit);
		
		int[] resultat = new int[this.nombreDes*multiplicateurEffectif*2+3];
		int total = 0;
		int temp;
		int j;
		
		resultat[1] = (this.bonusDegats+this.buff)*multiplicateurEffectif;
		total += resultat[1];
		
		resultat[2] = this.nombreDes;
		
		for (int i = 0 ; i<this.nombreFaces*multiplicateurEffectif ; i++) {
			
			j = 2*i + 3;
			temp = RNG.nextInt(this.nombreFaces)+1;
			resultat[j] = this.nombreFaces;
			resultat[j+1] = temp;
			total += temp;
		
		}

		this.buff = 0;
		
		resultat[0] = total;
		
		return resultat;
		
	}
	
	public void buff (int b) {

		this.buff += b;
		
	}
}
