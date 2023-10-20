package com.bradleylyman.poweredpicks;

import static com.google.common.truth.Truth.assertWithMessage;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.kyori.adventure.text.TextComponent;

class PoweredPicksTest {

  @SuppressWarnings("unused")
  private ServerMock server;

  @BeforeEach
  void setup() {
    this.server = MockBukkit.mock();
  }

  @AfterEach
  void teardown() {
    MockBukkit.unmock();
  }

  @Test
  public void createStonePick_should_create_a_single_item() {
    // Given an item stack created by PoweredPicks
    final ItemStack stonePick = PoweredPicks.createStonePick();

    // Then there should only be one item in the stack
    assertWithMessage("there should be only one item in the stack")
        .that(stonePick.getAmount())
        .isEqualTo(1);
  }

  @Test
  public void createStonePick_should_have_the_correct_metadata() {
    // Given an item stack created by PoweredPicks
    final ItemStack stonePick = PoweredPicks.createStonePick();

    // When considering the metadata
    final ItemMeta meta = stonePick.getItemMeta();

    assertWithMessage("the stack should have the correct display name")
        .that(((TextComponent) meta.displayName()).content())
        .isEqualTo("Powered Stone Pick");
    assertWithMessage("the stack should have the correct model data")
        .that(meta.getCustomModelData())
        .isEqualTo(51215001);
  }
}
