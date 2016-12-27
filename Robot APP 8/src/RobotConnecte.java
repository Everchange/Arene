
public class RobotConnecte extends Robot{

	private Puce puce;

	/**
	 * default constructor
	 */
	public RobotConnecte(int ligne, int colonne, String direction){
		super(ligne,colonne,direction);
		Puce p=new Puce(this);
		// in this case the puce belongs only to this robot
		this.puce=p;
	} 

	/**
	 * Constructors with parameters
	 * @param puce
	 */
	public RobotConnecte(int ligne, int colonne, String direction, Puce puce) {
		super(ligne,colonne,direction);
		this.puce = new Puce(this);
	}
	
	public Puce getPuce(){
		return this.puce;
	}

	@Override
	public int avancer(){
		int retour = super.avancer();
		this.puce.afficherPosition();
		this.puce.afficherBatterie();

		return retour;
	}

	@Override
	public int reculer(){
		int retour = super.reculer();
		this.puce.afficherPosition();
		this.puce.afficherBatterie();

		return retour;
	}

	@Override
	public int tournerDroite(){
		int retour = super.tournerDroite();
		this.puce.afficherDirection();
		this.puce.afficherBatterie();

		return retour;
	}

	@Override
	public int tournerGauche(){
		int retour = super.tournerGauche();
		this.puce.afficherDirection();
		this.puce.afficherBatterie();

		return retour;
	}

}
