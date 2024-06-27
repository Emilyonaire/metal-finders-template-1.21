package net.emilyonaire.metalfinders.item;

import net.emilyonaire.metalfinders.MetalFinders;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class ModItemGroups {
    private static final ItemGroup METAL_FINDERS_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.RUBY))
            .displayName(Text.translatable("itemGroup.metal_finder_group"))
            .entries((context, entries) -> {
                entries.add(ModItems.RUBY);
                entries.add(ModItems.RAW_RUBY);
                entries.add(ModItems.HEADPHONES);
                entries.add(ModItems.BASIC_METAL_DETECTOR);
                entries.add(ModItems.GOLD_METAL_DETECTOR);
                entries.add(ModItems.DIAMOND_METAL_DETECTOR);
                entries.add(ModItems.NETHERITE_METAL_DETECTOR);
            })
            .build();

    public static void registerItemGroups(){
        MetalFinders.LOGGER.info("Registering Mod Item Groups for " + MetalFinders.MOD_ID);
        Registry.register(Registries.ITEM_GROUP, Identifier.of("metal_finders"), METAL_FINDERS_ITEM_GROUP);
    }
}
