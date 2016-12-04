package partieA.schema;

import partieA.intervenants.Caissier;
import partieA.intervenants.Client;

/**
 * La classe Caisse repr�sente la caisse du supermarch�
 * C'est un �l�ment partag� pour les thread clients et caissier
 * 
 * @author Aodren Letellier - Jordan Monfort
 *
 */
public class Caisse {
	private Caissier caissier;
	private Tapis tapis;
	private boolean tapisAccessible;

	public Caisse() {
		this.tapis = new Tapis();
		tapisAccessible = true;
	}

	public void setCaissier(Caissier caissier) {
		this.caissier = caissier;
	}

	/**
	 * M�thode atomique d'acc�s au tapis pour un client
	 * 
	 * @param client le client voulant acceder au tapis
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized boolean accederTapis(Client client) throws InterruptedException {
		while (!tapisAccessible) {
			System.err.println("Client " + client.getIdClient() + " attend.");
			client.setEtat(Client.attenteCaisse);
			//Si le tapis est non accessible le thread client est mis en attente
			wait();
		}

		System.err.println("Client " + client.getIdClient() + " : Acc�de au tapis");
		client.setEtat(Client.aLaCaisse);
		tapisAccessible = false;
		return true;
	}

	/**
	 * M�thode atomique permettant au client et au caissier de se synchroniser pour payer
	 * 
	 * @param idClient l'id du client voulant payer
	 * @throws InterruptedException
	 */
	public synchronized void payer(int idClient) throws InterruptedException {
		while (!caissier.getDisponibilite()) {
			//Si le caissier n'est pas disponible le thread client est mis en attente
			wait();
		}
		System.err.println("Client " + idClient + " : Paiement en caisse");
		//Le caissier n'est plus disponible car il est synchro avec le client pour payer
		caissier.setDisponibilite(false);
	}

	
	/**
	 * M�thode atomique permettant au caissier de parcourir le tapis, faire passer les produit et faire avancer le tapis
	 * @throws InterruptedException
	 */
	public synchronized void parcourirTapis() throws InterruptedException {

		String etatTapis = tapis.recupererArticleEtAvancerTapis();
		if (etatTapis.equals("next client")) {
			//Le caissier a reconnu le tag next client
			//Le tapis est donc accessible pour un nouveau clier
			tapisAccessible = true;
			//Le caissier est dispo pour encaisser le paiement du client actuel
			caissier.setDisponibilite(true);
			//On reveille donc les threads en attente a l'entr�e du tapis 
			//Et en attente du caissier
			notifyAll();
		}
		//Si le caissier a r�cup�r� un produit (vid� une partie du tapis et fait avanc�)
		
		else if (etatTapis.equals("article next") && !this.tapisAccessible){
			//On r�veille le thread client attendant pour deposer ses produit
			notifyAll();
		}

	}

	public Tapis getTapis() {
		return tapis;
	}

}
