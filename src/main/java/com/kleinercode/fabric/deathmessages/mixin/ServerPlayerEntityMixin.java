package com.kleinercode.fabric.deathmessages.mixin;

import com.kleinercode.fabric.deathmessages.OnPlayerDeathCallback;
import com.kleinercode.fabric.deathmessages.OnPlayerDeathMessageCallback;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerEntityMixin {

    @Inject(method = "die", at = @At(value = "HEAD"))
    private void onDeathSound(DamageSource source, CallbackInfo info) {
        ServerPlayer killedPlayer = (ServerPlayer)(Object)this;
        OnPlayerDeathCallback.EVENT.invoker().interact(source, killedPlayer);
    }

    // Adds custom death message text

    @Inject(method = "die", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;Lio/netty/channel/ChannelFutureListener;)V"))
    public void onDeath(DamageSource source, CallbackInfo info, @Local(ordinal = 0) LocalRef<Component> text) {

        ServerPlayer killedPlayer = (ServerPlayer)(Object)this;
        MutableComponent mutable = Component.empty();
        OnPlayerDeathMessageCallback.EVENT.invoker().interact(source, killedPlayer, text.get(), mutable);

        text.set(mutable);

    }

}
