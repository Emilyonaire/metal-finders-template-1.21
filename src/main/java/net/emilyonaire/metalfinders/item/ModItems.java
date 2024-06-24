package net.emilyonaire.metalfinders.item;

import net.emilyonaire.metalfinders.MetalFinders;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.intellij.lang.annotations.Identifier;

public class ModItems {
    public static void registerModItems() {
        MetalFinders.LOGGER.info("Registering Mod Items for " + MetalFinders.MOD_ID);

    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MetalFinders.MOD_ID, name), item);
        //what the fuck why doesnt this work???
        //https://www.youtube.com/watch?v=5ms6RiR4SQ4&list=PLKGarocXCE1EO43Dlf5JGh7Yk-kRAXUEJ&index=2
    }
}
