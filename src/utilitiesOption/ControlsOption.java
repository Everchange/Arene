package utilitiesOption;

import ressources.Config;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class ControlsOption extends Group{
	
	public ControlsOption(){
		for (int k =0 ; k<Config.getControlCodeLength();k++){
			this.getChildren().add(new KeyBindingButton(k));
			this.getChildren().add(new Text(10,15+KeyBindingButton.getSpaceBetween()*(k+1)+KeyBindingButton.getButtonH()*k,
					Config.getControl(k).getName().toString()));
		}
	}

}