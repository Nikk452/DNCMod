package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nikk.dncmod.util.ExperienceData;
import net.nikk.dncmod.util.IEntityDataSaver;

public class MineBlockEvent implements PlayerBlockBreakEvents.After{
    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        NbtCompound nbtCompound = ((IEntityDataSaver)(player)).getPersistentData();
        if(nbtCompound.getBoolean("created")){
            if(state.getBlock() instanceof ExperienceDroppingBlock) ExperienceData.addExperience((ServerWorld) world,player,5);
            else if(state.getBlock() instanceof CropBlock) if(((CropBlock)(state.getBlock())).isMature(state)){
                ExperienceData.addExperience((ServerWorld) world,player,1);
            }
        }

    }
}
