package net.emilyonaire.metalfinders;


import net.emilyonaire.metalfinders.item.ModItemGroups;
import net.emilyonaire.metalfinders.item.ModItems;
import net.emilyonaire.metalfinders.item.custom.ModArmorMaterials;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetalFinders implements ModInitializer {

	public static final String MOD_ID = "metal-finders";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {


		LOGGER.info("Hello Fabric world! Registering!");
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
	}
}