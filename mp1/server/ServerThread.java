package mp1.server;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import mp1.connection.ConnectionHandler;
import mp1.util.MessageUtil;

public class ServerThread extends Thread{

	private ConnectionHandler conn_handler;
	private Map<String,ConnectionHandler> cur_connections;
	private ClientData client_data;
	private Map<String,String> emoticon_map;
	private List<ClientData> clients;

	public ServerThread(ConnectionHandler myCon, Map<String,ConnectionHandler> cur, List<ClientData> list,ClientData client){
		this.conn_handler = myCon;
		this.cur_connections = cur;
		this.client_data = client;
		this.clients = list;
		this.emoticon_map = new HashMap<String,String>();
		init_emoticons();
		//conn_handler.sendMessage("POP Welcome to the Chat Room! type /help to see commands and /emoticons to see emoticon codes");			
	}
	

	public void init_emoticons(){
		emoticon_map.put(":wut:","\u0CA0_\u0CA0");
		emoticon_map.put(":returntable:","┬──┬ ノ( ゜-゜ノ)");
		emoticon_map.put(":shrug:","¯\\_(ツ)_/¯");
		emoticon_map.put(":tableflip:","(╯°□°）╯︵ ┻━┻");
	}

	public void run(){
		try{
			
			Boolean isRunning = true;
			Pattern cmd_ptrn = Pattern.compile("/\\S.*");
			Pattern emoticon_ptrn = Pattern.compile(":\\S*:");
			String msg = "";
			Matcher mtch;
			Matcher e_mtch;
			List<String> e_matches = new ArrayList<String>();

			while(isRunning){

				msg = conn_handler.getMessage();
				mtch = cmd_ptrn.matcher(msg);
				e_mtch = emoticon_ptrn.matcher(msg);

				System.out.println( client_data.getName() + "> " + msg);

				if(!mtch.matches()){
				
					while(e_mtch.find()){
						String found = e_mtch.group();
						if(!e_matches.contains(found))
							e_matches.add(found);
					}
					
					for(String str : e_matches){
						if(emoticon_map.containsKey(str))
							msg = msg.replace(str,emoticon_map.get(str));
					}

					sendToAll("DISPLAY " + client_data.getName() +": " + msg);
					e_matches.clear();
				}

				else{
					String cmd_string = MessageUtil.extractCommand(msg);
					String param_string = MessageUtil.extractParameter(cmd_string, msg);

					if(cmd_string.equals("/quit")){

						if(param_string.equals(cmd_string)){
							isRunning = false;
							conn_handler.sendMessage("END");
							cur_connections.remove(client_data.getName());
							clients.remove(client_data);
							sendToAll("DISPLAY Server Message: " + client_data.getName() + " has disconnected");
							Thread.sleep(500);
							updateUserToAll(clients);
						} else{
							conn_handler.sendMessage("DISPLAY Server Message: usage /quit");
						}
					
					} else if(cmd_string.equals("/changename")){
						
						if(param_string.indexOf(" ") == -1 && !param_string.equals(cmd_string)){
							ConnectionHandler tmp = cur_connections.remove(client_data.getName());
							String old_name = client_data.getName();
							
							client_data.setName(param_string);
							cur_connections.put(param_string,tmp);
							updateUserToAll(clients);
							sendToAll("DISPLAY Server Message: " + old_name + " has changed name to " + param_string);
							

						} else{
							conn_handler.sendMessage("DISPLAY Server Message: " + param_string + " is not a valid name ");
						}
					
					} else if(cmd_string.equals("/changestatus")){
						if(!param_string.equals(cmd_string)){
							String status = param_string;
							client_data.setStatus(status);
							updateUserToAll(clients);	
						}else{
							conn_handler.sendMessage("DISPLAY Server Message: " + param_string + " is not a valid status ");
						}

					} else if(cmd_string.equals("/whisper")){

						String send_to = MessageUtil.extractCommand(param_string);
						String send_msg = MessageUtil.extractParameter(cmd_string + " " + send_to, param_string);
						send_msg = "DISPLAY [" + client_data.getName() + " whisphers ]: " + send_msg;
						if(cur_connections.containsKey(send_to)){
							ConnectionHandler receiver = cur_connections.get(send_to);
							receiver.sendMessage(send_msg);	
						}else{
							conn_handler.sendMessage("DISPLAY Server Message: User " + send_to  + " does not exist");
						}

					} else if(cmd_string.equals("/help")){

						String help = "POP /help - displays a list of commands\n";
						help += "/emoticons - displays a list of available emoticons\n";
						help += "/chanename <name> - changes you current name\n";
						help += "/changestatus <status> - changes you current status\n";
						help += "/whisper <user> <message> - sends a private message to user\n";
						help += "/quit - exits the client";

						conn_handler.sendMessage(help);

					} else if(cmd_string.equals("/emoticons")){
						String emoticons = "POP ";
						for(String k : emoticon_map.keySet())
							emoticons += k + " - " + emoticon_map.get(k) + "\n";

						conn_handler.sendMessage(emoticons);

					}else{
						conn_handler.sendMessage("DISPLAY Server Message: Invalid command " + cmd_string);
					}
				}
		  		
	  		}

	  	}catch(Exception e){
	  		System.out.println("S: Something bad happened :(");
	 		e.printStackTrace();
	  	}
	}

	public void sendToAll(String msg){
		synchronized(cur_connections){
			for(ConnectionHandler con: cur_connections.values())
				con.sendMessage(msg);
		}	
	
	}

	public void sendObjectToAll(Object obj){
		synchronized(cur_connections){
		for(ConnectionHandler con: cur_connections.values())
			con.sendObject(obj);
		}
	}

	public void updateUserToAll(List<ClientData> list){
		try{
			synchronized(list){
				Thread.sleep(200);
				sendToAll("UPDATE");
				Thread.sleep(200);
				sendObjectToAll(list);
				Thread.sleep(200);
			}
		
		} catch(InterruptedException e){

		}
		

			
	}




}
