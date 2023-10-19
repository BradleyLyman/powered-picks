package my_first_plugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

class Hammer {
  // This value must match what is used for the predicate in the resource pack.
  private static final int STONE_HAMMER_CUSTOM_MODEL_DATA = 51215001;

  /**
   * Creates a new stone hammer.
   *
   * @return An item stack containing a single stone hammer.
   **/
  public static ItemStack createStoneHammer() {
    final ItemStack itemStack = new ItemStack(Material.STONE_PICKAXE, 1);
    final ItemMeta meta = itemStack.getItemMeta();
    meta.setCustomModelData(STONE_HAMMER_CUSTOM_MODEL_DATA);
    meta.displayName(
        Component.text("Stone Hammer", TextColor.color(0.5f, 0.5f, 0.5f)));
    itemStack.setItemMeta(meta);
    return itemStack;
  }

}
