package chat.client;

import chat.server.ClientData;
import chat.connection.ConnectionHandler;
import chat.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

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
            String cmd_string = MessageUtil.extractCommand(msg);
            String param_string = MessageUtil.extractParameter(cmd_string,msg);
            if(cmd_string.equals("END")){
                isRunning = false;
                client_frame.dispatchEvent(new WindowEvent(client_frame, WindowEvent.WINDOW_CLOSING));
            } else if(cmd_string.equals("DISPLAY")){
                client_frame.addMessage(param_string);                
            } else if(cmd_string.equals("POP")){
                JOptionPane.showMessageDialog(null,param_string);
            }else if(cmd_string.equals("UPDATE")){
                @SuppressWarnings("unchecked")
                List<ClientData> holder = (ArrayList<ClientData>)conn_handler.getObject();
               
                client_frame.updateUserList(holder);
            }


        }

    }

}