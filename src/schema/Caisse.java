package schema;

import intervenants.Caissier;

public class Caisse{
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

	public synchronized boolean accederTapis(int idClient) throws InterruptedException {
		while (!tapisAccessible) {
			System.err.println("Client " + idClient + " attend.");
			wait();
		}

		System.err.println("Client " + idClient + " : Accède au tapis");
		tapisAccessible = false;
		return true;
	}

	public synchronized void Payer(int idClient) throws InterruptedException {
		while (!caissier.getDisponibilite()) {
			wait();
		}
		System.err.println("Client " + idClient + " : Je paye et me barre");
		caissier.setDisponibilite(false);
		notifyAll();
	}

	public synchronized void parcourirTapis() throws InterruptedException {

		String etatTapis = tapis.recupererArticleEtAvancerTapis();
		if (etatTapis.equals("next client")) {
			tapisAccessible = true;
			caissier.setDisponibilite(true);
			// wait();
		}

	}

	public Tapis getTapis() {
		return tapis;
	}

}
