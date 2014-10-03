package hyg.hydrosis.WoolMix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WoolInv {
	
	static Inventory black = Bukkit.createInventory(null, InventoryType.PLAYER);
	static Inventory blue = Bukkit.createInventory(null, InventoryType.PLAYER);
	static Inventory brown = Bukkit.createInventory(null, InventoryType.PLAYER);
	static Inventory cyan = Bukkit.createInventory(null, InventoryType.PLAYER);
	static Inventory gray = Bukkit.createInventory(null, InventoryType.PLAYER);
	static Inventory green = Bukkit.createInventory(null, InventoryType.PLAYER);
	static Inventory light_blue = Bukkit.createInventory(null, InventoryType.PLAYER);
	static 	Inventory lime = Bukkit.createInventory(null, InventoryType.PLAYER);
	static 	Inventory magenta = Bukkit.createInventory(null, InventoryType.PLAYER);
	static 	Inventory orange = Bukkit.createInventory(null, InventoryType.PLAYER);
	static 	Inventory pink = Bukkit.createInventory(null, InventoryType.PLAYER);
	static 	Inventory purple = Bukkit.createInventory(null, InventoryType.PLAYER);
	static 	Inventory red = Bukkit.createInventory(null, InventoryType.PLAYER);
	static 	Inventory silver = Bukkit.createInventory(null, InventoryType.PLAYER);
	static 	Inventory white = Bukkit.createInventory(null, InventoryType.PLAYER);
	static 	Inventory yellow = Bukkit.createInventory(null, InventoryType.PLAYER);
	
	@SuppressWarnings("deprecation")
	public WoolInv()
	{
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.BLACK.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.BLACK+""+ChatColor.BOLD+"BLACK");
			item.setItemMeta(meta);
			black.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.BLUE.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.BLUE+""+ChatColor.BOLD+"BLUE");
			item.setItemMeta(meta);
			blue.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.BROWN.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"BROWN");
			item.setItemMeta(meta);
			brown.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.CYAN.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"CYAN");
			item.setItemMeta(meta);
			cyan.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.GRAY.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GRAY+""+ChatColor.BOLD+"GRAY");
			item.setItemMeta(meta);
			gray.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_GREEN+""+ChatColor.BOLD+"GREEN");
			item.setItemMeta(meta);
			green.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.LIGHT_BLUE.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.AQUA+""+ChatColor.BOLD+"LIGHT_BLUE");
			item.setItemMeta(meta);
			light_blue.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.LIME.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"LIME");
			item.setItemMeta(meta);
			lime.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.MAGENTA.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"MAGENTA");
			item.setItemMeta(meta);
			magenta.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.ORANGE.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"ORANGE");
			item.setItemMeta(meta);
			orange.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.PINK.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"PINK");
			item.setItemMeta(meta);
			pink.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.PURPLE.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"PURPLE");
			item.setItemMeta(meta);
			purple.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_RED+""+ChatColor.BOLD+"RED");
			item.setItemMeta(meta);
			red.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.SILVER.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GRAY+""+ChatColor.BOLD+"SILVER");
			item.setItemMeta(meta);
			silver.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.WHITE.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE+""+ChatColor.BOLD+"WHITE");
			item.setItemMeta(meta);
			white.setItem(i, item);
		}
		for(int i=0; i<9; i++)
		{
			ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.YELLOW.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW+""+ChatColor.BOLD+"YELLOW");
			item.setItemMeta(meta);
			yellow.setItem(i, item);
		}
	}


	public Inventory getInv(DyeColor dyeColor) {
		switch(dyeColor)
		{
		case BLACK:
			return black;
		case BLUE:
			return blue;
		case BROWN:
			return brown;
		case CYAN:
			return cyan;
		case GRAY:
			return gray;
		case GREEN:
			return green;
		case LIGHT_BLUE:
			return light_blue;
		case LIME:
			return lime;
		case MAGENTA:
			return magenta;
		case ORANGE:
			return orange;
		case PINK:
			return pink;
		case PURPLE:
			return purple;
		case RED:
			return red;
		case SILVER:
			return silver;
		case WHITE:
			return white;
		case YELLOW:
			return yellow;
		default:
			return null;
		}
	}

}
