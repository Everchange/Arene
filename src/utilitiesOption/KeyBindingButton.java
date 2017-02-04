package utilitiesOption;


import java.util.Arrays;

import graphics.OptionScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ressources.Config;

public class KeyBindingButton extends Button{

	private static double buttonW=80,buttonH=25,spaceBetween=10,xOffset=(Config.getSizeW()*3/8)-(buttonW+15);
	static final String waitTxt="> ? <";



	/**
	 * Create a button that is ready 
	 * 
	 * @param k the index of the  Key code of the Main.controlsCodes list
	 */
	public KeyBindingButton(int k){

		super();
		this.setText(Config.getControl(k).getKeyName());

		this.relocate(xOffset, spaceBetween*(k+1)+buttonH*k);
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
						if (!Arrays.asList(Config.blackList).contains(e.getCode())){
							//if the key is not in the black list
							//we change the key code
							Config.setControlCode(k, e.getCode());
							//just not to trigger the event
							e.consume();
							//we reset the text
							KeyBindingButton.this.setText(Config.getControl(k).getName());
							// we remove the focus from the button
							OptionScene.removeFocus();
						}
					}
				});

				//when the focus is lost and the user had to press a new key but he didn't, we reset the text
				KeyBindingButton.this.focusedProperty().addListener((observable, oldValue, newValue) -> {
					KeyBindingButton.this.setText(Config.getControlCode(k).getName());
				});

			}
		});

		this.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			public void handle(KeyEvent e)
			{
				if (e.getCode()==KeyCode.ENTER){
					KeyBindingButton.this.fire();
				}
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
	


	/**
	 * @return the buttonW
	 */
	public static double getButtonW() {
		return buttonW;
	}


	/**
	 * @return the buttonH
	 */
	public static double getButtonH() {
		return buttonH;
	}


	/**
	 * @return the spaceBetween
	 */
	public static double getSpaceBetween() {
		return spaceBetween;
	}


	/**
	 * @return the xOffset
	 */
	public static double getxOffset() {
		return xOffset;
	}

}
