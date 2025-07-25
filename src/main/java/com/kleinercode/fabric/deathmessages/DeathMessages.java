package com.kleinercode.fabric.deathmessages;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class DeathMessages implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {

        // Event handler on player death
        OnPlayerDeathCallback.EVENT.register((source, player) -> {
            // Play oof death sound
            player.getWorld().playSound(
                    player,
                    player.getBlockPos(),
                    SoundEvent.of(Identifier.of("kleinercode", "oof")),
                    SoundCategory.PLAYERS
            );

            if (Utils.checkForBitchwhipper(source, true) || Utils.checkForBitchripper(source, false)) {
                // Bitchwhipping has happened, play sound
                player.getWorld().playSound(
                        player,
                        player.getBlockPos(),
                        SoundEvent.of(Identifier.of("kleinercode", "whip")),
                        SoundCategory.PLAYERS
                );
            }
            return ActionResult.PASS;
        });

        // Event handler on LivingEntity death
        OnLivingEntityDeathCallback.EVENT.register(((source, killedEntity) -> {
            if (Utils.checkForBitchripper(source, false)) {
                // Bitchripping has happened, play sound
                killedEntity.getWorld().playSound(
                        killedEntity,
                        killedEntity.getBlockPos(),
                        SoundEvent.of(Identifier.of("kleinercode", "whip")),
                        SoundCategory.PLAYERS,
                        1f,
                        1f
                );
            }
            return ActionResult.PASS;
        }));

        // Event handler on player death message death
        OnPlayerDeathMessageCallback.EVENT.register(((source, killedPlayer, text, mutable) -> {

            if (Utils.checkForBitchwhipper(source, false)) {
                // Player was bitchwhipped
                mutable.append(Text.literal( source.getName() + " bitchwhipped " + killedPlayer.getName()));
            } else {
                mutable.append(text);
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
                String tpCommand = "/tp " + pos.getX() + " " + pos.getY() + " " + pos.getZ();
                return style.withColor(Colors.BLUE)
                        .withHoverEvent(new HoverEvent.ShowText(Text.literal("Click to teleport")))
                        .withClickEvent(new ClickEvent.SuggestCommand(tpCommand));
            }));

            return ActionResult.PASS;
        }));

    }
}
