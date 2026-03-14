package com.kleinercode.fabric.deathmessages;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface OnLivingEntityDeathCallback {

    /**
     * Callback when a living entity is killed (except players, they override this)
     * Upon return:
     *  - PASS falls back to further processing and defaults to SUCCESS if no other listeners are registered.
     *  - SUCCESS cancels further processing and continues with death logic
     * This event cannot be cancelled, that would break a lot of things
     */

    Event<OnLivingEntityDeathCallback> EVENT = EventFactory.createArrayBacked(OnLivingEntityDeathCallback.class,
            (listeners) -> (source, killedEntity) -> {
                for (OnLivingEntityDeathCallback listener : listeners) {
                    InteractionResult result = listener.interact(source, killedEntity);

                    if (result.equals(InteractionResult.PASS)) {
                        return result;
                    }
                }

                return InteractionResult.PASS;
            });

    InteractionResult interact(DamageSource source, LivingEntity killedEntity);

}
