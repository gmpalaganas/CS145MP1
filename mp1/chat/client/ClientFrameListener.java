package chat.client;

import java.awt.*;
import java.awt.event.*;
import chat.connection.ConnectionHandler;

public class ClientFrameListener extends WindowAdapter{

	ConnectionHandler conn_handler;

    public ClientFrameListener(ConnectionHandler myCon){
        this.conn_handler = myCon;
    }

	public void windowClosing(WindowEvent e){
		conn_handler.sendMessage("/quit");
	}

}