package graphics;

import character.ArenaCharacter;
import character.CharacterGUI;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import ressources.Beacon;

public class FieldScene extends Scene {
	private static Group root=new Group();
	private boolean escapeOn=false;
	private static Menu menu=new Menu();
	private Group characterGp=new Group();
	private CharacterGUI characterGUIGroup=new CharacterGUI();
	
	
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
        System.out.println();
        
        FieldScene.root.getChildren().add(canvas);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(bg, 0, 0);
        
        
        FieldScene.root.getChildren().add(characterGp);
        characterGp.toFront();
        
        //set background
        this.setFill(Color.GREY);
        
        
        //key events 
        this.setOnKeyPressed(
        	new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    if(e.getCode()==Main.getControlCode(0) && !FieldScene.this.escapeOn){
                    	// we reset the character GUI
                    	FieldScene.this.characterGUIGroup.getChildren().clear();
                    	//if menu off and escape pressed
                        FieldScene.root.getChildren().add(FieldScene.menu.getMenuGroup());
                        FieldScene.this.escapeOn=true;
                    }
                    else if(e.getCode()==Main.getControlCode(0) && FieldScene.this.escapeOn){
                    	// if escape pressed and menu on
                        FieldScene.root.getChildren().remove(FieldScene.menu.getMenuGroup());
                        FieldScene.this.escapeOn=false;
                    	
                    }
                    if(e.getCode()==Main.getControlCode(1)){
                    	// console
                    	if (!Main.dev){
                    		Main.console.show();
                    	}
                    	Main.console.toFront();
                    }
                    
                }
        });
        
        canvas.toBack();
        //just to be sure that canvas is the back ground

		
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
	public void addCharacterToField(ArenaCharacter charact,int[] coords){
		// add  the character to the character group
		this.characterGp.getChildren().add(charact.getRepresentationOnField());
		//we retrieve the add character
		this.characterGp.getChildren().get(this.characterGp.getChildren().size()-1).relocate(coords[0], coords[1]);
		// we make sure that the character is in front of the field background
		this.characterGp.toFront();
	}
	
	public void displayCharacterGUI(CharacterGUI cG){
		if(!this.characterGUIGroup.getChildren().isEmpty()){
			//if there was something displayed as a GUI before, we delete it
			this.characterGUIGroup.getChildren().clear();
		}
		//we set the new element to display
		this.characterGUIGroup=cG;

        FieldScene.root.getChildren().add(this.characterGUIGroup);
        //just to be sure that the GUI is in front of everything
		this.characterGUIGroup.toFront();
		this.setOnMouseClicked(evt->{
			//if the target is not a character
			if (evt.getButton().equals(MouseButton.PRIMARY) && evt.getTarget().getClass()==javafx.scene.canvas.Canvas.class){
				//we clean the group
				this.characterGUIGroup.getChildren().clear();
			}
		});
	}
	
}
