package hyg.hydrosis.WoolMix;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StoredInventory {
	  Location location = null;
	  ItemStack[] playerinventory = null;
	  ItemStack[] armorcontents = null;
	  double health = 20.0D;
	  float exp = 0.0F;
	  int foodlevel = 20;
	  int level = 0;
	  float saturation = 3.0F;
	  Player player = null;
	  
	  public StoredInventory(Player player)
	  {
	    this.location = player.getLocation();
	    this.playerinventory = player.getInventory().getContents();
	    this.armorcontents = player.getInventory().getArmorContents();
	    this.health = player.getHealth();
	    this.exp = player.getExp();
	    this.foodlevel = player.getFoodLevel();
	    this.level = player.getLevel();
	    this.saturation = player.getSaturation();
	    this.player = player;
	  }
	  
	  public void restorePlayer()
	  {
	    this.player.setFallDistance(0.0F);
	    this.player.teleport(this.location);
	    this.player.getInventory().clear();
	    this.player.getInventory().setContents(this.playerinventory);
	    this.player.getInventory().setArmorContents(this.armorcontents);
	    this.player.setHealth(this.health);
	    this.player.setExp(this.exp);
	    this.player.setFoodLevel(this.foodlevel);
	    this.player.setLevel(this.level);
	    this.player.setSaturation(this.saturation);
	  }
}
