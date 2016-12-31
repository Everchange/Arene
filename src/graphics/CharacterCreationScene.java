package graphics;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CharacterCreationScene extends Scene {
	
	private static Group root=new Group();
	
	public CharacterCreationScene(){
		super(root);
		
		Rectangle bg=new Rectangle(Main.sizeW,Main.sizeH);
		bg.setFill(Color.BLACK);
		
		root.getChildren().add(bg);
	}

}
