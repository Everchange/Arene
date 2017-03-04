package character;

import java.util.Random;

import graphics.FieldScene;
import graphics.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import ressources.Beacon;
import ressources.Config;
import ruleset.CS;
import ruleset.Race;

public class ArenaCharacter {

	
	private Image img;
	private String name;
	private double[] position;
	private int fieldId=-1;
	private double[] size;
	private ImageView representationOnField;
	private boolean isMovable=false;
	private int pvCurrent;
	private int initiative;
	private CS characterSheet;
	private Random RNG;
	
	public int DEFENSIVE = 1;
	public int DEFENSIVE_ATMALUS;
	public int CHARGE = 2;
	public int CHARGE_ARREMOVAL = -2;
	public int DEFENSIVE_ARBONUS = 2;
	
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
	public ArenaCharacter(int[] att, int armr, int raceIndex,String name,String imgPath,double[] position) {
		//CS wait =new CS(att, armr, raceIndex);
		this.position=position;
		this.name=name;
		
		
		double alpha=Math.sqrt(Config.getSizeH())*5;//sqrt because it's a surface
		this.size=new double[] {Race.getRaceSize(raceIndex)[0]*alpha,Race.getRaceSize(raceIndex)[1]*alpha};
		
		
		
		try{
			//path 
			this.img=new Image(imgPath,this.size[0],this.size[1],true,true);
		}catch(Exception e){
			//if the specified image doesn't exist
			this.img=new Image(Beacon.class.getResourceAsStream("defaultCharacter.png"),this.size[0],this.size[1],false,true);
			Main.console.println(this.name+"'s representation set to default");
		}
		
		//this.initiative = Character.rollInitiative();
		//this.pvCurrent = character.getPVMax();
		
		
		this.createRepresentation(this.img);
	}
	
	/**
	 * create an object that can be display on the field
	 * @param img
	 */
	private void createRepresentation(Image img){
		ImageView ret=new ImageView(this.img);
		ret.setOnMouseEntered(evt -> {
			// implement quick view here
        	((FieldScene) Main.getScene(1)).displayCharacterGUIHov(new CharacterGUIHov(this));
        });
		
		ret.setOnMouseExited(evt -> {
			((FieldScene) Main.getScene(1)).removeCharacterGUIHov();
        	
        });
		
		ret.setOnMouseClicked(evt -> {
			//we always display the GUI
			if (evt.getButton()==MouseButton.PRIMARY){
				((FieldScene) Main.getScene(1)).displayCharacterGUI(this);
				evt.consume();
			}
			if (evt.getButton()==MouseButton.SECONDARY && !FieldScene.drawPath){
				//the character can move
				this.unlockMovement();
				//draw path and other ...
				double[] middle={this.position[0]+this.size[0]/2,this.position[1]+this.size[1]/2};
				((FieldScene) Main.getScene(1)).drawPath(middle,this,new double[] {evt.getSceneX(),evt.getSceneY()});
				//just to be sure that nothing unexpected occurs
				evt.consume();
				
			}
			
        	
        });
		
		this.representationOnField=ret;
		
		
	}
	
	
	/**
	 * Returns a character image that can be displayed on the field
	 * 
	 * @return ImageView object
	 */
	public ImageView getRepresentationOnField(){
		return this.representationOnField;
	}
	
	public double[] getPosition(){
		return this.position;
	}
	
	public double getx() {
		
		return this.position[0];
		
	}
	
	public double gety() {
		
		return this.position[1];
		
	}
	
	public String getName(){
		return this.name;
	}
	

	public boolean feinte(ArenaCharacter ennemy) {
		
		if (this.characterSheet.possedeTalent(ruleset.Talent.TALENT_FEINTE)) {
			return ennemy.feinte(this.RNG.nextInt(20)+6+this.characterSheet.getFeinteBonus()-this.characterSheet.getFeinteDebuff());
		}
		else
			return false;
			
	}
	
	public boolean feinte(int roll) {
		
		if (roll > 10+this.characterSheet.getMods()[3]+this.characterSheet.getBBA())
			return this.characterSheet.getArmor().dexRemove();

		else
			return false;
		
	}
	
	public void total_defense() {
		
		this.characterSheet.getArmor().totaldef();
		
	}
		
	public void attack(ArenaCharacter ennemy, int special){
		
		int bonustouchercirconstanciel = (ennemy.getCS().getRace() == this.characterSheet.getRacialHatred())? this.characterSheet.getRacialHatredBonus()[0] : 0;  //bonus de toucher circonstanciel lié à la haine raciale
		bonustouchercirconstanciel -= (special == DEFENSIVE)? this.DEFENSIVE_ATMALUS : 0;
		bonustouchercirconstanciel += (special == CHARGE)? (this.characterSheet.possedeTalent(ruleset.Talent.TALENT_CHARGE_PUISSANTE))? 0 : 2 : 0;
		
		int bonusdegatscirconstanciel = (ennemy.getCS().getRace() == this.characterSheet.getRacialHatred())? this.characterSheet.getRacialHatredBonus()[1] : 0;
		
		if (special == CHARGE)
			this.characterSheet.getArmor().tempbonus(CHARGE_ARREMOVAL);
		if (special == DEFENSIVE)
			this.characterSheet.getArmor().tempbonus(DEFENSIVE_ARBONUS);
		
		bonusdegatscirconstanciel += (special == CHARGE)? (this.characterSheet.possedeTalent(ruleset.Talent.TALENT_CHARGE_PUISSANTE))? 2 : 0 : 0;
		ennemy.TakeDamage(this.characterSheet.getWeapon().attaque(ennemy.getCS(), this.getCaC(ennemy), bonustouchercirconstanciel, bonusdegatscirconstanciel, (ennemy.getCS().getEnTenaille() && this.characterSheet.possedeTalent(ruleset.Talent.TALENT_ATTAQUE_SOURNOISE))));				//On fait prendre à l'ennemi les dégâts infligés par l'attaque (0 si elle a échoué)
		
	}
		
	public void attack(ArenaCharacter ennemy){
			
		int bonustouchercirconstanciel = (ennemy.getCS().getRace() == this.characterSheet.getRacialHatred())? this.characterSheet.getRacialHatredBonus()[0] : 0;  //bonus de toucher circonstanciel lié à la haine raciale
		int bonusdegatscirconstanciel = (ennemy.getCS().getRace() == this.characterSheet.getRacialHatred())? this.characterSheet.getRacialHatredBonus()[1] : 0;
		ennemy.TakeDamage(this.characterSheet.getWeapon().attaque(ennemy.getCS(), this.getCaC(ennemy), bonustouchercirconstanciel, bonusdegatscirconstanciel, (ennemy.getCS().getEnTenaille() && this.characterSheet.possedeTalent(ruleset.Talent.TALENT_ATTAQUE_SOURNOISE))));				//On fait prendre à l'ennemi les dégâts infligés par l'attaque (0 si elle a échoué)
		
	}
	
	public void endTurn() {
		
		this.characterSheet.getArmor().endTurn();
		
		
	}
	
	public boolean getCaC(ArenaCharacter ennemy) {
		
		return ((Math.abs(ennemy.getx() - this.getx()) <= 1) && (Math.abs(ennemy.gety() - this.gety()) <= 1)) ;
		
	}
	
	public void TakeDamage(int damage) {
		
		int damagetaken = Math.max(damage-this.characterSheet.getReductionDegats(), 0);
		this.pvCurrent -= damagetaken;
		
	}
	
	public CS getCS() {
		
		return this.characterSheet;
		
	}
	
	public int getInitiative() {
		
		return this.initiative;
		
	}
	
	public double[] getSize(){
		return this.size;
	}
	public boolean isMovable(){
		return this.isMovable;
	}
	
	public int getFieldId(){
		return this.fieldId;
	}
	
	public void setFieldId(int pId){
		this.fieldId=pId;
	}
	
	public void setCoord(double x, double y){
		this.position=new double[] {x,y};
	}
	
	public void lockMovement(){
		this.isMovable=false;
	}
	
	public void unlockMovement(){
		this.isMovable=true;
	}

	public boolean relocate(double x, double y){
		// if the character can be moved
		if (this.isMovable){

			double targetX=x-this.size[0]/2, targetY=y-this.size[1]/2;

			// if the given coordinates will place the character outside of the window
			if(targetX<0){
				//too far away (left)
				targetX=0;
			}
			if (x+this.size[0]>Config.getSizeW()){
				//too far away (right)
				targetX=Config.getSizeW()-this.size[0];
			}
			if (targetY<0){
				//too high
				targetY=0;
			}
			if (y+this.size[1]/2>Config.getSizeH()){
				//too low
				targetY=Config.getSizeH()-this.size[1];
			}
			
			//we move the representation of the character to the given coordinates
			this.representationOnField.relocate(targetX, targetY);
			// /2 because this is centered on the representation
			// and the character can no longer move
			this.lockMovement();
			this.position=new double[] {targetX, targetY};
			return true;

		}
		return false;
	}
	
	// just to handle an relocate with an array as parameter
	public boolean relocate (double[] pos){
		return relocate(pos[0],pos[1]);
	}
	
	public double[] getCenter(){
		return new double[] {this.position[0]+this.size[0]/2,this.position[1]+this.size[1]/2};
	}
	
	public void toFront(){
		this.representationOnField.toFront();
	}

	
	
}
