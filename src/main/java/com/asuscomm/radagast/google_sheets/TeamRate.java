package com.asuscomm.radagast.google_sheets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class TeamRate {
	
	public String spreadsheetId;
    public Sheets sheetsService;
    
    public static Set<String> stop = new HashSet<String>(Arrays.asList(
    		"0",
//    		"@followddk",
//    		"@HenryGcsgo",
//    		"@OnFireAnders",
//    		"@Sadokist",
    		"GOTV"
//    		"PGL",
//    		"Anders",
//    		"Bob",
//    		"dJ",
//    		"Elliot",
//    		"Ernie",
//    		"Fergus",
//    		"George",
//    		"Harvey",
//    		"Larry",
//    		"Neil",
//    		"pANdA)",
//    		"PGL-Coach-Blue",
//    		"PGL-OBSERVER",
//    		"PGL-OBSERVER1",
//    		"Ryan",
//    		"Ulysses",
//    		"Uri",
//    		"Wally",
//    		"Yanni",
//    		"Yogi",
//    		"Yuri"
    		)); 
    
	public TeamRate(String spreadsheetId, Sheets sheetsService) {
		super();
		this.spreadsheetId = spreadsheetId;
		this.sheetsService = sheetsService;
		teams.put(-1, new HashSet<Player>());
		teams.put(0, new HashSet<Player>());
		teams.put(1, new HashSet<Player>());
		teams.put(2, new HashSet<Player>());
		teams.put(3, new HashSet<Player>());
	}
	
    public static class Player {
    	String nick;
    	public Player(String nick) {
			super();
			this.nick = nick;
		}
		int teamnum = -1;
    	int kills;
    	int deaths;
    	boolean dead = false;
    	int headshots;
    	int rounds = 1;
    	int k1, k2, k3, k4, k5;
    	int cl1, cl2, cl3, cl4, cl5, cl_loose;
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
    		s.append("\nNick: " + nick);
    		s.append("\nDead: " + dead);
    		s.append("\nDisconnected: " + disconnected);
    		s.append("\nTeam: " + teamnum);
//    		s.append("\nKills: " + kills);
//    		s.append("\nDeaths: " + deaths);
//    		s.append("\nHeadshots: " + headshots);
//    		s.append("\nRounds: " + rounds);
//    		s.append("\n1 kill: " + k1);
//    		s.append("\n2 kills: " + k2);
//    		s.append("\n3 kills: " + k3);
//    		s.append("\n4 kills: " + k4);
//    		s.append("\n5 kills: " + k5);
//    		s.append("\n1 vs 1: " + cl1);
//    		s.append("\n1 vs 2: " + cl2);
//    		s.append("\n1 vs 3: " + cl3);
//    		s.append("\n1 vs 4: " + cl4);
//    		s.append("\n1 vs 5: " + cl5);
//    		s.append("\nAssists: " + assists);
//    		s.append("\nMVPs: " + mvp);
//    		s.append("\nADR: " + adr());
//    		s.append("\nTeam damage: " + team_damage);
//    		s.append("\nSelf flash: " + self_flash);
//    		s.append("\nTeam flash: " + team_flash);
//    		s.append("\nEnemy flash: " + enemy_flash);
//    		s.append("\nOpen kills: " + open_kills);
//    		s.append("\nHE grenades: " + hegrenades);
//    		s.append("\nHE damage: " + hedamage);
//    		s.append("\nMolotovs: " + molotovs);
//    		s.append("\nMolotov's damage: " + molotovdamage);
//    		s.append("\nFlashbangs: " + flashbangs);
    		return s.toString();
    	}
    	public void clear() {
    		teamnum = -1;
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
    		cl_loose = 0;
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
		public boolean isDead() {
			return dead;
		}
    	static int t_alive = 5;		// team 2
    	static int ct_alive = 5;	// team 3
		public void setDead(boolean dead) {
			this.dead = dead;
			if (dead) {
				++deaths;
				if (teamnum == -1) {
					System.out.println("Wrong!");
				}
				if (teamnum == 2) 
					--t_alive;
				if (teamnum == 3) 
					--ct_alive;
			}
			if (t_alive < 0 || t_alive > 5 || ct_alive < 0 || ct_alive > 5) {
				System.out.println("Wrong!");
			}
		}
    	public boolean isDisconnected() {
			return disconnected;
		}
		public void setDisconnected(boolean disconnected) {
			this.disconnected = disconnected;
			if (dead == false) {
				dead = true;
				if (teamnum == 2) 
					--t_alive;
				if (teamnum == 3) 
					--ct_alive;
			}
		}
    }
    
    public Map<String, Player> players = new HashMap<String, Player>();
    
    public Map<Integer, Set<Player>> teams = new HashMap<Integer, Set<Player>>();
    
    public List<String> players_to_track = Arrays.asList(
    		"Maximka",
			"ada",
			"sup",
			"aydin",
			"lolwto?!",
			"Sidekrown"
    		);
    
    Player clutch_player;
    List<Player> clutch_enemies = new LinkedList<Player>();
    Player clutch_opposite;
	int clutch_team = -1;
	int clutch_vs = -1;
    public void parseInfo(CSGOFileParser csgofp) throws FileNotFoundException, IOException {
    	
    	boolean game_started = false;
    	boolean open_kill = false;
    	boolean round_end = true;
    		
//    	Map<Integer, String> indices = new HashMap<Integer, String>();
    	Map<String, String> event = csgofp.nextEvent();
    	
    	while (event != null) { 
    		
	    	String type = event.get("name");
	    	
	    	if (!game_started) {
	    		
	    		switch (type) {
	    		case "begin_new_match": {
	    			game_started = true;
	    			Player.t_alive = 5;
	    			Player.ct_alive = 5;
	    		} break;
	    		case "player_disconnected": {
	    			Player player = players.get(event.get("userid"));
		    		if (player != null) player.setDisconnected(true);
		    	} break;
	    		case "player_team": {
		    		players.putIfAbsent(event.get("userid"), new Player(event.get("userid")));
		    		int teamnum = Integer.parseUnsignedInt(event.get("team"));
		    		Player player = players.get(event.get("userid"));
	    			player.teamnum = teamnum;
	    			if (event.get("disconnect") == "1") {
	    				player.setDisconnected(true);
	    			}
	    			teams.get(teamnum).add(player);
	    			teams.forEach((team, players)->{
	    				if (team != teamnum) {
	    					players.remove(player);
	    				}
	    			});
		    	} break;
	    		case "player_spawn": {
		    		players.putIfAbsent(event.get("userid"), new Player(event.get("userid")));
	    			players.get(event.get("userid")).teamnum = Integer.parseUnsignedInt(event.get("teamnum"));
		    	} break;
	    		}
	    		
	    	} else {
	    	
		    	switch (type) {
		    	case "player_disconnected": {
		    		players.get(event.get("userid")).setDisconnected(true);
		    	} break;
		    	case "player_team": {
		    		players.putIfAbsent(event.get("userid"), new Player(event.get("userid")));
		    		int teamnum = Integer.parseUnsignedInt(event.get("team"));
		    		Player player = players.get(event.get("userid"));
	    			player.teamnum = teamnum;
	    			if (event.get("disconnect") == "1") {
	    				player.setDisconnected(true);
	    			}
	    			teams.get(teamnum).add(player);
	    			teams.forEach((team, players)->{
	    				if (team != teamnum) {
	    					players.remove(player);
	    				}
	    			});
		    	} break;
		    	case "player_spawn": {
		    		players.putIfAbsent(event.get("userid"), new Player(event.get("userid")));
		    		Player player = players.get(event.get("userid"));
		    		int teamnum = Integer.parseInt(event.get("teamnum"));
		    		player.setDead(false);
	    			player.teamnum = teamnum;
	    			teams.get(teamnum).add(player);
	    			teams.forEach((team, players)->{
	    				if (team != teamnum) {
	    					players.remove(player);
	    				}
	    			});
		    		if (!player.disconnected) {
    					player.rounds++;
    				} else {
    					player.disconnected = false;
    				}
	    		} break;
		    	case "round_start": {
		    		open_kill = false;
		    		round_end = false;
		    		Player.t_alive = 5;
		    		Player.ct_alive = 5;
		    	} break;
		    	case "player_death": {
		    		Player userid = players.putIfAbsent(event.get("userid"), new Player(event.get("userid")));
		    		userid.setDead(true);
		    		players.putIfAbsent(event.get("attacker"), new Player(event.get("userid")));
		    		Player player = players.get(event.get("attacker"));
    				player.kills_cur++;
    				if (event.get("headshot").equals("1")) {
    					player.headshots++;
    				}
    				if (open_kill == false) {
    					player.open_kills++;
    				}
		    		if (stop.contains(event.get("assister")) == false) {
		    			players.putIfAbsent(event.get("assister"), new Player(event.get("userid")));
		    			players.get(event.get("assister")).assists++;
		    		}
					open_kill = true;
		    	} break;
		    	case "player_hurt": {
		    		if (stop.contains(event.get("attacker"))) {
		    			break;
		    		}
		    		players.putIfAbsent(event.get("attacker"), new Player(event.get("userid")));
		    		Player attacker = players.get(event.get("attacker"));
			    	players.putIfAbsent(event.get("userid"), new Player(event.get("userid")));
		    		Player userid = players.get(event.get("userid"));
		    		int damage = Integer.parseUnsignedInt(event.get("dmg_health"));
		    		if (attacker.teamnum == userid.teamnum) {
		    			attacker.team_damage += damage;
		    		} else {
    					attacker.damage += damage;
    					switch (event.get("weapon")) {
	    				case "hegrenade": {
	    					attacker.hedamage += damage;
	    				} break;
	    				case "inferno":
	    				case "molotov_projectile": {
	    					attacker.molotovdamage += damage;
	    				} break;
	    				}
		    		}		    		
		    	} break;
		    	case "weapon_fire": {
		    		if (stop.contains(event.get("userid"))) {
		    			break;
		    		}
		    		players.putIfAbsent(event.get("userid"), new Player(event.get("userid")));
		    		Player player = players.get(event.get("userid"));
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
		    	} break;
		    	case "player_blind": {
		    		players.putIfAbsent(event.get("attacker"), new Player(event.get("userid")));
		    		Player attacker = players.get(event.get("attacker"));
		    		players.putIfAbsent(event.get("userid"), new Player(event.get("userid")));
		    		Player userid = players.get(event.get("userid"));
		    		float blind_duration = Float.parseFloat(event.get("blind_duration"));
		    		if (attacker == userid) {
		    			attacker.self_flash += blind_duration;
		    		} else if (attacker.teamnum == userid.teamnum) {
		    			attacker.team_flash += blind_duration;
		    		} else {
		    			attacker.enemy_flash += blind_duration;
		    		}
		    	} break;
		    	case "round_mvp": {
		    		players.putIfAbsent(event.get("userid"), new Player(event.get("userid")));
		    		players.get(event.get("userid")).mvp++;
		    	} break;
		    	case "round_end": {
		    		round_end = true;
		    		int winner = Integer.parseUnsignedInt(event.get("winner"));
		    		if (clutch_team != -1) {
			    		if (clutch_team == winner) {
			    			switch (clutch_vs) {
				    			case 1: clutch_player.cl1++; break;
				    			case 2: clutch_player.cl2++; break;
				    			case 3: clutch_player.cl3++; break;
				    			case 4: clutch_player.cl4++; break;
				    			case 5: clutch_player.cl5++; break;
			    			}
			    			for (Player player: clutch_enemies) {
			    				player.cl_loose++;
			    			}
		    			} else if (clutch_opposite != null) {
		    				clutch_opposite.cl1++;
		    				clutch_player.cl_loose++;
		    			}
		    		}
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
		    		clutch_team = -1;
		    		clutch_vs = -1;
		    		clutch_player = null;
		    		clutch_opposite = null;
		    		clutch_enemies.clear();
		    	}
		    	}
	    	
	    	}
	    	

	    	if (round_end == false) {
	    		if (clutch_team == -1) {
			    	if (Player.ct_alive == 1) {
			    		clutch_vs = Player.t_alive;
			    		if (clutch_vs > 1) {
				    		clutch_team = 3;
				    		players.forEach((nick, player)->{
				    			if (player.isDisconnected() == false
				    					&& player.isDead() == false) {
				    				if (player.teamnum == clutch_team) {
				    					clutch_player = player;
				    				} else if (player.teamnum == (clutch_team == 2 ? 3 : 2)) {
				    					clutch_enemies.add(player);
				    				}
				    			}
				    		});
			    		} else {
			    			System.out.println("Wrong!");
			    		}
			    	} else if (Player.t_alive == 1) {
			    		clutch_vs = Player.ct_alive;
			    		if (clutch_vs > 1) {
				    		clutch_team = 2;
				    		players.forEach((nick, player)->{
				    			if (	player.isDisconnected() == false
				    					&& player.isDead() == false) {
				    				if (player.teamnum == clutch_team) {
				    					clutch_player = player;
				    				} else if (player.teamnum == (clutch_team == 2 ? 3 : 2)) {
				    					clutch_enemies.add(player);
				    				}
				    			}
				    		});
			    		} else {
			    			System.out.println("Wrong!");
			    		}
			    	}
	    		} else if (clutch_team == 2 && Player.ct_alive == 1
	    				|| clutch_team == 3 && Player.t_alive == 1) 
	    		{
	    			players.forEach((nick, player)->{
		    			if (	player.isDisconnected() == false
		    					&& player.isDead() == false
		    					&& player.teamnum == (clutch_team == 2 ? 3 : 2)) {
		    				clutch_opposite = player;
		    			}
		    		});
	    		}
	    	}
	    	event = csgofp.nextEvent();
    	}
    	
        Iterator<Entry<String, Player>> it = players.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Player> pair = (Map.Entry<String, Player>)it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            if (players_to_track.contains(pair.getKey()) == false) 
            	it.remove(); // avoids a ConcurrentModificationException
        }
    	
    }
    
    public void clear_players_info() {
    	players.clear();
    	Player.t_alive = 5;
    	Player.ct_alive = 5;
    }
    
    public void updateInfo() throws IOException {
    	
    	List<Player> tracking_players = new LinkedList<Player>();
    	
    	for (String nick: players_to_track) {
    		Player player = players.get(nick);
    		if (player != null)	{
    			tracking_players.add(player);
    		}
    	}
    	
        // How the input data should be interpreted.
        String valueInputOption = "USER_ENTERED";

        // How the input data should be inserted.
        String insertDataOption = "OVERWRITE";
        
        List<Object> objectList = new ArrayList<Object>();
        
        List<List<Object>> values = null;
        
        Sheets.Spreadsheets.Values.Append request = null;
        
        AppendValuesResponse response = null;
        
        ValueRange body = new ValueRange();
        
        String range = null;
        
//----
             
        range = "K!B:F";
        
        objectList.clear();
        
        for (Player player : tracking_players) {
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
        
        for (Player player : tracking_players) {
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
        
        for (Player player : tracking_players) {
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
        
        for (Player player : tracking_players) {
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
        
        for (Player player : tracking_players) {
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
        
        for (Player player : tracking_players) {
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
        
        for (Player player : tracking_players) {
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
        
        for (Player player : tracking_players) {
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
        
        for (Player player : tracking_players) {
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
        
        for (Player player : tracking_players) {
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
        
        for (Player player : tracking_players) {
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
        
        for (Player player : tracking_players) {
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
		  
		  for (Player player : tracking_players) {
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
		  
		  for (Player player : tracking_players) {
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
		  
		  for (Player player : tracking_players) {
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
		  
		  for (Player player : tracking_players) {
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
		  
		  for (Player player : tracking_players) {
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
		  
		  for (Player player : tracking_players) {
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
		  
		  for (Player player : tracking_players) {
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
		  
		  for (Player player : tracking_players) {
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
		  
		  for (Player player : tracking_players) {
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
		  
		  for (Player player : tracking_players) {
		  	objectList.add(player.hedamage);
		  }
		  values = Arrays.asList(objectList);
		  
		  body.setValues(values);
		  
		  request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
		  request.setValueInputOption(valueInputOption);
		  request.setInsertDataOption(insertDataOption);
		
		  response = request.execute();	
		  
//----
        
        range = "'1CL'!B:F";
        
        objectList.clear();
        
        for (Player player : tracking_players) {
        	objectList.add(player.cl1);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'2CL'!B:F";
        
        objectList.clear();
        
        for (Player player : tracking_players) {
        	objectList.add(player.cl2);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'3CL'!B:F";
        
        objectList.clear();
        
        for (Player player : tracking_players) {
        	objectList.add(player.cl3);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'4CL'!B:F";
        
        objectList.clear();
        
        for (Player player : tracking_players) {
        	objectList.add(player.cl4);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
//----
        
        range = "'5CL'!B:F";
        
        objectList.clear();
        
        for (Player player : tracking_players) {
        	objectList.add(player.cl5);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
	        
//----
        
        range = "'CL_LOOSE'!B:F";
        
        objectList.clear();
        
        for (Player player : tracking_players) {
        	objectList.add(player.cl_loose);
        }
        values = Arrays.asList(objectList);
        
        body.setValues(values);
        
        request = sheetsService.spreadsheets().values().append(spreadsheetId, range, body);
        request.setValueInputOption(valueInputOption);
        request.setInsertDataOption(insertDataOption);

        response = request.execute();
        
    }

}
