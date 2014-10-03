package hyg.hydrosis.WoolMix;

import hyg.hydrosis.WoolMix.Runable.GameLogic;
import hyg.hydrosis.WoolMix.Runable.GameLogic.Task;
import hyg.hydrosis.WoolMix.Runable.GameTimer;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Arena {
	
	public enum Status
	{
		IN_PROGRESS, EMPTY, CLOSED;
	}
	
	public enum RoundType
	{
		SINGLE, REPEAT;
	}
	private RoundType roundType = RoundType.REPEAT;
	private int randColor;
	private Scoreboard sb;
	private int playersNeeded;
	private int timeLimit;
	private WoolMix plugin;
	private String name;
	private Status status = Status.EMPTY;
	private Location corner1;
	private Location corner2;
	private Location spawn;
	private Map<String, StoredInventory> players = new HashMap<String,StoredInventory>();
	private Set<String> playersOut = new HashSet<String>();
	private Set<String> playersIn = new HashSet<String>();
	private Set<Location> white = new HashSet<Location>();
	private Set<Location> orange = new HashSet<Location>();
	private Set<Location> magenta = new HashSet<Location>();
	private Set<Location> light_blue = new HashSet<Location>();
	private Set<Location> yellow = new HashSet<Location>();
	private Set<Location> lime = new HashSet<Location>();
	private Set<Location> pink = new HashSet<Location>();
	private Set<Location> gray = new HashSet<Location>();
	private Set<Location> light_gray = new HashSet<Location>();
	private Set<Location> cyan = new HashSet<Location>();
	private Set<Location> purple = new HashSet<Location>();
	private Set<Location> blue = new HashSet<Location>();
	private Set<Location> brown = new HashSet<Location>();
	private Set<Location> green = new HashSet<Location>();
	private Set<Location> red = new HashSet<Location>();
	private Set<Location> black = new HashSet<Location>();
	private List<Integer> woolOnArena = new ArrayList<Integer>();
	private List<DyeColor> woolColor = new ArrayList<DyeColor>();
	private int numOfWool;
	private WoolInv wools = new WoolInv();
	private Location spec;
	private double lowestHeight;
	private Objective timer;
	private GameLogic gl;
	private GameTimer gt;
	Arena(WoolMix plugin, String name)
	{
		this.plugin = plugin;
		this.name = name;
		this.playersNeeded = 1;
		timeLimit = plugin.getConfig().getInt("Time Limit");
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		String time = secondsToMinutes(timeLimit);
		timer = sb.registerNewObjective(time, "Timer");
		gt = new GameTimer(plugin,this,sb, timer);
		gl = new GameLogic(plugin, this, timer);
	}
	private String secondsToMinutes(int time) {
		int min = time / 60;
		int sec = time % 60;
		if(min<10)
		{
			if(sec<10)
				return "0"+min+":0"+sec;
			return "0"+min+":"+sec;
		}
		if(sec==0)
			return ""+min+":00";
		if(sec<10)
			return ""+min+":0"+sec;
		return ""+min+":"+sec;
	}
	public void loadWool() {
		int x1 = corner1.getBlockX();
		int y1 = corner1.getBlockY();
		int z1 = corner1.getBlockZ();
		int x2 = corner2.getBlockX();
		int y2 = corner2.getBlockY();
		int z2 = corner2.getBlockZ();
		if(x2<x1)
		{
			int lowestX = x2;
			x2 = x1;
			x1 = lowestX;
		}
		lowestHeight = y1;
		if(y2<y1)
		{
			int lowestY = y2;
			y2 = y1;
			y1 = lowestY;
			lowestHeight =lowestY;
		}
		if(z2<z1)
		{
			int lowestZ = z2;
			z2 = z1;
			z1 = lowestZ;
		}
		World world = corner1.getWorld();
		for(int x=x1; x<=x2; x++)
		{
			for(int y=y1; y<=y2; y++)
			{
				for(int z=z1; z<=z2; z++)
				{
					Block block = world.getBlockAt(x, y, z);
					if(!block.getType().equals(Material.WOOL))
						continue;
					Wool wool = (Wool) block.getState().getData();
					DyeColor color = wool.getColor();
					switch(color)
					{
					case BLACK:
						black.add(block.getLocation());
						if(!this.woolOnArena.contains(15))
						{
							woolOnArena.add(15);
							woolColor.add(DyeColor.BLACK);
						}
						break;
					case BLUE:
						blue.add(block.getLocation());
						if(!this.woolOnArena.contains(11))
						{
							woolOnArena.add(11);
							woolColor.add(DyeColor.BLUE);
						}
						break;
					case BROWN:
						brown.add(block.getLocation());
						if(!this.woolOnArena.contains(12))
						{
							woolOnArena.add(12);
							woolColor.add(DyeColor.BROWN);
						}
						break;
					case CYAN:
						cyan.add(block.getLocation());
						if(!this.woolOnArena.contains(9))
						{
							woolOnArena.add(9);
							woolColor.add(DyeColor.CYAN);
						}
						break;
					case GRAY:
						gray.add(block.getLocation());
						if(!this.woolOnArena.contains(7))
						{
							woolOnArena.add(7);
							woolColor.add(DyeColor.GRAY);
						}
						break;
					case GREEN:
						green.add(block.getLocation());
						if(!this.woolOnArena.contains(13))
						{
							woolOnArena.add(13);
							woolColor.add(DyeColor.GREEN);
						}
						break;
					case LIGHT_BLUE:
						light_blue.add(block.getLocation());
						if(!this.woolOnArena.contains(3))
						{
							woolOnArena.add(3);
							woolColor.add(DyeColor.LIGHT_BLUE);
						}
						break;
					case LIME:
						lime.add(block.getLocation());
						if(!this.woolOnArena.contains(5))
						{
							woolOnArena.add(5);
							woolColor.add(DyeColor.LIME);
						}
						break;
					case MAGENTA:
						magenta.add(block.getLocation());
						if(!this.woolOnArena.contains(2))
						{
							woolOnArena.add(2);
							woolColor.add(DyeColor.MAGENTA);
						}
						break;
					case ORANGE:
						orange.add(block.getLocation());
						if(!this.woolOnArena.contains(1))
						{
							woolOnArena.add(1);
							woolColor.add(DyeColor.ORANGE);
						}
						break;
					case PINK:
						pink.add(block.getLocation());
						if(!this.woolOnArena.contains(6))
						{
							woolOnArena.add(6);
							woolColor.add(DyeColor.PINK);
						}
						break;
					case PURPLE:
						purple.add(block.getLocation());
						if(!this.woolOnArena.contains(10))
						{
							woolOnArena.add(10);
							woolColor.add(DyeColor.PURPLE);
						}
						break;
					case RED:
						red.add(block.getLocation());
						if(!this.woolOnArena.contains(14))
						{
							woolOnArena.add(14);
							woolColor.add(DyeColor.RED);
						}
						break;
					case SILVER:
						light_gray.add(block.getLocation());
						if(!this.woolOnArena.contains(8))
						{
							woolOnArena.add(8);
							woolColor.add(DyeColor.SILVER);
						}
						break;
					case WHITE:
						white.add(block.getLocation());
						if(!this.woolOnArena.contains(0))
						{
							woolOnArena.add(0);
							woolColor.add(DyeColor.WHITE);
						}
						break;
					case YELLOW:
						yellow.add(block.getLocation());
						if(!this.woolOnArena.contains(4))
						{
							woolOnArena.add(4);
							woolColor.add(DyeColor.YELLOW);
						}
						break;
					default:
						System.out.println("ERROR: Block does not have a color set.");
						break;
					}
				}
			}	
		}
		numOfWool  = white.size() + orange.size() + magenta.size() + light_blue.size() + yellow.size() + lime.size() + pink.size() +gray.size()
				+light_gray.size() + cyan.size() + purple.size() + blue.size() + brown.size() + green.size() + red.size() + black.size();
		
	}

	public void setSpawn(Location spawn)
	{
		this.spawn = spawn;
	}
	
	public void setCorner1(Location corner1)
	{
		this.corner1 = corner1;
	}
	
	public void setCorner2(Location corner2)
	{
		this.corner2 = corner2;
	}
	
	public void setStatus(Status status)
	{
		this.status = status;
	}
	
	public void sendInfo(Player player)
	{
		player.sendMessage(ChatColor.GRAY+"Name: " + ChatColor.GREEN+ name);
		player.sendMessage(ChatColor.GRAY+"Corner 1: " + ChatColor.YELLOW +corner1.getBlockX() + "," + corner1.getBlockY() + "," + corner1.getBlockZ());
		player.sendMessage(ChatColor.GRAY+"Corner 2: " + ChatColor.GOLD + corner2.getBlockX() + "," + corner2.getBlockY() + "," + corner2.getBlockZ());
		player.sendMessage(ChatColor.GRAY+"Number of wool blocks: " + ChatColor.BLUE + numOfWool );
	}
	
	public int getNumberofWool()
	{
		return numOfWool;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean addPlayer(Player player)
	{
		if(status.equals(Status.CLOSED))
			return false;
		StoredInventory inv = new StoredInventory(player);
		players.put(player.getName(), inv);
		player.getInventory().clear();
		player.teleport(spawn);
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setLevel(0);
		player.setExp(0);
//		updateScoreboards();
		player.setScoreboard(sb);
		sendMessageToPlayers(ChatColor.AQUA+player.getName()+ChatColor.GREEN+" has joined the game!");
		if(status.equals(Status.IN_PROGRESS))
		{
			playersOut.add(player.getName());
			player.teleport(spec);
			return true;
		}
		playersIn.add(player.getName());
		player.teleport(spawn);
		if(gl.getTask()==Task.TERMINAL)
		{
			status = Status.IN_PROGRESS;
			gl.setTask(Task.COUNTDOWN);
			System.out.println(gl.getTask());
			gt = new GameTimer(plugin,this,sb, timer);
			Bukkit.getScheduler().runTaskLater(plugin, gl,1);
		}
		return true;
	}
	
	
	private void updateScoreboards() {
		Objective obj = sb.getObjective(ChatColor.GREEN+"Players: ");
		obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN+"Current: ")).setScore(players.size());
		obj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED+"Needed: ")).setScore(playersNeeded);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

	}
	public boolean containsPlayer(Player p)
	{
		if(players.containsKey(p))
			return true;
		return false;
	}
	
	public int getNumberOfPlayers()
	{
		return players.size();
	}
	
	public int getPlayersNeeded()
	{
		return playersNeeded;
	}
	public Scoreboard getScoreboard()
	{
		return sb;
	}
	public int getTimeLimit()
	{
		return timeLimit;
	}
	public void startGame()
	{
		for(String player : players.keySet())
		{
			playersIn.add(player);
		}
		setStatus(Status.IN_PROGRESS);
	}
	

	
	
	  public void sendMessageToPlayers(String message)
	  {
	    Iterator<String> tplayers = this.players.keySet().iterator();
	    while (tplayers.hasNext())
	    {
	      String splayer = (String)tplayers.next();
	      Player player = Bukkit.getServer().getPlayerExact(splayer);
	      if ((player != null) && (player.isOnline())) {
	        player.sendMessage(message);
	      } else {
	        this.players.remove(splayer);
	      }
	    }
	  }
	  
		public void removeWool()
		{
			if(randColor!=0)
			{
				for(Location l :white)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=1)
			{
				for(Location l :orange)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=2)
			{
				for(Location l :magenta)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=3)
			{
				for(Location l :light_blue)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=4)
			{
				for(Location l :yellow)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=5)
			{
				for(Location l :lime)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=6)
			{
				for(Location l :pink)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=7)
			{
				for(Location l :gray)
					l.getBlock().setType(Material.AIR);				
			}
			if(randColor!=8)
			{
				for(Location l :light_gray)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=9)
			{
				for(Location l :cyan)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=10)
			{
				for(Location l :purple)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=11)
			{
				for(Location l :blue)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=12)
			{
				for(Location l :brown)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=13)
			{
				for(Location l :green)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=14)
			{
				for(Location l :red)
					l.getBlock().setType(Material.AIR);
			}
			if(randColor!=15)
			{
				for(Location l :black)
					l.getBlock().setType(Material.AIR);
			}
			
		}
		
		public void restoreWool()
		{
			if(randColor!=0)
			{
				for(Location l :white)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.WHITE);
				}
			}
			if(randColor!=1)
			{
				for(Location l :orange)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.ORANGE);
				}
			}
			if(randColor!=2)
			{
				for(Location l :magenta)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.MAGENTA);
				}
			}
			if(randColor!=3)
			{
				for(Location l :light_blue)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.LIGHT_BLUE);
				}
			}
			if(randColor!=4)
			{
				for(Location l :yellow)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.YELLOW);
				}
			}
			if(randColor!=5)
			{
				for(Location l :lime)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.LIME);
				}
			}
			if(randColor!=6)
			{
				for(Location l :pink)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.PINK);
				}
			}
			if(randColor!=7)
			{
				for(Location l :gray)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.GRAY);
				}
			}
			if(randColor!=8)
			{
				for(Location l :light_gray)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.SILVER);
				}
			}
			if(randColor!=9)
			{
				for(Location l :cyan)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.CYAN);
				}
			}
			if(randColor!=10)
			{
				for(Location l :purple)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.PURPLE);
				}
			}
			if(randColor!=11)
			{
				for(Location l :blue)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.BLUE);
				}
			}
			if(randColor!=12)
			{
				for(Location l :brown)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.BROWN);
				}
			}
			if(randColor!=13)
			{
				for(Location l :green)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.GREEN);
				}
			}
			if(randColor!=14)
			{
				for(Location l :red)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.RED);
				}
			}
			if(randColor!=15)
			{
				for(Location l :black)
				{
					l.getBlock().setType(Material.WOOL);
					((Wool)l.getBlock().getState().getData()).setColor(DyeColor.BLACK);
				}
			}
		}
		public void updateInventory() {
			randColor = woolOnArena.get((int) (Math.random()*woolOnArena.size()));
			for(String name : playersIn)
			{
				Player p = Bukkit.getPlayer(name);
				p.getInventory().setContents(wools.getInv(woolColor.get(woolOnArena.indexOf(randColor))).getContents());
			}
		}
		public void updateExpBar(float percent) {
			for(String s : playersIn)
			{
				Player p = Bukkit.getPlayer(s);
				p.setExp(percent);
			}
		}
		public void setSpec(Location spec) {
			this.spec = spec;
		}
		public void checkPlayers() {
			try
			{
			for(String name: playersIn)
			{
				Player p = Bukkit.getPlayer(name);
				if(p.getLocation().getBlockY()<lowestHeight)
				{
					p.sendMessage(placePosition());
					playersIn.remove(name);
					playersOut.add(name);
					p.getInventory().clear();
					p.teleport(spec);
				}
			}
			}catch(ConcurrentModificationException e){};
		}
		private String placePosition() {
			if(playersIn.size()==1)
				return ChatColor.GREEN + "You've came in " + ChatColor.GOLD + "1st place" + ChatColor.GREEN + "!!!!";
			else if(playersIn.size()==2)
				return ChatColor.GREEN + "You've came in " + ChatColor.GRAY + "2nd place" + ChatColor.GREEN + "!!";
			else if(players.size()==3)
				return ChatColor.GREEN + "You've came in " + ChatColor.DARK_PURPLE + "3rd place" + ChatColor.GREEN + "!";
			else
				return ChatColor.GREEN + "You've came in " + ChatColor.GOLD + playersIn.size()+"th place" + ChatColor.GREEN + "!!!!";
		}
		public void restToSpec() {
			for(String name: playersIn)
			{
				Player p = Bukkit.getPlayer(name);
				p.sendMessage(placePosition());
				playersIn.remove(name);
				playersOut.add(name);
				p.teleport(spec);
			}
		}
		public void resetGame() {
			playersIn.clear();
			playersOut.clear();
			for(String s: players.keySet())
			{
				Bukkit.getPlayer(s).teleport(spawn);
				playersIn.add(s);
			}
		}
		public int getSizeOfParticipants() {
			return playersIn.size();
		}
		public int getSizeOfSpectators() {
			return playersOut.size();
		}
		public void waitInLobby() {
			for(String s : players.keySet())
			{
				Player p = Bukkit.getPlayer(s);
				p.teleport(spawn);
				updateScoreboards();
			}
		}
		
		public GameTimer getGameTimer()
		{
			return gt;
		}
		
		public GameLogic getGameLogic()
		{
			return gl;
		}
		public void removePlayer(Player player) {
			StoredInventory si = players.get(player.getName());
			si.restorePlayer();
			players.remove(player.getName());
			playersIn.remove(player.getName());
			playersOut.remove(player.getName());
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			player.sendMessage(ChatColor.LIGHT_PURPLE+"You have left the arena!");
		}
		
		public void playerDeathHandler(Player p)
		{
			
			p.sendMessage(placePosition());
			playersIn.remove(name);
			playersOut.add(name);
			p.getInventory().clear();
			p.teleport(spec);
		}
		public Location getSpawn() {
			return spawn;
		}
		public Location getSpectatorLocation()
		{
			return spec;
		}
		public Location getCorner1() {
			return corner1;
		}
		public Location getCorner2() {
			return corner2;
		}
		public void DeletePrep() {
			for(String s : players.keySet())
			{
				Player player = Bukkit.getPlayer(s);
				removePlayer(player);
				players.remove(s);
			}
			setStatus(Status.CLOSED);
		}
		public Set<String> getPlayersOut() {
			return playersOut;
		}
		public void forcePutIn(Player player) {
			player.teleport(spawn);
			playersOut.remove(player.getName());
			playersIn.add(player.getName());
		}
		public void setRoundType(String type) {
			if(type.equalsIgnoreCase("repeat"))
				roundType = RoundType.REPEAT;
			else if(type.equalsIgnoreCase("single"))
				roundType = RoundType.SINGLE;
		}
		public RoundType getRoundType() {
			return roundType;
		}
	  
}
