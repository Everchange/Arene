package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OptionStage extends Stage {
	
	
	/**
	 * Creation of the OptionStage
	 */
	public OptionStage(){
		
		
		Group root = new Group();
		
		//(Rage)Quit button
        Button bQuit = new Button();
        bQuit.setText("Close");
        // ajout des coordonnées pour que le bouton ne soit pas sur l'image
        bQuit.relocate(80, 10);
        //defines the action when the button is pressed
        bQuit.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                OptionStage.this.close();
            }
        });
        
        root.getChildren().add(bQuit);
		
		Scene optionScene =new Scene(root);
		
		optionScene.setFill(Color.BEIGE);
		this.setScene(optionScene);
		this.setHeight(200);
		this.setWidth(200);
	}

}