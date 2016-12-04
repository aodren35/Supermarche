package partieA.schema;

/**
 * Classe tapis repr�sentant le tapis de la caisse Objet partag� entre le thread
 * client d�posant ses produit et le thread caissier
 * 
 * @author Aodren Letellier - Jordan Monfort
 *
 */
public class Tapis {
	private static final int tailleTapis = 5;

	// Position du tapis pour le client
	private int positionTapisClient;
	// Position du tapis pour le caissier
	private int positionTapisCaissier;
	private String[] tapis;

	/**
	 * Constructeur d'un tapis sans rien dessus
	 */
	public Tapis() {
		tapis = new String[tailleTapis];
		for (int i = 0; i < tailleTapis; i++) {
			tapis[i] = "";
		}
		positionTapisClient = 0;
		positionTapisCaissier = tailleTapis - 1;
	}

	/**
	 * M�thode atomique permettant au client de deposer si possible un article
	 * sur le tapis
	 * 
	 * @param article
	 * @return Retourne si oui ou non le client a pu d�poser son article
	 */
	public synchronized boolean deposerArticle(String article) {
		boolean bool;
		// Si le tapis est vide � la position du client il peut d�poser son
		// article
		if (tapis[positionTapisClient].equals("")) {
			tapis[positionTapisClient] = article;
			System.out.println(article + " d�pos�");
			bool = true;
		} else {
			bool = false;
		}

		return bool;
	}

	/**
	 * 
	 * M�thode permettant au caissier de r�cup�rer un article et de faire
	 * avancer le tapis
	 * 
	 * @return retourne l'article que le caissier a r�cup�r�
	 */
	public String recupererArticleEtAvancerTapis() {
		String value;
		// rien sur le tapis
		if (tapis[positionTapisCaissier].equals("")) {
			value = "rien";
		}
		// R�cup�re le tag client suivant
		else if (tapis[positionTapisCaissier].equals("Client suivant")) {
			tapis[positionTapisCaissier] = "";
			value = "next client";
		}
		// recup�re un article et passe � l'article suivant
		else {
			System.out.println("Caissier : article " + tapis[positionTapisCaissier] + " recupere");
			tapis[positionTapisCaissier] = "";
			value = "article next";
		}

		/*
		 * Gestion de la position du tapis pour le Caissier
		 */
		// on avance sur le tapis
		if (positionTapisCaissier > 0) {
			positionTapisCaissier--;

		} 
		//Rend le tapis circulaire
		else if (positionTapisCaissier == 0) {
			positionTapisCaissier = tailleTapis - 1;
		}
		/*
		 * Gestion de la position du tapis pour le Client
		 */

		// on avance le tapis
		if (positionTapisClient > 0) {
			positionTapisClient--;
		} 
		//Rend le tapis circulaire
		else if (positionTapisClient == 0) {
			positionTapisClient = tailleTapis - 1;
		}
		return value;
	}

}
