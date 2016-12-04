package partieA.schema;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

import partieA.intervenants.*;

import partieB.ressources.ClientResource;
import partieB.ressources.ClientsResource;
import partieB.ressources.RootResource;
import partieB.ressources.StockResource;

/**
 * Classe Supermarche représente le supermarche sous forme d'application
 * Contient les chariots, les rayons, la caisse, les employés et peut acceuillir des clients
 * 
 * @author Aodren Letellier - Jordan Monfort
 *
 */
public class Supermarche extends Application {

	public static final int NB_CHARIOTS = 10;
	public static final int NB_CLIENTS = 11;
	public static final int RAYON_STOCK_MAX = 5;
	public static final int RAYON_STOCK_INIT = 5;
	
	private Chariots chariots;
	private HashMap<Integer, Client> clients;
	private HashMap<String, Rayon> rayons;
	private ChefDeRayon chef;
	private Caisse caisse;
	private Caissier caissier;

	/**
	 * Constructeur de supermarche dans le cas ou restket est utilisé
	 * @param context
	 */
	public Supermarche(Context context) {
		super(context);
		clients = new HashMap<Integer, Client>();
		chariots = new Chariots();
		rayons = new HashMap<String, Rayon>();
		chef = new ChefDeRayon(this);
		caissier = new Caissier(this);
		caisse = new Caisse();
		caisse.setCaissier(caissier);
	}
	
	/**
	 * Constructeur de supermarche simple
	 */
	public Supermarche(){
		clients = new HashMap<Integer, Client>();
		chariots = new Chariots();
		rayons = new HashMap<String, Rayon>();
		chef = new ChefDeRayon(this);
		caissier = new Caissier(this);
		caisse = new Caisse();
		caisse.setCaissier(caissier);
	}

	public Chariots getChariots() {
		return chariots;
	}

	/**
	 * @return retourne les stocks actuels de tous les rayons
	 */
	public HashMap<String, Integer> getStock() {
		HashMap<String, Integer> newMap = new HashMap<String, Integer>();
		Set<String> keyset = rayons.keySet();
		Iterator<String> it = keyset.iterator();
		while (it.hasNext()) {
			String key = it.next();
			Rayon ray = rayons.get(key);
			newMap.put(ray.getArticle(), ray.getStockActuel());
		}

		return newMap;
	}

	public HashMap<String, Rayon> getRayons() {
		return rayons;
	}

	public void addClient(int i, Client nouveauClient) {
		clients.put(i, nouveauClient);
	}

	public HashMap<Integer, Client> getClients() {
		return clients;
	}

	public void addRayon(Rayon nouveauRayon) {
		rayons.put(nouveauRayon.getArticle(), nouveauRayon);
	}

	public Rayon getRayon(String article) {
		return rayons.get(article);
	}

	public ChefDeRayon getChef() {
		return chef;
	}

	public Caisse getCaisse() {
		return caisse;
	}

	public Caissier getCaissier() {
		return caissier;
	}

	
	/**
	 * Méthode permettant de démarrer le supermarche
	 * Start les thread employés
	 */
	public void launchSupermarche() {
		Rayon sucre, farine, beurre, lait;
		sucre = new Rayon("sucre");
		farine = new Rayon("farine");
		beurre = new Rayon("beurre");
		lait = new Rayon("lait");

		this.addRayon(sucre);
		this.addRayon(farine);
		this.addRayon(lait);
		this.addRayon(beurre);

		//Les threads chef de rayon et caissier sont set en Daemon pour qu'ils puissent 
		//S'arreter lorsqu'il n'y a plus aucun client
		this.getChef().setDaemon(true);
		this.getCaissier().setDaemon(true);
		
		this.getChef().start();
		this.getCaissier().start();

	}

	/**
	 * Méthode permettant d'ajouter NB_CLIENTS au supermarche 
	 * et de les lancer
	 */
	public void addAndStartClient() {
		int i;
		for (i = 1; i <= NB_CLIENTS; i++) {
			Client nouveauClient = new Client(this, i);
			this.addClient(i, nouveauClient);
		}
		for (i = 1; i <= NB_CLIENTS; i++) {
			this.getClients().get(i).start();
		}
	}

	//Méthode Restlet
	@Override
	public Restlet createInboundRoot() {
		File staticDirectory = new File("static/");
		Directory directory = new Directory(getContext(), "file:///" + staticDirectory.getAbsolutePath() + "/");
		directory.isDeeplyAccessible();
		directory.isListingAllowed();

		Router router = new Router(getContext());
		router.attach("/supermarche", RootResource.class);
		router.attach("/static", directory);
		router.attach("/supermarche/clients/", ClientsResource.class);
		router.attach("/supermarche/clients/{clientId}", ClientResource.class);
		router.attach("/supermarche/clients/{clientId}/", ClientResource.class);
		router.attach("/supermarche/stock", StockResource.class);
		router.attach("/supermarche/stock/", StockResource.class);
		return router;
	}
	
	
	/**
	 * 
	 * Méthode main pour lancer un Supermarche simple observable dans la console
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Supermarche marche = new Supermarche();
		marche.launchSupermarche();
		marche.addAndStartClient();
	}

}
