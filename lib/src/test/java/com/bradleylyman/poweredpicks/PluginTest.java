package com.bradleylyman.poweredpicks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

class PluginTest {
  private ServerMock server;

  @SuppressWarnings("unused")
  private Plugin powered_picks;

  @BeforeEach
  void setup() {
    this.server = MockBukkit.mock();
    this.powered_picks = MockBukkit.load(Plugin.class);
  }

  @AfterEach
  void teardown() {
    MockBukkit.unmock();
  }

  @Test
  public void Plugin_Should_Load() {
    //
  }
}
