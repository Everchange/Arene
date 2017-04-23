package graphics;

import java.util.ArrayList;

import character.ArenaCharacter;
import character.CharacterGUI;
import character.CharacterGUIHov;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import ressources.Beacon;
import ressources.Config;

public class FieldScene extends ArenaScene {
	private static Group root=new Group();
	private boolean escapeOn=false;
	private Menu menu;
	private Group characterGp=new Group();
	private CharacterGUI characterGUIGroup=new CharacterGUI();
	private Group characterGUIHOVGroup=new Group();
	private Group pathGroup=new Group();
	private ArrayList<ArenaCharacter> charac = new ArrayList<ArenaCharacter>();
	public static boolean drawPath=false;
	private Group backGround = new Group();


	/**
	 * Creation of the scene where the combat takes place
	 */
	public FieldScene(){

		super(root);
		
		this.menu=new Menu();

		// load the image for the background
		Image bg =new Image(Beacon.class.getResourceAsStream("field.png"));

		//FieldScene.root.getChildren().add(canvas);

		backGround.getChildren().add(new ImageView(bg));
		FieldScene.root.getChildren().add(backGround);
		
		
		
		FieldScene.root.getChildren().add(characterGp);
		characterGp.toFront();

		FieldScene.root.getChildren().add(pathGroup);
		
		FieldScene.root.getChildren().add(characterGUIHOVGroup);

		//set background color
		this.setFill(Color.GREY);


		//key events 
		this.setOnKeyPressed(
				new EventHandler<KeyEvent>()
				{
					public void handle(KeyEvent e)
					{
						if(e.getCode()==Config.getControlCode(Config.ESCAPE_KEY_INDEX) && !FieldScene.this.escapeOn){
							//if menu off and escape pressed
							FieldScene.root.getChildren().add(FieldScene.this.menu.getMenuGroup());
							FieldScene.this.menu.getMenuGroup().toFront();
							FieldScene.this.escapeOn=true;
							//remove the path if there was a path drawn
							FieldScene.this.pathGroup.getChildren().clear();
							FieldScene.this.setOnMouseClicked(null);
							FieldScene.this.setOnMouseMoved(null);
						}
						else if(e.getCode()==Config.getControlCode(Config.ESCAPE_KEY_INDEX) && FieldScene.this.escapeOn){
							// if escape pressed and menu on
							FieldScene.this.menu.getMenuGroup().toBack();
							FieldScene.root.getChildren().remove(FieldScene.this.menu.getMenuGroup());
							
							FieldScene.this.escapeOn=false;

						}
						if(e.getCode()==Config.getDevControlCode(0)){
							// console
							if (!Config.inDev){
								Main.console.show();
							}
							Main.console.toFront();
						}

					}
				});

		this.backGround.toBack();
		//just to be sure that the image is in the back ground

		this.characterGUIGroup.center();
		FieldScene.root.getChildren().add(characterGUIGroup);
		
	}


	/**
	 * Removes the escape menu node of the scene
	 */
	public void closeMenu(){
		FieldScene.root.getChildren().remove(FieldScene.this.menu.getMenuGroup());
		this.escapeOn=false;
	}
	/**
	 * Getter for the attribute characterGp that contains the character that are currently displayed
	 * @return A Group object
	 */
	public Group getCharacterGroup(){
		return this.characterGp;
	}
	/**
	 * Allows to add a character on the field
	 * 
	 * @param charact an ArenaCharacter object to display
	 * @param coords the coordinates of the charater
	 */
	public void addCharacterToField(ArenaCharacter charact,double[] coords){
		this.charac.add(charact);
		// add  the character to the character group
		this.characterGp.getChildren().add(charact.getRepresentationOnField());
		//we retrieve the add character
		this.characterGp.getChildren().get(this.characterGp.getChildren().size()-1).relocate(coords[0], coords[1]);
		// we make sure that the character is in front of the field background
		this.characterGp.toFront();
		//we set the id of the character to the correct one
		charact.setFieldId(this.charac.indexOf(charact));
		//if the GUI was empty
		if (this.characterGUIGroup.isSetToNull()){
			this.characterGUIGroup.setCharacterToDisplay(charact);
		}
	}
	
	/**
	 * Allows to add a character on the field
	 * 
	 * @param charact an ArenaCharacter object to display
	 */
	public void addCharacterToField(ArenaCharacter charact){
		addCharacterToField(charact,charact.getPosition());
	}
	

	/**
	 * Allows to add a list of characters on the field
	 * 
	 * @param charact an ArenaCharacter array object to display
	 */
	public void addCharacterToField(ArenaCharacter[] charact){
		for (int k =0 ; k<charact.length;k++){
		addCharacterToField(charact[k],charact[k].getPosition());
		}
	}

	public void displayCharacterGUI(ArenaCharacter ac){
		//we set the new element to display
		this.characterGUIGroup.setCharacterToDisplay(ac);

		//just to be sure that the GUI is in front of everything
		this.characterGUIGroup.toFront();
		this.setOnMouseClicked(evt->{
			System.out.println(evt.getTarget().getClass().getPackage());
			//if the target is not a character
			if (evt.getButton().equals(MouseButton.PRIMARY) &&( evt.getTarget().getClass()==javafx.scene.canvas.Canvas.class || evt.getTarget().getClass().getPackage()==this.getClass().getPackage())){
				//we clean the group
				this.characterGUIGroup.reset();
			}
		});
	}


	public void displayCharacterGUIHov(ArenaCharacter ac) {
		if(!this.characterGUIHOVGroup.getChildren().isEmpty()){
			//if there was something displayed as a GUI before, we delete it
			this.characterGUIHOVGroup.getChildren().clear();
		}
		//we set the new element to display
		this.characterGUIHOVGroup.getChildren().add(new CharacterGUIHov(ac));

		//just to be sure that the GUI is in front of everything
		this.characterGUIHOVGroup.toFront();

	}

	public void removeCharacterGUIHov(){
		// when the mouse exit the character we remove the display
		this.characterGUIHOVGroup.getChildren().clear();
		this.characterGUIHOVGroup.toBack();
	}

	public void drawPath(double[] startCoord, ArenaCharacter aC, double[]  startMouseCoord){
		FieldScene.drawPath=true;
		
		//we set the GUI character to the selected one
		this.displayCharacterGUI(aC);
		//just to be sure that there is no path already drawn
		this.pathGroup.getChildren().clear();
		//first line
		Line sPath= new Line(startCoord[0],startCoord[1],startMouseCoord[0]-1,startMouseCoord[1]-1);
		sPath.setStroke(Color.RED);
		this.pathGroup.getChildren().add(sPath);
		//the path is the first element that is display
		this.pathGroup.toFront();
		
		this.setOnMouseMoved(evt->{
			this.pathGroup.getChildren().clear();
			//-1 to prevent some graphical bugs
			Line path= new Line(startCoord[0],startCoord[1],evt.getSceneX()-1,evt.getSceneY()-1);
			//color of the line that represent the path
			path.setStroke(Color.RED);
			//add the line to a group that is displayed on the field
			this.pathGroup.getChildren().add(path);
			
			
		});
		
		this.setOnMouseClicked(evt->{
			evt.consume();
			//relocate the character if we right click to an other location
			if (evt.getButton()==MouseButton.SECONDARY){
				//we relocate the character
				
				double[] newPos={evt.getSceneX(), evt.getSceneY()};
				
				
				if (isCharacter(evt.getSceneX(), evt.getSceneY(),aC)){
					//if there is a character at the given position
					ArenaCharacter onLocation=getCharac(evt.getSceneX(), evt.getSceneY(),aC);
					newPos=onLocation.getCenter();
					//next condition : bug fix when we want to relocate a character next to an other which result
					//in moving the character that we to relocate outside of the bounds or behind the bottom GUI 
					if ((aC.getSize()[1]+newPos[1]>Config.getSizeH())||((newPos[0]>Config.getSizeW()/2-250 && newPos[0]<Config.getSizeW()/2+250) && newPos[1]+aC.getSize()[1]>Config.getSizeH()-100)){
						// out of bounds									//next to the menu of the character (the bottom GUI)
						newPos=null;
					}else{
					//we center the two characters
					newPos[1]+=getCharac(evt.getSceneX(), evt.getSceneY(),aC).getSize()[1]/2;
					}
				}
				
				
				
				if (aC.relocate(newPos)){
					this.pathGroup.getChildren().clear();
					this.pathGroup.toBack();
					aC.toFront();
					this.setOnMouseMoved(null);
				}
			}
			else{
				//if the user doesn't right click again but left click
				this.pathGroup.getChildren().clear();
				this.pathGroup.toBack();
				this.setOnMouseMoved(null);
				aC.lockMovement();
			}
			
			FieldScene.drawPath=false;
		});
	}

	public void removeCharacterGUI(){
		FieldScene.root.getChildren().remove(this.characterGUIGroup);
	}
	
	public int getIndexCharac(String name){
		for(int k=this.charac.size()-1 ; k>-1 ; k--){
			if (this.charac.get(k).getName().equals(name)){
				return k;
			}
		}
		return -1;
	}
	
	/**
	 * perform the given action on the given character 
	 * @param ac
	 * @param actionString
	 * @return
	 */
	public boolean targetCharac(ArenaCharacter ac, String actionString){
		if (ac==null){
			return false;
		}
		boolean ret=false;
		String[] action = actionString.split(" ");
		//if some of the previous elements of the array are almost null
		int plus=0;//control variable
		for (int k =0 ; k<action.length ; k++){
			if(action[k].equals("") && plus+k<action.length){
				//the element k become the next element not null 
				while(action[k+plus].equals("") && k+plus+1<action.length){
					//while the next element is null or the end of the list hasn't been reach
					plus++;
				}
				action[k]=action[k+plus];
				//we empty the element that is now at the position k
				action[k+plus]="";
			}
		}
		switch(action[0]){
		case("healf"):
			ac.healF();
			ret=true;
			break;
		case("hurt"):
			if (action.length>1){
				try{
				ac.damage(Integer.valueOf(action[1]));
				this.displayCharacterGUI(ac);
				ret=true;
				}catch(NumberFormatException ex){
					Main.console.println("Error, unable to transform the argument \""+action[1]
							+"\" into an integer");
				}
			}
		break;
		case("describe"):
			Main.console.println(ac.toString());
			ret=true;
			break;
		case("kill"):
			Main.console.println("Killed "+ac.getName());
			ac.damage(ac.getCurrentHp());
			//we don't want to record this command as it can't be triggered one again
			ret=false;
			break;
		default :
			Main.console.println("Unknown action : "+action[0]);
			break;
		}
		this.characterGUIGroup.update();
		return ret;
	}
	
	public boolean targetCharac(String name, String action){
		return targetCharac(getCharac(name),action);
	}
	
	public int getNumberCharOnField(){
		return this.charac.size();
	}
	
	public String getCharName(int index){
		if (index<this.charac.size()){
			return this.charac.get(index).getName();
		}
		return "";
	}
	
	/**
	 * return all the characters on the field
	 * @return
	 */
	public ArrayList<ArenaCharacter> getCharOnField(){
		return this.charac;
	}
	
	/**
	 * return true if there is a character on the given coordinates
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isCharacter(double x, double y){
		for (int k =0 ; k<this.charac.size(); k++){
			ArenaCharacter aC=this.charac.get(k);
			//if a character is on this position
			if ((x>aC.getPosition()[0] && x<aC.getPosition()[0]+aC.getSize()[0]) && (y>aC.getPosition()[1] && y<aC.getPosition()[1]+aC.getSize()[1])){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * return true if there is a character on the given coordinates with escluding a character
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isCharacter(double x, double y,ArenaCharacter pAC){
		for (int k =0 ; k<this.charac.size(); k++){
			ArenaCharacter aC=this.charac.get(k);
			//if a character is on this position
			if (((x>aC.getPosition()[0] && x<aC.getPosition()[0]+aC.getSize()[0]) &&
					(y>aC.getPosition()[1] && y<aC.getPosition()[1]+aC.getSize()[1]))&& !aC.equals(pAC)){
				//System.out.println(x+" in ["+aC.getPosition()[0]+","+(aC.getPosition()[0]+aC.getSize()[0])+"]");
				//System.out.println(y+" in ["+aC.getPosition()[1]+","+(aC.getPosition()[1]+aC.getSize()[1])+"]");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * return the character at the given coordinates
	 * @param x
	 * @param y
	 * @return
	 */
	public ArenaCharacter getCharac(double x, double y){
		for (int k =0 ; k<this.charac.size(); k++){
			//if a character is on this position
			if ((x>this.charac.get(k).getPosition()[0] && x<this.charac.get(k).getPosition()[0]+this.charac.get(k).getSize()[0]) 
					&& (y>this.charac.get(k).getPosition()[1] && y<this.charac.get(k).getPosition()[1]+this.charac.get(k).getSize()[1])){
				return this.charac.get(k);
			}
		}
		return null;
	}
	
	/**
	 * return the character at the given coordinates that is not pAc
	 * @param x
	 * @param y
	 * @return
	 */
	public ArenaCharacter getCharac(double x, double y,ArenaCharacter pAc){
		for (int k =0 ; k<this.charac.size(); k++){
			//if a character is on this position
			if (((x>this.charac.get(k).getPosition()[0] && x<this.charac.get(k).getPosition()[0]+this.charac.get(k).getSize()[0]) 
					&& (y>this.charac.get(k).getPosition()[1] && y<this.charac.get(k).getPosition()[1]+this.charac.get(k).getSize()[1]))
					&& !this.charac.get(k).equals(pAc)){
				return this.charac.get(k);
			}
		}
		return null;
	}
	
	@Override
	/**
	 * Update the language in all the components of this scene 
	 */
	public void updateLang(){
		this.menu.updateLang();
	}
	@Override
	/**
	 * resize the background and recenter the GUI
	 */
	public void resize(double[] size){
		((ImageView)this.backGround.getChildren().get(0)).setFitWidth(size[0]);
		((ImageView)this.backGround.getChildren().get(0)).setFitHeight(size[1]);
		this.characterGUIGroup.center();
	}
	
	/**
	 * Return the character at the given index in the array list if it exists
	 * @param index 
	 * @return ArenaCharacter at the given index or a null object 
	 */
	public ArenaCharacter getCharac(int index){
		if (index<this.charac.size()){
			return this.charac.get(index);
		}
		else{
			return null;
		}
	}
	/**
	 * Return the character named "name" if it exists
	 * @param name 	The name of the character
	 * @return ArenaCharacter at the given index or a null object 
	 */
	public ArenaCharacter getCharac(String name){
		for(int k=this.charac.size()-1 ; k>-1 ; k--){
			if (this.charac.get(k).getName().equals(name)){
				return this.charac.get(k);
			}
		}
		return null;
	}
	
	/**
	 * A way to known the composition of the field and to export it in a txt file
	 * @return a string representation of the field
	 */
	public String getFieldStateString(){
		return " ";
	}


	public void removeCharacter(ArenaCharacter arenaCharacter) {
		//we reset the character GUI
		if (this.charac.size()==1){
			this.characterGUIGroup.reset();
		}
		else{
			for (ArenaCharacter ac : this.charac){
				if (!ac.isEnemy()){
					this.characterGUIGroup.setCharacterToDisplay(ac);
					break;
				}
			}
		}
		//the method "toBack" is used to prevent a bug in which a phantom character is left on the field
		arenaCharacter.getRepresentationOnField().toBack();
		this.characterGp.getChildren().remove(arenaCharacter.getRepresentationOnField());
		//update the field
		this.charac.remove(arenaCharacter);
		//if the name of this character was displayed
		if (Config.alwaysDisplayNames()){
			this.characterGUIHOVGroup.getChildren().clear();
			this.DisplayAllNames();
		}
	}

	/**
	 * method to display all the character's names
	 */
	public void DisplayAllNames() {
		//clear the group
		this.characterGUIHOVGroup.getChildren().clear();
		//add each name tag
		for (ArenaCharacter ac : this.charac){
			this.characterGUIHOVGroup.getChildren().add(new CharacterGUIHov(ac));
		}
		//just to correct a bug in which we can't see the new name tags 
		this.characterGUIHOVGroup.toBack();
		this.characterGUIHOVGroup.toFront();
		//just o be sure that the menu is still in front of everything
		if(escapeOn){
			FieldScene.this.menu.getMenuGroup().toFront();
		}
	}

/*
	public boolean testOutOfBounds(ArenaCharacter aCMoving,double targetX, double targetY){
		//if we change the targeted position we check if there is a character at the given coordinate
		for (int k=0; k<charac.size();k++){
			ArenaCharacter aC=charac.get(k);
			if(!this.equals(aC)){
				if (targetX>aC.getPosition()[0] && targetX<aC.getPosition()[0]+aC.getSize()[0]){
					if (targetY>aC.getPosition()[1]&& targetY<aC.getPosition()[1]+aC.getSize()[1]){
						//if there is a character
						if (aC.getPosition()[1]+aC.getSize()[1]/2+aCMoving.getSize()[1]>Config.getSizeH()){
							System.out.println("out of bounds");
							return true;
						}
					}
				}
			}
		}
		return false;
	}*/


}
