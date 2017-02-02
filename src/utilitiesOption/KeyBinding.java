package utilitiesOption;

import graphics.Main;
import javafx.scene.Group;

public class KeyBinding extends Group{
	
	public KeyBinding(){
		for (int k =0 ; k<Main.getControlCodeLength();k++){
			this.getChildren().add(new KeyBindingButton(k));
		}
	}

}
