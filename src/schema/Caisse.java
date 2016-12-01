package schema;

import intervenants.Caissier;

public class Caisse extends Thread {
	private Caissier caissier;
	private Tapis tapis;
	private boolean tapisAccessible;
	
	public Caisse(){
		this.tapis = new Tapis();
		tapisAccessible = true;
	}
	
	public void setCaissier(Caissier caissier){
		this.caissier= caissier;
	}
	
	public synchronized boolean accederTapis(int idClient) throws InterruptedException{
		while(!tapisAccessible){
			wait();
		}
		
		System.out.println("Client " + idClient  + " : Accède au tapis");
		tapisAccessible=false;
		return true;
	}
	
	public synchronized void Payer(int idClient) throws InterruptedException{
		while(!caissier.getDisponibilite()){
			wait();
		}
		System.out.println("Client " + idClient  + " : Je paye et me barre");
		notifyAll();
	}

}
