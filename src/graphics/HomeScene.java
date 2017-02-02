package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HomeScene extends Scene{
	
	private Scene scene;
	private static Group root=new Group();
	private Button bStart = new Button();
	
	/**
	 * Creation of the welcome scene 
	 */
	public HomeScene(){
		super(root);
		
		bStart.setText("Start");
        bStart.setPrefWidth(100);
        // ajout des coordonnées pour que le bouton soit bien placé
        //NB: les coordonée sont fonction du groupe menuGroup
        
        
        
        bStart.relocate(0,10);
        //defines the action when the button is pressed
        bStart.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                Main.setScene(1, false);
            }
        });
		
		HomeScene.root.getChildren().add(this.bStart);
		
		// when the F11 key is pressed, the console pops at the foreground
				this.setOnKeyPressed(
			        	new EventHandler<KeyEvent>()
			            {
			                public void handle(KeyEvent e)
			                {
			                    if(e.getCode()==Main.getControlCode(1)){
			                    	if (!Main.dev){
			                    		Main.console.show();
			                    	}
			                    	Main.console.toFront();
			                    }
			                    
			                }
			        });
				// When select and enter pressed
				bStart.setOnKeyPressed(new EventHandler<KeyEvent>()
		        {
		            public void handle(KeyEvent e)
		            {
		                if(e.getCode()==KeyCode.ENTER){
		                	bStart.fire();
		                }
		                
		            }
		        });
		
	}
	/**
	 * Returns the scene
	 * 
	 * @return the Scene (scene) attribute
	 */
	public Scene getScene(){
		return this.scene;
	}
}
