package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utilitiesOption.*;
import ressources.Config;

public class OptionScene extends Scene{

	private static Group root=new Group(), buttons=new Group(), options=new Group();
	private Button bBack = new Button(),bGraphics=new Button(), bControls =new Button();
	private int buttonWidth=(int)(Config.getSizeW()/4-20);
	//-20 to let a little space between the end of the button and the border
	private int buttonHeight=(int)(40), buttonGap=10;
	/*//keySet is used as a security that will disable all the buttons when the player want to reset a key
	private static boolean keySet=false;*/
	private static ControlsOption cO=new ControlsOption();
	private static GraphicsOption gO= new GraphicsOption();

	/**
	 * Creation of the option scene
	 */
	public OptionScene(){

		super(root);
		
		root.setOnKeyPressed(
	        	new EventHandler<KeyEvent>()
	            {
	                public void handle(KeyEvent e)
	                {
	                    if(e.getCode()==Config.getControlCode(0)){
	                    	Main.setScene(1, false);
	                    }
	                }
	        });

		buttons.resizeRelocate(0, 0, (int)(Config.getSizeW()/4), Config.getSizeH());
		Rectangle panel = new Rectangle(0,0,(int)(Config.getSizeW()/4),Config.getSizeH());
		panel.setFill(Color.GREY);
		buttons.getChildren().add(panel);

		options.resizeRelocate(Config.getSizeW()/4, 0, Config.getSizeW()*(3/4), Config.getSizeH() );
		Rectangle panel2 = new Rectangle(0,0,(int)((Config.getSizeW()*3)/4),Config.getSizeH());
		panel2.setFill(Color.LAVENDER);
		options.getChildren().add(panel2);

		//(Rage)Quit button

		bBack.setText("back");
		bBack.setPrefWidth(buttonWidth);
		bBack.setPrefHeight(buttonHeight);
		// ajout des coordonnées pour que le bouton soit bien placé
		//NB: les coordonée sont fonction du groupe menuGroup



		bBack.relocate(10,buttonGap);
		//defines the action when the button is pressed
		bBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				options.getChildren().clear();
				Rectangle panel2 = new Rectangle(0,0,(int)((Config.getSizeW()*3)/4),Config.getSizeH());
				panel2.setFill(Color.LAVENDER);
				options.getChildren().add(panel2);
				Main.setScene(1, false);
				
			}
		});
		// When select and enter pressed
		bBack.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			public void handle(KeyEvent e)
			{
				if(e.getCode()==KeyCode.ENTER){
					bBack.fire();
				}

			}
		});

		OptionScene.buttons.getChildren().add(this.bBack);

		//the following button allows to enter the menu where we can change the controls

		bControls.setText("controls");
		bControls.setPrefWidth(buttonWidth);
		bControls.setPrefHeight(buttonHeight);
		// ajout des coordonnées pour que le bouton soit bien placé
		//NB: les coordonée sont fonction du groupe menuGroup



		bControls.relocate(10,2*buttonHeight+3*buttonGap);
		//defines the action when the button is pressed
		bControls.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				//we remove everything 
				OptionScene.options.getChildren().clear();
				
				//background
				Rectangle panel = new Rectangle(0,0,(int)(Config.getSizeW()*3/4),Config.getSizeH());
				panel.setFill(Color.LIGHTSKYBLUE);
				OptionScene.options.getChildren().add(panel);
				
				//ad to the option group
				OptionScene.options.getChildren().add(cO);
				
			}
		});
		
		// When select and enter pressed
		bControls.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			public void handle(KeyEvent e)
			{
				if(e.getCode()==KeyCode.ENTER){
					bControls.fire();
				}

			}
		});

		OptionScene.buttons.getChildren().add(this.bControls);

		//graphics
		bGraphics.setText("Graphics");
		bGraphics.setPrefWidth(buttonWidth);
		bGraphics.setPrefHeight(buttonHeight);
		// ajout des coordonnées pour que le bouton soit bien placé
		//NB: les coordonée sont fonction du groupe menuGroup



		bGraphics.relocate(10,2*buttonGap+buttonHeight);
		//defines the action when the button is pressed
		bGraphics.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				//we remove everything 
				OptionScene.options.getChildren().clear();
				
				//background
				Rectangle panel = new Rectangle(0,0,(int)(Config.getSizeW()*3/4),Config.getSizeH());
				panel.setFill(Color.LIGHTSKYBLUE);
				OptionScene.options.getChildren().add(panel);
				
				//ad to the option group
				OptionScene.options.getChildren().add(gO);
				
			}
		});

		// When select and enter pressed
		bGraphics.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			public void handle(KeyEvent e)
			{
				if(e.getCode()==KeyCode.ENTER){
					bGraphics.fire();
				}

			}
		});

		OptionScene.buttons.getChildren().add(this.bGraphics);



		// when the "console key" is pressed, the console pops at the foreground
		this.setOnKeyPressed(
				new EventHandler<KeyEvent>()
				{
					public void handle(KeyEvent e)
					{
						if(e.getCode()==Config.getDevControl(0).getCode()){
							if (!Config.inDev){
								Main.console.show();
							}
							Main.console.toFront();
						}

					}
				});

		OptionScene.root.getChildren().addAll(buttons,options);


	}

	public static void removeFocus(){
		OptionScene.root.requestFocus();
	}


}
