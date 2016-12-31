package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
		
		
	}
	
	
}
