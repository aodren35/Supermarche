package schema;

import main.Supermarche;

public class Chariots {
	private int stockChariot;
	
	public Chariots(){
		stockChariot = Supermarche.NB_CHARIOTS;
	}
	
	public synchronized void prendreChariot() throws InterruptedException{
		//attente d'un chariot
		while (stockChariot<=0){
			System.out.println("Attente chariot, stock chariot : "+stockChariot);
			wait();
		}
		System.out.println("Chariot pris, stock chariot : "+stockChariot);
		stockChariot --;
		
	}
	
	public synchronized void rendreChariot(){
		System.out.println("Chariot rendu, stock chariot : "+stockChariot);
		stockChariot ++;
		//Reveil du client qui attendait son chariot
		notifyAll();
	}
	
	
	

}
