package com.kleinercode.fabric.deathmessages.mixin;

import com.kleinercode.fabric.deathmessages.OnLivingEntityDeathCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract boolean shouldDropXp();

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeath(DamageSource source, CallbackInfo info) {
        LivingEntity killedEntity = (LivingEntity)(Object)this;
        OnLivingEntityDeathCallback.EVENT.invoker().interact(source, killedEntity);
    }

}
