package se.aresiel.aresportals;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item CORRIDOR_WAND = new CorridorWandItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1), 20, 200);
    public static final Item PORTAL_WAND = new PortalWandItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1), 100);

    public static void registerItems(){
        Registry.register(Registry.ITEM, new Identifier("aresportals", "corridor_wand"), CORRIDOR_WAND);
        Registry.register(Registry.ITEM, new Identifier("aresportals", "portal_wand"), PORTAL_WAND);
    }
}
