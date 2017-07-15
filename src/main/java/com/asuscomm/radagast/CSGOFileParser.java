package com.asuscomm.radagast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSGOFileParser {

	private BufferedReader br;
	CSGOFileParser(String filename) throws IOException {
		this.br = new BufferedReader(new FileReader(filename));
	}
	
	public Map<String, String> nextEvent() throws FileNotFoundException, IOException {
		Map<String, String> event = new HashMap<String, String>();
	    String line = null;
	    
	    line = br.readLine();
	    if (line == null) return null;
	    line = line.replace(':', ' ');
    	line = line.replace("  ", " ");
    	String[] parts = line.split(" ");
    	while (parts.length > 1) {
    		if (parts.length > 4 && parts[4].equals("disconnected.")) {
	    		event.put("name", "player_disconnected");
	    		event.put("userid", parts[1]);
	    		return event;
	    	}
    		line = br.readLine();
    		if (line == null) return null;
    	    line = line.replace(':', ' ');
        	line = line.replace("  ", " ");
        	parts = line.split(" ");
    	}
	    
//	    if (line == null) return null;
	    event.put("name", line);
	    line = br.readLine();
	    if (line == null) return null;
	    
//	    if (!line.equals("{")) {
//	    	line = line.replace(':', ' ');
//	    	line = line.replace("  ", " ");
//	    	String[] parts = line.split(" ");
//	    	if (parts[4].equals("disconnected")) {
//	    		event.put("name", "player_disconnected");
//	    		event.put("userid", parts[1]);
//	    		return event;
//	    	}
//	    }
	    
//	    while (!line.equals("{")) {
//	    	line = line.replace(':', ' ');
//	    	line = line.replace("  ", " ");
//	    	parts = line.split(" ");
//	    	if (parts.length > 4 && parts[4].equals("disconnected")) {
//	    		event.put("name", "player_disconnected");
//	    		event.put("userid", parts[1]);
//	    		return event;
//	    	}
//	    	line = br.readLine();
//	    }
	    
	    do {
	    	line = br.readLine();
	    	if (line == null) return null;
	    	line = line.replace(':', ' ');
	    	line = line.replace("  ", " ");
	    	parts = line.split(" ");
	    	if (parts.length > 2) {
	    		event.put(parts[1], parts[2]);
	    	}
	    } while (!line.equals("}"));

		return event;
	}
}
