package hyg.hydrosis.WoolMix.Runable;

import hyg.hydrosis.WoolMix.Arena;
import hyg.hydrosis.WoolMix.WoolMix;
import hyg.hydrosis.WoolMix.Arena.Status;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class GameLogic extends BukkitRunnable {
	
	public enum Task
	{
		COUNTDOWN, 
		INVEXP, 
		RUSH, 
		REMOVE, 
		RESTORE, 
		CHECK, 
		RESTART,
		TERMINAL;
	}
	WoolMix plugin;
	Arena arena;
	final float origRoundTime;
	float roundTime;
	float timeLeft;
	double speedup;
	int timeLimit;
	long checkDelay = 3*20;
	Scoreboard sb;
	Objective obj;
	Task task = Task.TERMINAL;
	GameTimer gt;
	boolean countingDown;
	BukkitTask timerTask;
	public GameLogic(WoolMix plugin, Arena arena, Objective obj) {
		this.arena = arena;
		this.plugin = plugin;
		timeLimit = arena.getTimeLimit();
		//TODO: Implement
		speedup = plugin.getConfig().getDouble("Speedup");
		roundTime = (float) (plugin.getConfig().getDouble("Timer")*20F);
		origRoundTime = roundTime;
		timeLeft = roundTime;
		sb = arena.getScoreboard();
		sb.clearSlot(DisplaySlot.SIDEBAR);
		this.obj = obj;
	}


	@Override
	public void run() {
		switch(task)
		{
		case COUNTDOWN:
			arena.sendMessageToPlayers(ChatColor.DARK_AQUA+"~-~-~-~-~-~-~" + ChatColor.GRAY+ "Wool Rush" + ChatColor.DARK_AQUA+ "~-~-~-~-~-~-~_~");
			arena.sendMessageToPlayers(ChatColor.RED +"===" +ChatColor.AQUA+ "To win, stand on the wool in your inventory!"+ChatColor.RED+ "===");
			arena.sendMessageToPlayers(ChatColor.BLUE + "" + ChatColor.BOLD+ ".:." + ChatColor.RESET+ ChatColor.DARK_RED + "To quit, type " + ChatColor.GREEN + "/quit"+ChatColor.BLUE + ChatColor.BOLD+".:.");
			obj.getScore(Bukkit.getOfflinePlayer("Players left")).setScore(arena.getSizeOfParticipants());
			if(timerTask!=null)
				timerTask.cancel();
			gt = new GameTimer(plugin,arena,sb, obj);
			timerTask = Bukkit.getScheduler().runTaskLater(plugin,gt, 1);
			task = Task.INVEXP;
			break;
		case INVEXP:
			if(arena.getNumberOfPlayers()==0)
			{
				arena.setStatus(Status.EMPTY);
				gt.getTask().cancel();
				this.setTask(Task.TERMINAL);
				return;
			}
			arena.updateInventory();
			arena.updateExpBar(.99F);
			roundTime = roundTime*(float)speedup;
			if(roundTime<1F)
				roundTime=2;
			timeLeft=roundTime-1;
			task = Task.RUSH;
			Bukkit.getScheduler().runTaskLater(plugin, this, 1);
			break;
		case RUSH:
			float percent = timeLeft/roundTime;
			arena.updateExpBar(percent);
			timeLeft--;
			if(timeLeft<=0)
				task = Task.REMOVE;
			Bukkit.getScheduler().runTaskLater(plugin, this, 1);
			break;
		case CHECK:
			arena.checkPlayers();
			obj.getScore(Bukkit.getOfflinePlayer("Players left")).setScore(arena.getSizeOfParticipants());
			task = Task.RESTORE;
			Bukkit.getScheduler().runTaskLater(plugin, this, 1);
			break;
		case REMOVE:
			arena.removeWool();
			task = Task.CHECK;
			Bukkit.getScheduler().runTaskLater(plugin, this, 60L);
			break;
		case RESTORE:
			arena.restoreWool();
			if(gt.getTimeLeft()==0 || arena.getSizeOfParticipants()==0)
			{
				arena.restToSpec();
				task = Task.RESTART;
				Bukkit.getScheduler().runTaskLater(plugin, this, 1);
				return;
			}
			task = Task.INVEXP;
			Bukkit.getScheduler().runTaskLater(plugin, this, 1);
			break;
		case RESTART:
			arena.resetGame();
			roundTime = origRoundTime;
			gt.getTask().cancel();
			if(arena.getSizeOfParticipants()==0)
			{
				task = Task.TERMINAL;
				arena.setStatus(Status.EMPTY);;
				return;
			}
			task = Task.COUNTDOWN;
			gt = new GameTimer(plugin,arena,sb, obj);
			Bukkit.getScheduler().runTaskLater(plugin, this, 1);
			return;
		case TERMINAL:
			return;
		default:
			break;
		}
		
		
	}
	
	public void setTask(Task task)
	{
		this.task = task;
	}
	
	public Task getTask()
	{
		return task;
	}

}
