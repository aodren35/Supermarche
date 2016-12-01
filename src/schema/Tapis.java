package schema;

public class Tapis {
	private static final int tailleTapis = 5;
	private int positionTapisClient;
	private int positionTapisCaissier;
	private String[] tapis;
	
	public Tapis(){
		tapis = new String[tailleTapis];
		for(int i=0; i <tailleTapis ; i++){
			tapis[i]="";
		}
		
		positionTapisClient = 0;
		positionTapisCaissier = tailleTapis-1;
	}
	
	//Client
	public boolean deposerArticle(String article){
		boolean bool;
		if(tapis[positionTapisClient].equals("")){
			tapis[positionTapisClient]=article;
			bool=true;
		}
		else {
			bool=false;
		}
		//on avance sur le tapis
		positionTapisClient++;
		if(positionTapisClient==tailleTapis){
			positionTapisClient=0;
		}
		return bool;
	}
	
	//Caissier 
	public int recupererArticle(){
		int value;
		//rien sur le tapis
		if(tapis[positionTapisCaissier].equals("")){
			value= 1;
		}
		//Passe au client suivant
		else if(tapis[positionTapisCaissier].equals("Client suivant")) {
			tapis[positionTapisCaissier]="";
			value= 2;
		}
		//recupère un article et passe à l'article suivant
		else{
			tapis[positionTapisCaissier]="";
			value = 3;
		}
		positionTapisCaissier--;
		if(positionTapisCaissier<0){
			positionTapisCaissier=tailleTapis-1;
		}
		return value;
	}
	
	
}
