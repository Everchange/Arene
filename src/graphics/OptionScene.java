package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OptionScene extends Scene{
	
	private static Group root=new Group(), buttons=new Group(), options=new Group();
	private Button bBack = new Button(),bGraphics=new Button();
	private int buttonWidth=(int)(Main.sizeW/4-20);
	private int buttonHeight=(int)(Main.sizeH/4-5*10);
	
	public OptionScene(){
		
		super(root);
		
		buttons.resizeRelocate(0, 0, (int)(Main.sizeW/4), Main.sizeH);
		Rectangle panel = new Rectangle(0,0,(int)(Main.sizeW/4),Main.sizeH);
		panel.setFill(Color.GREY);
		buttons.getChildren().add(panel);
		
		options.resizeRelocate(0, 0, (int)(Main.sizeW/4), Main.sizeH);
		Rectangle panel2 = new Rectangle(0,0,(int)(Main.sizeW/4),Main.sizeH);
		panel2.setFill(Color.LAVENDER);
		options.getChildren().add(panel2);
		
		//(Rage)Quit button
        
        bBack.setText("back");
        bBack.setPrefWidth(buttonWidth);
        bBack.setPrefHeight(buttonHeight);
        // ajout des coordonnées pour que le bouton soit bien placé
        //NB: les coordonée sont fonction du groupe menuGroup
        
        
        
        bBack.relocate(10,10);
        //defines the action when the button is pressed
        bBack.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                Main.setScene(1, false);
            }
        });
     // When select and enter pressed
        bBack.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                if(e.getCode()==KeyCode.ENTER){
                	bBack.fire();
                }
                
            }
        });
		
		OptionScene.buttons.getChildren().add(this.bBack);
		
		//graphics
		bGraphics.setText("Graphics");
		bGraphics.setPrefWidth(buttonWidth);
		bGraphics.setPrefHeight(buttonHeight);
        // ajout des coordonnées pour que le bouton soit bien placé
        //NB: les coordonée sont fonction du groupe menuGroup
        
        
        
		bGraphics.relocate(10,20+buttonHeight);
        //defines the action when the button is pressed
		bGraphics.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
		
		// When select and enter pressed
		bGraphics.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                if(e.getCode()==KeyCode.ENTER){
                	bGraphics.fire();
                }
                
            }
        });
		
		OptionScene.buttons.getChildren().add(this.bGraphics);
		
		
		
		// when the F11 key is pressed, the console pops at the foreground
		this.setOnKeyPressed(
	        	new EventHandler<KeyEvent>()
	            {
	                public void handle(KeyEvent e)
	                {
	                    if(e.getCode()==KeyCode.F11){
	                    	if (!Main.dev){
	                    		Main.console.show();
	                    	}
	                    	Main.console.toFront();
	                    }
	                    
	                }
	        });
		
		OptionScene.root.getChildren().add(buttons);
		
		
	}
	
	
}
