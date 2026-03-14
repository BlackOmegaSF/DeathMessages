package com.kleinercode.fabric.deathmessages;

import static com.kleinercode.fabric.deathmessages.Utils.Constants.THE_BITCHRIPPER;
import static com.kleinercode.fabric.deathmessages.Utils.Constants.THE_BITCHWHIPPER;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Utils {

    public static boolean checkForBitchwhipper(DamageSource source, boolean onlyPlayer) {
        Entity killer = source.getEntity();
        if (!checkIfPlayer(killer) && onlyPlayer) return false;
        ItemStack weapon = source.getWeaponItem();
        if (weapon != null) {
            Component weaponName = weapon.getHoverName();
            if (weaponName.contains(THE_BITCHWHIPPER)) return true;
            for (Component sibling : weaponName.getSiblings()) {
                return sibling.contains(THE_BITCHWHIPPER);
            }
            return false;
        }
        return false;
    }

    public static boolean checkForBitchripper(DamageSource source, boolean onlyPlayer) {
        Entity killer = source.getEntity();
        if (!checkIfPlayer(killer) && onlyPlayer) return false;
        ItemStack weapon = source.getWeaponItem();
        if (weapon != null) {
            Component weaponName = weapon.getHoverName();
            if (weaponName.contains(THE_BITCHRIPPER)) return true;
            for (Component sibling : weaponName.getSiblings()) {
                return sibling.contains(THE_BITCHRIPPER);
            }
            return false;
        }
        return false;
    }

    private static boolean checkIfPlayer(Entity entity) {
        if (entity instanceof Player) return true;
        return entity instanceof ServerPlayer;
    }

    public static class Constants {
        public static final Component THE_BITCHWHIPPER = Component.nullToEmpty("The Bitchwhipper");
        public static final Component THE_BITCHRIPPER = Component.nullToEmpty("The Bitchripper");
    }

}
