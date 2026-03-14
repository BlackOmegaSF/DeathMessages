package com.kleinercode.fabric.deathmessages.mixin;

import com.kleinercode.fabric.deathmessages.OnLivingEntityDeathCallback;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "die", at = @At("HEAD"))
    private void onDeath(DamageSource source, CallbackInfo info) {
        LivingEntity killedEntity = (LivingEntity)(Object)this;
        OnLivingEntityDeathCallback.EVENT.invoker().interact(source, killedEntity);
    }

}
