package partieB.main;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.resource.Directory;

import partieA.schema.Supermarche;
import partieB.backend.Backend;

/**
 * Appli RestLet
 * @author Aodren Letellier - Jordan Monfort
 *
 */
public final class Main {
	private static Supermarche supermarche;

	/** Hide constructor. */
	private Main() {
		throw new UnsupportedOperationException();
	}

	public void setSuperMarche(Supermarche sm) {
		supermarche = sm;
	}

	/**
	 * Main method.
	 *
	 * @param args
	 *            The arguments of the command line
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Create a component
		Component component = new Component();
		Context context = component.getContext().createChildContext();
		component.getServers().add(Protocol.HTTP, 5001);
		component.getClients().add(Protocol.FILE);

		// Create an application
		Application application = new Supermarche(context);

		// Add the backend into component's context
		Backend backend = new Backend();
		context.getAttributes().put("backend", backend);
		component.getDefaultHost().attach(application);

		// Start the application
		((Supermarche) application).launchSupermarche();

		// Start the component
		component.start();
	}

}
