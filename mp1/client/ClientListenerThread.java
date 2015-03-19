package mp1.client;

import mp1.server.ClientData;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import mp1.connection.ConnectionHandler;

public class ClientListenerThread extends Thread{

    ConnectionHandler conn_handler;
    ClientFrame client_frame;

    public ClientListenerThread(ConnectionHandler myCon, ClientFrame c_frame){
        this.conn_handler = myCon;
        this.client_frame = c_frame;
    }

    public void run(){

        Boolean isRunning = true;
        String msg;

        while(isRunning){

            msg = conn_handler.getMessage();
            String cmd_string = 
            if(msg.equals("END")){
                isRunning = false;
                client_frame.dispatchEvent(new WindowEvent(client_frame, WindowEvent.WINDOW_CLOSING));
            } 

            client_frame.addMessage(msg);

        }

    }

}