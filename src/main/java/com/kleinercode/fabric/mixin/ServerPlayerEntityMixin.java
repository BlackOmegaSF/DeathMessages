package com.kleinercode.fabric.mixin;

import com.kleinercode.fabric.Utils;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    // Play custom oof sound on death

    @Inject(method = "onDeath", at = @At(value = "HEAD"))
    private void onDeathSound(DamageSource source, CallbackInfo info) {
        ServerPlayerEntity killedPlayer = (ServerPlayerEntity)(Object)this;
        killedPlayer.getServerWorld().playSound(
                killedPlayer,
                killedPlayer.getBlockPos(),
                SoundEvent.of(Identifier.of("custom", "oof")),
                SoundCategory.PLAYERS
        );
    }

    // Adds custom death message text

    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V"))
    public void onDeath(DamageSource source, CallbackInfo info, @Local(ordinal = 0) LocalRef<Text> text) {

        MutableText mutable = (MutableText) text.get();
        ServerPlayerEntity killedPlayer = (ServerPlayerEntity)(Object)this;
        LivingEntity entity = killedPlayer.getPrimeAdversary();
        if (Utils.checkForBitchwhipper(entity, source)) {
            // Player was bitchwhipped
            mutable = Text.literal( entity.getName() + " bitchwhipped " + killedPlayer.getName());
        }

        mutable.append(Text.literal(" at "));
        BlockPos pos = killedPlayer.getBlockPos();
        MutableText mutable2 = Text.literal("[");
        mutable2.append(Text.literal(String.valueOf(pos.getX())));
        mutable2.append(Text.literal(", "));
        mutable2.append(Text.literal(String.valueOf(pos.getY())));
        mutable2.append(Text.literal(", "));
        mutable2.append(Text.literal(String.valueOf(pos.getZ())));
        mutable2.append(Text.literal("]"));

        mutable.append(mutable2.styled((style) -> {
            String tpCommand = "tp " + pos.getX() + " " + pos.getY() + " " + pos.getZ();
            return style.withHoverEvent(
                    new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to teleport")))
                    .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, tpCommand));
        }));

        text.set(mutable);

    }

    // Play custom bitchwhipped sound upon death

    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;onKilledBy(Lnet/minecraft/entity/LivingEntity;)V"))
    public void beforeOnKilledBy(DamageSource source, CallbackInfo info, @Local(ordinal = 0)LivingEntity entity) {

        if (Utils.checkForBitchwhipper(entity, source)) {
            // Bitchwhipping has happened, play sound
            ServerPlayerEntity killedPlayer = (ServerPlayerEntity)(Object)this;
            killedPlayer.getServerWorld().playSound(
                    killedPlayer,
                    killedPlayer.getBlockPos(),
                    SoundEvent.of(Identifier.of("custom", "whip")),
                    SoundCategory.PLAYERS
            );
        }

    }

}
