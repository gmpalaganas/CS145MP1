package chat.client;

import java.awt.*;
import java.awt.event.*;
import chat.connection.ConnectionHandler;

public class ClientSendListener implements ActionListener{

    ConnectionHandler conn_handler;
    ClientFrame client_frame;

    public ClientSendListener(ConnectionHandler myCon, ClientFrame c_frame){
        this.conn_handler = myCon;
        this.client_frame = c_frame;
    }

    public void actionPerformed(ActionEvent e){
    	String msg = client_frame.getSendMessage();
    	client_frame.clearChatBox();
    	conn_handler.sendMessage(msg);
    }

}