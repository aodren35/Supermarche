package partieA.schema;

/**
 * La classe Chariot représente les chariots disponibles dans le supermarché
 * C'est un objet partagé entre les threads clients
 * @author Aodren Letellier - Jordan Monfort
 *
 */
public class Chariots {
	private int stockChariot;

	public Chariots() {
		stockChariot = Supermarche.NB_CHARIOTS;
	}

	/**
	 * Méthode atomique permettant au client de prendre un chariot
	 * @param idClient id du client voulant emprunter un chariot
	 * @throws InterruptedException
	 */
	public synchronized void prendreChariot(int idClient) throws InterruptedException {
		//Tant qu'il n'y a pas de chariot le client attend
		while (stockChariot <= 0) {
			System.out.println("Client " + idClient + " : Attente chariot par le Client, stock chariot : " + stockChariot);
			wait();
		}
		System.out.println("Client " + idClient + " : Chariot pris, stock chariot : " + stockChariot);
		stockChariot--;

	}

	/**Méthode atomique permettant au client de rendre son chariot
	 * 
	 * @param idClient id du client rendant son chariot
	 * @throws InterruptedException
	 */
	public synchronized void rendreChariot(int idClient) throws InterruptedException {
		stockChariot++;
		System.out.println("Client " + idClient + " : Chariot rendu par le Client, stock chariot : " + stockChariot);
		// Reveille les threads client en attente d'un chariot
		notifyAll();
	}

}
