package schema;

import main.Supermarche;

public class Chariots {
	private int stockChariot;
	
	public Chariots(){
		stockChariot = Supermarche.NB_CHARIOTS;
	}
	
	public synchronized void prendreChariot(int idClient) throws InterruptedException{
		//attente d'un chariot
		while (stockChariot<=0){
			System.out.println("Client " + idClient + " : Attente chariot par le Client, stock chariot : "+stockChariot);
			wait();
		}
		System.out.println("Client " + idClient + " : Chariot pris, stock chariot : "+stockChariot);
		stockChariot --;
		
	}
	
	public synchronized void rendreChariot(int idClient) throws InterruptedException{
		stockChariot ++;
		System.out.println("Client " + idClient + " : Chariot rendu par le Client, stock chariot : "+stockChariot);
		//Reveil du client qui attendait son chariot
		notifyAll();
	}
	
	
	

}
