package intervenants;

import java.util.HashMap;
import java.util.Random;

import main.Supermarche;

public class Client extends Thread {

	private Supermarche marche;
	private HashMap<String, Integer> listeCourse;

	public Client(Supermarche marcheInit) {
		listeCourse = new HashMap<String, Integer>();
		// on rend aléatoire le nombre d'articles dans la liste de course entre
		// 0 et 20
		int bound = 5;
		Random r = new Random();
		int randomNumber = r.nextInt(bound);
		listeCourse.put("sucre", randomNumber);
		System.out.println("Le client a besoin de " + randomNumber + " sucre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("beurre", randomNumber);
		System.out.println("Le client a besoin de " + randomNumber + " beurre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("lait", randomNumber);
		System.out.println("Le client a besoin de " + randomNumber + " lait");
		randomNumber = r.nextInt(bound);
		listeCourse.put("farine", randomNumber);
		System.out.println("Le client a besoin de " + randomNumber + " farine \n");
		marche = marcheInit;

	}

	public void run() {
		try {
			// Arrivé dans le supermarché -> prendre chariot
			marche.getChariots().prendreChariot();

			// simulation de courses
			// Rayon sucre
			int i;
			i = listeCourse.get("sucre");
			if (i > 0) {
				while (i > 0) {
					marche.getRayon("sucre").prendreArticle();
					i--;
				}
			}
			// Rayon farine
			sleep(300);
			i = listeCourse.get("farine");
			if (i > 0) {

				while (i > 0) {
					marche.getRayon("farine").prendreArticle();
					i--;
				}
			}
			// Rayon beurre
			sleep(300);
			i = listeCourse.get("beurre");
			if (i > 0) {

				while (i > 0) {
					marche.getRayon("beurre").prendreArticle();
					i--;
				}
			}
			// Rayon lait
			sleep(300);
			i = listeCourse.get("lait");
			if (i > 0) {

				while (i > 0) {
					marche.getRayon("lait").prendreArticle();
					i--;
				}
			}
			// TODO PASSER EN CAISSE

			marche.getChariots().rendreChariot();
		} catch (

		InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
