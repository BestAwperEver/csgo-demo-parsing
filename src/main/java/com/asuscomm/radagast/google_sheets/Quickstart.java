package com.asuscomm.radagast.google_sheets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;

public class Quickstart {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "CSGO demo parser";

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
    	boolean dead = true;
    	int headshots;
    	int rounds = 1;
    	int k1, k2, k3, k4, k5;
    	int cl1, cl2, cl3, cl4, cl5;
    	int kills_cur;
    	int assists;
    	int mvp;
    	boolean disconnected = false;
    	int damage;
    	int team_damage;
    	int open_kills;
    	float team_flash;
    	float self_flash;
    	float enemy_flash;
    	int hegrenades;
    	int hedamage;
    	int molotovs;
    	int molotovdamage;
    	int flashbangs;
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
    		s.append("\n1 vs 1: " + cl1);
    		s.append("\n1 vs 2: " + cl2);
    		s.append("\n1 vs 3: " + cl3);
    		s.append("\n1 vs 4: " + cl4);
    		s.append("\n1 vs 5: " + cl5);
    		s.append("\nAssists: " + assists);
    		s.append("\nMVPs: " + mvp);
    		s.append("\nADR: " + adr());
    		s.append("\nTeam damage: " + team_damage);
    		s.append("\nSelf flash: " + self_flash);
    		s.append("\nTeam flash: " + team_flash);
    		s.append("\nEnemy flash: " + enemy_flash);
    		s.append("\nOpen kills: " + open_kills);
    		s.append("\nHE grenades: " + hegrenades);
    		s.append("\nHE damage: " + hedamage);
    		s.append("\nMolotovs: " + molotovs);
    		s.append("\nMolotov's damage: " + molotovdamage);
    		s.append("\nFlashbangs: " + flashbangs);
    		return s.toString();
    	}
    	public void clear() {
    		kills = 0;
    		deaths = 0;
    		dead = true;
    		headshots = 0;
    		rounds = 1;
    		k1 = 0;
    		k2 = 0;
    		k3 = 0;
    		k4 = 0;
    		k5 = 0;
    		cl1 = 0;
    		cl2 = 0;
    		cl3 = 0;
    		cl4 = 0;
    		cl5 = 0;
    		assists = 0;
    		mvp = 0;
    		damage = 0;
    		team_damage = 0;
    		self_flash = 0;
    		team_flash = 0;
    		enemy_flash = 0;
    		open_kills = 0;
    		hegrenades = 0;
    		molotovs = 0;
    		molotovdamage = 0;
    		hedamage = 0;
    		flashbangs = 0;
    	}
    }
    
	public static List<Player> players = Arrays.asList(
			new Player("Maximka"),
			new Player("ada"),
			new Player("aydin"),
			new Player("lolwto?!"),
			new Player("Sidekrown"));
	
    @Deprecated
    public static void parseInfo(CSGOFileParser csgofp) throws FileNotFoundException, IOException {
    	
    	boolean game_started = false;
    	boolean open_kill = false;
    	
//        int enemies_before_clutch = 0;
//        int teammates = 0;
//        boolean clutch = false;
    		
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
		    			}
		    		}
		    	} break;
		    	case "round_start": {
		    		open_kill = false;
		    	} break;
		    	case "player_death": {
		    		for (Player player : players) {
		    			if (player.nick.length() <= event.get("attacker").length()
		    					&& event.get("attacker").substring(0, player.nick.length()).equals(player.nick)) {
		    				player.kills_cur++;
		    				if (event.get("headshot").equals("1")) {
		    					player.headshots++;
		    				}
		    				if (open_kill == false) {
		    					player.open_kills++;
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
					open_kill = true;
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
		    				if (team_damage == false) {
		    					int damage = Integer.parseUnsignedInt(event.get("dmg_health"));
		    					player.damage += damage;
		    					switch (event.get("weapon")) {
			    				case "hegrenade": {
			    					player.hedamage += damage;
			    				} break;
			    				case "inferno":
			    				case "molotov_projectile": {
			    					player.molotovdamage += damage;
			    				} break;
			    				}
		    				}
		    				break;
		    			}
		    		}
		    	} break;
		    	case "weapon_fire": {
		    		for (Player player : players) {
		    			if (event.get("userid").equals(player.nick)) {
		    				switch (event.get("weapon")) {
		    				case "weapon_hegrenade": {
		    					player.hegrenades++;
		    				} break;
		    				case "weapon_incgrenade":
		    				case "weapon_molotov": {
		    					player.molotovs++;
		    				} break;
		    				case "weapon_flashbang": {
		    					player.flashbangs++;
		    				} break;
		    				}
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
		    				if (team_flash == false && (event.get("userid").length() != 4
		    						|| !event.get("userid").substring(0, 4).equals("GOTV"))) {
		    					player.enemy_flash += Float.parseFloat(event.get("blind_duration"));
		    				}
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
    
    public static String spreadsheetId;
    public static Sheets sheetsService;
    
    public static void processDumpFile(Path path) {
    	System.out.println(path);
    	CSGOFileParser csgofp;
		try {
			csgofp = new CSGOFileParser(path.toString());
	    	parseInfo(csgofp);
	    	for (Player player : players) {
	    		System.out.println(player.toString());
	    		System.out.println();
	    	}
	    	updateInfo();
	    	updateGraph();
	    	for (Player player : players) {
	    		player.clear();
	    	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static TournamentRate tr;
    
    public static TeamRate tm;
    
    public static void processTournamentDumpFile(Path path) {
    	System.out.println(path);
    	CSGOFileParser csgofp;

		try {
			csgofp = new CSGOFileParser(path.toString());
			tr.parseInfo(csgofp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	tr.players.forEach((nick, player)->{
    		System.out.println(nick);
    		System.out.println(player.toString());
    		System.out.println();
    	});

    }
    
    public static void processTeamDumpFile(Path path) throws IOException {
    	System.out.println(path);
    	CSGOFileParser csgofp;

		try {
			csgofp = new CSGOFileParser(path.toString());
			tm.parseInfo(csgofp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	tm.players.forEach((nick, player)->{
    		System.out.println(nick);
    		System.out.println(player.toString());
    		System.out.println();
    	});
		
        tm.updateInfo();
        
        updateGraph();
    	
        tm.clear_players_info();

    }
    
    @Deprecated
    public static void our_stats() throws IOException {
        spreadsheetId = "1fEhlb1Q00ihLRBU8T8VoH4TksDmo-6q4mf1vuhST2HA";
        sheetsService = getSheetsService();
        
        try (Stream<Path> paths = Files.walk(Paths.get("d:\\Games\\steamapps\\common\\Counter-Strike Global Offensive\\csgo\\replays\\grammsov\\dump"))) {
            paths.filter(Files::isRegularFile)
                .forEach(Quickstart::processDumpFile);
        }
        
        System.out.println("Done.");
    }
    
    public static void tournament_stats() throws IOException {
        spreadsheetId = "1eMu-hgTFU0QIlYlSdmoal4_9Hapme1NT1Rml1DYpvH8";
        sheetsService = getSheetsService();
        
    	tr = new TournamentRate(spreadsheetId, sheetsService);
        
        Stream<Path> paths = Files.walk(Paths.get("d:\\Games\\steamapps\\common\\Counter-Strike Global Offensive\\csgo\\replays\\tournament\\dump"));
        paths.filter(Files::isRegularFile).forEach(Quickstart::processTournamentDumpFile);
        paths.close();
        
        tr.updateInfo();
        
        System.out.println("Done.");	
    }
    
	public static void team_stats() throws IOException {
		spreadsheetId = "1fEhlb1Q00ihLRBU8T8VoH4TksDmo-6q4mf1vuhST2HA";
        sheetsService = getSheetsService();
        
    	tm = new TeamRate(spreadsheetId, sheetsService);
        
        Stream<Path> paths = Files.walk(Paths.get("d:\\Games\\steamapps\\common\\Counter-Strike Global Offensive\\csgo\\replays\\grammsov\\dump"));
        paths.filter(Files::isRegularFile).forEach(t -> {
			try {
				processTeamDumpFile(t);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        paths.close();        
        
        System.out.println("Done.");	
    }
    
    public static void main(String[] args) throws IOException {
        team_stats();
    }

    public static void updateInfo() throws IOException {
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
		  
//----
	      
		  range = "'OK'!B:F";
		  
		  objectList.clear();
		  
		  for (Player player : players) {
		  	objectList.add(player.open_kills);
		  }
		  values = Arrays.asList(objectList);
		  
		  body.setValues(values);
		  
		  request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
		  request.setValueInputOption(valueInputOption);
		  request.setInsertDataOption(insertDataOption);
		
		  response = request.execute();
		  
//----
	      
		  range = "'HE'!B:F";
		  
		  objectList.clear();
		  
		  for (Player player : players) {
		  	objectList.add(player.hegrenades);
		  }
		  values = Arrays.asList(objectList);
		  
		  body.setValues(values);
		  
		  request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
		  request.setValueInputOption(valueInputOption);
		  request.setInsertDataOption(insertDataOption);
		
		  response = request.execute();	
		  
//----
	      
		  range = "'FL'!B:F";
		  
		  objectList.clear();
		  
		  for (Player player : players) {
		  	objectList.add(player.flashbangs);
		  }
		  values = Arrays.asList(objectList);
		  
		  body.setValues(values);
		  
		  request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
		  request.setValueInputOption(valueInputOption);
		  request.setInsertDataOption(insertDataOption);
		
		  response = request.execute();	
		  
//----
	      
		  range = "'ML'!B:F";
		  
		  objectList.clear();
		  
		  for (Player player : players) {
		  	objectList.add(player.molotovs);
		  }
		  values = Arrays.asList(objectList);
		  
		  body.setValues(values);
		  
		  request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
		  request.setValueInputOption(valueInputOption);
		  request.setInsertDataOption(insertDataOption);
		
		  response = request.execute();	
		  
//----
	      
		  range = "'MLD'!B:F";
		  
		  objectList.clear();
		  
		  for (Player player : players) {
		  	objectList.add(player.molotovdamage);
		  }
		  values = Arrays.asList(objectList);
		  
		  body.setValues(values);
		  
		  request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
		  request.setValueInputOption(valueInputOption);
		  request.setInsertDataOption(insertDataOption);
		
		  response = request.execute();
		  
//----
	      
		  range = "'HED'!B:F";
		  
		  objectList.clear();
		  
		  for (Player player : players) {
		  	objectList.add(player.hedamage);
		  }
		  values = Arrays.asList(objectList);
		  
		  body.setValues(values);
		  
		  request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
		  request.setValueInputOption(valueInputOption);
		  request.setInsertDataOption(insertDataOption);
		
		  response = request.execute();	
    }

    public static void updateGraph() throws IOException {
//    	String spreadsheetId = "1fEhlb1Q00ihLRBU8T8VoH4TksDmo-6q4mf1vuhST2HA";
//    	Sheets sheetsService = getSheetsService();
    	String range = "Info!S2:S6";
    	
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

        range = "Rating!H2";
        
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
        
        range = "Rating!A:F";
        
        Sheets.Spreadsheets.Values.Append request =
            sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        AppendValuesResponse response = request.execute();
    }
    
}