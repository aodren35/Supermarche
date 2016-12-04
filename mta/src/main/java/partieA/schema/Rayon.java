package partieA.schema;

import partieA.intervenants.Client;

/**
 * Classe Rayon représentant un rayon d'un seul produit
 * Objet partagé entre les threads clients et chef de rayon
 * @author Aodren Letellier - Jordan Monfort
 *
 */
public class Rayon {

	private String article;
	private int stockArticle;

	public Rayon(String articleInit) {
		article = articleInit;
		stockArticle = Supermarche.RAYON_STOCK_INIT;

	}

	public String getArticle() {
		return article;
	}

	public int getStockActuel() {
		return stockArticle;
	}

	public void setStockActuel(int stockActuel) {
		this.stockArticle = stockActuel;
	}

	
	/**
	 * Méthode atomique permettant au thread client de prendre un article dans le rayon
	 * @param client Le client voulant prendre un article dans le rayon
	 * @throws InterruptedException
	 */
	public synchronized void prendreArticle(Client client) throws InterruptedException {
		//Tant que le stock est vide le client attend
		while (stockArticle <= 0) {
			client.setEtat(Client.attenteProduit);
			System.out.println("Client " + client.getIdClient() + " : Attente produit, stock " + article + " : " + stockArticle);
			wait();
		}
		client.setEtat(Client.enCourse);
		System.out.println("Client " + client.getIdClient() + " : Produit pris, stock " + article + " : " + stockArticle);
		stockArticle--;

	}

	/**
	 * Méthode atomique permettant au chef de rayon de remplir un rayon
	 * @param stockChef Stock personnel du chef de rayon correspondant au bon produit
	 * @return
	 */
	public synchronized int remplirStock(int stockChef) {
		//Tant que le stock n'est pas plein ou que le stock perso du chef n'est pas vide
		// Il rempli le rayon
		while (stockArticle < Supermarche.RAYON_STOCK_MAX || stockChef >= 0) {
			stockArticle++;
			stockChef--;
			// Reveil des clients qui attendaient le produit
			notifyAll();
		}
		return stockChef;
	}

}
