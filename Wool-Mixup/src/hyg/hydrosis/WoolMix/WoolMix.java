package hyg.hydrosis.WoolMix;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WoolMix extends JavaPlugin{
	
	//Player names : Stage
	Map<String, Integer> steps = new HashMap<String, Integer>();
	//Player names : Arena
	Map<String, Arena> creation = new HashMap<String, Arena>();
	//Set of arenas;
	Set<Arena> arenas = new HashSet<Arena>();
	//Player Names: Arena Names
	Map<String, String> players = new HashMap<String, String>();
	
	@Override
	public void onEnable()
	{
		this.saveDefaultConfig();
		loadArenas();
		new PlayerListener(this);
		this.getLogger().info("Wool Rush (by Hydrosis) was successfully loaded!");
		
	}
	


	@Override
	public void onDisable()
	{
		Bukkit.getLogger().info("Safely removing all players...");
		for(Arena a : arenas)
		{
			a.DeletePrep();
		}
		Bukkit.getLogger().info("Safely stopping all games...");
		for(Arena a : arenas)
		{
			a.restoreWool();
		}
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(label.equalsIgnoreCase("woolrush"))
		{
			if(!(sender instanceof Player))
				sender.sendMessage("You must be a player to run this command!");
			Player player = (Player) sender;
			if(args.length==1)
			{
				if(args[0].equalsIgnoreCase("setup") && player.hasPermission("woolrush.setup"))
				{
					player.sendMessage(ChatColor.DARK_AQUA+"~-~-~-~-~-~-~" + ChatColor.GRAY+ "Wool Rush" + ChatColor.DARK_AQUA+ "~-~-~-~-~-~-~_~");
					player.sendMessage(ChatColor.GRAY+"Please name this arena");
					steps.put(player.getName(), 0);
					return true;
				}
				
				if(args[0].equalsIgnoreCase("abort"))
				{
					if(steps.containsKey(player.getName()))
					{
						creation.remove(player.getName());
						steps.remove(player.getName());
						player.sendMessage(ChatColor.RED+"Your arena has been discarded");
						return true;
					}
					player.sendMessage(ChatColor.RED+"You are not in the middle of creating an arena!");
					return true;
				}
				
				if(args[0].equalsIgnoreCase("list"))
				{
					if(!player.hasPermission("woolrush.list"))
					{
						player.sendMessage(ChatColor.RED+"You do not have permission to use this command");
						return true;
					}
					int c = 1;
					for(Arena a : arenas)
					{
						player.sendMessage(ChatColor.GREEN+""+c+") "+a.getName());
						c++;
					}
					return true;
				}
				
			}
			if(args.length==2)
			{
				if(args[0].equalsIgnoreCase("join") && player.hasPermission("woolrush.join"))
				{
					String arenaName = args[1];
					for(Arena a : arenas)
					{
						if(a.getName().equalsIgnoreCase(arenaName))
						{
							a.addPlayer(player);
							player.sendMessage(ChatColor.LIGHT_PURPLE+"You've joined arena: " + ChatColor.BLUE+ arenaName);
							players.put(player.getName(), a.getName());
							return true;
						}
					}
					player.sendMessage("No arena found by that name");
					return true;
				}
				if(args[0].equalsIgnoreCase("delete") && player.hasPermission("woolrush.delete"))
				{
					String arenaName = args[1];
					for(Arena a : arenas)
					{
						if(a.getName().endsWith(arenaName))
						{
							a.DeletePrep();
							arenas.remove(a);
							this.getConfig().set("Arenas."+arenaName, null);
							this.saveConfig();
							player.sendMessage(ChatColor.RED+"You have deleted "+ ChatColor.GOLD+arenaName);
							return true;
						}
					}
					player.sendMessage("No arena found by that name");
					return true;
				}
			}
//			if(args.length==3)
//			{
//				if(args[0].equalsIgnoreCase("rounds"))
//				{
//					if((!args[2].equalsIgnoreCase("repeat")) || (!args[2].equalsIgnoreCase("single")))
//					{
//						player.sendMessage(ChatColor.RED+"That is not a valid round type!");
//					}
//					String arenaName = args[1];
//					for(Arena a : arenas)
//					{
//						if(a.getName().equalsIgnoreCase(arenaName))
//						{
//							a.setRoundType(args[2]);
//							this.getConfig().set("Arenas."+a.getName()+"", arg1);
//							return true;
//						}
//						player.sendMessage(ChatColor.RED+"The arena was not found!");
//					}
//				}
//			}
			else
			{
				player.sendMessage("No command found");
				return true;
			}
		}
		return false;
		
	}
	
	
	public Arena stringToArena(String s)
	{
		for(Arena a : arenas)
		{
			if(a.getName().equalsIgnoreCase(s))
			{
				return a;
			}
		}
		return null;
	}
	
	
	private void loadArenas() {
		Set<String> arenaNames = null;
		try{
		arenaNames = this.getConfig().getConfigurationSection("Arenas").getKeys(false);
		}catch(NullPointerException e)
		{
			System.out.println("No arenas loaded!");
			return;
		}
		for(String name : arenaNames)
		{
			Location spawn = loadLocation("Arenas."+name+".Spawn Location");
			System.out.println(spawn);
			Location spec = loadLocation("Arenas."+name+".Spectator Location");
			Location corner1 = loadLocation("Arenas."+name+".Corner 1");
			Location corner2 = loadLocation("Arenas."+name+".Corner 2");
			Arena arena = new Arena(this, name);
			arena.setSpawn(spawn);
			arena.setSpec(spec);
			arena.setCorner1(corner1);
			arena.setCorner2(corner2);
			arena.loadWool();
			arenas.add(arena);
		}
	}
	
	public Location loadLocation(String path)
	{
	    try
	    {
	        ConfigurationSection loc = getConfig().getConfigurationSection(path);
	        World w = Bukkit.getWorld(loc.getString("world", ""));
	        double x = loc.getDouble("x");
	        double y = loc.getDouble("y");
	        double z = loc.getDouble("z");
	        float yaw = (float)loc.getDouble("yaw");
	        float pitch = (float)loc.getDouble("pitch");
	        if(w == null)
	        {
	            throw new IllegalStateException("World was not found");
	        }
	        return new Location(w,x,y,z,yaw,pitch);
	    }
	    catch(Exception ex)
	    {
	        return null;
	    }
	 
	}
	

}


