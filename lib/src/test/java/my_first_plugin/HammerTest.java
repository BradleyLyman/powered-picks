package my_first_plugin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.kyori.adventure.text.TextComponent;

import org.bukkit.inventory.ItemStack;

/**
 * Behavioral 'unit' tests for my plugin's custom item.
 **/
class HammerTest {
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
  void test_CreateStoneHammer_ShouldReturn_AnItem_With_TheCorrectName() {
    final ItemStack hammer = Hammer.createStoneHammer();
    final TextComponent displayName = (TextComponent) hammer.getItemMeta()
        .displayName();
    assertThat(displayName.content()).isEqualTo("Stone Hammer");
  }

  @Test
  void test_CreateStoneHammer_ShouldReturn_OneItem() {
    final ItemStack hammer = Hammer.createStoneHammer();
    assertThat(hammer.getAmount()).isEqualTo(1);
  }
}
