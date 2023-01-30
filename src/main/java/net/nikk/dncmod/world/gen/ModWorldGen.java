package net.nikk.dncmod.world.gen;

public class ModWorldGen {
    public static void generateWorldGen(){
        ModOreGeneration.generateOres();
        ModEntitySpawn.AddEntitySpawn();
    }
}
