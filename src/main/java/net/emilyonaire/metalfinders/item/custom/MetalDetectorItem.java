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

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

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

                if (isValuableBlock(blockState)) {
                    outputValuableCoordinates(currentPos, player, blockState.getBlock(), context.getStack().getRarity().asString());
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

    private void outputValuableCoordinates(BlockPos blockPos, PlayerEntity player, Block block, String rarity){
        ItemStack helmetStack = player.getEquippedStack(EquipmentSlot.HEAD);
        if(rarity != "common"){
            if(!helmetStack.isEmpty() && helmetStack.isOf(ModItems.HEADPHONES)){
//                Items.DIAMOND_HELMET
                //check to see if the player has headphones on (this is a custom item, for now just check if truthy)
                player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at " + "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"), true);

            }else{
                Text msg = Text.literal("You need headphones for this tier of detector!");

                player.sendMessage(msg, true);
            }

        } else {
            player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at " + "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"), true);
        }


    }
}
