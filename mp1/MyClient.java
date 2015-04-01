import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

import mp1.client.*;
import mp1.connection.ConnectionHandler;


public class MyClient{

	public static void main(String args[]){

		try {

			String ip = JOptionPane.showInputDialog("Input IP address");
			
			Socket s = new Socket(ip, 8888);
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