package com.kleinercode.fabric;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class Utils {

    public static boolean checkForBitchwhipper(LivingEntity entity, DamageSource source) {
        if (entity == null) return false;
        if (entity instanceof PlayerEntity) {
            ItemStack weapon = source.getWeaponStack();
            if (weapon != null) {
                return weapon.getName().toString().equalsIgnoreCase("The Bitchwhipper");
            }
        }
        return false;
    }

}
