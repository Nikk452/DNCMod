package net.nikk.dncmod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.nikk.dncmod.config.ModConfig;
import net.nikk.dncmod.util.HashMapOf;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOManager {
    public static void updateModConfig(ModConfig config){
        String gson = new GsonBuilder().setPrettyPrinting().create().toJson(config);
        File file = new File("./config/dungeons-and-crafting/config.json");
        fileWriter(file, gson);
    }

    public static void generateModConfig(){
        String gson = new GsonBuilder().setPrettyPrinting().create().toJson(new ModConfig(1,false, true, true,
                true, true, true, true, true,true,true));
        File file = new File("./config/dungeons-and-crafting/config.json");
        fileWriter(file, gson);
    }

    public static ModConfig readModConfig(){
        ModConfig config;
        try {
            config = new Gson().fromJson(new FileReader("./config/dungeons-and-crafting/config.json"), ModConfig.class);
        } catch (IOException e){
            e.printStackTrace();
            config = new ModConfig(1, false, true, true,
                    true, true, true, true, true,true,true);
        }
        return config;
    }

    public static void fileWriter(File file, String gson){
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(gson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void craftPaths(){
        try{
            if(!Files.isDirectory(Paths.get("./config"))){
                Files.createDirectory(Paths.get("./config"));
            }
            if(!Files.isDirectory(Paths.get("./config/dungeons-and-crafting"))){
                Files.createDirectory(Paths.get("./config/dungeons-and-crafting"));
            }
            if(!Files.exists(Paths.get("./config/dungeons-and-crafting/config.json"))){
                IOManager.generateModConfig();
            }
            DNCMod.CONFIG = IOManager.readModConfig();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}