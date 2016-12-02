package main;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.inria.restlet.mta.resources.ClientResource;
import org.inria.restlet.mta.resources.ClientsResource;
import org.inria.restlet.mta.resources.RootResource;
import org.inria.restlet.mta.resources.StockResource;
import org.json.JSONObject;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

import intervenants.*;
import schema.*;

public class Supermarche extends Application{

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
	
	
	
	public Supermarche(Context context){
		super(context);
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
	
	public HashMap<String,Integer> getStock(){
		HashMap<String,Integer> newMap = new HashMap<String,Integer>();
		Set<String>keyset = rayons.keySet();
		Iterator<String>it = keyset.iterator();
		while(it.hasNext()){
			String key = it.next();
			Rayon ray = rayons.get(key); 
			newMap.put(ray.getArticle(),ray.getStockActuel() );
		}
		
		return newMap;
	}
	
	public HashMap<String,Rayon> getRayons(){
		return rayons;
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
	
	public void launchSupermarche(){
		Rayon sucre , farine , beurre,lait;
		sucre=new Rayon("sucre");
		farine=new Rayon("farine");
		beurre=new Rayon("beurre");
		lait=new Rayon("lait");
		
		this.addRayon(sucre);
		this.addRayon(farine);
		this.addRayon(lait);
		this.addRayon(beurre);

		this.getChef().setDaemon(true);
		this.getCaissier().setDaemon(true);
		this.getChef().start();
		this.getCaissier().start();
//		int i ;
//		for(i = 1;i<=NB_CLIENTS;i++){
//			Client nouveauClient = new Client(superMarche,i);
//			superMarche.addClient(i,nouveauClient);
//		}
//		for (i=1;i<=NB_CLIENTS;i++){
//			superMarche.getClients().get(i).start();
//		}
		
	}
	
    @Override
    public Restlet createInboundRoot()
    {
    	File staticDirectory = new File("static/");
    	Directory directory = new Directory(getContext(), "file:///" + staticDirectory.getAbsolutePath() + "/");
    	directory.isDeeplyAccessible();
    	directory.isListingAllowed();
    	   	
        Router router = new Router(getContext() );
        router.attach("/supermarche", RootResource.class);
        router.attach("/static", directory);
        //router.attach("/clients", ClientsResource.class);
        router.attach("/supermarche/clients/", ClientsResource.class);
        router.attach("/supermarche/clients/{clientId}", ClientResource.class);
        router.attach("/supermarche/clients/{clientId}/", ClientResource.class);
        router.attach("/supermarche/stock", StockResource.class);
        router.attach("/supermarche/stock/", StockResource.class);
        return router;
    }

}
