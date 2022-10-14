package net.nikk.dncmod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.nikk.dncmod.config.ModConfig;
import net.nikk.dncmod.config.SystemTimeConfig;
import net.nikk.dncmod.config.TimeDataStorage;
import net.nikk.dncmod.util.HashMapOf;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
public class IOManager {
    public static void genTimeData(){
        String gson = new GsonBuilder().setPrettyPrinting().create().toJson(new HashMapOf<>("minecraft:overworld", new TimeDataStorage()));
        File file = new File("./config/dungeons-and-crafting/time-data.json");
        fileWriter(file, gson);
    }

    public static void generateSysTimeCfg(){
        String gson = new GsonBuilder().setPrettyPrinting().create().toJson(new SystemTimeConfig("5:00", "20:00", "3:00"));
        File file = new File("./config/dungeons-and-crafting/system-time-data-global.json");
        fileWriter(file, gson);
    }

    public static void generateMapSysTime(){
        String gson2 = new GsonBuilder().setPrettyPrinting().create().toJson(new HashMapOf<>("minecraft:overworld", new SystemTimeConfig("5:00", "20:00", "3:00")));
        File file2 = new File("./config/dungeons-and-crafting/system-time-data.json");
        fileWriter(file2, gson2);
    }



    public static void updateModConfig(ModConfig config){
        String gson = new GsonBuilder().setPrettyPrinting().create().toJson(config);
        File file = new File("./config/dungeons-and-crafting/config.json");
        fileWriter(file, gson);
    }

    public static void updateMapSysTime(String worldId, String sunrise, String sunset, String timeZone){
        DNCMod.sysTimeMap.put(worldId, new SystemTimeConfig(sunrise, sunset, timeZone));
        String gson = new GsonBuilder().setPrettyPrinting().create().toJson(DNCMod.sysTimeMap);
        File file = new File("./config/dungeons-and-crafting/system-time-data.json");
        fileWriter(file, gson);
    }

    public static void updateGlobalSysTimeCfg(String sunrise, String sunset, String timezone){
        DNCMod.systemTimeConfig = new SystemTimeConfig(sunrise, sunset, timezone);
        String gson = new GsonBuilder().setPrettyPrinting().create().toJson(DNCMod.systemTimeConfig);
        File file = new File("./config/dungeons-and-crafting/system-time-data-global.json");
        fileWriter(file, gson);
    }

    public static SystemTimeConfig readGlobalSysTimeCfg(){
        SystemTimeConfig config;
        try {
            config = new Gson().fromJson(new FileReader("./config/dungeons-and-crafting/system-time-data-global.json"), SystemTimeConfig.class);
        } catch (IOException e){
            e.printStackTrace();
            config = new SystemTimeConfig("7:00", "19:00", "local");
        }
        return config;
    }

    public static HashMap<String, SystemTimeConfig> readSysTimeCfg(){
        HashMap<String, SystemTimeConfig> map;
        try {
            map = new Gson().fromJson(new FileReader("./config/dungeons-and-crafting/system-time-data.json"), new TypeToken<HashMap<String, SystemTimeConfig>>(){}.getType());
        } catch (IOException e){
            e.printStackTrace();
            map = new HashMapOf<>("minecraft:overworld", new SystemTimeConfig("5:00", "20:00", "3:00"));
        }
        return map;
    }

    public static void generateModConfig(){
        String gson = new GsonBuilder().setPrettyPrinting().create().toJson(new ModConfig(true, false, false,
                true, 10, true, 50, false));
        File file = new File("./config/dungeons-and-crafting/config.json");
        fileWriter(file, gson);
    }

    public static ModConfig readModConfig(){
        ModConfig config;
        try {
            config = new Gson().fromJson(new FileReader("./config/dungeons-and-crafting/config.json"), ModConfig.class);
            if(config.config_ver == 1){
                config = patchModConfigV1(config);
                String gson = new GsonBuilder().setPrettyPrinting().create().toJson(config);
                File file = new File("./config/dungeons-and-crafting/config.json");
                fileWriter(file, gson);
            }
        } catch (IOException e){
            e.printStackTrace();
            config = new ModConfig(true, false, false,
                    true, 10, true, 50, false);
        }
        return config;
    }

    public static ModConfig patchModConfigV1(ModConfig config){
        return new ModConfig(config.patchSkyAngle, config.syncWithSystemTime,false, true, 10, true, 50, false);
    }
    public static void updateTimeData(String id, long dayD, long nightD){
        DNCMod.timeDataMap.put(id, new TimeDataStorage(dayD, nightD));
        String gson = new GsonBuilder().setPrettyPrinting().create().toJson(DNCMod.timeDataMap);
        File file = new File("./config/dungeons-and-crafting/time-data.json");
        fileWriter(file, gson);
    }

    public static void updateTimeData(){
        String gson = new GsonBuilder().setPrettyPrinting().create().toJson(DNCMod.timeDataMap);
        File file = new File("./config/dungeons-and-crafting/time-data.json");
        fileWriter(file, gson);
    }

    public static int readTimeData(){
        HashMap<String, TimeDataStorage> timeDataMap;
        int result;
        try {
            timeDataMap = new Gson().fromJson(new FileReader("./config/dungeons-and-crafting/time-data.json"), new TypeToken<HashMap<String, TimeDataStorage>>(){}.getType());
            result = 1;
        } catch (IOException e){
            e.printStackTrace();
            timeDataMap = new HashMapOf<>("minecraft:overworld", new TimeDataStorage());
            result = 0;
        }
        DNCMod.timeDataMap = timeDataMap;
        return result;
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

}