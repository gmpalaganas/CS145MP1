package chat.connection;

import java.io.*;
import java.net.*;

public class ConnectionHandler {

	private Socket socket;

	private OutputStream out_stream;
	private ObjectOutputStream obj_out;
	private OutputStreamWriter os_writer;
	private PrintWriter p_writer;

	private InputStream in_stream;
	private ObjectInputStream obj_in;
	private InputStreamReader ins_reader;
	private BufferedReader b_reader;

	public ConnectionHandler(Socket s){
		try{
			
			this.socket = s;
		
			this.out_stream = socket.getOutputStream();
			this.obj_out = new ObjectOutputStream(out_stream);
			this.os_writer = new OutputStreamWriter(out_stream);
			this.p_writer = new PrintWriter(os_writer);

			this.in_stream = socket.getInputStream();
			this.obj_in = new ObjectInputStream(in_stream);
			this.ins_reader = new InputStreamReader(in_stream);
			this.b_reader = new BufferedReader(ins_reader);	
		}catch(IOException e){
			System.out.println("Unable to initialize MyConnection :(");
         	e.printStackTrace();
		}
		
	}

	public boolean sendMessage(String msg){
		boolean ret = true; 

		try{
			synchronized(p_writer){
				p_writer.println(msg);
				p_writer.flush();
			}
		}catch(Exception e){
			ret = false;
		}

		return ret;
	}

	public String getMessage(){
		String ret = "";
		InetAddress ip = socket.getInetAddress();

		try{

			synchronized(b_reader){
				String inc =  b_reader.readLine();
				ret = inc;

				if(b_reader.ready())
					ret += "\n";

				while(b_reader.ready()){
					inc =  b_reader.readLine();
					ret += inc;

					if(b_reader.ready())
					ret += "\n";
				}
			}
			
		}catch(IOException e){
			System.out.println("Unable read message from " + ip +  " :(");
         	e.printStackTrace();
		}

		return ret;
	}

	public boolean sendObject(Object obj){
		
		boolean ret = true;

		try{
			synchronized(obj_out){
				obj_out.reset();
	            obj_out.writeObject(obj);   	            
	        	obj_out.flush();
	        }
        }catch (IOException e){
	        e.printStackTrace();
	        ret = false;
        } 

        return ret;
	}

	public Object getObject(){
		Object ret = null;
		
		try{
			synchronized(obj_in){
				ret = obj_in.readObject();	
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return ret;
	}
}