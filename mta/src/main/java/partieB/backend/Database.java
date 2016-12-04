package partieB.backend;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import partieA.intervenants.Client;


/**
 * @author Aodren Letellier - Jordan Monfort
 *
 */
public interface Database {

	/**
	 *
	 * Create a new user in the database.
	 *
	 * @param name
	 *            The name of the user
	 * @param age
	 *            The age of the user
	 * @return the new user.
	 */
	Client createClient(int id) throws InterruptedException;

	/**
	 *
	 * Create a new user in the database.
	 *
	 * @param id
	 *            The id of the user
	 */
	void deleteClient(int id) throws InterruptedException;

	/**
	 *
	 * Returns the list of users.
	 *
	 * @return the list of users
	 */
	Collection<Client> getClients();

	/**
	 * Returns the user with a given id.
	 *
	 * @return the user
	 */
	Client getClient(int id);

	void updateClient(Client client);

	/**
	 * @return retourne le compteur client
	 */
	public int getCount();

	public HashMap<String, Integer> getStock();

	public void updateStock(String produit, int nombre);

}
