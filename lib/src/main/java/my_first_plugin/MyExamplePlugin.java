package my_first_plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class MyExamplePlugin extends JavaPlugin implements Listener {

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
    getLogger().info("hello world from whatever this is");
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    event.getPlayer().sendMessage(
        Component.text("Hello, " + event.getPlayer().getName() + "!"));

    final ItemStack itemStack = new ItemStack(Material.STONE_PICKAXE, 1);
    final ItemMeta meta = itemStack.getItemMeta();
    meta.setCustomModelData(51215001);
    meta.displayName(
        Component.text("Stone Hammer", TextColor.color(0.5f, 0.5f, 0.5f)));
    itemStack.setItemMeta(meta);

    event.getPlayer().getInventory().addItem(itemStack);
  }
}
