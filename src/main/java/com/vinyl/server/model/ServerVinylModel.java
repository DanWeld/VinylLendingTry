package com.vinyl.server.model;

import com.vinyl.shared.User;
import com.vinyl.shared.Vinyl;
import com.vinyl.util.PropertyChangeSubject;
import java.util.ArrayList;

public interface ServerVinylModel extends PropertyChangeSubject
{
    void addUser(User user);
    boolean login(String username, String password);
    Boolean checkUser(String user);
    Boolean addVinyl(Vinyl vinyl);
    Boolean borrowVinyl(String title, String borrower);
    Boolean returnVinyl(String title);
    ArrayList<String> getAllVinyls();
    ArrayList<String> getMyVinyls(String username);
    ArrayList<String> getBorrowedVinyls(String username);
    ArrayList<String> getAvailableVinyls();
} 