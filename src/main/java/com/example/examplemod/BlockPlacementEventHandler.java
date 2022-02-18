package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "aoemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockPlacementEventHandler {
//    @SubscribeEvent
//    public static void placeCaveVines(BlockEvent.NeighborNotifyEvent event){
//        Block block=event.getState().getBlock();
//        BlockPos pos=event.getPos();
//        if(block== Blocks.CAVE_VINES||block==Blocks.CAVE_VINES_PLANT) {
//            if (event.getWorld().getBlockState(pos.above()).is(BlockTags.LEAVES)) {
//                event.setCanceled(true);
//            }
//        }
//    }
//    @SubscribeEvent
//    public static void placeCaveVines(BlockEvent.EntityPlaceEvent event){
//        Block block=event.getPlacedBlock()
//        BlockPos pos=event.getPos();
//        if(block== Blocks.CAVE_VINES||block==Blocks.CAVE_VINES_PLANT) {
//            if (event.getWorld().getBlockState(pos.above()).is(BlockTags.LEAVES)) {
//                event.setCanceled(true);
//            }
//        }
//    }
}
