package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class HomeScene extends Scene{
	
	private Scene scene;
	private static Group root=new Group();
	private Button bStart = new Button();
	
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
		
	}
	
	public Scene getScene(){
		return this.scene;
	}
}
