package utilitiesOption;


import javafx.scene.Group;
import javafx.scene.text.Text;

/**
 * @author Clément
 *
 */
public class GraphicsOption extends Group {

	/**
	 * 
	 */
	public GraphicsOption() {
		this.getChildren().addAll(new Text(10,40,"Resolution"),new Text(10,80,"Full screen"));
	}

}
