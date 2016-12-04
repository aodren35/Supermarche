package partieB.ressources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import partieA.intervenants.Client;
import partieA.schema.Supermarche;
import partieB.backend.Backend;

/**
 * Ressource pour les clients URI : /supermarche/clients
 * @author Aodren Letellier - Jordan Monfort
 *
 */
public class ClientsResource extends ServerResource {
	/** Backend. */
	private Backend backend_;

	/**
	 * Constructor. Call for every single user request.
	 */
	public ClientsResource() {
		super();
		backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
	}

	/**
	 * Returns the list of all the clients
	 *
	 * @return JSON representation of the clients
	 * @throws JSONException
	 */
	@Get("json")
	public Representation getClients() throws JSONException {
		//Récupere les clients dans la database
		Collection<Client> clients = backend_.getDatabase().getClients();
		Collection<JSONObject> jsonClients = new ArrayList<JSONObject>();

		//Construction du json
		for (Client client : clients) {
			JSONObject current = new JSONObject();
			current.put("id", client.getIdClient());
			current.put("url", getReference().getPath() + client.getIdClient());

			jsonClients.add(current);

		}
		JSONArray jsonArray = new JSONArray(jsonClients);
		JsonRepresentation result = new JsonRepresentation(jsonArray);
		result.setIndenting(true);
		return result;
	}

	/**
	 * Crée un nombre de client correspondant dans la JSONRepresentation
	 * 
	 * @param json
	 *            representation of the number of client to create
	 * @return JSON representation of the newly created clients
	 * @throws JSONException
	 */
	@Post("json")
	public Representation createUsers(JsonRepresentation representation) throws Exception {

		JSONObject object = representation.getJsonObject();
		JSONObject resultObject = new JSONObject();
		//Récupère le nombre de client que l'utilisateur veut creer
		int nombre = object.getInt("nombre_de_client");
		System.out.println("NOMBRE DE CLIENT : " + nombre);
		//Nombre de client déjà dans l'appli
		int count = backend_.getDatabase().getCount();
		System.out.println("Count database : " + count);
		for (int i = count; i < count + nombre; i++) {
			// Crée un client dans la database
			Client client = backend_.getDatabase().createClient(i);
			//Ajoute ce client au supermarche
			client.setMarche(((Supermarche) getApplication()));
			((Supermarche) getApplication()).addClient(client.getIdClient(), client);
			//Lance le thread
			client.start();
			// generate result
			resultObject.put("id", client.getIdClient());
		}
		JsonRepresentation result = new JsonRepresentation(resultObject);
		return result;
	}

}
