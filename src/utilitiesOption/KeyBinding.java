package utilitiesOption;

import ressources.Config;
import javafx.scene.Group;

public class KeyBinding extends Group{
	
	public KeyBinding(){
		for (int k =0 ; k<Config.getControlCodeLength();k++){
			this.getChildren().add(new KeyBindingButton(k));
		}
	}

}