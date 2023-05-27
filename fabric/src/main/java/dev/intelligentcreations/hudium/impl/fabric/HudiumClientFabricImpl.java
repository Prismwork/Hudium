package dev.intelligentcreations.hudium.impl.fabric;

import dev.intelligentcreations.hudium.HudiumClient;
import net.fabricmc.api.ClientModInitializer;

public final class HudiumClientFabricImpl implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HudiumClient.onInitializeClient();
	}
}
