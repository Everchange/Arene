package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class OptionScene extends Scene{
	
	private static Group root=new Group();
	private Button bQuit = new Button();
	
	public OptionScene(){
		
		super(root);
		
//(Rage)Quit button
        
        bQuit.setText("Quit");
        bQuit.setPrefWidth(100);
        // ajout des coordonnées pour que le bouton soit bien placé
        //NB: les coordonée sont fonction du groupe menuGroup
        
        
        
        bQuit.relocate(0,10);
        //defines the action when the button is pressed
        bQuit.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                Main.setScene(1, false);
            }
        });
		
		OptionScene.root.getChildren().add(this.bQuit);
		
		
		
		
		
		
		
		
	}
	
	
}
