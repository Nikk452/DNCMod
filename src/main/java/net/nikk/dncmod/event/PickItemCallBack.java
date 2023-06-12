package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface PickItemCallBack {
    Event<PickItemCallBack> EVENT = EventFactory.createArrayBacked(PickItemCallBack.class,
            (listeners) -> (player, itemEntity) -> {
                for (PickItemCallBack listener : listeners) {
                    ActionResult result = listener.pickup(player, itemEntity);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult pickup(PlayerEntity player, ItemEntity itemEntity);
}
