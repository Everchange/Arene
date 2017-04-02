package character;

import graphics.FieldScene;
import graphics.Main;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import ressources.Config;

/**
 * this class will implement the characeter's GUI when this last is selected
 * 
 * @author Clément
 *
 */
public class CharacterGUI extends Group {

	private Text txtName = new Text();
	private Text hp = new Text();
	private Image img;
	private int charID=-1;

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
		hp.relocate(250, 10);
		caracBg.setFill(Color.AQUA);
		charGUI.getChildren().addAll(caracBg,hp);

		Group actions=new Group();
		actions.relocate(90, 40);
		Rectangle actionBg=new Rectangle(0,0,400,50);
		actionBg.setFill(Color.BISQUE);
		Text action=new Text("Choix de l'action du personnage \n(attaque,parade,deplacement)");
		action.relocate(50, 5);
		actions.getChildren().addAll(actionBg,action);
		charGUI.getChildren().add(actions);

		this.getChildren().add(charGUI);



	}

	public void setCharacterToDisplay(ArenaCharacter ac){
		this.txtName.setText(ac.getName());
		this.hp.setText(Integer.toString(ac.getCurrentHp()));
		this.charID=ac.getFieldId();
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

}
