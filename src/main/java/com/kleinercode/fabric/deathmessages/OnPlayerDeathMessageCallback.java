package com.kleinercode.fabric.deathmessages;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;

public interface OnPlayerDeathMessageCallback {

    /**
     * Callback after the server generates a player's death message, before it is sent or saved elsewhere.
     * The mutable object is initially empty and can be modified to change the death message.
     * Upon return:
     *  - PASS falls back to further processing and defaults to SUCCESS if no other listeners are registered.
     *  - SUCCESS cancels further processing and continues with death logic
     * This event cannot be cancelled, that would break a lot of things
     */

    Event<OnPlayerDeathMessageCallback> EVENT = EventFactory.createArrayBacked(OnPlayerDeathMessageCallback.class,
            (listeners) -> (source, killedPlayer, text, mutable) -> {
                for (OnPlayerDeathMessageCallback listener : listeners) {
                    InteractionResult result = listener.interact(source, killedPlayer, text, mutable);

                    if (result != InteractionResult.PASS) {
                        return result;
                    }
                }

                return InteractionResult.SUCCESS;
            });

    InteractionResult interact(DamageSource source, ServerPlayer killedPlayer, Component text, MutableComponent mutable);

}
