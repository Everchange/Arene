package graphics;

import java.util.ArrayList;

import character.ArenaCharacter;
import character.CharacterGUI;
import character.CharacterGUIHov;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import ressources.Beacon;
import ressources.Config;

public class FieldScene extends Scene {
	private static Group root=new Group();
	private boolean escapeOn=false;
	private static Menu menu=new Menu();
	private Group characterGp=new Group();
	private CharacterGUI characterGUIGroup=new CharacterGUI();
	private Group characterGUIHOVGroup=new Group();
	private Group pathGroup=new Group();
	private ArrayList<ArenaCharacter> charac = new ArrayList<ArenaCharacter>();


	/**
	 * Creation of the scene where the combat takes place
	 */
	public FieldScene(){

		super(root);

		Canvas canvas =new Canvas(735,600);
		canvas.setLayoutX(0);
		canvas.setLayoutY(0);



		// load the image for the background
		Image bg =new Image(Beacon.class.getResourceAsStream("field.png"));

		FieldScene.root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(bg, 0, 0);


		FieldScene.root.getChildren().add(characterGp);
		characterGp.toFront();

		FieldScene.root.getChildren().add(pathGroup);

		//set background
		this.setFill(Color.GREY);


		//key events 
		this.setOnKeyPressed(
				new EventHandler<KeyEvent>()
				{
					public void handle(KeyEvent e)
					{
						if(e.getCode()==Config.getControlCode(0) && !FieldScene.this.escapeOn){
							//if menu off and escape pressed
							FieldScene.root.getChildren().add(FieldScene.menu.getMenuGroup());
							FieldScene.this.escapeOn=true;
						}
						else if(e.getCode()==Config.getControlCode(0) && FieldScene.this.escapeOn){
							// if escape pressed and menu on
							FieldScene.root.getChildren().remove(FieldScene.menu.getMenuGroup());
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

		canvas.toBack();
		//just to be sure that canvas is the back ground

		this.characterGUIGroup.center();
		FieldScene.root.getChildren().add(characterGUIGroup);
		
	}


	/**
	 * Removes the escape menu node of the scene
	 */
	public void closeMenu(){
		FieldScene.root.getChildren().remove(FieldScene.menu.getMenuGroup());
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
	 */
	public void addCharacterToField(ArenaCharacter charact,double[] coords){
		this.charac.add(charact);
		// add  the character to the character group
		this.characterGp.getChildren().add(charact.getRepresentationOnField());
		//we retrieve the add character
		this.characterGp.getChildren().get(this.characterGp.getChildren().size()-1).relocate(coords[0], coords[1]);
		// we make sure that the character is in front of the field background
		this.characterGp.toFront();
		charact.setFieldId(this.characterGp.getChildren().size()-1);
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


	public void displayCharacterGUIHov(CharacterGUIHov cGH) {
		if(!this.characterGUIHOVGroup.getChildren().isEmpty()){
			//if there was something displayed as a GUI before, we delete it
			this.characterGUIHOVGroup.getChildren().clear();
		}
		//we set the new element to display
		this.characterGUIHOVGroup=cGH;

		FieldScene.root.getChildren().add(this.characterGUIHOVGroup);
		//just to be sure that the GUI is in front of everything
		this.characterGUIHOVGroup.toFront();

	}

	public void removeCharacterGUIHov(){
		// when the mouse exit the character we remove the display
		this.characterGUIHOVGroup.getChildren().clear();
	}

	public void drawPath(double[] startCoord, ArenaCharacter aC, double[]  startMouseCoord){
		System.out.println("path from ("+startCoord[0]+","+startCoord[1]+")");
		
		this.pathGroup.getChildren().clear();
		Line sPath= new Line(startCoord[0],startCoord[1],startMouseCoord[0],startMouseCoord[1]);
		sPath.setStroke(Color.RED);
		this.pathGroup.getChildren().add(sPath);
		this.pathGroup.toFront();
		
		this.setOnMouseMoved(evt->{
			this.pathGroup.getChildren().clear();
			Line path= new Line(startCoord[0],startCoord[1],evt.getSceneX(),evt.getSceneY());
			//color of the line that represent the path
			path.setStroke(Color.RED);
			//add the line to a group that is displayed on the field
			this.pathGroup.getChildren().add(path);
			
			
		});
		this.setOnMouseClicked(evt->{
			//relocate the character if we right click to an other location
			if (evt.getButton()==MouseButton.SECONDARY){
				//we relocate the character
				if (aC.relocate(evt.getSceneX(), evt.getSceneY())){
					this.pathGroup.getChildren().clear();
					this.setOnMouseMoved(null);
				}
			}
			else{
				//if the user doesn't right click again but left click
				this.pathGroup.getChildren().clear();
				this.setOnMouseMoved(null);
				aC.lockMovement();
			}
		});
	}

	public void removeCharacterGUI(){
		FieldScene.root.getChildren().remove(this.characterGUIGroup);
	}
	
	public int targetChar(String name){
		for(int k=this.charac.size()-1 ; k>-1 ; k--){
			if (this.charac.get(k).getName().equals(name)){
				return k;
			}
		}
		return -1;
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
	
	public ArrayList<ArenaCharacter> getCharOnField(){
		return this.charac;
	}

}
