package net.nikk.dncmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.util.AttributeData;
import net.nikk.dncmod.util.IEntityDataSaver;

public class FinishCreationC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // Everything here happens ONLY on the Server!
        NbtCompound res_nbt = buf.readNbt();
        server.execute(()->{
            NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();
            if(!nbt.getBoolean("created")){
                // Play the ding sound
                // actually add stats to the player
                nbt.putString("first_name",res_nbt.getString("first_name"));
                nbt.putString("last_name",res_nbt.getString("last_name"));
                String race = res_nbt.getString("race");
                nbt.putString("race",race);
                nbt.putInt("total_level",0);
                nbt.putInt("experience",0);
                nbt.putInt("max_experience",500);
                nbt.putInt("max_mana",0);
                nbt.putInt("mana",0);
                nbt.putInt("max_ki",0);
                nbt.putInt("ki",0);
                int[] index = res_nbt.getIntArray("index");
                int[] stats = nbt.getIntArray("stats");
                int[] new_stats = {stats[index[0]],stats[index[1]],stats[index[2]],stats[index[3]],stats[index[4]],stats[index[5]]};
                int extra_stat = res_nbt.getInt("extra_stat");
                if(race.equals("Elf")) new_stats[extra_stat] +=2;
                nbt.putIntArray("stats",new_stats);
                int[] classes = {-1,-1,-1,-1,-1,-1};
                String[] class_names = {"Fighter","Wizard","Druid","Cleric","Sorcerer","Monk"};
                String class_name = res_nbt.getString("class_name");
                for(int z=0;z<6;z++) if(class_names[z].equals(class_name)) classes[z]=0;
                nbt.putIntArray("classes",classes);
                nbt.putIntArray("sub_classes",classes);
                int[] skills = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};;
                int[] skills_index = res_nbt.getIntArray("skills");
                for(int i=0;i<4;i++) skills[skills_index[i]] = 0;
                nbt.putIntArray("skills",skills);
                nbt.putIntArray("throws_proficiency",new int[]{0,0,0,0,0,0});
                int[] stat_mod = {0,0,0,0,0,0};
                for(int x=0;x<6;x++) stat_mod[x] = ((Math.max(new_stats[x], 10))-10)/2;
                nbt.putIntArray("stat_mod",stat_mod);
                nbt.putIntArray("stat_throws",new int[]{stat_mod[0],stat_mod[1],stat_mod[2],stat_mod[3],stat_mod[4],stat_mod[5]});
                nbt.putInt("hit_dice",0);
                nbt.putInt("proficiency_modifier",0);
                nbt.putString("gender", "Male");
                nbt.putBoolean("created",true);
                nbt.putIntArray("hit_dices", new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
                int con_health = race.equals("Dwarf")?(new_stats[2])*3/2-4:new_stats[2]-4;
                nbt.putInt("con_health_boost",con_health);
                AttributeData.addHealth(player,con_health,"con_health_boost","80b3a28a-42cd-4926-8327-91e75ab0191f");
                nbt.putBoolean("celestial",true);
            }
            ServerPlayNetworking.send(player, Networking.CREATION_SYNC_ID, PacketByteBufs.create().writeNbt(nbt));
        });
    }
}
