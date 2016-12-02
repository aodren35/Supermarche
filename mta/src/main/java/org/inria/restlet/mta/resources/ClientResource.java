package org.inria.restlet.mta.resources;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.inria.restlet.mta.backend.Backend;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import intervenants.Client;

public class ClientResource extends ServerResource {
	/** Backend. */
	private Backend backend_;

	/** User handled by this resource. */
	private Client client_;

	/**
	 * Constructor. Call for every single user request.
	 */
	public ClientResource() {
		backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
	}

	/**
	 * Returns the user matching the id given in the URI
	 * 
	 * @return JSON representation of a user
	 * @throws JSONException
	 */
	@Get("json")
	public Representation getClient() throws Exception {
		String clientIdString = (String) getRequest().getAttributes().get("clientId");
		int clientId = Integer.valueOf(clientIdString);
		client_ = backend_.getDatabase().getClient(clientId);

		JSONObject userObject = new JSONObject();
		userObject.put("id", client_.getId());
		HashMap<String, Integer> listeLocale = client_.getListeCourseCourrante();
		userObject.put("liste de course", listeLocale);
		userObject.put("état", client_.getEtat());

		JsonRepresentation result = new JsonRepresentation(userObject);
		result.setIndenting(true);
		return result;
	}

}
