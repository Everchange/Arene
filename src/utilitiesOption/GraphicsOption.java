package utilitiesOption;


import graphics.FieldScene;
import graphics.Main;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import ressources.Config;

/**
 * @author Altaren
 *
 */
public class GraphicsOption extends Group {

	/**
	 * 
	 */
	public GraphicsOption() {
		
		Button switchFullS=new Button();
		switchFullS.setPrefSize(80, 25);
		switchFullS.setText(Boolean.toString(Config.isFullScreen()));
		switchFullS.setOnAction(evt->{
			Config.setFullScreen(!Config.isFullScreen());
			switchFullS.setText(Boolean.toString(Config.isFullScreen()));
		});
		switchFullS.relocate(100, 65);
		
		
		
		Button characName=new Button();
		characName.setPrefSize(80, 25);
		characName.setText(Boolean.toString(Config.alwaysDisplayNames()));
		characName.relocate(100, 105);
		characName.setOnAction(evt->{
			Config.setAlwaysDisplayName(!Config.alwaysDisplayNames());
			//if the value is not set to true
			if(Config.alwaysDisplayNames()){
				((FieldScene) Main.getScene(1)).DisplayAllNames();
			}
			else{
				((FieldScene) Main.getScene(1)).removeCharacterGUIHov();
			}
			characName.setText(Boolean.toString(Config.alwaysDisplayNames()));
		});
		
		this.getChildren().addAll(new Text(10,40,"Resolution"),new Text(10,80,"Full screen"),switchFullS,
				new Text(10,120,"Alway display character names"),characName);
		
	}

}
