package com.asuscomm.radagast.google_sheets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class TournamentRate {
	
	public String spreadsheetId;
    public Sheets sheetsService;
    
    public static Set<String> stop = new HashSet<String>(Arrays.asList(
    		"0",
    		"@followddk",
    		"@HenryGcsgo",
    		"@OnFireAnders",
    		"@Sadokist",
    		"GOTV",
    		"PGL",
    		"Anders",
    		"Bob",
    		"dJ",
    		"Elliot",
    		"Ernie",
    		"Fergus",
    		"George",
    		"Harvey",
    		"Larry",
    		"Neil",
    		"pANdA)",
    		"PGL-Coach-Blue",
    		"PGL-OBSERVER",
    		"PGL-OBSERVER1",
    		"Ryan",
    		"Ulysses",
    		"Uri",
    		"Wally",
    		"Yanni",
    		"Yogi",
    		"Yuri"
    		)); 
    
	public TournamentRate(String spreadsheetId, Sheets sheetsService) {
		super();
		this.spreadsheetId = spreadsheetId;
		this.sheetsService = sheetsService;
	}
	
    public class Player {
//    	Player(String nick) {
//    		this.nick = nick;
//    	}
//    	String nick;
    	int teamnum = -1;
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
    	int open_kills;
    	float team_flash;
    	float self_flash;
    	float enemy_flash;
    	public int adr() {
    		return (int)(damage/(float)rounds);
    	}
    	public String toString() {
    		StringBuilder s = new StringBuilder();
    		//s.append("Nick: " + nick);
    		s.append("Kills: " + kills);
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
    		s.append("\nOpen kills: " + open_kills);
    		return s.toString();
    	}
    	public void clear() {
    		kills = 0;
    		deaths = 0;
    		headshots = 0;
    		rounds = 1;
    		k1 = 0;
    		k2 = 0;
    		k3 = 0;
    		k4 = 0;
    		k5 = 0;
    		assists = 0;
    		mvp = 0;
    		damage = 0;
    		team_damage = 0;
    		self_flash = 0;
    		team_flash = 0;
    		enemy_flash = 0;
    		open_kills = 0;
    	}
    }
    
    public Map<String, Player> players = new HashMap<String, Player>();
    
    public void parseInfo(CSGOFileParser csgofp) throws FileNotFoundException, IOException {
    	
    	boolean game_started = false;
    	boolean open_kill = false;
    		
//    	Map<Integer, String> indices = new HashMap<Integer, String>();
    	Map<String, String> event = csgofp.nextEvent();
    	
    	while (event != null) { 
    		
	    	String type = event.get("name");
	    	
	    	if (!game_started) {
	    		
	    		switch (type) {
	    		case  "begin_new_match": {
	    			game_started = true;
	    		} break;
	    		}
	    		
	    	} else {
	    	
		    	switch (type) {
		    	case "player_disconnected": {
		    		if (stop.contains(event.get("userid"))) {
		    			break;
		    		}
		    		players.putIfAbsent(event.get("userid"), new Player());
		    		players.get(event.get("userid")).disconnected = true;
		    	} break;
		    	case "round_start": {
		    		open_kill = false;
		    	} break;
		    	case "player_death": {
		    		if (stop.contains(event.get("userid"))) {
		    			break;
		    		}
		    		players.putIfAbsent(event.get("userid"), new Player());
		    		players.get(event.get("userid")).deaths++;
		    		players.putIfAbsent(event.get("attacker"), new Player());
		    		Player player = players.get(event.get("attacker"));
    				player.kills_cur++;
    				if (event.get("headshot").equals("1")) {
    					player.headshots++;
    				}
    				if (open_kill == false) {
    					player.open_kills++;
    				}
		    		if (event.get("assister").equals("0") == false) {
		    			players.putIfAbsent(event.get("assister"), new Player());
		    			players.get(event.get("assister")).assists++;
		    		}
					open_kill = true;
		    	} break;
		    	case "player_hurt": {
		    		if (stop.contains(event.get("userid"))) {
		    			break;
		    		}
		    		players.putIfAbsent(event.get("attacker"), new Player());
		    		Player attacker = players.get(event.get("attacker"));
		    		players.putIfAbsent(event.get("userid"), new Player());
		    		Player userid = players.get(event.get("userid"));
		    		int damage = Integer.parseUnsignedInt(event.get("dmg_health"));
		    		if (attacker.teamnum == userid.teamnum) {
		    			attacker.team_damage += damage;
		    		} else {
		    			attacker.damage += damage;
		    		}		    		
		    	} break;
		    	case "player_blind": {
		    		if (stop.contains(event.get("userid"))) {
		    			break;
		    		}
		    		players.putIfAbsent(event.get("attacker"), new Player());
		    		Player attacker = players.get(event.get("attacker"));
		    		players.putIfAbsent(event.get("userid"), new Player());
		    		Player userid = players.get(event.get("userid"));float blind_duration = Float.parseFloat(event.get("blind_duration"));
		    		if (attacker == userid) {
		    			attacker.self_flash += blind_duration;
		    		} else if (attacker.teamnum == userid.teamnum) {
		    			attacker.team_flash += blind_duration;
		    		} else {
		    			attacker.enemy_flash += blind_duration;
		    		}
		    	} break;
		    	case "player_spawn": {
		    		if (stop.contains(event.get("userid"))) {
		    			break;
		    		}
		    		players.putIfAbsent(event.get("userid"), new Player());
		    		Player player = players.get(event.get("userid"));
		    		player.teamnum = Integer.parseInt(event.get("teamnum"));
		    		if (!player.disconnected) {
    					player.rounds++;
    				} else {
    					player.disconnected = false;
    				}
	    		} break;
		    	case "round_mvp": {
		    		players.putIfAbsent(event.get("userid"), new Player());
		    		players.get(event.get("userid")).mvp++;
		    	} break;
		    	case "round_end": {
		    		players.forEach((nick, player)->{
		    			switch (player.kills_cur) {
		    			case 1: player.k1++; break;
		    			case 2: player.k2++; break;
		    			case 3: player.k3++; break;
		    			case 4: player.k4++; break;
		    			case 5: player.k5++; break;
		    			}
		    			player.kills += player.kills_cur;
		    			player.kills_cur = 0;
		    		});
		    	}
		    	}
	    	
	    	}
	    	
	    	event = csgofp.nextEvent();
    	}
    }
    
    public void updateInfo() throws IOException {
        // How the input data should be interpreted.
        String valueInputOption = "USER_ENTERED";

        // How the input data should be inserted.
        String insertDataOption = "INSERT_ROWS";
        
        List<Object> objectList = new ArrayList<Object>();
        
        List<List<Object>> values = new LinkedList<List<Object>>();
        
        Sheets.Spreadsheets.Values.Append request = null;
        
        AppendValuesResponse response = null;
        
        ValueRange body = new ValueRange();
        
        String range = null;
        
        range = "Sheet4!B2:Z";
                
        players.forEach((nick, player)->{
        	if (nick.equals("0")) return;
        	objectList.clear();
        	objectList.add(nick);
        	objectList.add(player.kills);
        	objectList.add(player.open_kills);
        	objectList.add(player.deaths);
        	objectList.add(player.rounds);
        	objectList.add(player.headshots);
        	objectList.add(player.assists);
        	objectList.add(player.k1);
        	objectList.add(player.k2);
        	objectList.add(player.k3);
        	objectList.add(player.k4);
        	objectList.add(player.k5);
        	objectList.add(player.adr());
        	objectList.add(player.team_damage);
        	objectList.add(player.self_flash);
        	objectList.add(player.team_flash);
        	objectList.add(player.enemy_flash);
        	values.add(new ArrayList<Object>(objectList));
        });
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();  
    }


}
