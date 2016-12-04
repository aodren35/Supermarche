package partieA.intervenants;

import java.util.HashMap;
import java.util.Random;

import partieA.schema.Supermarche;

/**
 * La classe Client représente un client sous la forme d'un thread
 * @author Aodren Letellier / Jordan Monfort
 *
 */
public class Client extends Thread {

	private Supermarche marche;
	//La liste de course du client
	private HashMap<String, Integer> listeCourse;
	//La liste de course restante du client
	private HashMap<String, Integer> listeCourseCourrante;
	
	private int idClient;
	private String etat;
	
	public final static String attenteChariot = "ATTENTE_CHARIOT";
	public final static String enCourse = "EN_COURSE";
	public final static String attenteProduit = "ATTENTE_PRODUIT";
	public final static String attenteCaisse = "ATTENTE_CAISSE";
	public final static String aLaCaisse = "A_LA_CAISSE";
	public final static String aFiniSesCourses = "A_FIN_SES_COURSES";

	
	/**
	 * Constructeur du Client avec remplissage aléatoire de sa liste de course
	 * 
	 * @param marcheInit le supermarché
	 * @param idClient l'id du client
	 */
	public Client(Supermarche marcheInit, int idClient) {
		this.idClient = idClient;
		listeCourse = new HashMap<String, Integer>();
		listeCourseCourrante = new HashMap<String, Integer>();
		// on rend aleatoire la liste de course
		int bound = 5;
		Random r = new Random();
		int randomNumber = r.nextInt(bound);
		listeCourse.put("sucre", randomNumber);
		listeCourseCourrante.put("sucre", randomNumber);
		System.out.println("Client " + this.idClient + " : besoin de " + randomNumber + " sucre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("beurre", randomNumber);
		listeCourseCourrante.put("beurre", randomNumber);
		System.out.println("Client " + this.idClient + " : a besoin de " + randomNumber + " beurre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("lait", randomNumber);
		listeCourseCourrante.put("lait", randomNumber);
		System.out.println("Client " + this.idClient + " : a besoin de " + randomNumber + " lait");
		randomNumber = r.nextInt(bound);
		listeCourse.put("farine", randomNumber);
		listeCourseCourrante.put("farine", randomNumber);
		System.out.println("Client " + this.idClient + " : a besoin de " + randomNumber + " farine \n");
		marche = marcheInit;
		

	}

	/**
	 * Constructeur d'un client à partir de son id
	 * et Remplissage aléatoire de sa liste de course
	 * 
	 * @param id l'id du client
	 */
	public Client(int id) {
		idClient = id;
		listeCourse = new HashMap<String, Integer>();
		listeCourseCourrante = new HashMap<String, Integer>();
		int bound = 5;
		Random r = new Random();
		int randomNumber = r.nextInt(bound);
		listeCourse.put("sucre", randomNumber);
		listeCourseCourrante.put("sucre", randomNumber);
		System.out.println("Client " + idClient + " : besoin de " + randomNumber + " sucre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("beurre", randomNumber);
		listeCourseCourrante.put("beurre", randomNumber);
		System.out.println("Client " + idClient + " : a besoin de " + randomNumber + " beurre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("lait", randomNumber);
		listeCourseCourrante.put("lait", randomNumber);
		System.out.println("Client " + idClient + " : a besoin de " + randomNumber + " lait");
		randomNumber = r.nextInt(bound);
		listeCourse.put("farine", randomNumber);
		listeCourseCourrante.put("farine", randomNumber);
		System.out.println("Client " + idClient + " : a besoin de " + randomNumber + " farine \n");
	}


	
	/**
	 * Set le supermarché pour un client
	 * @param sm le supermarché
	 */
	public void setMarche(Supermarche sm) {
		marche = sm;
	}

	//Set liste de course au client, mais implémentation actuelle génére un liste aléatoire
	public void setListe(HashMap<String, Integer> liste) {
		listeCourse = liste;
		listeCourseCourrante = liste;
	}
	
	

	/**
	 * @return retourne l'id du client
	 */
	public int getIdClient() {
		return idClient;
	}

	
	/**
	 * set l'id du client
	 * @param id id du client
	 */
	public void setIdClient(int id) {
		idClient = id;
	}

	/**
	 * @return retourne la liste de course du client
	 */
	public HashMap<String, Integer> getListeCourse() {
		return listeCourse;
	}

	/**
	 * @return retourne la liste de course des produit restant à acheter d'un client
	 */
	public HashMap<String, Integer> getListeCourseCourrante() {
		return listeCourseCourrante;
	}

	
	public void setEtat(String et) {
		etat = et;
	}

	/**
	 * @return retourne l'état / la situation d'un clients
	 */
	public String getEtat() {
		return etat;
	}

	public void run() {
		try {
			//Arrive dans le supermarche 
			//Le client cherche à avoir un chariot
			this.setEtat(attenteChariot);
			marche.getChariots().prendreChariot(idClient);

			// simulation de courses
			this.setEtat(enCourse);
			//Arrivé au rayon sucre
			int i;
			i = listeCourse.get("sucre");
			if (i > 0) {
				//Le client va chercher a avoir autant de sucre qu'il désire
				while (i > 0) {
					marche.getRayon("sucre").prendreArticle(this);
					i--;
					int x = listeCourseCourrante.get("sucre");
					x--;
					listeCourseCourrante.remove("sucre");
					listeCourseCourrante.put("sucre", x);
				}
			}
			//Arrivé au Rayon farine
			sleep(300);
			i = listeCourse.get("farine");
			if (i > 0) {

				while (i > 0) {
					marche.getRayon("farine").prendreArticle(this);
					i--;
					int x = listeCourseCourrante.get("farine");
					x--;
					listeCourseCourrante.remove("farine");
					listeCourseCourrante.put("farine", x);
				}
			}
			//Arrivé au Rayon beurre
			sleep(300);
			i = listeCourse.get("beurre");
			if (i > 0) {

				while (i > 0) {
					marche.getRayon("beurre").prendreArticle(this);
					i--;
					int x = listeCourseCourrante.get("beurre");
					x--;
					listeCourseCourrante.remove("beurre");
					listeCourseCourrante.put("beurre", x);
				}
			}
			//Arrivé au Rayon lait
			sleep(300);
			i = listeCourse.get("lait");
			if (i > 0) {

				while (i > 0) {
					marche.getRayon("lait").prendreArticle(this);
					i--;
					int x = listeCourseCourrante.get("lait");
					x--;
					listeCourseCourrante.remove("lait");
					listeCourseCourrante.put("lait", x);
				}
			}
			// Le client a fini ses courses il cherche maintenant à payer
			this.setEtat(attenteCaisse);
			// Il cherche a acceder au tapis
			marche.getCaisse().accederTapis(this);

			// Le client dépose ses articles
			for (String article : listeCourse.keySet()) {
				int nombreArticle = listeCourse.get(article);
				while (nombreArticle > 0) {
					System.out.print("Client" + idClient + " ");
					//Il cherche a déposé un produit sur le tapis (impossible si tapis rempli)
					if (marche.getCaisse().getTapis().deposerArticle(article) == true) {
						sleep(20);
						nombreArticle--;
					} else {
						System.err.println("le Client " + idClient + " a été bloqué.");
					}
				}
			}
			//Le client a fini de deposer ses articles il dépose donc le marqueur Client Suivant
			System.out.print("Client" + idClient + " ");
			while (!marche.getCaisse().getTapis().deposerArticle("Client suivant")){
				
			}

			//Le client paye
			marche.getCaisse().payer(idClient);

			// Le client rend le chariot
			marche.getChariots().rendreChariot(idClient);
			this.setEtat(aFiniSesCourses);

		} catch (

		InterruptedException e) {
			e.printStackTrace();
		}
	}

}
