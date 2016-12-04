package partieA.intervenants;

import partieA.schema.Supermarche;

/**
 * La classe Caissier représente le caissier sous forme de thread
 * @author Aodren Letellier / Jordan Monfort
 *
 */
public class Caissier extends Thread {
	
	private Supermarche supermarche;
	private boolean disponibilite;

	public Caissier(Supermarche superMarche) {
		this.disponibilite = true;
		this.supermarche = superMarche;
	}

	/**
	 * @return retourne la disponibilité du caissier pour encaisser
	 */
	public boolean getDisponibilite() {
		return this.disponibilite;
	}

	public void setDisponibilite(boolean dispo) {
		this.disponibilite = dispo;
	}

	
	public void run() {
		while (true) {
			try {
				//Le caissier reste à sa caisse tout le temps et gère le tapis
				supermarche.getCaisse().parcourirTapis();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
