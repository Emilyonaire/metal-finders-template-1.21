package net.emilyonaire.metalfinders.item;

import net.emilyonaire.metalfinders.MetalFinders;
import net.emilyonaire.metalfinders.item.custom.MetalDetectorItem;
import net.emilyonaire.metalfinders.item.custom.ModArmorMaterials;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static net.minecraft.item.Items.register;

public class ModItems {
    public static final Item RUBY = registerItem("ruby", new Item(new Item.Settings()));
    public static final Item RAW_RUBY = registerItem("raw_ruby", new Item(new Item.Settings()));

//    public static final Item HEADPHONES = registerItem("headphones", new Item(new Item.Settings()));
    public static final Item HEADPHONES = registerItem(
            "headphones", new ArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(33)))
    );

    public static final Item BASIC_METAL_DETECTOR = registerItem("basic_metal_detector", new MetalDetectorItem(new Item.Settings().maxDamage(64).rarity(Rarity.COMMON)));
    public static final Item GOLD_METAL_DETECTOR = registerItem("gold_metal_detector", new MetalDetectorItem(new Item.Settings().maxDamage(64).rarity(Rarity.UNCOMMON)));
    public static final Item DIAMOND_METAL_DETECTOR = registerItem("diamond_metal_detector", new MetalDetectorItem(new Item.Settings().maxDamage(64).rarity(Rarity.RARE)));
    public static final Item NETHERITE_METAL_DETECTOR = registerItem("netherite_metal_detector", new MetalDetectorItem(new Item.Settings().maxDamage(64).rarity(Rarity.EPIC)));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries){
        entries.add(RUBY);
        entries.add(RAW_RUBY);

        entries.add(HEADPHONES);

        entries.add(BASIC_METAL_DETECTOR);
        entries.add(GOLD_METAL_DETECTOR);
        entries.add(DIAMOND_METAL_DETECTOR);
        entries.add(NETHERITE_METAL_DETECTOR);
    }

    public static void registerModItems() {
        MetalFinders.LOGGER.info("Registering Mod Items for " + MetalFinders.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MetalFinders.MOD_ID, name), item);


    }
}
