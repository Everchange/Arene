package graphics;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Menu {
	
	protected Group menuGroup=new Group();
	
	public Menu(){
		
		this.menuGroup.resizeRelocate(200, 100 , 200, 130);
		
		 Rectangle panel = new Rectangle(0,0 ,200,300 );
	     panel.setFill(Color.DARKGRAY);
	     this.menuGroup.getChildren().add(panel);   
		
		
		//(Rage)Quit button
        Button bQuit = new Button();
        bQuit.setText("Quit");
        // ajout des coordonnées pour que le bouton ne soit pas sur l'image
        bQuit.relocate(80, 10);
        //defines the action when the button is pressed
        bQuit.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        this.menuGroup.getChildren().add(bQuit);
        
	}
	
	public Group getMenuGroup(){
		return this.menuGroup;
	}
	
}
