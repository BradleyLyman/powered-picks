package com.bradleylyman.poweredpicks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * PoweredPicks plugin entrypoint.
 */
public class Plugin extends JavaPlugin implements Listener {
  @Override
  public void onEnable() {
    getLogger().info("hello world from powered picks!");
    Bukkit.getPluginManager().registerEvents(this, this);
    setupRecipe();
  }

  private void setupRecipe() {
    final ItemStack stonePick = PoweredPicks.createStonePick();
    final var key = new NamespacedKey(this, "powered_stone_pick");
    final var recipe = new ShapedRecipe(key, stonePick);
    recipe.shape( //
        " SS", //
        " |S", //
        "|  " //
    );
    recipe.setCategory(CraftingBookCategory.EQUIPMENT);
    recipe.setIngredient('S', new RecipeChoice.MaterialChoice(
        Material.COBBLESTONE,
        Material.STONE,
        Material.DEEPSLATE,
        Material.COBBLED_DEEPSLATE,
        Material.DIORITE,
        Material.ANDESITE,
        Material.GRANITE));
    recipe.setIngredient('|', Material.STICK);
    Bukkit.addRecipe(recipe);
  }

  @EventHandler
  public void onPlayerJoin(final PlayerJoinEvent event) {
    final Inventory inventory = event.getPlayer().getInventory();
    inventory.addItem(PoweredPicks.createStonePick());
    event.getPlayer().updateInventory();
  }
}
