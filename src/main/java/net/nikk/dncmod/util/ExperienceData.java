package net.nikk.dncmod.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.networking.Networking;

import java.util.Random;

public class ExperienceData {

    public static void LevelUp(ServerPlayerEntity player,int class_type){
        NbtCompound nbt = ((IEntityDataSaver)player).getPersistentData();
        NbtCompound nbt_res = new NbtCompound();
        nbt_res.putInt("experience",nbt.getInt("experience"));
        if(nbt.getInt("level")<5) if(nbt.getInt("experience")>=nbt.getInt("max_experience")){
            int[] classes = nbt.getIntArray("classes");
            int[] dices = nbt.getIntArray("hit_dices");
            classes[class_type]= classes[class_type]>=0?classes[class_type]+1:-1;
            int total_level = 0;
            for(int i:classes){if (i>=0) total_level+=i;}
            dices[total_level-1]=RollHPDice(class_type,total_level);
            nbt.putIntArray("classes",classes);
            nbt.putIntArray("hit_dices",dices);
            nbt.putInt("hit_dice",dices[total_level-1]);
            nbt.putInt("total_level", total_level);
            nbt.putInt("experience",nbt.getInt("experience")-nbt.getInt("max_experience"));
            nbt.putInt("max_experience",nbt.getInt("max_experience")*2);
            nbt.putInt("proficiency_modifier",(total_level+1)/5);
            nbt_res.putIntArray("classes",classes);
            nbt_res.putIntArray("hit_dices",dices);
            nbt_res.putInt("total_level", total_level);
            nbt_res.putInt("experience", nbt.getInt("experience"));
            nbt_res.putInt("max_experience", nbt.getInt("max_experience"));
            nbt_res.putInt("proficiency_modifier", nbt.getInt("proficiency_modifier"));
            nbt_res.putBoolean("leveled",true);
            classMovementSpeed(player,class_type,total_level,false);
            AttributeData.setHealth(player,total_level>1?(total_level-1)*nbt.getInt("con_health_boost"):nbt.getInt("con_health_boost"),total_level*nbt.getInt("con_health_boost"),"con_health_boost","80b3a28a-42cd-4926-8327-91e75ab0191f");
            AttributeData.setHealth(player,total_level>=2?dices[total_level-2]:1,dices[total_level-1],"dice_health","f00905f3-63f1-4dd4-3977-dea03d419d5d");
            }
        ServerPlayNetworking.send(player, Networking.LEVELUPS2C, PacketByteBufs.create().writeNbt(nbt_res));
    }
    public static void LevelDown(ServerPlayerEntity player,int class_type){
        NbtCompound nbt = ((IEntityDataSaver)player).getPersistentData();
        NbtCompound nbt_res = new NbtCompound();
        int[] classes = nbt.getIntArray("classes");
        int[] dices = nbt.getIntArray("hit_dices");
        classes[class_type] = classes[class_type] > 0 ? classes[class_type] - 1 : -1;
        int total_level = 0;
        for (int i : classes) {
            if (i >= 0) total_level += i;
        }
        nbt.putIntArray("classes", classes);
        nbt.putIntArray("hit_dices", dices);
        nbt.putInt("hit_dice", total_level>0?dices[total_level - 1]:0);
        nbt.putInt("total_level", total_level);
        nbt.putInt("max_experience", nbt.getInt("max_experience") / 2);
        nbt.putInt("proficiency_modifier", (total_level + 1) / 5);
        nbt_res.putIntArray("classes", classes);
        nbt_res.putInt("total_level", total_level);
        nbt_res.putInt("max_experience", nbt.getInt("max_experience"));
        nbt_res.putInt("proficiency_modifier", nbt.getInt("proficiency_modifier"));
        nbt_res.putBoolean("leveled", false);
        classMovementSpeed(player, class_type, total_level, true);
        AttributeData.setHealth(player, (total_level+1) * nbt.getInt("con_health_boost"), total_level>0? total_level * nbt.getInt("con_health_boost"):nbt.getInt("con_health_boost"), "con_health_boost", "80b3a28a-42cd-4926-8327-91e75ab0191f");
        AttributeData.setHealth(player, dices[total_level], total_level>=1?dices[total_level - 1]:0, "dice_health", "f00905f3-63f1-4dd4-3977-dea03d419d5d");
        dices[total_level] = 0;
        nbt.putIntArray("hit_dices",dices);
        nbt_res.putIntArray("hit_dices", dices);
        ServerPlayNetworking.send(player, Networking.LEVELUPS2C, PacketByteBufs.create().writeNbt(nbt_res));
    }
    public static void addExperience(ServerWorld world, LivingEntity livingEntity, int amount){
        NbtCompound nbt = ((IEntityDataSaver)livingEntity).getPersistentData();
        if(nbt.getBoolean("created")) {
            nbt.putInt("experience", nbt.getInt("experience") + amount);
            if (livingEntity instanceof PlayerEntity) {
                NbtCompound nbt_res = new NbtCompound();
                nbt_res.putInt("experience", nbt.getInt("experience"));
                ServerPlayNetworking.send((ServerPlayerEntity) livingEntity, Networking.LEVELUPS2C, PacketByteBufs.create().writeNbt(nbt_res));
            }
        }
    }
    public static int RollHPDice(int class_type,int level) {
        Random random = new Random();
        if (level==0){
            switch (class_type) {
                case 0 -> {return 10;}
                case 1, 4, 5 -> {return 4;}
                case 2 -> {return 8;}
                case 3 -> {return 6;}
                default -> {return 0;}
            }
        }
        else {
            switch (class_type) {
                case 0 -> {return random.nextInt(10)+1;}
                case 1, 4, 5 -> {return random.nextInt(4)+1;}
                case 2 -> {return random.nextInt(8)+1;}
                case 3 -> {return random.nextInt(6)+1;}
                default -> {return 0;}
            }
        }

    }
    private static void classMovementSpeed(ServerPlayerEntity player, int class_type, int total_level, boolean levelDown){
        if(levelDown){
            switch(class_type){
                case 0 -> AttributeData.setMovementSpeed(player,(total_level+1)*0.025,total_level*0.025,"fighter_movement_speed_bonus","18aca998-d739-4c3c-8afb-e875ca5cb615");
                case 2 -> AttributeData.setMovementSpeed(player,(total_level+1)*0.01,total_level*0.01,"druid_movement_speed_bonus","b899a106-ea11-46da-b9e5-b63e580a9521");
                case 5 -> AttributeData.setMovementSpeed(player,(total_level+1)*0.1,total_level*0.1,"monk_movement_speed_bonus","722e1a26-63a6-4b76-bb78-2ce8180b1183");
                default -> {}
            }
        }else{
            switch(class_type){
                case 0 -> AttributeData.setMovementSpeed(player,(total_level-1)*0.025,total_level*0.025,"fighter_movement_speed_bonus","18aca998-d739-4c3c-8afb-e875ca5cb615");
                case 2 -> AttributeData.setMovementSpeed(player,(total_level-1)*0.01,total_level*0.01,"druid_movement_speed_bonus","b899a106-ea11-46da-b9e5-b63e580a9521");
                case 5 -> AttributeData.setMovementSpeed(player,(total_level-1)*0.1,total_level*0.1,"monk_movement_speed_bonus","722e1a26-63a6-4b76-bb78-2ce8180b1183");
                default -> {}
            }
        }
    }
}
