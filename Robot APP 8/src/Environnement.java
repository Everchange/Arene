/**
 * Classe qui permet de créer l'environnement du simulateur
 * 
 * @author Département TIC - ESIGELEC
 * @version 1.0
 */
public class Environnement {

	/**
	 * permet de créer l'environnement du simulateur (fenêtre + terrain)
	 * 
	 * @return le terrain situé sur la fenêtre
	 */
	public static Terrain creerEnvironnement(int nbLignes, int nbColonnes) {

		// création de la fenêtre
		Fenetre f = new Fenetre(880, 500);
		f.setTitle("Robot ESIGELEC");
		// blocage du redimmensionnement de la fenetre
		f.setResizable(false);
		// création du terrain
		Terrain terrain = new Terrain(nbLignes, nbColonnes);
		terrain.setBounds(30, 40, 400, 400);
		f.ajouterTerrain(terrain);
		// création de la zone de controle
		PanneauDeControle pc = new PanneauDeControle(terrain);
		pc.setBounds(440, 40, 400, 400);
		f.ajouterPanneauDeControle(pc);

		// on centre la fenêtre à l'ecran
		f.setLocationRelativeTo(f.getParent());
		// on rend la fenêtre visible (par défaut elle est invisible)
		f.setVisible(true);

		return terrain;

	}

}