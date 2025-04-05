package client.clientModel;

import util.PropertyChangeSubject;
import java.util.ArrayList;

public interface ClientVinylModel extends PropertyChangeSubject
{
    Boolean checkUsername(String user, String password);
    Boolean login(String username, String password);
    Boolean addVinyl(String title, String artist, String owner);
    Boolean borrowVinyl(String title, String borrower);
    Boolean returnVinyl(String title);
    ArrayList<String> getAllVinyls();
    ArrayList<String> getMyVinyls(String username);
    ArrayList<String> getBorrowedVinyls(String username);
    ArrayList<String> getAvailableVinyls();
} 