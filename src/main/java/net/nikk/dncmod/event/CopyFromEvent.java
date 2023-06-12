package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nikk.dncmod.util.AttributeData;
import net.nikk.dncmod.util.ExperienceData;
import net.nikk.dncmod.util.IEntityDataSaver;

public class CopyFromEvent implements ServerPlayerEvents.CopyFrom{
    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        NbtCompound res_nbt = ((IEntityDataSaver)oldPlayer).getPersistentData();
        if(res_nbt.getBoolean("celestial")) if(res_nbt.getInt("total_level")>0){
            NbtCompound nbt2 = ((IEntityDataSaver)newPlayer).getPersistentData();
            nbt2.putString("first_name", res_nbt.getString("first_name"));
            nbt2.putString("last_name", res_nbt.getString("last_name"));
            nbt2.putString("race", res_nbt.getString("race"));
            nbt2.putInt("total_level", res_nbt.getInt("total_level"));
            nbt2.putInt("experience", res_nbt.getInt("experience"));
            nbt2.putInt("max_experience", res_nbt.getInt("max_experience"));
            nbt2.putInt("max_mana", res_nbt.getInt("max_mana"));
            nbt2.putInt("mana", res_nbt.getInt("mana"));
            nbt2.putInt("max_ki", res_nbt.getInt("max_ki"));
            nbt2.putInt("ki", res_nbt.getInt("ki"));
            nbt2.putIntArray("stats", res_nbt.getIntArray("stats"));
            nbt2.putIntArray("classes", res_nbt.getIntArray("classes"));
            nbt2.putIntArray("sub_classes", res_nbt.getIntArray("sub_classes"));
            nbt2.putIntArray("skills", res_nbt.getIntArray("skills"));
            nbt2.putIntArray("throws_proficiency", res_nbt.getIntArray("throws_proficiency"));
            nbt2.putIntArray("stat_mod", res_nbt.getIntArray("stat_mod"));
            nbt2.putIntArray("stat_throws", res_nbt.getIntArray("stat_throws"));
            nbt2.putInt("hit_dice", res_nbt.getInt("hit_dice"));
            nbt2.putInt("proficiency_modifier", res_nbt.getInt("proficiency_modifier"));
            nbt2.putString("gender", res_nbt.getString("gender"));
            nbt2.putBoolean("created", res_nbt.getBoolean("created"));
            nbt2.putIntArray("hit_dices", res_nbt.getIntArray("hit_dices"));
            nbt2.putInt("con_health_boost",res_nbt.getInt("con_health_boost"));
            nbt2.putBoolean("celestial",res_nbt.getBoolean("celestial"));
            ExperienceData.LevelDown(newPlayer, AttributeData.getIndexOfLargest(nbt2.getIntArray("classes")));
        }
    }
}
