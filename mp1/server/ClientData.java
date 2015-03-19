package mp1.server;

import java.io.*;

public class ClientData implements Serializable{

	private String name;
	private String status;
	private String ip_addr;

	public ClientData(String c_name, String c_status, String c_ip){
		name = c_name;
		status = c_status;
		ip_addr = c_ip;
	}

	public String getName(){
		return name;
	}

	public String getStatus(){
		return status;
	}

	public String getIPAddress(){
		return ip_addr;
	}

	public void setName(String new_name){
		name = new_name;
	}

	public void setStatus(String new_status){
		status = new_status;
	}

	public void setIPAdress(String new_ip){
		ip_addr = new_ip;
	}
}