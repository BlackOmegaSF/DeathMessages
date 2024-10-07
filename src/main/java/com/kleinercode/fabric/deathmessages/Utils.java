package com.kleinercode.fabric.deathmessages;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static com.kleinercode.fabric.deathmessages.Utils.Constants.THE_BITCHRIPPER;
import static com.kleinercode.fabric.deathmessages.Utils.Constants.THE_BITCHWHIPPER;

public class Utils {

    public static boolean checkForBitchwhipper(DamageSource source, boolean onlyPlayer) {
        Entity killer = source.getAttacker();
        if (!checkIfPlayer(killer) && onlyPlayer) return false;
        ItemStack weapon = source.getWeaponStack();
        if (weapon != null) {
            Text weaponName = weapon.getName();
            if (weaponName.contains(THE_BITCHWHIPPER)) return true;
            for (Text sibling : weaponName.getSiblings()) {
                return sibling.contains(THE_BITCHWHIPPER);
            }
            return false;
        }
        return false;
    }

    public static boolean checkForBitchripper(DamageSource source, boolean onlyPlayer) {
        Entity killer = source.getAttacker();
        if (!checkIfPlayer(killer) && onlyPlayer) return false;
        ItemStack weapon = source.getWeaponStack();
        if (weapon != null) {
            Text weaponName = weapon.getName();
            if (weaponName.contains(THE_BITCHRIPPER)) return true;
            for (Text sibling : weaponName.getSiblings()) {
                return sibling.contains(THE_BITCHRIPPER);
            }
            return false;
        }
        return false;
    }

    private static boolean checkIfPlayer(Entity entity) {
        if (entity instanceof PlayerEntity) return true;
        return entity instanceof ServerPlayerEntity;
    }

    public static class Constants {
        public static Text THE_BITCHWHIPPER = Text.of("The Bitchwhipper");
        public static Text THE_BITCHRIPPER = Text.of("The Bitchripper");
    }

}
