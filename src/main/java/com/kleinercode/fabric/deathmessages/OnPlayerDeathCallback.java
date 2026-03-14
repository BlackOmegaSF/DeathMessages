package com.kleinercode.fabric.deathmessages;


import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;

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
                    InteractionResult result = listener.interact(source, player);

                    if (result != InteractionResult.PASS) {
                        return result;
                    }
                }

                return InteractionResult.SUCCESS;
            });

    InteractionResult interact(DamageSource source, ServerPlayer player);

}
