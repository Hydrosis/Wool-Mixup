package hyg.hydrosis.WoolMix.Runable;

import hyg.hydrosis.WoolMix.Arena;
import hyg.hydrosis.WoolMix.WoolMix;
import hyg.hydrosis.WoolMix.Arena.Status;
import hyg.hydrosis.WoolMix.Runable.GameLogic.Task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class GameTimer extends BukkitRunnable {
	public enum State
	{
		COUNTDOWN, TIMER;
	}
	State state = State.COUNTDOWN;
	WoolMix plugin;
	Arena arena;
	Scoreboard sb;
	Objective obj;
	int timeLeft;
	int countDown;
	int counter;
	BukkitTask task;

	public GameTimer(WoolMix plugin, Arena arena, Scoreboard sb, Objective obj) {

		this.sb = sb;
		this.arena = arena;
		this.plugin = plugin;
		this.obj = obj;
		timeLeft = arena.getTimeLimit();
		countDown = plugin.getConfig().getInt("Countdown");
		counter = countDown;
	}

	@Override
	public void run() {
		
		switch(state)
		{
		case COUNTDOWN:
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			System.out.println("Count Down: " + ChatColor.DARK_AQUA+secondsToMinutes(counter));
			obj.setDisplayName(ChatColor.DARK_AQUA+secondsToMinutes(counter));
			if(arena.getNumberOfPlayers()==0)
			{
				arena.getGameLogic().setTask(Task.TERMINAL);
				arena.setStatus(Status.EMPTY);
				return;
			}
			if(counter==0)
			{
				counter = countDown+1;
				state = State.TIMER;
				task = Bukkit.getScheduler().runTaskLater(plugin, arena.getGameLogic(), 1);
			}
			for(String name : arena.getPlayersOut())
			{
				arena.forcePutIn(Bukkit.getPlayer(name));
			}
			counter--;
			task = Bukkit.getScheduler().runTaskLater(plugin, this, 20L);
			break;
		case TIMER:
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			timeLeft--;
			if(timeLeft<0)
				timeLeft=0;
			obj.setDisplayName(secondsToMinutes(timeLeft));
			System.out.println("Timer: " + secondsToMinutes(timeLeft));
			task = Bukkit.getScheduler().runTaskLater(plugin, this, 20L);
			break;
		default:
			break;
		
		}

		
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
	
	public int getTimeLeft()
	{
		return timeLeft;
	}
	
	public BukkitTask getTask()
	{
		return task;
	}
	
	public GameTimer.State getState()
	{
		return state;
	}
}
