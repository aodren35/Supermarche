package schema;

public class Tapis {
	private static final int tailleTapis = 5;
	//Tapis du point de vue du client
	private int positionTapisClient;
	
	//Tapis du point du vue du caissier
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
	
	//Point du vue du client qui d�pose ses articles
	public boolean deposerArticle(String article){
		boolean bool;
		if(tapis[positionTapisClient].equals("")){
			tapis[positionTapisClient]=article;
			System.out.println(article + " d�pos�");
			bool=true;
		}
		else {
			bool=false;
		}

		return bool;
	}
	
	//Point du vue du caissier qui r�cup�re les articles du client 
	public String recupererArticleEtAvancerTapis(){
		String value;
		//rien sur le tapis
		if(tapis[positionTapisCaissier].equals("")){
			value="rien";
		}
		//Passe au client suivant
		else if(tapis[positionTapisCaissier].equals("Client suivant")) {
			tapis[positionTapisCaissier]="";
			value= "next client";
		}
		//recup�re un article et passe � l'article suivant
		else{
			tapis[positionTapisCaissier]="";
			value ="article next";
		}
		positionTapisCaissier--;
		if(positionTapisCaissier<0){
			positionTapisCaissier=tailleTapis-1;
		}
		//on avance sur le tapis
		positionTapisClient++;
		//Remise � z�ro du tapis du point de vue du client
		if(positionTapisClient==tailleTapis-1){
			positionTapisClient=0;
		}
		return value;
	}
	
	
	
}
