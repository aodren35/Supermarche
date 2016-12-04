package partieB.backend;


/**
 * @author Aodren Letellier - Jordan Monfort
 *
 */
public class Backend
{
    /** Database.*/
    private Database database_;

    public Backend() {
        database_ = new InMemoryDatabase();
    }

    public Database getDatabase()
    {
        return database_;
    }

}
