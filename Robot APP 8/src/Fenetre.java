import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Classe représentant la fenêtre du simulateur
 * 
 * @author Département TIC - ESIGELEC
 * @version 1.0
 */
public class Fenetre extends JFrame {

	/**
	 * numéro de version de la classe
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * container principal de la fenêtre
	 */
	Container container;
	/**
	 * Terrain situé sur le simulateur
	 */
	Terrain terrain;
	/**
	 * Panneau de contrôle du simulateur
	 */
	PanneauDeControle controle;
	/**
	 * texte affiché en haut du simulateur
	 */
	JLabel labelDescription;

	/**
	 * Constructeur de la fenêtre
	 * 
	 * @param largeur
	 *            largeur de la fenêtre en pixel
	 * @param hauteur
	 *            hauteur de la fenêtre en pixel
	 */
	public Fenetre(int largeur, int hauteur) {
		super();
		// on fixe la taille de la fenêtre
		this.setSize(largeur, hauteur);
		// on récupère le container principal de la fenêtre
		container = this.getContentPane();
		// on modifie la couleur de fond du container
		container.setBackground(Color.WHITE);

		// on désactive le layout manager (gestionnaire de placement) du
		// container
		// afin de pouvoir placer les composants au pixel près
		container.setLayout(null);
		// on définit le texte de la fenêtre
		labelDescription = new JLabel(
				"Simulateur de Robot sauveteur en milieu hostile");
		// on définit la position et la taille du texte de la fenêtre
		labelDescription.setBounds(5, 5, 780, 20);
		// on ajoute le composant texte dans le container de la fenêtre
		this.add(labelDescription);
		// on précise que le programme doit fermer complètement si on clique sur
		// la croix de la fenêtre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Permet d'ajouter le terrain dans le container principal de la fenêtre
	 * 
	 * @param t
	 *            le terrain à ajouter
	 */
	public void ajouterTerrain(Terrain t) {
		terrain = t;
		// on ajoute le composant graphique du terrain dans le container de la
		// fenetre
		container.add(terrain);
		// on redessine la fenêtre
		repaint();
	}

	/**
	 * Permet d'ajouter le panneau de contrôle dans la fenêtre
	 * 
	 * @param pc
	 *            le panneau de contrôle à ajouter
	 */
	public void ajouterPanneauDeControle(PanneauDeControle pc) {
		controle = pc;
		// on ajoute le composant graphique du panneau de contrôle dans le
		// container de la fenetre
		container.add(controle);
		// on redessine la fenêtre
		repaint();
	}
	public void message(String message){
		labelDescription = new JLabel(message);
		labelDescription.setBounds(20, 20, 200, 20);
		// on ajoute le composant texte dans le container de la fenêtre
		this.add(labelDescription);
	}
}
	