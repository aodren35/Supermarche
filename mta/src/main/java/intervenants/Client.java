package intervenants;

import java.util.HashMap;
import java.util.Random;

import main.Supermarche;

public class Client extends Thread {

	private Supermarche marche;
	private HashMap<String, Integer> listeCourse;
	private HashMap<String, Integer> listeCourseCourrante;
	private int idClient;
	private String etat;
	public final static String  attenteChariot = "ATTENTE_CHARIOT";
	public final static String  enCourse = "EN_COURSE";
	public final static String  attenteProduit = "ATTENTE_PRODUIT";
	public final static String  attenteCaisse = "ATTENTE_CAISSE";
	public final static String  aLaCaisse = "A_LA_CAISSE";
	public final static String aFiniSesCourses = "A_FIN_SES_COURSES";


	public Client(Supermarche marcheInit, int idClient) {
		listeCourse = new HashMap<String, Integer>();
		
		this.idClient = idClient;
		// on rend alÃ©atoire le nombre d'articles dans la liste de course entre
		// 0 et 5
		int bound = 5;
		Random r = new Random();
		int randomNumber = r.nextInt(bound);
		listeCourse.put("sucre", randomNumber);
		System.out.println("Client " + this.idClient + " : besoin de " + randomNumber + " sucre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("beurre", randomNumber);
		System.out.println("Client " + this.idClient + " : a besoin de " + randomNumber + " beurre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("lait", randomNumber);
		System.out.println("Client " + this.idClient + " : a besoin de " + randomNumber + " lait");
		randomNumber = r.nextInt(bound);
		listeCourse.put("farine", randomNumber);
		System.out.println("Client " + this.idClient + " : a besoin de " + randomNumber + " farine \n");
		marche = marcheInit;
		listeCourseCourrante = listeCourse;

	}
	
	public Client(int id){
		idClient = id;
		listeCourse = new HashMap<String, Integer>();
		int bound = 5;
		Random r = new Random();
		int randomNumber = r.nextInt(bound);
		listeCourse.put("sucre", randomNumber);
		System.out.println("Client " + idClient + " : besoin de " + randomNumber + " sucre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("beurre", randomNumber);
		System.out.println("Client " + idClient + " : a besoin de " + randomNumber + " beurre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("lait", randomNumber);
		System.out.println("Client " + idClient + " : a besoin de " + randomNumber + " lait");
		randomNumber = r.nextInt(bound);
		listeCourse.put("farine", randomNumber);
		System.out.println("Client " + idClient + " : a besoin de " + randomNumber + " farine \n");
		listeCourseCourrante = listeCourse;
	}
	
	public Client(){
		listeCourse = new HashMap<String, Integer>();
		int bound = 5;
		Random r = new Random();
		int randomNumber = r.nextInt(bound);
		listeCourse.put("sucre", randomNumber);
		System.out.println("Client " + idClient + " : besoin de " + randomNumber + " sucre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("beurre", randomNumber);
		System.out.println("Client " + idClient + " : a besoin de " + randomNumber + " beurre");
		randomNumber = r.nextInt(bound);
		listeCourse.put("lait", randomNumber);
		System.out.println("Client " + idClient + " : a besoin de " + randomNumber + " lait");
		randomNumber = r.nextInt(bound);
		listeCourse.put("farine", randomNumber);
		System.out.println("Client " + idClient + " : a besoin de " + randomNumber + " farine \n");
		listeCourseCourrante = listeCourse;
		//marche = new Supermarche();
		//idClient = 0;
	}
	
	public void setMarche(Supermarche sm){
		marche = sm;
	}
	
	public void setListe(HashMap<String,Integer> liste){
		listeCourse=liste;
		listeCourseCourrante = listeCourse;
	}
	
	public int getIdClient(){
		return idClient;
	}
	
	public void setIdClient(int id){
		idClient=id;
	}
	
	public HashMap<String,Integer> getListeCourse(){
		return listeCourse;
	}
	
	public HashMap<String,Integer> getListeCourseCourrante(){
		return listeCourseCourrante;
	}

	public void setEtat(String et){
		etat=et;
	}
	
	public String getEtat(){
		return etat;
	}
	
	public void run() {
		try {
			// ArrivÃ© dans le supermarchÃ© -> prendre chariot
			this.setEtat(attenteChariot);
			marche.getChariots().prendreChariot(idClient);

			// simulation de courses
			this.setEtat(enCourse);
			// Rayon sucre
			int i;
			i = listeCourse.get("sucre");
			if (i > 0) {
				while (i > 0) {
					marche.getRayon("sucre").prendreArticle(this);
					i--;
					int x=listeCourseCourrante.get("sucre");
					x--;
					listeCourseCourrante.remove("sucre");
					listeCourseCourrante.put("sucre", x);
				}
			}
			// Rayon farine
			sleep(300);
			i = listeCourse.get("farine");
			if (i > 0) {

				while (i > 0) {
					marche.getRayon("farine").prendreArticle(this);
					i--;
					int x=listeCourseCourrante.get("farine");
					x--;
					listeCourseCourrante.remove("farine");
					listeCourseCourrante.put("farine", x);
				}
			}
			// Rayon beurre
			sleep(300);
			i = listeCourse.get("beurre");
			if (i > 0) {

				while (i > 0) {
					marche.getRayon("beurre").prendreArticle(this);
					i--;
					int x=listeCourseCourrante.get("beurre");
					x--;
					listeCourseCourrante.remove("beurre");
					listeCourseCourrante.put("beurre", x);
				}
			}
			// Rayon lait
			sleep(300);
			i = listeCourse.get("lait");
			if (i > 0) {

				while (i > 0) {
					marche.getRayon("lait").prendreArticle(this);
					i--;
					int x=listeCourseCourrante.get("lait");
					x--;
					listeCourseCourrante.remove("lait");
					listeCourseCourrante.put("lait", x);
				}
			}
			// Le client accède au tapis de la caisse
			this.setEtat(attenteCaisse);
			marche.getCaisse().accederTapis(this);

			// Le client dépose ses articles
			for (String article : listeCourse.keySet()) {
				int nombreArticle = listeCourse.get(article);
				
				// le client dépose ses articles
				while (nombreArticle > 0) {
					System.out.print("Client" + idClient + " ");
					if (marche.getCaisse().getTapis().deposerArticle(article) == true) {
						sleep(20);
						nombreArticle--;
					} else {
						System.err.println("le Client " + idClient + " a été bloqué.");
					}
				}
			}
			System.out.print("Client" + idClient + " ");
			marche.getCaisse().getTapis().deposerArticle("Client suivant");

			marche.getCaisse().Payer(idClient);

			// Le client rend le chariot
			marche.getChariots().rendreChariot(idClient);
			this.setEtat(aFiniSesCourses);
			
			
		} catch (

		InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
