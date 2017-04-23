package character;

import graphics.FieldScene;
import graphics.Main;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import ressources.Config;
import ruleset.Weapon;

/**
 * this class will implement the characeter's GUI when this last is selected
 * 
 * @author Clément
 *
 */
public class CharacterGUI extends Group {

	private Text txtName = new Text();
	private Text hp = new Text();
	private Rectangle life =new Rectangle(270, 13, 217, 14);
	private Image img;
	private int charID=-1;
	private Group actions=new Group();

	public CharacterGUI() {	

		reset();

		//debut test menu
		Group charGUI=new Group();
		charGUI.relocate(10, 10);

		Rectangle bg=new Rectangle(0,0,500,100);
		bg.setFill(Color.WHITE);
		charGUI.getChildren().add(bg);

		Rectangle portrait=new Rectangle(10,10,70,80);
		//replace photo by an image
		Text photo=new Text("photo");
		photo.relocate(20, 40);
		portrait.setFill(Color.BROWN);
		charGUI.getChildren().addAll(portrait,photo);

		txtName.relocate(100, 10);
		Rectangle nomBg=new Rectangle(90,10,140,20);
		nomBg.setFill(Color.AQUAMARINE);
		charGUI.getChildren().addAll(nomBg,txtName);

		//PV
		Rectangle caracBg=new Rectangle(240,10,250,20);
		hp.relocate(250, 11);
		//rectangle that represents the HP
		//life.resizeRelocate(240, 10, 250, 20);
		life.setFill(Color.RED);
		caracBg.setFill(Color.AQUA);
		charGUI.getChildren().addAll(caracBg,life,hp);

		actions.relocate(90, 40);
		Rectangle actionBg=new Rectangle(0,0,400,50);
		actionBg.setFill(Color.BISQUE);
		Text action=new Text(50, 5,"Choix de l'action du personnage \n(attaque,parade,deplacement)");
		actions.getChildren().addAll(actionBg,action);
		charGUI.getChildren().add(actions);

		this.getChildren().add(charGUI);



	}

	public void setCharacterToDisplay(ArenaCharacter ac){
		if (ac==null){
			this.reset();
			return;
		}
		this.txtName.setText(ac.getName());
		this.hp.setText(Integer.toString(ac.getCurrentHp()));
		this.life.setWidth(217*((double)ac.getCurrentHp()/(double)ac.getHPCount()));
		this.charID=ac.getFieldId();
		
		this.actions.getChildren().clear();
		Rectangle actionBg=new Rectangle(0,0,400,50);
		actionBg.setFill(Color.BISQUE);
		actions.getChildren().add(actionBg);
		
		
		for (int k=0; k<ac.getCS().getWeapon().length ; k++){
			Weapon w=ac.getCS().getWeapon()[k];
			if(w!=null){
				Rectangle r=new Rectangle(5+k*(40+5),5,40,40);
				r.setFill(Color.DARKGREY);

				EventHandler<MouseEvent> cli =new EventHandler<MouseEvent>(){
					public void handle(MouseEvent evt){
						if(evt.getButton()==MouseButton.PRIMARY){
							System.out.println("Attack with "+w.getName()+" from "+ac.getName());
						}
						if (evt.getButton()==MouseButton.SECONDARY){
							System.out.println("Description : bla bla bla");
						}
					}
				};

				r.setOnMouseClicked(cli);
				Text t=new Text (10+k*(40+5),30,w.getName());
				t.setOnMouseClicked(cli);
				actions.getChildren().addAll(r,t);
			}
		}


		//actions.getChildren().add(new Text(50, 25,"This character owns no weapon"));
		Rectangle r=new Rectangle(230,5,40,40);
		r.setFill(Color.DARKGREY);


		EventHandler<MouseEvent> cli =new EventHandler<MouseEvent>(){
			public void handle(MouseEvent evt){
				if(evt.getButton()==MouseButton.PRIMARY){
					System.out.println("Attack with the hands from "+ac.getName());
				}
				if (evt.getButton()==MouseButton.SECONDARY){
					System.out.println("Description : the character will attack with its own hands");
				}
			}
		};

		Text t=new Text (235,30,"Hand");

		r.setOnMouseClicked(cli);
		t.setOnMouseClicked(cli);
		actions.getChildren().addAll(r,t);


	}

	public void reset(){
		this.txtName.setText("NULL");;
		this.img=null;
	}

	public void center(){

		double x = Config.getSizeW()/2-this.getLayoutBounds().getWidth()/2;
		double y=Config.getSizeH()-this.getLayoutBounds().getHeight();

		this.relocate(x, y);
	}

	public void update(){
		if (charID>-1){
			setCharacterToDisplay(((FieldScene)Main.getScene(Main.FIELD_SCENE)).getCharac(charID));
		}
	}

	public boolean isSetToNull() {
		if(this.charID==-1){
			return true;
		}
		return false;
	}

}
