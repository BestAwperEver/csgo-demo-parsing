package com.asuscomm.radagast;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quickstart {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Google Sheets API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(SheetsScopes.SPREADSHEETS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            Quickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main2() throws IOException {
        // Build a new authorized API client service.
        Sheets service = getSheetsService();

        // Prints the names and majors of students in a sample spreadsheet:
        // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
        String spreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
        String range = "Class Data!A2:E";
        ValueRange response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.size() == 0) {
            System.out.println("No data found.");
        } else {
          System.out.println("Name, Major");
          for (List row : values) {
            // Print columns A and E, which correspond to indices 0 and 4.
            System.out.printf("%s, %s\n", row.get(0), row.get(4));
          }
        }   	
    }
    
    public static class Player {
    	Player(String nick) {
    		this.nick = nick;
    	}
    	String nick;
    	int kills;
    	int deaths;
    	int headshots;
    	int rounds = 1;
    	int k1, k2, k3, k4, k5;
    	int kills_cur;
    	int assists;
    	int mvp;
    	boolean disconnected = false;
    	int damage;
    	int team_damage;
    	float team_flash;
    	float self_flash;
    	float enemy_flash;
    	public int adr() {
    		return (int)(damage/(float)rounds);
    	}
    	public String toString() {
    		StringBuilder s = new StringBuilder();
    		s.append("Nick: " + nick);
    		s.append("\nKills: " + kills);
    		s.append("\nDeaths: " + deaths);
    		s.append("\nHeadshots: " + headshots);
    		s.append("\nRounds: " + rounds);
    		s.append("\n1 kill: " + k1);
    		s.append("\n2 kills: " + k2);
    		s.append("\n3 kills: " + k3);
    		s.append("\n4 kills: " + k4);
    		s.append("\n5 kills: " + k5);
    		s.append("\nAssists: " + assists);
    		s.append("\nMVPs: " + mvp);
    		s.append("\nADR: " + adr());
    		s.append("\nTeam damage: " + team_damage);
    		s.append("\nSelf flash: " + self_flash);
    		s.append("\nTeam flash: " + team_flash);
    		s.append("\nEnemy flash: " + enemy_flash);
    		return s.toString();
    	}
    }
    
	public static List<Player> players = Arrays.asList(
			new Player("Maximka"),
			new Player("sup"),
			new Player("aydin"),
			new Player("lolwto?!"),
			new Player("Sidekrown"));
    
    public static void parseInfo(CSGOFileParser csgofp) throws FileNotFoundException, IOException {
    	
    	boolean game_started = false;
    		
//    	Map<Integer, String> indices = new HashMap<Integer, String>();
    	Map<String, String> event = csgofp.nextEvent();
    	
    	while (event != null) { 
    		
	    	String type = event.get("name");
	    	
	    	if (!game_started) {
	    		
	    		switch (type) {
	//    		case "player_spawn": {
	//    			String user = event.get("userid");
	//    			int i = user.indexOf(':');
	//    			String nick = user.substring(0, i-1);
	//    			int index = Integer.parseInt(user.substring(i, -1));
	//    			indices.put(index, nick);
	//    		} break;
	    		case  "begin_new_match": {
	    			game_started = true;
	    		} break;
	    		}
	    		
	    	} else {
	    	
		    	switch (type) {
		    	case "player_disconnected": {
		    		for (Player player : players) {
			    		if (event.get("userid").equals(player.nick)) {
		    				player.disconnected = true;
		    				break;
		    			}
		    		}
		    	} break;
		    	case "player_death": {
		    		for (Player player : players) {
		    			if (player.nick.length() <= event.get("attacker").length()
		    					&& event.get("attacker").substring(0, player.nick.length()).equals(player.nick)) {
		    				player.kills_cur++;
		    				if (event.get("headshot").equals("1")) {
		    					player.headshots++;
		    				}
		    			}
		    			if (player.nick.length() <= event.get("userid").length()
		    					&& event.get("userid").substring(0, player.nick.length()).equals(player.nick)) {
		    				player.deaths++;
		    			}
		    			if (player.nick.length() <= event.get("assister").length()
		    					&& event.get("assister").substring(0, player.nick.length()).equals(player.nick)) {
		    				player.assists++;
		    			}
		    		}
		    	} break;
		    	case "player_hurt": {
		    		for (Player player : players) {
		    			if (player.nick.length() <= event.get("attacker").length()
		    					&& event.get("attacker").substring(0, player.nick.length()).equals(player.nick)) {
		    				boolean team_damage = false;
		    				for (Player player2 : players) {
		    					if (player2.nick.length() <= event.get("userid").length()
		    					&& event.get("userid").substring(0, player2.nick.length()).equals(player2.nick)) {
		    						player.team_damage += Integer.parseUnsignedInt(event.get("dmg_health"));
		    						team_damage = true;
		    						break;
		    					}
		    				}
		    				if (team_damage == false) player.damage += Integer.parseUnsignedInt(event.get("dmg_health"));
		    				break;
		    			}
		    		}
		    	} break;
		    	case "player_blind": {
		    		for (Player player : players) {
		    			if (player.nick.length() <= event.get("attacker").length()
		    					&& event.get("attacker").substring(0, player.nick.length()).equals(player.nick)) {
		    				boolean team_flash = false;
		    				for (Player player2 : players) {
		    					if (player2.nick.length() <= event.get("userid").length()
		    					&& event.get("userid").substring(0, player2.nick.length()).equals(player2.nick)) {
		    						if (player == player2) {
		    							player.self_flash += Float.parseFloat(event.get("blind_duration"));
		    						} else {
		    							player.team_flash += Float.parseFloat(event.get("blind_duration"));
		    						}
		    						team_flash = true;
		    						break;
		    					}
		    				}
		    				if (team_flash == false) player.enemy_flash += Float.parseFloat(event.get("blind_duration"));
		    				break;
		    			}
		    		}
		    	} break;
		    	case "player_spawn": {
		    		for (Player player : players) {
			    		if (player.nick.length() <= event.get("userid").length()
		    					&& event.get("userid").substring(0, player.nick.length()).equals(player.nick)) {
		    				if (!player.disconnected) {
		    					player.rounds++;
		    				} else {
		    					player.disconnected = false;
		    				}
		    				break;
			    		}
		    		}
	    		} break;
		    	case "round_mvp": {
		    		for (Player player: players) {
		    			if (player.nick.length() <= event.get("userid").length()
		    					&& event.get("userid").substring(0, player.nick.length()).equals(player.nick)) {
		    				player.mvp++;
		    				break;
		    			}
		    		}
		    	} break;
		    	case "round_end": {
		    		for (Player player: players) {
		    			switch (player.kills_cur) {
		    			case 1: player.k1++; break;
		    			case 2: player.k2++; break;
		    			case 3: player.k3++; break;
		    			case 4: player.k4++; break;
		    			case 5: player.k5++; break;
		    			}
		    			player.kills += player.kills_cur;
		    			player.kills_cur = 0;
		    		}
		    	}
		    	}
	    	
	    	}
	    	
	    	event = csgofp.nextEvent();
    	}
    }
    
    public static void main(String[] args) throws IOException {
    	CSGOFileParser csgofp = new CSGOFileParser("d:\\Games\\steamapps\\common\\Counter-Strike Global Offensive\\csgo\\replays\\grammsov\\info.txt");
    	parseInfo(csgofp);
    	for (Player player : players) {
    		System.out.println(player.toString());
    		System.out.println();
    	}
    	updateInfo();
    	//updateGraph();
    }

    public static void updateInfo() throws IOException {
        String spreadsheetId = "1fEhlb1Q00ihLRBU8T8VoH4TksDmo-6q4mf1vuhST2HA";

        Sheets sheetsService = getSheetsService();
        
        // How the input data should be interpreted.
        String valueInputOption = "USER_ENTERED";

        // How the input data should be inserted.
        String insertDataOption = "INSERT_ROWS";
        
        List<Object> objectList = new ArrayList<Object>();
        
        List<List<Object>> values = null;
        
        Sheets.Spreadsheets.Values.Append request = null;
        
        AppendValuesResponse response = null;
        
        ValueRange body = new ValueRange();
        
        String range = null;
        
//----
             
        range = "K!B:F";
        
        objectList.clear();
        
        for (Player player : players) {
        	objectList.add(player.kills);
        }
        
        values = Arrays.asList(objectList);

        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "D!B:F";
        
        objectList.clear();
        
        for (Player player : players) {
        	objectList.add(player.deaths);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "HS!B:F";
        
        objectList.clear();
        
        for (Player player : players) {
        	objectList.add(player.headshots);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'R'!B:F";
        
        objectList.clear();
        
        for (Player player : players) {
        	objectList.add(player.rounds);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'1K'!B:F";
        
        objectList.clear();
        
        for (Player player : players) {
        	objectList.add(player.k1);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'2K'!B:F";
        
        objectList.clear();
        
        for (Player player : players) {
        	objectList.add(player.k2);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'3K'!B:F";
        
        objectList.clear();
        
        for (Player player : players) {
        	objectList.add(player.k3);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'4K'!B:F";
        
        objectList.clear();
        
        for (Player player : players) {
        	objectList.add(player.k4);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'5K'!B:F";
        
        objectList.clear();
        
        for (Player player : players) {
        	objectList.add(player.k5);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'H'!B:F";
        
        objectList.clear();
        
        for (Player player : players) {
        	objectList.add(player.assists);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'MVP'!B:F";
        
        objectList.clear();
        
        for (Player player : players) {
        	objectList.add(player.mvp);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'ADR'!B:F";
        
        objectList.clear();
        
        for (Player player : players) {
        	objectList.add(player.adr());
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
      
		  range = "'TD'!B:F";
		  
		  objectList.clear();
		  
		  for (Player player : players) {
		  	objectList.add(player.team_damage);
		  }
		  values = Arrays.asList(objectList);
		  
		  body.setValues(values);
		  
		  request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
		  request.setValueInputOption(valueInputOption);
		  request.setInsertDataOption(insertDataOption);
		
		  response = request.execute();
		  
//----
	      
		  range = "'SF'!B:F";
		  
		  objectList.clear();
		  
		  for (Player player : players) {
		  	objectList.add(player.self_flash);
		  }
		  values = Arrays.asList(objectList);
		  
		  body.setValues(values);
		  
		  request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
		  request.setValueInputOption(valueInputOption);
		  request.setInsertDataOption(insertDataOption);
		
		  response = request.execute();
		  
//----
	      
		  range = "'TF'!B:F";
		  
		  objectList.clear();
		  
		  for (Player player : players) {
		  	objectList.add(player.team_flash);
		  }
		  values = Arrays.asList(objectList);
		  
		  body.setValues(values);
		  
		  request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
		  request.setValueInputOption(valueInputOption);
		  request.setInsertDataOption(insertDataOption);
		
		  response = request.execute();
		  
//----
	      
		  range = "'EF'!B:F";
		  
		  objectList.clear();
		  
		  for (Player player : players) {
		  	objectList.add(player.enemy_flash);
		  }
		  values = Arrays.asList(objectList);
		  
		  body.setValues(values);
		  
		  request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
		  request.setValueInputOption(valueInputOption);
		  request.setInsertDataOption(insertDataOption);
		
		  response = request.execute();
    }

    public static void updateGraph() throws IOException {
    	String spreadsheetId = "1fEhlb1Q00ihLRBU8T8VoH4TksDmo-6q4mf1vuhST2HA";
    	Sheets sheetsService = getSheetsService();
    	String range = "Лист2!S2:S6";
    	
    	ValueRange result = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();

    	List<List<Object>> new_row = result.getValues();
    	
    	int N = new_row.size();
    	for (int i = 1; i < N; ++i) {
    		new_row.get(0).addAll(new_row.get(1));
    		new_row.remove(1);
    	}
    	
    	// How the input data should be interpreted.
        String valueInputOption = "USER_ENTERED";

        // How the input data should be inserted.
        String insertDataOption = "INSERT_ROWS";

        range = "Лист3!H2";
        
        result = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();

        List<Object> objectList = new ArrayList<Object>();

        objectList.add(Integer.parseUnsignedInt( (String) result.getValues().get(0).get(0) ) + 1);

//        List<List<Object>> values = Arrays.asList(
//        		objectList
//                );
//        
        new_row.get(0).addAll(0, objectList);
        
        ValueRange body = new ValueRange()
                .setValues(new_row);
        
        range = "Лист3!A:F";
        
        Sheets.Spreadsheets.Values.Append request =
            sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        AppendValuesResponse response = request.execute();
    }
    
}