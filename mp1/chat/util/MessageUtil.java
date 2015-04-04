package chat.util;

import java.io.*;

public class MessageUtil{

		public static String extractParameter(String command, String msg){
			return msg.replace(command + " ", "");
		}

		public static String extractCommand(String msg){
			return msg.split(" ")[0];
		}


}