import java.awt.Color;
import java.awt.Graphics;

/**
 * Classe représentant une case vide du Terrain, ie zone du terrain sur laquelle
 * le robot peut se déplacer
 * 
 * @author Département TIC - ESIGELEC
 * @version 1.0
 */
public class CaseVide extends Case {

	/**
	 * numéro de version de la classe
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de la classe CaseVide
	 * 
	 * @param terrain
	 *            le terrain sur lequel se trouve la case vide
	 * @param ligne
	 *            numero de ligne de la case
	 * @param colonne
	 *            numero de colonne de la case
	 */
	public CaseVide(Terrain terrain, int ligne, int colonne) {
		// appel du constructeur de la classe mère (Case)
		super(terrain, ligne, colonne);
		// on fixe la couleur de la case vide
		this.setBackground(Color.ORANGE);
	}

	/**
	 * redéfinition de la méthode paintComponent afin de préciser comment une
	 * case vide doit s'afficher
	 * 
	 * @param g
	 *            Objet Graphics associé à la Case Vide
	 */
	protected void paintComponent(Graphics g) {
		// appel de la méthode de dessin d'un JPanel normal
		super.paintComponent(g);
		// si la case a été visitée par le robot, et si la case ne contient pas
		// le robot
		// alors on dessine un point
		if (this.isVisitee() && this.getRobot() == null) {
			// on fixe la couleur du point
			g.setColor(Color.RED);
			// on dessine le point au centre de la case
			/*
			 * g.fillOval(this.getWidth() / 2 - this.getWidth() / 8, this
			 * .getHeight() / 2 - this.getHeight() / 8, this.getWidth() / 4,
			 * this .getHeight() / 4);
			 */
		}

	}

}