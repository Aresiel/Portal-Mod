package se.aresiel.aresportals;

import net.fabricmc.api.ModInitializer;

public class AresPortals implements ModInitializer {
	@Override
	public void onInitialize() {
		ModItems.registerItems();
	}
}
