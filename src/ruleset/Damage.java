package ruleset;

import java.util.Random;

public class Damage {
	
	private int bonusDegats, nombreDes, nombreFaces, buff, multiplicateur, total;
	private int[] sousTotaux;
	private int[][] resultat;
	private Random RNG;
	
	public Damage(int b, int nd, int nf, int mult) {
		
		this.bonusDegats = b;
		this.nombreFaces = nf;
		this.nombreDes = nd;
		this.buff = 0;
		this.multiplicateur = mult;
		
	}
	
	public void damage(int crit){
		
		int multiplicateurEffectif = (this.multiplicateur*crit) + (1-crit);
		int temp;
		
		this.total = 0;
		this.sousTotaux = new int[multiplicateurEffectif];
		this.resultat = new int[multiplicateurEffectif][this.nombreDes];
		
		for (int i = 0 ; i < multiplicateurEffectif ; i++) {
			
			for (int j = 0 ; j < this.nombreDes ; j++) {
				
				temp = RNG.nextInt(this.nombreFaces)+1;
				this.resultat[i][j] = temp;
				this.sousTotaux[i] += temp;
				this.total += temp;
				
			}
			
			this.sousTotaux[i] += this.buff+this.bonusDegats;
			this.total += this.buff+this.bonusDegats;
			
		}
		
		this.reset();
		
	}
	
	public void buff (int b) {

		this.buff += b;
		
	}
	
	public int getTotalDamage() {
		
		return this.total;
		
	}
	
	public void reset() {
		
		this.buff = 0;
		
	}
}
