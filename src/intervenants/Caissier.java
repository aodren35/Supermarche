package intervenants;

import main.Supermarche;

public class Caissier extends Thread{
	private Supermarche supermarche;
	private boolean disponibilite;
	
	public Caissier(Supermarche superMarche) {
		this.disponibilite = true;	
		this.supermarche=superMarche;
	}

	public boolean getDisponibilite() {
		return this.disponibilite;
	}
	
	public void run(){
	while(true){
		//TODO Recup�rer les articles et qd il aura r�cup tous les articles du client il va �tre disponible pour payer.
		//Try Catch
	}
	}
}
