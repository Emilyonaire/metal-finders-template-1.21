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
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class MetalDetectorItem extends Item {



    public MetalDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        //setup
        //basic blocks
        List<Block> common = new ArrayList<>();
        List<Block> uncommon = new ArrayList<>();
        List<Block> rare = new ArrayList<>();
        List<Block> epic = new ArrayList<>();

        // Rarity 1 blocks are common
        common.add(Blocks.IRON_ORE);
        common.add(Blocks.DEEPSLATE_IRON_ORE);
        common.add(Blocks.COAL_ORE);
        common.add(Blocks.DEEPSLATE_COAL_ORE);
        common.add(Blocks.COPPER_ORE);
        common.add(Blocks.DEEPSLATE_COPPER_ORE);

        // Rarity 2 blocks are uncommon
        uncommon.add(Blocks.LAPIS_ORE);
        uncommon.add(Blocks.DEEPSLATE_LAPIS_ORE);
        uncommon.add(Blocks.REDSTONE_ORE);
        uncommon.add(Blocks.DEEPSLATE_REDSTONE_ORE);
        uncommon.add(Blocks.CHEST);
        uncommon.add(Blocks.TRAPPED_CHEST);
        uncommon.add(Blocks.BARREL);
        uncommon.add(Blocks.IRON_BLOCK);
        uncommon.add(Blocks.COAL_BLOCK);
        uncommon.add(Blocks.GILDED_BLACKSTONE);
        uncommon.add(Blocks.OBSIDIAN);

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

// Rarity 4 blocks are epic
        epic.add(Blocks.DIAMOND_BLOCK);
        epic.add(Blocks.NETHERITE_BLOCK);
        epic.add(Blocks.ANCIENT_DEBRIS);
        epic.add(Blocks.EMERALD_BLOCK);
        epic.add(Blocks.CRYING_OBSIDIAN);


        //FUNCTIONALITY!
        if(!context.getWorld().isClient()){ //if on server
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;
            int rarityOffset = 16;
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
            for (int y = startPos_y; y >= depthToScanTo; y--) {
                BlockPos currentPos = new BlockPos(positionClicked.getX(), y, positionClicked.getZ());
                BlockState blockState = context.getWorld().getBlockState(currentPos);

//                to be implemented
//                if(compareRarity())

                if (isValuableBlock(blockState)) {
                    outputValuableCoordinates(currentPos, player, blockState, context.getStack().getRarity().asString());
                    foundBlock = true;
                    break;
                }
            }
            if(!foundBlock){
                player.sendMessage(Text.literal("No Valuable Block Found!"), true);
            }

        }




        return ActionResult.SUCCESS;
    }
    //TO BE DEPRECATED, WORKING ON IT RN
    private boolean isValuableBlock(BlockState state){
        return state.isOf(Blocks.IRON_ORE) ||
                state.isOf(Blocks.DEEPSLATE_IRON_ORE) ||

                state.isOf(Blocks.DIAMOND_ORE) ||
                state.isOf(Blocks.DEEPSLATE_DIAMOND_ORE) ||

                state.isOf(Blocks.GOLD_ORE) ||
                state.isOf(Blocks.DEEPSLATE_GOLD_ORE) ||

                state.isOf(Blocks.EMERALD_ORE) ||
                state.isOf(Blocks.DEEPSLATE_EMERALD_ORE) ||

                state.isOf(Blocks.COPPER_ORE) ||
                state.isOf(Blocks.DEEPSLATE_COPPER_ORE) ||

                state.isOf(Blocks.COAL_ORE) ||
                state.isOf(Blocks.DEEPSLATE_COAL_ORE) ||

                state.isOf(Blocks.LAPIS_ORE) ||
                state.isOf(Blocks.DEEPSLATE_LAPIS_ORE) ||

                state.isOf(Blocks.REDSTONE_ORE) ||
                state.isOf(Blocks.DEEPSLATE_REDSTONE_ORE) ||

                state.isOf(Blocks.ANCIENT_DEBRIS)||
                state.isOf(Blocks.CHEST) ||
                state.isOf(Blocks.TRAPPED_CHEST) ||
                state.isOf(Blocks.BARREL) ||
                state.isOf(Blocks.IRON_BLOCK) ||
                state.isOf(Blocks.GOLD_BLOCK) ||
                state.isOf(Blocks.COAL_BLOCK) ||
                state.isOf(Blocks.DIAMOND_BLOCK) ||
                state.isOf(Blocks.NETHERITE_BLOCK) ||
                state.isOf(Blocks.EMERALD_BLOCK) ||
                state.isOf(Blocks.REDSTONE_BLOCK) ||
                state.isOf(Blocks.LAPIS_BLOCK) ||
                state.isOf(Blocks.GILDED_BLACKSTONE) ||
                state.isOf(Blocks.OBSIDIAN) ||
                state.isOf(Blocks.CRYING_OBSIDIAN) ||
                state.isOf(Blocks.WET_SPONGE);





    }

    private void outputValuableCoordinates(BlockPos blockPos, PlayerEntity player, BlockState blockstate, String rarity){
        Block block = blockstate.getBlock();
        ItemStack helmetStack = player.getEquippedStack(EquipmentSlot.HEAD);
        if(rarity != "common"){
            if(!helmetStack.isEmpty() && helmetStack.isOf(ModItems.HEADPHONES)){
//                Items.DIAMOND_HELMET
                player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at " + "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"), true);

            }else{
                Text msg = Text.literal("You need headphones for this tier of detector!");

                player.sendMessage(msg, true);
            }

        } else {
            player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at " + "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"), true);
        }


    }

    private boolean compareRarity(String rarity, BlockState blockState){
        switch (rarity) {
            case "common":

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

        return true;
    }
}
