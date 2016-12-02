package org.inria.restlet.mta.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.inria.restlet.mta.backend.Backend;
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

import intervenants.Client;
import main.Supermarche;

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
	 * Returns the list of all the users
	 *
	 * @return JSON representation of the users
	 * @throws JSONException
	 */
	@Get("json")
	public Representation getClients() throws JSONException {
		Collection<Client> clients = backend_.getDatabase().getClients();
		Collection<JSONObject> jsonClients = new ArrayList<JSONObject>();

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
	 * Create a user with the data present in the json representation
	 * 
	 * @param json
	 *            representation of the user to create
	 * @return JSON representation of the newly created user
	 * @throws JSONException
	 */
	@Post("json")
	public Representation createUsers(JsonRepresentation representation) throws Exception {

		JSONObject object = representation.getJsonObject();
		JSONObject resultObject = new JSONObject();
		int nombre = object.getInt("nombre_de_client");
		System.out.println("NOMBRE DE CLIENT : "+nombre);
		int count = backend_.getDatabase().getCount();
		System.out.println("Count database : "+count);
		for (int i = count; i < count + nombre; i++) {
			// Save the user
			Client client = backend_.getDatabase().createClient(i);
			client.setMarche(((Supermarche) getApplication()));
			((Supermarche) getApplication()).addClient(client.getIdClient(),client);
			client.start();
			// generate result
			resultObject.put("id", client.getIdClient());
		}
		JsonRepresentation result = new JsonRepresentation(resultObject);
		return result;
	}

}
