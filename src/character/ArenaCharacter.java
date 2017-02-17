package character;

import graphics.FieldScene;
import graphics.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import ressources.Beacon;
import ruleset.CS;

public class ArenaCharacter extends CS {

	
	private Image img;
	private String name;
	private int[] position;
	private int pvCurrent;
	private int initiative;
	
	/**
	 * Creates a new character that can be displayed on the field as an image is associated with him.
	 * 
	 * @param att the attributes chosen
	 * @param armr the index of the armor
	 * @param ra the index of the race of this character
	 * @param arm the index of the weapon
	 * @param armrenchant number of enchant levels of armor
	 * @param armenchant number of enchant level of weapon
	 * @param rH index of the hated race
	 * @param tal list of the talents
	 * @param name name of the character (this is the one that will be displayed in the 
	 * 				character menu 
	 * @param imgName path to the character's image
	 */
	public ArenaCharacter(int[] att, int armr, int ra, int arm, int armrenchant, int armenchant, int rH, int[] tal,String name,String imgPath,int[] position) {
		
		super(att, armr, ra, arm, armrenchant, armenchant, rH, tal);
		
		this.position=position;
		this.name=name;
		this.initiative = this.rollInitiative();
		this.pvCurrent = this.pvMax;
		
		try{
			double[] size=ruleset.Race.getRaceSize(ra);
			//path 
			this.img=new Image(imgPath,size[0],size[1],true,true);
		}catch(NullPointerException e){
			//if the specified image doesn't exist
			this.img=new Image(Beacon.class.getResourceAsStream("defaultCharacter.png"));
			System.out.println("set to default");
		}catch(IllegalArgumentException e){
			//if the specified path is incorrect
			this.img=new Image(Beacon.class.getResourceAsStream("defaultCharacter.png"));
			
		}
	}
	/**
	 * Returns a character image that can be displayed on the field
	 * 
	 * @return ImageView object
	 */
	public ImageView getRepresentationOnField(){
		ImageView ret=new ImageView(this.img);
		ret.setOnMouseEntered(evt -> {
			// implement quick view here
        	System.out.println("Name of the overflown character : "+this.name);
        	((FieldScene) Main.getScene(1)).displayCharacterGUIHov(new CharacterGUIHov(this));
        });
		
		ret.setOnMouseExited(evt -> {
			((FieldScene) Main.getScene(1)).removeCharacterGUIHov();
        	
        });
		
		ret.setOnMouseClicked(evt -> {
			//we always display the GUI

				((FieldScene) Main.getScene(1)).displayCharacterGUI(new CharacterGUI(this.name,this.position));
				System.out.println("full GUI");
				evt.consume();
			
        	
        });
		
		return ret;
		
		
	}
	
	public int[] getPosition(){
		return this.position;
	}
	
	public int getx() {
		
		return this.position[0];
		
	}
	
	public int gety() {
		
		return this.position[1];
		
	}
	
	public String getName(){
		return this.name;
	}
	
	public boolean feinte(ArenaCharacter ennemy) {
		
		if (this.talents.possedeTalent(ruleset.Talent.TALENT_FEINTE)) {
			return ennemy.feinte(this.RNG.nextInt(20)+6+this.feinteBonus-this.feinteDebufff);
		}
		else
			return false;
			
	}
	
	public boolean feinte(int roll) {
		
		if (roll > 10+this.mods[3]+this.BBA)
			return this.armure.dexRemove();

		else
			return false;
		
	}
	
	public void total_defense() {
		
		this.armure.totaldef();
		
	}
		
	public void attack(ArenaCharacter ennemy, int special){
		
		int bonustouchercirconstanciel = (ennemy.getRace() == this.racialHatred)? this.racialHatredbonus[0] : 0;  //bonus de toucher circonstanciel lié à la haine raciale
		bonustouchercirconstanciel -= (special == DEFENSIVE)? DEFENSIVE_ATMALUS : 0;
		bonustouchercirconstanciel += (special == CHARGE)? (this.talents.possedeTalent(ruleset.Talent.TALENT_CHARGE_PUISSANTE))? 0 : 2 : 0;
		
		int bonusdegatscirconstanciel = (ennemy.getRace() == this.racialHatred)? this.racialHatredbonus[1] : 0;
		
		if (special == CHARGE)
			this.armure.tempbonus(CHARGE_ARREMOVAL);
		if (special == DEFENSIVE)
			this.armure.tempbonus(DEFENSIVE_ARBONUS);
		
		bonusdegatscirconstanciel += (special == CHARGE)? (this.talents.possedeTalent(ruleset.Talent.TALENT_CHARGE_PUISSANTE))? 2 : 0 : 0;
		ennemy.TakeDamage(this.arme.attaque(ennemy, this.getCaC(ennemy), bonustouchercirconstanciel, bonusdegatscirconstanciel, (ennemy.getEnTenaille() && this.talents.possedeTalent(ruleset.Talent.TALENT_ATTAQUE_SOURNOISE))));				//On fait prendre à l'ennemi les dégâts infligés par l'attaque (0 si elle a échoué)
		
	}
		
	public void attack(ArenaCharacter ennemy){
			
		int bonustouchercirconstanciel = (ennemy.getRace() == this.racialHatred)? this.racialHatredbonus[0] : 0;  //bonus de toucher circonstanciel lié à la haine raciale
		int bonusdegatscirconstanciel = (ennemy.getRace() == this.racialHatred)? this.racialHatredbonus[1] : 0;
		ennemy.TakeDamage(this.arme.attaque(ennemy, this.getCaC(ennemy), bonustouchercirconstanciel, bonusdegatscirconstanciel, (ennemy.getEnTenaille() && this.talents.possedeTalent(ruleset.Talent.TALENT_ATTAQUE_SOURNOISE))));				//On fait prendre à l'ennemi les dégâts infligés par l'attaque (0 si elle a échoué)
		
	}
	
	public void endTurn() {
		
		this.armure.endTurn();
		
		
	}
	
	public boolean getCaC(ArenaCharacter ennemy) {
		
		return ((Math.abs(ennemy.getx() - this.getx()) <= 1) && (Math.abs(ennemy.gety() - this.gety()) <= 1)) ;
		
	}
	
	public void TakeDamage(int damage) {
		
		int damagetaken = Math.max(damage-this.reductionDegats, 0);
		this.pvCurrent -= damagetaken;
		
	}

}
