package se.aresiel.aresportals;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item WAND = new WandItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1));

    public static void registerItems(){
        Registry.register(Registry.ITEM, new Identifier("aresportals", "wand"), WAND);
    }
}
