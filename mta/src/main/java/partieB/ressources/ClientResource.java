package partieB.ressources;

import java.util.HashMap;


import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import partieA.intervenants.Client;
import partieB.backend.Backend;

/**
 * Ressource pour un client Uri : /supermarche/clients/{clientId}/
 * @author Aodren Letellier - Jordan Monfort
 *
 */
public class ClientResource extends ServerResource {
	/** Backend. */
	private Backend backend_;

	/** Client handled by this resource. */
	private Client client_;

	/**
	 * Constructor. Call for every single user request.
	 */
	public ClientResource() {
		backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
	}

	/*
	 * The method doInit is called prior to the others.
	 * Retourne un code erreur 404 si le client n'existe pas
	 */
	@Override
	protected void doInit() throws ResourceException {
		int idClient = Integer.valueOf((String) getRequest().getAttributes().get("clientId"));
		client_ = backend_.getDatabase().getClient(idClient);
		if (client_ == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}

	/**
	 * Retourn le client correspondant à l'URI
	 * 
	 * @return JSON representation of a user
	 * @throws JSONException
	 */
	@Get("json")
	public Representation getClient() throws Exception {
		//Prend l'id client de l'uri
		String clientIdString = (String) getRequest().getAttributes().get("clientId");
		int clientId = Integer.valueOf(clientIdString);
		//Récupère le client dans la database
		client_ = backend_.getDatabase().getClient(clientId);

		//Crée le json correspondant au client
		JSONObject userObject = new JSONObject();
		userObject.put("id", client_.getIdClient());
		HashMap<String, Integer> listeLocale = client_.getListeCourseCourrante();
		userObject.put("liste de course", listeLocale);
		userObject.put("état", client_.getEtat());

		JsonRepresentation result = new JsonRepresentation(userObject);
		result.setIndenting(true);
		return result;
	}

}
