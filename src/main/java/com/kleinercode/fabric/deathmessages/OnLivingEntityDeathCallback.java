package com.kleinercode.fabric.deathmessages;

import com.kleinercode.fabric.deathmessages.mixin.LivingEntityMixin;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;

public interface OnLivingEntityDeathCallback {

    /**
     * Callback when a living entity is killed (except players, they overide this)
     * Upon return:
     *  - PASS falls back to further processing and defaults to SUCCESS if no other listeners are registered.
     *  - SUCCESS cancels further processing and continues with death logic
     * This event cannot be cancelled, that would break a lot of things
     */

    Event<OnLivingEntityDeathCallback> EVENT = EventFactory.createArrayBacked(OnLivingEntityDeathCallback.class,
            (listeners) -> (source, killedEntity) -> {
                for (OnLivingEntityDeathCallback listener : listeners) {
                    ActionResult result = listener.interact(source, killedEntity);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.SUCCESS;
            });

    ActionResult interact(DamageSource source, LivingEntity killedEntity);

}
