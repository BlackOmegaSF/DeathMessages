package com.kleinercode.fabric.deathmessages;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

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
                    ActionResult result = listener.interact(source, killedPlayer, text, mutable);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.SUCCESS;
            });

    ActionResult interact(DamageSource source, ServerPlayerEntity killedPlayer, Text text, MutableText mutable);

}
