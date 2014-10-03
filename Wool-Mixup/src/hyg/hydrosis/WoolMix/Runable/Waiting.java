package hyg.hydrosis.WoolMix.Runable;

import hyg.hydrosis.WoolMix.Arena;
import hyg.hydrosis.WoolMix.WoolMix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Waiting extends BukkitRunnable{

	Arena arena;
	WoolMix plugin;
	int players;
	int playersNeeded;
	int repeat = 600;
	public Waiting(Arena arena, WoolMix plugin)
	{
		this.arena = arena;
		this.plugin = plugin;
		this.playersNeeded = arena.getPlayersNeeded();
	}
	@Override
	public void run() {
		if(arena.getNumberOfPlayers() == arena.getPlayersNeeded())
		{
			arena.sendMessageToPlayers(ChatColor.AQUA+"Game Beginning!");
			arena.startGame();
			return;
		}
	      this.arena.sendMessageToPlayers(ChatColor.GRAY+"Number of players: " + arena.getNumberOfPlayers());
	      this.arena.sendMessageToPlayers(ChatColor.GREEN+ "Number of players needed" + arena.getPlayersNeeded());
	      Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, this, repeat);
	      return;
	}

}
