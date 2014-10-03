package hyg.hydrosis.WoolMix;

import hyg.hydrosis.WoolMix.Arena.Status;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
public class PlayerListener implements Listener{
	
	WoolMix plugin;
	
	public PlayerListener(WoolMix plugin)
	{
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		String pName = player.getName();
		if(plugin.steps.containsKey(pName))
		{
			int step = plugin.steps.get(pName);
			if(step==1)
			{
				if(event.getClickedBlock()==null)
					return;
				Location loc = event.getClickedBlock().getLocation();
				plugin.creation.get(pName).setCorner1(loc);
				player.sendMessage(ChatColor.DARK_AQUA+"Good! Now click the other opposite corner.");
				step+=1;
				plugin.steps.put(pName, step);
				event.setCancelled(true);
				return;
			}
			if(step==2)
			{
				if(event.getClickedBlock()==null)
					return;
				Location loc = event.getClickedBlock().getLocation();
				plugin.creation.get(pName).setCorner2(loc);
				player.sendMessage(ChatColor.YELLOW+"Now go to where you want players to be teleported when they get out and type \"spec\"");
				step+=1;
				plugin.steps.put(pName, step);
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		if(plugin.steps.containsKey(player.getName()))
		{
			int step = plugin.steps.get(player.getName());
			if(step==0)
			{
				String name = event.getMessage();
				if(name.contains(" "))
				{
					player.sendMessage(ChatColor.RED+"Please do not include spaces in the arena name!");
					event.setCancelled(true);
					return;
				}
				for(Arena a : plugin.arenas)
				{
					if(a.getName().equalsIgnoreCase(name))
					{
						player.sendMessage(ChatColor.DARK_RED+"An arena already has that name!");
						player.sendMessage(ChatColor.RED+"To delete, type " + ChatColor.GOLD + "/woolrush delete <Arena Name>");
						event.setCancelled(true);
						return;
					}
				}
				plugin.creation.put(player.getName(), new Arena(plugin, name));
				player.sendMessage(ChatColor.AQUA+"Good, now click one corner of the arena");
				step+=1;
				plugin.steps.put(player.getName(), step);
				event.setCancelled(true);
			}
			else if((event.getMessage().equalsIgnoreCase("spec")) && (step==3))
			{
				player.sendMessage(ChatColor.GOLD+"Last step: Stand where you want spawn to be and type \"spawn\" ");
				Location spec = player.getLocation();
				plugin.creation.get(player.getName()).setSpec(spec);
				step+=1;
				plugin.steps.put(player.getName(), step);
				event.setCancelled(true);
			}
			else if((event.getMessage().equalsIgnoreCase("spawn")) && (step==4))
			{
				Location spawn = player.getLocation();
				plugin.creation.get(player.getName()).setSpawn(spawn);
				plugin.creation.get(player.getName()).loadWool();
				player.sendMessage(ChatColor.GREEN+"That's it! Does this look good?");
				plugin.creation.get(player.getName()).sendInfo(player);
				player.sendMessage("To keep this arena, type \"save\"");
				player.sendMessage("To discard this arena, type \"cancel\"");
				step+=1;
				plugin.steps.put(player.getName(), step);
				event.setCancelled(true);
			}
			else if(step==5)
			{
				if(event.getMessage().equalsIgnoreCase("save"))
				{
					plugin.creation.get(player.getName()).setStatus(Status.EMPTY);
					plugin.arenas.add(plugin.creation.get(player.getName()));
					//TODO: Save to disk
					Arena arena = plugin.creation.get(player.getName());
					saveToFile(arena);
					//TODO: Save to disk
					plugin.creation.remove(player.getName());
					plugin.steps.remove(player.getName());
					player.sendMessage(ChatColor.GREEN+"Your arena has been saved!");
					event.setCancelled(true);
				}
				else if(event.getMessage().equalsIgnoreCase("cancel"))
				{
					plugin.creation.remove(player.getName());
					plugin.steps.remove(player.getName());
					player.sendMessage(ChatColor.RED+"Your arena has been discarded");
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event)
	{
		if(!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		if(plugin.players.containsKey(player.getName()))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		if(plugin.players.containsKey(player.getName()))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		if(plugin.players.containsKey(player.getName()))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onSignClick(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if(!player.hasPermission("woolrush.sign"))
			return;
		Block block = event.getClickedBlock();
		if(block==null)
			return;
		if((block.getType().equals(Material.WALL_SIGN)) || (block.getType().equals(Material.SIGN_POST)))
		{
			Sign sign = (Sign) block.getState();
			if(sign.getLine(0).equalsIgnoreCase("[Wool Rush]"))
			{
				String arena = sign.getLine(1);
				for(Arena a : plugin.arenas)
				{
					if(a.getName().equalsIgnoreCase(arena))
					{
						a.addPlayer(player);
						plugin.players.put(player.getName(), a.getName());
						player.sendMessage(ChatColor.LIGHT_PURPLE+"You've joined arena: " + ChatColor.BLUE+ a.getName());
						return;
					}
					player.sendMessage(ChatColor.RED+"Sorry, this arena is not open!");
				}
			}
		}
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent event)
	{
		Player p = event.getPlayer();
		if(plugin.players.containsKey(p.getName()))
		{
			String arenaName = plugin.players.get(p.getName());
			Arena arena = plugin.stringToArena(arenaName);
			arena.removePlayer(p);
			plugin.players.remove(p.getName());
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event)
	{
		Player player = event.getPlayer();
		if(plugin.players.containsKey(player.getName()))
		{
			if(event.getMessage().substring(1).equalsIgnoreCase("quit"))
			{
				event.setCancelled(true);
				String arenaName = plugin.players.get(player.getName());
				Arena arena = plugin.stringToArena(arenaName);
				arena.removePlayer(player);
				plugin.players.remove(player.getName());
				return;
			}
			
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED+"Please quit (" + ChatColor.AQUA + "/quit" + ChatColor.RED+ ") the game before using any other commands!");
			return;

			}
	}
	
	
	
	private void saveToFile(Arena arena) {
		String name = arena.getName();
		Location spawn = arena.getSpawn();
		Location spec = arena.getSpectatorLocation();
		Location corner1 = arena.getCorner1();
		Location corner2 = arena.getCorner2();
		saveLocation(spawn, "Arenas."+name+".Spawn Location");
		saveLocation(spec, "Arenas."+name+".Spectator Location");
		saveLocation(corner1, "Arenas."+name+".Corner 1");
		saveLocation(corner2, "Arenas."+name+".Corner 2");		
	}
	
	public void saveLocation(Location l, String path)
	{
	    ConfigurationSection loc = plugin.getConfig().getConfigurationSection(path);
	    if(loc == null)
	    {
	        loc = plugin.getConfig().createSection(path);
	    }
	    loc.set("world", l.getWorld().getName());
	    loc.set("x", l.getX());
	    loc.set("y", l.getY());
	    loc.set("z", l.getZ());
	    loc.set("yaw", l.getYaw());
	    loc.set("pitch", l.getPitch());
	    plugin.saveConfig();
	}
	 
	
	
}
