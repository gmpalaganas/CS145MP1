package mp1.client;

import mp1.server.ClientData;
import mp1.connection.ConnectionHandler;
import mp1.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOPtionPane;

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
                JOPtionPane.showMessageDialog(param_string);
            }else if(cmd_string.equals("UPDATE")){
                List<ClientData> holder = (ArrayList<ClientData>)conn_handler.getObject();
                for(ClientData data: holder)
                    System.out.println(data.getName());
                client_frame.updateUserList(holder);
            }


        }

    }

}