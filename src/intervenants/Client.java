package intervenants;

import java.util.HashMap;
import java.util.Random;

import main.Supermarche;

public class Client extends Thread {

	private Supermarche marche;
	private HashMap<String, Integer> listeCourse;
	private int idClient;

	public Client(Supermarche marcheInit, int idClient) {
		listeCourse = new HashMap<String, Integer>();
		this.idClient = idClient; 
		// on rend alÃ©atoire le nombre d'articles dans la liste de course entre
		// 0 et 20
		int bound = 5;
		Random r = new Random();
		int randomNumber = r.nextInt(bound);
		listeCourse.put("sucre", randomNumber);
		System.out.println("Client " + idClient  + " : besoin de " + randomNumber + " sucre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("beurre", randomNumber);
		System.out.println("Client " + idClient + " : a besoin de " + randomNumber + " beurre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("lait", randomNumber);
		System.out.println("Client " + idClient + " : a besoin de " + randomNumber + " lait");
		randomNumber = r.nextInt(bound);
		listeCourse.put("farine", randomNumber);
		System.out.println("Client " + idClient + " : a besoin de " + randomNumber + " farine \n");
		marche = marcheInit;

	}

	public void run() {
		try {
			// ArrivÃ© dans le supermarchÃ© -> prendre chariot
			marche.getChariots().prendreChariot(idClient);

			// simulation de courses
			// Rayon sucre
			int i;
			i = listeCourse.get("sucre");
			if (i > 0) {
				while (i > 0) {
					marche.getRayon("sucre").prendreArticle(idClient);
					i--;
				}
			}
			// Rayon farine
			sleep(300);
			i = listeCourse.get("farine");
			if (i > 0) {

				while (i > 0) {
					marche.getRayon("farine").prendreArticle(idClient);
					i--;
				}
			}
			// Rayon beurre
			sleep(300);
			i = listeCourse.get("beurre");
			if (i > 0) {

				while (i > 0) {
					marche.getRayon("beurre").prendreArticle(idClient);
					i--;
				}
			}
			// Rayon lait
			sleep(300);
			i = listeCourse.get("lait");
			if (i > 0) {

				while (i > 0) {
					marche.getRayon("lait").prendreArticle(idClient);
					i--;
				}
			}
			// TODO PASSER EN CAISSE : Il va chercher à avoir accès au tapis de la caisse -> accéder Tapis = True et il va déposer ses articles pour déposer sa liste de course (le tapis indique alors Client Suivant) et enfin payer

			marche.getChariots().rendreChariot(idClient);
		} catch (

		InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
