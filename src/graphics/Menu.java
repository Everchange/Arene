package graphics;

import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Menu {
	
	protected Group menuGroup=new Group();
	private OptionStage optSta=new OptionStage();
	private int buttonWidth=100;
	private int buttonHeight=24;
	private Button bQuit = new Button(), bOption = new Button(), bBack=new Button(),bCreate=new Button(), bRestart=new Button();
	
	
	public Menu(){
		
		
		int buttonsCenter=(int)(Main.sizeH/2-buttonHeight);
		this.buttonWidth=(int)(Main.sizeW/4);
		
		System.out.println("Main.sizeW = "+Main.sizeW);
		System.out.println("button width : "+Main.sizeW/4);
		
		this.menuGroup.resizeRelocate(0,0,Main.sizeW,Main.sizeH);
		
		 Rectangle panel = new Rectangle(0,0,Main.sizeW,Main.sizeH);//Main.sizeH);
	     panel.setFill(Color.rgb(0,0,0,0.7));
	     this.menuGroup.getChildren().add(panel);   
		
		
		
        
        
      //Option button
        
        bOption.setText("Option");
        bOption.setPrefWidth(buttonWidth);
        // ajout des coordonnées pour que le bouton soit en dessous du premier
        bOption.relocate((int)(Main.sizeW/2-(buttonWidth/2)), buttonsCenter-40);
        //defines the action when the button is pressed
        bOption.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                 Main.setScene(3,false);;
            }
        });
        
        this.menuGroup.getChildren().add(bOption);
        
        //button to create a new character
        
        bCreate.setText("Create character");
        bCreate.setPrefWidth(buttonWidth);
        // ajout des coordonnées pour que le bouton soit en dessous du premier
        bCreate.relocate((int)(Main.sizeW/2-(buttonWidth/2)), buttonsCenter);
        //defines the action when the button is pressed
        bCreate.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                 
            }
        });
        
        this.menuGroup.getChildren().add(bCreate);
        
        //back to field button
        bBack.setText("Back to game");
        bBack.setPrefWidth(buttonWidth);
        // ajout des coordonnées pour que le bouton soit en dessous du second
        bBack.relocate((int)(Main.sizeW/2-(buttonWidth/2)), buttonsCenter-80);
        //defines the action when the button is pressed
        bBack.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	Main.fieldScene.closeMenu();
            }
        });
        
        this.menuGroup.getChildren().add(bBack);
        
        //Restart the game
        
        bRestart.setText("Restart");
        bRestart.setPrefWidth(buttonWidth);
        // ajout des coordonnées pour que le bouton soit en dessous du premier
        bRestart.relocate((int)(Main.sizeW/2-(buttonWidth/2)), buttonsCenter+40);
        //defines the action when the button is pressed
        bRestart.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                 
            }
        });
        
        this.menuGroup.getChildren().add(bRestart);
        
        //(Rage)Quit button
        
        bQuit.setText("Quit");
        bQuit.setPrefWidth(buttonWidth);
        // ajout des coordonnées pour que le bouton soit bien placé
        //NB: les coordonée sont fonction du groupe menuGroup
        
        
        
        bQuit.relocate((int)(Main.sizeW/2-buttonWidth/2), buttonsCenter+80);
        //defines the action when the button is pressed
        bQuit.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        bQuit.onMouseEnteredProperty();
        
        
        this.menuGroup.getChildren().add(bQuit);
        
	}
	
	public Group getMenuGroup(){
		return this.menuGroup;
	}
	
}
