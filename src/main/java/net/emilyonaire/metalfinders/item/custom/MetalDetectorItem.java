package net.emilyonaire.metalfinders.item.custom;

import net.emilyonaire.metalfinders.MetalFinders;
import net.emilyonaire.metalfinders.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import net.minecraft.sound.SoundEvents;

import java.util.ArrayList;
import java.util.List;


public class MetalDetectorItem extends Item {

    List<Block> common = new ArrayList<>();
    List<Block> uncommon = new ArrayList<>();
    List<Block> rare = new ArrayList<>();
    List<Block> epic = new ArrayList<>();
    List<Block> denylist = new ArrayList<>();

    public MetalDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {


        // Rarity 1 blocks are common
        common.add(Blocks.IRON_ORE);
        common.add(Blocks.DEEPSLATE_IRON_ORE);
        common.add(Blocks.COAL_ORE);
        common.add(Blocks.DEEPSLATE_COAL_ORE);
        common.add(Blocks.COPPER_ORE);
        common.add(Blocks.DEEPSLATE_COPPER_ORE);

        for (Block block : common) {
            MetalFinders.LOGGER.info("Adding block: " + block + "to common");
        }

        // Rarity 2 blocks are uncommon
        uncommon.add(Blocks.LAPIS_ORE);
        uncommon.add(Blocks.DEEPSLATE_LAPIS_ORE);
        uncommon.add(Blocks.REDSTONE_ORE);
        uncommon.add(Blocks.DEEPSLATE_REDSTONE_ORE);
        uncommon.add(Blocks.IRON_BLOCK);
        uncommon.add(Blocks.COAL_BLOCK);
        uncommon.add(Blocks.GILDED_BLACKSTONE);
        for (Block block : uncommon) {
            MetalFinders.LOGGER.info("Adding block: " + block + "to uncommon");
        }

        // Rarity 3 blocks are rare
        rare.add(Blocks.GOLD_ORE);
        rare.add(Blocks.DEEPSLATE_GOLD_ORE);
        rare.add(Blocks.EMERALD_ORE);
        rare.add(Blocks.DEEPSLATE_EMERALD_ORE);
        rare.add(Blocks.DIAMOND_ORE);
        rare.add(Blocks.DEEPSLATE_DIAMOND_ORE);
        rare.add(Blocks.LAPIS_BLOCK);
        rare.add(Blocks.REDSTONE_BLOCK);
        rare.add(Blocks.GOLD_BLOCK);
        rare.add(Blocks.WET_SPONGE);

        for (Block block : rare) {
            MetalFinders.LOGGER.info("Adding block: " + block + "to rare");
        }

// Rarity 4 blocks are epic
        epic.add(Blocks.DIAMOND_BLOCK);
        epic.add(Blocks.NETHERITE_BLOCK);
        epic.add(Blocks.ANCIENT_DEBRIS);
        epic.add(Blocks.EMERALD_BLOCK);

        //denylist
        denylist.add(Blocks.CHEST);
        denylist.add(Blocks.TRAPPED_CHEST);
        denylist.add(Blocks.BARREL);
        denylist.add(Blocks.BEACON);
        denylist.add(Blocks.FURNACE);
        denylist.add(Blocks.HOPPER);
        denylist.add(Blocks.SPONGE);
        denylist.add(Blocks.OBSIDIAN);
        denylist.add(Blocks.CRYING_OBSIDIAN);



        for (Block block : epic) {
            MetalFinders.LOGGER.info("Adding block: " + block + "to epic");
        }

        //FUNCTIONALITY!
        if(!context.getWorld().isClient()){ //if on server
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;
            int rarityOffset = 8;
            int startPos_y = positionClicked.getY();
            int depthToScanTo = startPos_y;

            switch (context.getStack().getRarity().asString()) {
                case "common":
                    depthToScanTo -= rarityOffset;
                    break;
                case "uncommon":
                    depthToScanTo -= rarityOffset * 2;
                    break;
                case "rare":
                    depthToScanTo -= rarityOffset * 3;
                    break;
                case "epic":
                    depthToScanTo -= rarityOffset * 4;
                    break;
            }

            // Scan from the player's clicked position down to the determined depth
            for (int y = startPos_y-1; y >= depthToScanTo; y--) {
                BlockPos currentPos = new BlockPos(positionClicked.getX(), y, positionClicked.getZ());
                BlockState blockState = context.getWorld().getBlockState(currentPos);

                String detectorRarity = context.getStack().getRarity().asString();
                String foundRarity = compareRarity(detectorRarity, blockState);
                if(foundRarity != "none"){
                    if(foundRarity == "deny"){
                        context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 3f, 1f);
                        break;
                    }

                    MetalFinders.LOGGER.info("compareRarity passed, checking headphones");
                    Block block = blockState.getBlock();
                    //if wearing headphones
                    ItemStack helmetStack = player.getEquippedStack(EquipmentSlot.HEAD);
                    if(detectorRarity != "common"){
                        MetalFinders.LOGGER.info("detector rarity not common check has passed, checking headphones");
                        if(!helmetStack.isEmpty() && helmetStack.isOf(ModItems.HEADPHONES)){
                            MetalFinders.LOGGER.info(detectorRarity);
                            if(detectorRarity != "epic"){
                                player.sendMessage(Text.literal("Found something!"), true);
                                //play basic ding sound
                                if(!context.getWorld().isClient) {
                                    context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.PLAYERS, 5f, 1f);
                                }


                                break;
                            } else if (detectorRarity == "epic")
                            {
                                player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at " + "(" + currentPos.getX() + ", " + currentPos.getY() + ", " + currentPos.getZ() + ")"), true);
                                player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at " + "(" + currentPos.getX() + ", " + currentPos.getY() + ", " + currentPos.getZ() + ")"), false);


                                //play sound based on rarity of found block
                                switch (foundRarity) {
                                    case "common":
                                        context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.PLAYERS, 5f, 1f);
                                        player.sendMessage(Text.literal("COMMON RARITY"), false);
                                        break;
                                    case "uncommon":
                                        context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.PLAYERS, 5f, 1.25f);
                                        player.sendMessage(Text.literal("UN-COMMON RARITY"), false);
                                        break;
                                    case "rare":
                                        context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.PLAYERS, 5f, 1.5f);
                                        player.sendMessage(Text.literal("RARE RARITY"), false);
                                        break;
                                    case "epic":
                                        context.getWorld().playSound(null, context.getPlayer().getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), SoundCategory.PLAYERS, 5f, 2f);
                                        player.sendMessage(Text.literal("EPIC RARITY"), false);
                                        break;
                                }

                                break;
                            }
                        }
                    } else {
                        MetalFinders.LOGGER.info("detector rarity is common, no need to check headphones");
                        player.sendMessage(Text.literal("Found something!"), true);
                    }

                    break;
                }

            }


        }




        return ActionResult.SUCCESS;
    }


    public String compareRarity(String rarity, BlockState blockState){
        String result = "none";
        MetalFinders.LOGGER.info("compareRarity");

        //if in blocklist, then cancel search
        if(denylist.contains(blockState.getBlock())){result = "deny";MetalFinders.LOGGER.info("YES IN DENYLIST");}

        MetalFinders.LOGGER.info("Comparing " + rarity + " with blockstate.getBlock() " + blockState.getBlock().getName().getString());
        switch (rarity) { // will always have one of these 4 so dont need a default state.
            case "common":
                if(common.contains(blockState.getBlock())){result = "common";MetalFinders.LOGGER.info("YES COMMON");}
                break;
            case "uncommon":
                if(common.contains(blockState.getBlock())){result = "common";MetalFinders.LOGGER.info("YES COMMON");}
                if(uncommon.contains(blockState.getBlock())){result = "uncommon";MetalFinders.LOGGER.info("YES UNCOMMON");}
                break;
            case "rare":
                if(common.contains(blockState.getBlock())){result = "common";MetalFinders.LOGGER.info("YES COMMON");}
                if(uncommon.contains(blockState.getBlock())){result = "uncommon";MetalFinders.LOGGER.info("YES UNCOMMON");}
                if(rare.contains(blockState.getBlock())){result = "rare";MetalFinders.LOGGER.info("YES RARE");}
                break;
            case "epic":
                if(common.contains(blockState.getBlock())){result = "common";MetalFinders.LOGGER.info("YES COMMON");}
                if(uncommon.contains(blockState.getBlock())){result = "uncommon";MetalFinders.LOGGER.info("YES UNCOMMON");}
                if(rare.contains(blockState.getBlock())){result = "rare";MetalFinders.LOGGER.info("YES RARE");}
                if(epic.contains(blockState.getBlock())){result = "epic";MetalFinders.LOGGER.info("YES EPIC");}
                break;
        }
        MetalFinders.LOGGER.info(String.valueOf(result));
        return result;
    }
}
