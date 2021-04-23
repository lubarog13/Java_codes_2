package laba_1;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Output output = null;
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "rw");
            output = new Output(new BufferedOutputStream(new FileOutputStream(raf.getFD())), 1000);
            kryo.writeObject(output, world);
        } finally {
            output.close();
        }
    }

    public static World loadWorld(File path) throws IOException {
        Input input = null;
        try {
            RandomAccessFile raf = new RandomAccessFile(path, "rw");
            input = new Input(new BufferedInputStream(new FileInputStream(raf.getFD())), 10000);
            return kryo.readObjectOrNull(input, World.class);
        }catch (KryoException e){return null;}
        finally {
            input.close();
        }
    }
}

