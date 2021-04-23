package laba_3;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileUtils {
    private static Kryo kryo = new Kryo();


    public static void saveConfig(File path, GameConfig config) throws IOException {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
        Files.write(path.toPath(),
                gson.toJson(config).getBytes(),
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE);

    }

    public static GameConfig loadConfig(File path) throws IOException {
        String json = new String(Files.readAllBytes(path.toPath()));
        Gson gson = new Gson();
        GameConfig gameConfig = gson.fromJson(json, GameConfig.class);
        return gameConfig;
    }

    public static void saveWorld(File path, World world) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))){
            oos.writeObject(world);
        }
    }

    public static World loadWorld(File path) throws IOException{
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))){
            World world = (World)ois.readObject();
            return world;

        } catch (ClassNotFoundException e) {
            return null;
        }catch (EOFException e){
            return null;
        }
    }
}

