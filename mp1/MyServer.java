import java.io.*;
import java.net.*;
import java.util.*;

import chat.server.*;
import chat.connection.ConnectionHandler;

//Server Must be Open for the project to work
public class MyServer{

	public static void main(String args[]){
		try{
			
			ServerSocket ssocket = new ServerSocket(8888);
			Map<String,ConnectionHandler> cur_connections = new HashMap<String,ConnectionHandler>();
			List<ClientData> clients = new ArrayList<ClientData>();
			int num_clients = 0;

			while(true) {

				Socket s = ssocket.accept();
				ConnectionHandler connection = new ConnectionHandler(s);
				
				String client_name = "Client" + (++num_clients);
				String status = "";
				InetAddress ip = s.getInetAddress();
				
				cur_connections.put(client_name,connection);
				
				ClientData data = new ClientData(client_name, status, ip.toString());
				clients.add(data);
				ServerThread s_thread = new ServerThread(connection,cur_connections,clients,data);
				s_thread.updateUserToAll(clients);
				s_thread.sendToAll("DISPLAY Server message: " + client_name + " has connected!");
				s_thread.start();
				
		    
		    }
    	}catch(IOException e){
    		System.out.println("S: Something bad happened :(");
         	e.printStackTrace();
    	}
		

	}

}
