package intervenants;

import main.Supermarche;

public class ChefDeRayon extends Thread {
	private int stockFarine;
	private int stockLait;
	private int stockBeurre;
	private int stockSucre;
	private Supermarche supermarche;

	public ChefDeRayon(Supermarche superMarche) {
		stockFarine = 0;
		stockLait = 0;
		stockBeurre = 0;
		stockSucre = 0;
		supermarche = superMarche;
	}
	
	public void faireLePlein(){
		stockFarine = 5;
		stockLait = 5;
		stockBeurre = 5;
		stockSucre = 5;
	}
	
	public void run(){
		 try {
			 while(true){
				 //debut journée dans l'entrepôt
				 //"faire le plein"
				 faireLePlein();
				 sleep(500);
				 //Entrepôt->rayon sucre
				 sleep(200);
				 //Remplir rayon sucre
				 stockSucre=supermarche.getRayon("sucre").remplirStock(stockSucre);
				 //sucre -> farine
				 sleep(200);
				 //Remplir rayon sucre
				 stockFarine=supermarche.getRayon("farine").remplirStock(stockFarine);
				 //farine -> beurre
				 sleep(200);
				 //Remplir rayon beurre
				 stockBeurre=supermarche.getRayon("beurre").remplirStock(stockBeurre);
				 //beurre->lait
				 sleep(200);
				 //Remplir rayon lait
				 stockLait=supermarche.getRayon("lait").remplirStock(stockLait);
				 //Lait->entrepôt
				 sleep(200);

			 }
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

