
package org.inria.restlet.mta.database.api.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.inria.restlet.mta.database.api.Database;

import intervenants.Client;

/**
 *
 * In-memory database 
 *
 * @author ctedeschi
 * @author msimonin
 *
 */
public class InMemoryDatabase implements Database
{

    /** User count (next id to give).*/
    private int clientCount_;

    /** User Hashmap. */
    Map<Integer, Client> clients_;
    HashMap<String,Integer>stock_;


    public InMemoryDatabase()
    {
        clients_ = new HashMap<Integer, Client>();
        stock_ = new HashMap<String,Integer>();
    }




	@Override
	public Client getClient(int id) {
		
		return clients_.get(id);
	}


	@Override
	public void deleteClient(int id) throws InterruptedException {
		clients_.remove(id);
		clientCount_ --;
		
	}

	@Override
	public Collection<Client> getClients() {
		return clients_.values();
	}



	@Override
	public void updateClient(Client client) {
		int clientId = client.getIdClient();
		clients_.put(clientId, client);	
	}

	@Override
	public Client createClient(int id) throws InterruptedException {
        Client client = new Client(id);
        //client.setIdClient(id);
        clients_.put(clientCount_, client);
        Thread.sleep(100);
        clientCount_ ++;
        return client;
	}

	public int getCount(){
		return clientCount_;
	}




	@Override
	public HashMap<String, Integer> getStock() {
		return stock_;
	}




	@Override
	public void updateStock(String produit, int nombre) {
		stock_.replace(produit, nombre);
	}
	
	


}
