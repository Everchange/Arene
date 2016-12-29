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
	private static OptionStage optSta=new OptionStage();
	
	public Menu(){
		
		this.menuGroup.resizeRelocate(200, 100 , 200, 130);
		
		 Rectangle panel = new Rectangle(0,0 ,200,300 );
	     panel.setFill(Color.DARKGRAY);
	     this.menuGroup.getChildren().add(panel);   
		
		
		//(Rage)Quit button
        Button bQuit = new Button();
        bQuit.setText("Quit");
        // ajout des coordonnées pour que le bouton soit bien placé
        //NB: les coordonée sont fonction du groupe menuGroup
        bQuit.relocate(80, 10);
        //defines the action when the button is pressed
        bQuit.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        this.menuGroup.getChildren().add(bQuit);
        
        
      //Option button
        Button bOption = new Button();
        bOption.setText("Option");
        // ajout des coordonnées pour que le bouton soit en dessous du premier
        bOption.relocate(73, 50);
        //defines the action when the button is pressed
        bOption.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                 Menu.optSta.show();
            }
        });
        
        this.menuGroup.getChildren().add(bOption);
        
	}
	
	public Group getMenuGroup(){
		return this.menuGroup;
	}
	
}
