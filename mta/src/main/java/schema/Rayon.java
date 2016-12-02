package schema;

import intervenants.Client;
import main.Supermarche;

public class Rayon {

	private String article;
	private int stockArticle;
	//private Supermarche supermarche;
	
	public Rayon(String articleInit){
		article=articleInit;
		//supermarche=superMarche;
		stockArticle=Supermarche.RAYON_STOCK_INIT;
		
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
	
	public synchronized void prendreArticle(Client client) throws InterruptedException{
		while (stockArticle<=0){
			client.setEtat(Client.attenteProduit);
			System.out.println("Client " + client.getIdClient()  + " : Attente produit, stock " + article + " : " +stockArticle);
			wait();
		}
		client.setEtat(Client.enCourse);
		System.out.println("Client " + client.getIdClient()  + " : Produit pris, stock " + article + " : "+stockArticle);
		stockArticle --;
		
	}
	
	public synchronized int remplirStock(int stockChef){
		while(stockArticle<Supermarche.RAYON_STOCK_MAX && stockChef>=0){
			
			System.out.println("Stock incremente, stock " + article + " : " + stockArticle);
			//Ajout d'un article en rayon
			stockArticle ++;
			//Décrémentation du stock du chef de rayon
			stockChef--;
			//Reveil du client qui attendait son chariot
			notifyAll();
		}
		return stockChef;
	}
	
}
