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
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import main.Supermarche;


public class StockResource extends ServerResource {
	/** Backend. */
	private Backend backend_;

	/**
	 * Constructor. Call for every single user request.
	 */
	public StockResource() {
		super();
		backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
	}

	/**
	 * Returns the current stock
	 * 
	 * @return JSON representation of a user
	 * @throws JSONException
	 */
	@Get("json")
	public Representation getStock() throws Exception {
		
		Collection<JSONObject> stocks = new ArrayList<JSONObject>();
		//remplir le stock
		HashMap<String,Integer>stock=((Supermarche) getApplication()).getStock();
		Set<String>keyset = stock.keySet();
		Iterator<String>it = keyset.iterator();
		while(it.hasNext()){
			String key = it.next();
			int value = stock.get(key);
			System.out.println("STOCK : "+key+ " "+value);
			JSONObject stockObject = new JSONObject();
			stockObject.put(key, value);
			stocks.add(stockObject);
		}
		
        JSONArray jsonArray = new JSONArray(stocks);
        JsonRepresentation result = new JsonRepresentation(jsonArray);
        result.setIndenting(true);
        return result;
		
		
	}

}
