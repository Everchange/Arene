package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ressources.ArenaText;
import ressources.Config;

public class Menu {

	protected Group menuGroup=new Group();
	private int buttonWidth=100;
	private int buttonHeight=24;
	private Button bQuit = new Button(), bOption = new Button(), bBack=new Button(),bCreate=new Button(), bRestart=new Button();

	/**
	 * Creates the escape menu node
	 */
	public Menu(){


		int buttonsCenter=(int)(Config.getSizeH()/2-buttonHeight);
		this.buttonWidth=(int)(Config.getSizeW()/4);


		this.menuGroup.resizeRelocate(0,0,Config.getSizeW(),Config.getSizeH());

		Rectangle panel = new Rectangle(0,0,Config.getSizeW(),Config.getSizeH());
		//the panel is the filter applied to the field scene when the escape menu is on
		panel.setFill(Color.rgb(0,0,0,0.7));
		//0.7 is the transparency
		this.menuGroup.getChildren().add(panel);   


		//back to field button
		bBack.setText(Config.arenaText.getText(ArenaText.backBt));
		bBack.setPrefWidth(buttonWidth);
		// ajout des coordonnées pour que le bouton soit en dessous du second
		bBack.relocate((int)(Config.getSizeW()/2-(buttonWidth/2)), buttonsCenter-80);
		//defines the action when the button is pressed
		bBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Main.fieldScene.closeMenu();
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
		
		
		this.menuGroup.getChildren().add(bBack);


		//Option button

		bOption.setText(Config.arenaText.getText(ArenaText.optionBt));
		bOption.setPrefWidth(buttonWidth);
		// ajout des coordonnées pour que le bouton soit en dessous du premier
		bOption.relocate((int)(Config.getSizeW()/2-(buttonWidth/2)), buttonsCenter-40);
		//defines the action when the button is pressed
		bOption.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Main.setScene(3,false);;
			}
		});
		// When select and enter pressed
		bOption.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                if(e.getCode()==KeyCode.ENTER){
                	bOption.fire();
                }
                
            }
        });

		this.menuGroup.getChildren().add(bOption);

		//button to create a new character

		bCreate.setText(Config.arenaText.getText(ArenaText.newCharacterBt));
		bCreate.setPrefWidth(buttonWidth);
		// ajout des coordonnées pour que le bouton soit en dessous du premier
		bCreate.relocate((int)(Config.getSizeW()/2-(buttonWidth/2)), buttonsCenter);
		//defines the action when the button is pressed
		bCreate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});
		// When select and enter pressed
		bCreate.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                if(e.getCode()==KeyCode.ENTER){
                	bCreate.fire();
                }
                
            }
        });

		this.menuGroup.getChildren().add(bCreate);



		//Restart the game

		bRestart.setText(Config.arenaText.getText(ArenaText.restartBt));
		bRestart.setPrefWidth(buttonWidth);
		// ajout des coordonnées pour que le bouton soit en dessous du premier
		bRestart.relocate((int)(Config.getSizeW()/2-(buttonWidth/2)), buttonsCenter+40);
		//defines the action when the button is pressed
		bRestart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});
		// When select and enter pressed
		bRestart.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                if(e.getCode()==KeyCode.ENTER){
                	bRestart.fire();
                }
                
            }
        });

		this.menuGroup.getChildren().add(bRestart);

		//(Rage)Quit button

		bQuit.setText(Config.arenaText.getText(ArenaText.quitBt));
		bQuit.setPrefWidth(buttonWidth);
		// ajout des coordonnées pour que le bouton soit bien placé
		//NB: les coordonée sont fonction du groupe menuGroup



		bQuit.relocate((int)(Config.getSizeW()/2-buttonWidth/2), buttonsCenter+80);
		//defines the action when the button is pressed
		bQuit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Main.quit();
			}
		});
		// When select and enter pressed
		bQuit.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent e)
            {
                if(e.getCode()==KeyCode.ENTER){
                	bQuit.fire();
                }
                
            }
        });
		
		bQuit.onMouseEnteredProperty();


		this.menuGroup.getChildren().add(bQuit);

	}
	/**
	 * Returns the group that contains all of the nodes of this menu
	 * 
	 * @return the Group (menuGroup) attribute
	 */
	public Group getMenuGroup(){
		return this.menuGroup;
	}
	
	public void updateLang(){
		bBack.setText(Config.arenaText.getText(ArenaText.backBt));
		bQuit.setText(Config.arenaText.getText(ArenaText.quitBt));
		bRestart.setText(Config.arenaText.getText(ArenaText.restartBt));
		bOption.setText(Config.arenaText.getText(ArenaText.optionBt));
		bCreate.setText(Config.arenaText.getText(ArenaText.newCharacterBt));
		
	}

}
