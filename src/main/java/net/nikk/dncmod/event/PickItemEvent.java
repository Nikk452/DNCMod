package net.nikk.dncmod.event;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.nikk.dncmod.util.WeightManager;

public class PickItemEvent implements PickItemCallBack{
    @Override
    public ActionResult pickup(PlayerEntity player, ItemEntity itemEntity) {

        return WeightManager.canPlayerPickup(player,itemEntity.getStack()) ? ActionResult.PASS:ActionResult.FAIL;
    }
}
