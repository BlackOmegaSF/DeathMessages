package com.kleinercode.fabric.deathmessages;


import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public interface OnPlayerDeathCallback {

    /**
     * Callback when a player is killed while having a prime adversary
     * Upon return:
     *  - PASS falls back to further processing and defaults to SUCCESS if no other listeners are registered.
     *  - SUCCESS cancels further processing and continues with death logic
     * This event cannot be cancelled, that would break a lot of things
     */

    Event<OnPlayerDeathCallback> EVENT = EventFactory.createArrayBacked(OnPlayerDeathCallback.class,
            (listeners) -> (source, player) -> {
                for (OnPlayerDeathCallback listener : listeners) {
                    ActionResult result = listener.interact(source, player);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.SUCCESS;
            });

    ActionResult interact(DamageSource source, ServerPlayerEntity player);

}
