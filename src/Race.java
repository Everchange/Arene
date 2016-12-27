/**
 * 
 *@author Clément
 *@version 1.0
 */

public class Race {
	
	private static String[] races={"Humain","Elfe","Nain","Orque","Squelette"};
	
	protected int race=0;
	
	public Race(int pRace){
		this.race=pRace;
	}
	
	public String getRaceName(){
		return races[this.race];
	}

}
