package com.kleinercode.fabric.deathmessages;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.CommonColors;
import net.minecraft.world.InteractionResult;

public class DeathMessages implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {

        // Event handler on player death
        OnPlayerDeathCallback.EVENT.register((source, player) -> {
            // Play oof death sound
            player.level().playSound(
                    player,
                    player.blockPosition(),
                    SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("kleinercode", "oof")),
                    SoundSource.PLAYERS
            );

            if (Utils.checkForBitchwhipper(source, true) || Utils.checkForBitchripper(source, false)) {
                // Bitchwhipping has happened, play sound
                player.level().playSound(
                        player,
                        player.blockPosition(),
                        SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("kleinercode", "whip")),
                        SoundSource.PLAYERS
                );
            }
            return InteractionResult.PASS;
        });

        // Event handler on LivingEntity death
        OnLivingEntityDeathCallback.EVENT.register(((source, killedEntity) -> {
            if (Utils.checkForBitchripper(source, false)) {
                // Bitchripping has happened, play sound
                killedEntity.level().playSound(
                        killedEntity,
                        killedEntity.blockPosition(),
                        SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("kleinercode", "whip")),
                        SoundSource.PLAYERS,
                        1f,
                        1f
                );
            }
            return InteractionResult.PASS;
        }));

        // Event handler on player death message death
        OnPlayerDeathMessageCallback.EVENT.register(((source, killedPlayer, text, mutable) -> {

            if (Utils.checkForBitchwhipper(source, false)) {
                // Player was bitchwhipped
                mutable.append(Component.literal( source.getMsgId() + " bitchwhipped " + killedPlayer.getName()));
            } else {
                mutable.append(text);
            }

            mutable.append(Component.literal(" at "));
            BlockPos pos = killedPlayer.blockPosition();
            MutableComponent mutable2 = Component.literal("[");
            mutable2.append(Component.literal(String.valueOf(pos.getX())));
            mutable2.append(Component.literal(", "));
            mutable2.append(Component.literal(String.valueOf(pos.getY())));
            mutable2.append(Component.literal(", "));
            mutable2.append(Component.literal(String.valueOf(pos.getZ())));
            mutable2.append(Component.literal("]"));

            mutable.append(mutable2.withStyle((style) -> {
                String tpCommand = "/tp " + pos.getX() + " " + pos.getY() + " " + pos.getZ();
                return style.withColor(CommonColors.BLUE)
                        .withHoverEvent(new HoverEvent.ShowText(Component.literal("Click to teleport")))
                        .withClickEvent(new ClickEvent.SuggestCommand(tpCommand));
            }));

            return InteractionResult.PASS;
        }));

    }
}
