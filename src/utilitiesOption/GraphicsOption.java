package utilitiesOption;


import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import ressources.Config;

/**
 * @author Clément
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
		
		this.getChildren().addAll(new Text(10,40,"Resolution"),new Text(10,80,"Full screen"),switchFullS);
	}

}
