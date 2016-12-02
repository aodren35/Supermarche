package main;

import java.util.HashMap;

import intervenants.*;
import schema.*;

public class Supermarche {

	public static final int NB_CHARIOTS = 10;
	public static final int NB_CLIENTS = 11;
	public static final int RAYON_STOCK_MAX = 5;
	public static final int RAYON_STOCK_INIT = 5;
	private Chariots chariots;
	private HashMap<Integer,Client>clients;
	private HashMap<String,Rayon> rayons;
	private ChefDeRayon chef;
	private Caisse caisse;
	private Caissier caissier;
	
	public Supermarche(){
		clients=new HashMap<Integer,Client>();
		chariots=new Chariots();
		rayons = new HashMap<String,Rayon>();
		chef = new ChefDeRayon(this);
		caissier = new Caissier(this);
		caisse = new Caisse();
		caisse.setCaissier(caissier);
	}
	
	public Chariots getChariots(){
		return chariots;
	}
	
	public void addClient(int i,Client nouveauClient){
		clients.put(i, nouveauClient);
	}
	
	public HashMap<Integer,Client> getClients(){
		return clients;
	}
	
	public void addRayon(Rayon nouveauRayon){
		rayons.put(nouveauRayon.getArticle(),nouveauRayon);
	}
	public Rayon getRayon(String article){
		return rayons.get(article);
	}
	
	public ChefDeRayon getChef(){
		return chef;
	}
	
	public Caisse getCaisse(){
		return caisse;
	}
	
	public Caissier getCaissier(){
		return caissier;
	}
	
	
	public static void main(String[] args) {
				
		Supermarche superMarche = new Supermarche();
		Rayon sucre , farine , beurre,lait;
		sucre=new Rayon("sucre");
		farine=new Rayon("farine");
		beurre=new Rayon("beurre");
		lait=new Rayon("lait");
		
		superMarche.addRayon(sucre);
		superMarche.addRayon(farine);
		superMarche.addRayon(lait);
		superMarche.addRayon(beurre);

		superMarche.getChef().setDaemon(true);
		superMarche.getCaissier().setDaemon(true);
		
		superMarche.getCaissier().start();
		int i ;
		for(i = 1;i<=NB_CLIENTS;i++){
			Client nouveauClient = new Client(superMarche,i);
			superMarche.addClient(i,nouveauClient);
		}
		for (i=1;i<=NB_CLIENTS;i++){
			superMarche.getClients().get(i).start();
		}
		superMarche.getChef().start();
		
	}

}
