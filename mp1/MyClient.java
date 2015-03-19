package mp1;

import java.io.*;
import java.net.*;

import mp1.client.*;
import mp1.connection.ConnectionHandler;


public class MyClient{

	public static void main(String args[]){

		try {
			
			Socket s = new Socket("127.0.0.1", 8888);
			ConnectionHandler conn_handler = new ConnectionHandler(s);

			ClientFrame c_frame = new ClientFrame("Chat Box");
			ClientListenerThread c_listener = new ClientListenerThread(conn_handler, c_frame);
		    ClientSendListener c_send_listener = new ClientSendListener(conn_handler, c_frame);

		    c_frame.setSendActionListener(c_send_listener);
		    c_frame.addWindowListener(new ClientFrameListener(conn_handler));
		    c_listener.start();
	 
	    } catch (Exception e) {
	    	System.out.println("C: Something bad happened :(");
	   		e.printStackTrace();
	    }

	}

}