package utilitiesOption;


import graphics.Main;
import graphics.OptionScene;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

public class KeyBindingButton extends Button{
	
	private static double buttonW=80,buttonH=25,spaceBetween=10,xOffset=50;
	static final String waitTxt="> ? <";

	
	
	/**
	 * Create a button that is ready 
	 * 
	 * @param k the index of the  Key code of the Main.controlsCodes list
	 */
	public KeyBindingButton(int k){
		
		super();
		this.setText(Main.getControlCode(k).getName());
		
		this.relocate(xOffset, spaceBetween*(k+1));
		this.setPrefSize(buttonW, buttonH);
		
		
		//we set the action when clicked
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//we are waiting for a new key 
				KeyBindingButton.this.setText(waitTxt);
				
				
				KeyBindingButton.this.setOnKeyPressed(new EventHandler<KeyEvent>()
				{
					public void handle(KeyEvent e)
					{
						//we change the key code
						Main.setControlCode(0, e.getCode());
						//we reset the text
						KeyBindingButton.this.setText(Main.getControlCode(0).getName());
						// we remove the focus from the button
						OptionScene.removeFocus();
					}
				});
				
				//when the focus is lost and the user had to press a new key but he didn't, we reset the text
				KeyBindingButton.this.focusedProperty().addListener((observable, oldValue, newValue) -> {
					KeyBindingButton.this.setText(Main.getControlCode(k).getName());
				});
				
			}
		});
		
		
		
	}


	/**
	 * @param buttonW the buttonW to set
	 */
	public static void setButtonW(double buttonW) {
		KeyBindingButton.buttonW = buttonW;
	}


	/**
	 * @param buttonH the buttonH to set
	 */
	public static void setbuttonH(double buttonH) {
		KeyBindingButton.buttonH = buttonH;
	}


	/**
	 * @param spaceBetween the spaceBetween to set
	 */
	public static void setSpaceBetween(double spaceBetween) {
		KeyBindingButton.spaceBetween = spaceBetween;
	}


	/**
	 * @param xOffset the xOffset to set
	 */
	public static void setxOffset(double xOffset) {
		KeyBindingButton.xOffset = xOffset;
	}
}
