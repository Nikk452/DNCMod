package net.nikk.dncmod.util;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nikk.dncmod.DNCMod;

import java.util.UUID;

public class AttributeData {
    public static void addHealth(ServerPlayerEntity player,int amount, String name, String uuid){
        EntityAttributeModifier STAT_HEALTH_BOOST = new EntityAttributeModifier(UUID.fromString(uuid),
                DNCMod.MOD_ID+":" +name, amount, EntityAttributeModifier.Operation.ADDITION);
        EntityAttributeInstance health = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if(health!=null && !health.hasModifier(STAT_HEALTH_BOOST)){
            health.addPersistentModifier(STAT_HEALTH_BOOST);
            player.setHealth(player.getMaxHealth());
        }
    }
    public static void removeHealth(ServerPlayerEntity player,int amount, String name, String uuid){
        EntityAttributeInstance health = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        EntityAttributeModifier STAT_HEALTH_BOOST = new EntityAttributeModifier(UUID.fromString(uuid),
                DNCMod.MOD_ID+":"+name, amount, EntityAttributeModifier.Operation.ADDITION);
        if (health != null && health.hasModifier(STAT_HEALTH_BOOST)) {
            health.removeModifier(STAT_HEALTH_BOOST);
            if (player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
        }
    }
    public static void setHealth(ServerPlayerEntity player,int amount,int new_amount, String name, String uuid){
        EntityAttributeInstance health = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        EntityAttributeModifier STAT_HEALTH_BOOST = new EntityAttributeModifier(UUID.fromString(uuid),
                DNCMod.MOD_ID+":"+name, amount, EntityAttributeModifier.Operation.ADDITION);
        EntityAttributeModifier STAT_HEALTH_BOOST2 = new EntityAttributeModifier(UUID.fromString(uuid),
                DNCMod.MOD_ID+":"+name, new_amount, EntityAttributeModifier.Operation.ADDITION);
        if (health != null) {
            if(health.hasModifier(STAT_HEALTH_BOOST)) health.removeModifier(STAT_HEALTH_BOOST);
            health.addPersistentModifier(STAT_HEALTH_BOOST2);
            player.setHealth(player.getMaxHealth());
        }
    }
    public static void setMovementSpeed(ServerPlayerEntity player,double amount,double new_amount, String name, String uuid){
        EntityAttributeInstance speed = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        double default_speed = speed.getBaseValue();
        EntityAttributeModifier STAT_SPEED_BOOST = new EntityAttributeModifier(UUID.fromString(uuid),
                DNCMod.MOD_ID+":"+name, amount*default_speed, EntityAttributeModifier.Operation.ADDITION);
        EntityAttributeModifier STAT_SPEED_BOOST2 = new EntityAttributeModifier(UUID.fromString(uuid),
                DNCMod.MOD_ID+":"+name, new_amount*default_speed, EntityAttributeModifier.Operation.ADDITION);
        if (speed != null) {
            if(speed.hasModifier(STAT_SPEED_BOOST)) speed.removeModifier(STAT_SPEED_BOOST);
            speed.addPersistentModifier(STAT_SPEED_BOOST2);
        }
    }
    public static void setSlowness(ServerPlayerEntity player,double amount,double new_amount, String name, String uuid){
        EntityAttributeInstance speed = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        double default_speed = speed.getBaseValue();
        EntityAttributeModifier STAT_SPEED_BOOST = new EntityAttributeModifier(UUID.fromString(uuid),
                DNCMod.MOD_ID+":"+name, amount*default_speed, EntityAttributeModifier.Operation.ADDITION);
        EntityAttributeModifier STAT_SPEED_BOOST2 = new EntityAttributeModifier(UUID.fromString(uuid),
                DNCMod.MOD_ID+":"+name, new_amount*default_speed, EntityAttributeModifier.Operation.ADDITION);
        if (speed != null) {
            if(speed.hasModifier(STAT_SPEED_BOOST)) speed.removeModifier(STAT_SPEED_BOOST);
            speed.addPersistentModifier(STAT_SPEED_BOOST2);
        }
    }
    public static int getIndexOfLargest(int[] array)
    {
        if ( array == null || array.length == 0 ) return -1;

        int largest = 0;
        for ( int i = 1; i < array.length; i++ )
        {
            if ( array[i] > array[largest] ) largest = i;
        }
        return largest;
    }
}
