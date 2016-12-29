package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Console extends Stage {
	
	private static int[] size={300,300};
	
	public Console(){
		
		Group root = new Group();
		
		Text heading =new Text("Project arena (V"+Main.version+")");
		System.out.println(heading.getText().length());
		
		heading.relocate((int)(size[0]/2-heading.getBoundsInLocal().getWidth()/2), 10);
		
		root.getChildren().add(heading);
		Scene optionScene =new Scene(root);
		
		optionScene.setFill(Color.WHITE);
		this.setScene(optionScene);
		this.setHeight(size[0]);
		this.setWidth(size[1]);
		this.show();
	}

}