package partieA.intervenants;

import partieA.schema.Supermarche;

/**
 * La classe ChefDeRayon représente le chef de Rayon sous forme de thread
 * @author Aodren Letellier / Jordan Monfort
 *
 */
public class ChefDeRayon extends Thread {
	private int stockFarine;
	private int stockLait;
	private int stockBeurre;
	private int stockSucre;
	private Supermarche supermarche;

	/**
	 * Constructeur avec le stock personnel du chef de rayon initialisé à vide
	 * @param supermarche2 le supermarché
	 */
	public ChefDeRayon(Supermarche supermarche2) {
		stockFarine = 0;
		stockLait = 0;
		stockBeurre = 0;
		stockSucre = 0;
		supermarche = supermarche2;
	}

	
	/**
	 * Remplis le stock du chef de rayon
	 */
	public void faireLePlein() {
		stockFarine = 5;
		stockLait = 5;
		stockBeurre = 5;
		stockSucre = 5;
	}

	public void run() {
		try {
			//Le caissier parcours le supermarche non stop
			while (true) {
				// debut journée dans l'entrepot
				// remplis son stock
				faireLePlein();
				sleep(500);
				// trajet Entrepot -> Rayon sucre
				sleep(200);
				// Remplir rayon sucre
				stockSucre = supermarche.getRayon("sucre").remplirStock(stockSucre);
				// sucre -> farine
				sleep(200);
				// Remplir rayon farine
				stockFarine = supermarche.getRayon("farine").remplirStock(stockFarine);
				// farine -> beurre
				sleep(200);
				// Remplir rayon beurre
				stockBeurre = supermarche.getRayon("beurre").remplirStock(stockBeurre);
				// beurre->lait
				sleep(200);
				// Remplir rayon lait
				stockLait = supermarche.getRayon("lait").remplirStock(stockLait);
				// Lait->entrepot
				sleep(200);

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
