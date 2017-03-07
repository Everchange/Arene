package graphics;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ressources.Config;

public class CharacterCreationScene extends ArenaScene {
	
	private static Group root=new Group();
	
	public CharacterCreationScene(){
		super(root);
		
		Rectangle bg=new Rectangle(Config.getSizeW(),Config.getSizeH());
		bg.setFill(Color.BLACK);
		
		root.getChildren().add(bg);
	}
	
	@Override
	public void updateLang(){
		
	}

}
