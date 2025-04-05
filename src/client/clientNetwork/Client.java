package client.clientNetwork;

import util.PropertyChangeSubject;
import java.util.ArrayList;
import java.util.List;

public interface Client extends PropertyChangeSubject
{
    void startClient();
    Boolean login(String username, String password);
    Boolean checkUser(String user, String password);
    Boolean addVinyl(String title, String artist, String owner);
    Boolean borrowVinyl(String title, String borrower);
    Boolean returnVinyl(String title);
    ArrayList<String> getAllVinyls();
    ArrayList<String> getMyVinyls(String username);
    ArrayList<String> getBorrowedVinyls(String username);
    ArrayList<String> getAvailableVinyls();
} 