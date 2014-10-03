package hyg.hydrosis.WoolMix.Runable;

import hyg.hydrosis.WoolMix.Arena;
import hyg.hydrosis.WoolMix.WoolMix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Timer extends BukkitRunnable{
	public enum State
	{
		COUNTDOWN, 
		INVEXP, 
		RUSH, 
		REMOVE, 
		CHECK,
		RESTORE,
		RESTART,
		TERMINAL;
	}
	
	State state = State.COUNTDOWN;
	WoolMix plugin;
	Arena arena;
	Scoreboard scoreboard;
	Objective obj;
	int orig_countdown;
	int countDowner;
	int orig_TimeLimit;
	int timer;
	float orig_RoundTime;
	float roundTime;
	float timeLeft;
	double speedup;
	
	
	public Timer(WoolMix plugin, Arena arena)
	{
		this.plugin = plugin;
		this.arena = arena;
		obj = scoreboard.registerNewObjective("Timer", "derp");
		orig_countdown = plugin.getConfig().getInt("Countdown");
		countDowner = orig_countdown;
		orig_TimeLimit = plugin.getConfig().getInt("Time Limit");
		//once it's %20, update clock
		timer = 0;
		orig_RoundTime = plugin.getConfig().getInt("Timer");
		roundTime = orig_RoundTime;
		speedup = plugin.getConfig().getDouble("Speedup");
	}
	
	boolean welcome = true;
	@Override
	public void run() {
		
		switch(state)
		{
		case COUNTDOWN:
			if(welcome)
			{
				arena.sendMessageToPlayers(ChatColor.DARK_AQUA+"~-~-~-~-~-~-~" + ChatColor.GRAY+ "Wool Rush" + ChatColor.DARK_AQUA+ "~-~-~-~-~-~-~_~");
				arena.sendMessageToPlayers(ChatColor.RED +"===" +ChatColor.AQUA+ "To win, stand on the wool in your inventory!"+ChatColor.RED+ "===");
				arena.sendMessageToPlayers(ChatColor.BLUE + "" + ChatColor.BOLD+ ".:." + ChatColor.RESET+ ChatColor.DARK_RED + "To quit, type " + ChatColor.GREEN + "/quit"+ChatColor.BLUE + ChatColor.BOLD+".:.");
				welcome = false;
			}
			if(countDowner<=0)
			{
				state = State.INVEXP;
				welcome = true;
				countDowner= orig_countdown;
				obj.setDisplayName(ChatColor.BLUE+secondsToString(orig_TimeLimit));
				Bukkit.getScheduler().runTaskLater(plugin, this, 1);
			}
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(ChatColor.BLUE+secondsToString(countDowner));
			obj.getScore(Bukkit.getOfflinePlayer("Players left")).setScore(arena.getSizeOfParticipants());
			countDowner--;
			Bukkit.getScheduler().runTaskLater(plugin, this, 20);
			break;
		case INVEXP:
			if(arena.getNumberOfPlayers()==0)
			{
				this.setState(State.TERMINAL);
				return;
			}
			arena.updateInventory();
			arena.updateExpBar(.99F);
			roundTime = roundTime*(float)speedup;
			timeLeft = roundTime;
			state = State.RUSH;
			Bukkit.getScheduler().runTaskLater(plugin, this, 1);
			break;
		case RUSH:
			float percent = timeLeft/roundTime;
			arena.updateExpBar(percent);
			timeLeft--;
			if(timeLeft<=0)
				state = State.REMOVE;
			Bukkit.getScheduler().runTaskLater(plugin, this, 1);
			break;
		case REMOVE:
			break;
		case CHECK:
			break;
		case RESTORE:
			break;
		case RESTART:
			break;
		default:
			break;
		
		}
			
	}
	private void setState(State state) {
		this.state = state;
	}
	private String secondsToString(int time) {
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

}
