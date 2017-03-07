package character;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CharacterGUIHov extends Group {

	public CharacterGUIHov(ArenaCharacter aC) {
		if (aC.getPosition()[1]-40>0){
		this.relocate(aC.getPosition()[0], aC.getPosition()[1]-40);
		}else{
			this.relocate(aC.getPosition()[0], aC.getPosition()[1]+aC.getSize()[1]+5);
		}
		
		
		this.getChildren().addAll(new Rectangle(100,35,Color.BISQUE),new Text(40,17,aC.getName()));
	}

}
