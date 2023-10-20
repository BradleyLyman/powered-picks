package com.bradleylyman.poweredpicks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

class PoweredPicks {

  /** Used to identify the powered pick in the resource pack. */
  private static final int STONE_PICKAXE_CUSTOM_MODEL_DATA = 51215001;

  /**
   * Creates a new powered stone pickaxe.
   *
   * @return An item stack containing a single stone pickaxe.
   */
  public static ItemStack createStonePick() {
    final var item = new ItemStack(Material.STONE_PICKAXE, 1);
    final ItemMeta meta = item.getItemMeta();
    meta.setCustomModelData(STONE_PICKAXE_CUSTOM_MODEL_DATA);
    meta.displayName(
        Component.text("Powered Stone Pick",
            TextColor.color(0.5f, 0.5f, 0.5f)));
    item.setItemMeta(meta);
    return item;
  }
}
