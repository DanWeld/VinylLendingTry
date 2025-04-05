package com.vinyl.server.network;

import com.vinyl.server.model.ServerVinylModel;
import com.vinyl.shared.Request;
import com.vinyl.shared.User;
import com.vinyl.shared.Vinyl;
import com.vinyl.util.Logger;
import com.vinyl.util.MessageFormatter;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSocketHandler implements Runnable
{
    private Socket socket;
    private ServerVinylModel serverVinylModel;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private ConnectionPool pool;
    private Logger logger;
    private String clientIP;

    public ServerSocketHandler(Socket socket, ServerVinylModel serverVinylModel,
        ConnectionPool pool)
    {
        this.socket = socket;
        this.serverVinylModel = serverVinylModel;
        this.pool = pool;
        this.clientIP = socket.getInetAddress().getHostAddress();
        this.logger = Logger.getInstance();

        try
        {
            outToClient = new ObjectOutputStream(socket.getOutputStream());
            inFromClient = new ObjectInputStream(socket.getInputStream());
            
            // Log connection established
            logger.log("New client connection established", clientIP);
        }
        catch (IOException e)
        {
            try
            {
                String errorMsg = MessageFormatter.createErrorJsonMessage("Failed to initialize client connection: " + e.getMessage());
                logger.log(errorMsg, clientIP);
            }
            catch (IOException ex)
            {
                System.err.println("Failed to log error: " + ex.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    @Override public void run()
    {
        try
        {
            Request request = (Request) inFromClient.readObject();
            // Log incoming request
            logger.log(MessageFormatter.createJsonMessage("request", "Received request: " + request.getType()), clientIP);
            
            switch(request.getType()) {
                case "Listener":
                    handleListener();
                    break;
                case "checkUser":
                    handleCheckUser(request);
                    break;
                case "login":
                    handleLogin(request);
                    break;
                case "addVinyl":
                    handleAddVinyl(request);
                    break;
                case "borrowVinyl":
                    handleBorrowVinyl(request);
                    break;
                case "returnVinyl":
                    handleReturnVinyl(request);
                    break;
                case "getAllVinyls":
                    handleGetAllVinyls();
                    break;
                case "getMyVinyls":
                    handleGetMyVinyls(request);
                    break;
                case "getBorrowedVinyls":
                    handleGetBorrowedVinyls(request);
                    break;
                case "getAvailableVinyls":
                    handleGetAvailableVinyls();
                    break;
                default:
                    logger.log(MessageFormatter.createErrorJsonMessage("Unknown request type: " + request.getType()), clientIP);
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            try
            {
                logger.log(MessageFormatter.createErrorJsonMessage("Error processing request: " + e.getMessage()), clientIP);
            }
            catch (IOException ex)
            {
                System.err.println("Failed to log error: " + ex.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    private void handleListener() throws IOException
    {
        serverVinylModel.addPropertyChangeListener("vinylAdded",
            this::onVinylAdded);
        serverVinylModel.addPropertyChangeListener("vinylBorrowed",
            this::onVinylBorrowed);
        serverVinylModel.addPropertyChangeListener("vinylReturned",
            this::onVinylReturned);
        logger.log(MessageFormatter.createJsonMessage("listener", "Client registered as listener"), clientIP);
    }

    private void handleCheckUser(Request request) throws IOException
    {
        User user = (User) request.getArg();
        boolean exists = serverVinylModel.checkUser(user.getUsername());
        if (!exists)
        {
            serverVinylModel.addUser(user);
        }
        outToClient.writeObject(new Request("checkUser", exists));
    }

    private void handleLogin(Request request) throws IOException
    {
        User user = (User) request.getArg();
        boolean success = serverVinylModel.login(user.getUsername(),
            user.getPassword());
        outToClient.writeObject(new Request("login", success));
    }

    private void handleAddVinyl(Request request) throws IOException
    {
        Vinyl vinyl = (Vinyl) request.getArg();
        boolean success = serverVinylModel.addVinyl(vinyl);
        outToClient.writeObject(new Request("addVinyl", success));
    }

    private void handleBorrowVinyl(Request request) throws IOException
    {
        String[] args = (String[]) request.getArg();
        boolean success = serverVinylModel.borrowVinyl(args[0],
            args[1]);
        outToClient.writeObject(new Request("borrowVinyl", success));
    }

    private void handleReturnVinyl(Request request) throws IOException
    {
        String title = (String) request.getArg();
        boolean success = serverVinylModel.returnVinyl(title);
        outToClient.writeObject(new Request("returnVinyl", success));
    }

    private void handleGetAllVinyls() throws IOException
    {
        ArrayList<String> vinyls = serverVinylModel.getAllVinyls();
        outToClient.writeObject(new Request("getAllVinyls", vinyls));
    }

    private void handleGetMyVinyls(Request request) throws IOException
    {
        String username = (String) request.getArg();
        ArrayList<String> vinyls = serverVinylModel.getMyVinyls(
            username);
        outToClient.writeObject(new Request("getMyVinyls", vinyls));
    }

    private void handleGetBorrowedVinyls(Request request) throws IOException
    {
        String username = (String) request.getArg();
        ArrayList<String> vinyls = serverVinylModel.getBorrowedVinyls(
            username);
        outToClient.writeObject(
            new Request("getBorrowedVinyls", vinyls));
    }

    private void handleGetAvailableVinyls() throws IOException
    {
        ArrayList<String> vinyls = serverVinylModel.getAvailableVinyls();
        outToClient.writeObject(
            new Request("getAvailableVinyls", vinyls));
    }

    private void onVinylAdded(PropertyChangeEvent event)
    {
        try
        {
            String message = MessageFormatter.createJsonMessage("vinylAdded", "Vinyl added: " + event.getNewValue());
            logger.log(message, clientIP);
            outToClient.writeObject(new Request(event.getPropertyName(), event.getNewValue()));
        }
        catch (IOException e)
        {
            try
            {
                logger.log(MessageFormatter.createErrorJsonMessage("Error sending vinyl added event: " + e.getMessage()), clientIP);
            }
            catch (IOException ex)
            {
                System.err.println("Failed to log error: " + ex.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    private void onVinylBorrowed(PropertyChangeEvent event)
    {
        try
        {
            String message = MessageFormatter.createJsonMessage("vinylBorrowed", "Vinyl borrowed: " + event.getNewValue());
            logger.log(message, clientIP);
            outToClient.writeObject(new Request(event.getPropertyName(), event.getNewValue()));
        }
        catch (IOException e)
        {
            try
            {
                logger.log(MessageFormatter.createErrorJsonMessage("Error sending vinyl borrowed event: " + e.getMessage()), clientIP);
            }
            catch (IOException ex)
            {
                System.err.println("Failed to log error: " + ex.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    private void onVinylReturned(PropertyChangeEvent event)
    {
        try
        {
            String message = MessageFormatter.createJsonMessage("vinylReturned", "Vinyl returned: " + event.getNewValue());
            logger.log(message, clientIP);
            outToClient.writeObject(new Request(event.getPropertyName(), event.getNewValue()));
        }
        catch (IOException e)
        {
            try
            {
                logger.log(MessageFormatter.createErrorJsonMessage("Error sending vinyl returned event: " + e.getMessage()), clientIP);
            }
            catch (IOException ex)
            {
                System.err.println("Failed to log error: " + ex.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(Request request)
    {
        try
        {
            outToClient.writeObject(request);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /*private void close()
    {
        try
        {
            pool.removeConnection(this);
            socket.close();
        }
        catch (IOException e)
        {

            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }*/
}