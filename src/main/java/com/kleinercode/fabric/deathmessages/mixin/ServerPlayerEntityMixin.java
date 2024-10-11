package com.kleinercode.fabric.deathmessages.mixin;

import com.kleinercode.fabric.deathmessages.OnPlayerDeathCallback;
import com.kleinercode.fabric.deathmessages.OnPlayerDeathMessageCallback;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Inject(method = "onDeath", at = @At(value = "HEAD"))
    private void onDeathSound(DamageSource source, CallbackInfo info) {
        ServerPlayerEntity killedPlayer = (ServerPlayerEntity)(Object)this;
        OnPlayerDeathCallback.EVENT.invoker().interact(source, killedPlayer);
    }

    // Adds custom death message text

    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V"))
    public void onDeath(DamageSource source, CallbackInfo info, @Local(ordinal = 0) LocalRef<Text> text) {

        ServerPlayerEntity killedPlayer = (ServerPlayerEntity)(Object)this;
        MutableText mutable = Text.empty();
        OnPlayerDeathMessageCallback.EVENT.invoker().interact(source, killedPlayer, text.get(), mutable);

        text.set(mutable);

    }

}
