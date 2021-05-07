package laba_3;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameServer {
    GameConfig gameConfig;
    private int updater=0;
    private static GameServer instance;
    private World serverWorld;
    public static void main(String[] args){
        new GameServer();
    }
    public GameServer() {
        instance = this;
        try {
            this.loadConfig();
            this.loadWorld();
        }catch (IOException e){
            e.printStackTrace();
        }
        for ( int i = 0; i < 30; i++ ) {
            System.out.println(instance);
            updater++;
            try {
                this.serverWorld.update();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if(updater%(gameConfig.getSavePeriod())==0){
                try {
                    FileUtils.saveWorld(new File("world.dat"), serverWorld);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(gameConfig.getUpdatePeriod());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(instance);
        }
    }
    private void loadConfig() throws IOException{
        this.gameConfig = FileUtils.loadConfig(new File("gconfig.json"));
        if (this.gameConfig==null){
            this.gameConfig = new GameConfig();
            FileUtils.saveConfig(new File("gconfig.json"), gameConfig);
        }
    }
    private void loadWorld() throws IOException{
        this.serverWorld = FileUtils.loadWorld(new File("world.dat"));
        if(serverWorld == null){
            Entity[] entities = new Entity[7];
            try {
            entities[0]= new Entity("Dragon", 20, 15, true, 50, 50, 20);
            entities[1]=new Entity("Wolf", 40, 50, true, 30, 30, 10);
            entities[2] = new EntityPlayer("Me", 30, 25,50, 45, 20, "hello");
            entities[3] = new Entity("Cow", 41, 10, false, 20, 20, 0);
            entities[4] = new Entity("Cow", 27, 35, false, 20, 20, 0);
            entities[5] = new Entity("Cow", 40, 37, false, 20, 20, 0);
            entities[6] = new EntityPlayer("Anouther", 20, 20,50, 40, 25, "cool");
            this.serverWorld = new World(1, "Hello, world", new ArrayList<>(Arrays.asList(entities)));
            FileUtils.saveWorld(new File("world.dat"), serverWorld);}
            catch (SQLException e){
                e.printStackTrace();
            }
            }
    }
    @Override
    public String toString() {
        return "GameServer{" +
                "gameConfig=" + gameConfig +
                ", updater=" + updater +
                ", serverWorld=" + serverWorld +
                '}';
    }

    public int getUpdater() {
        return updater;
    }

    public void setUpdater(int updater) {
        this.updater = updater;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public World getServerWorld() {
        return serverWorld;
    }

    public void setServerWorld(World serverWorld) {
        this.serverWorld = serverWorld;
    }

    public static GameServer getInstance() {
        return instance;
    }

    public static void setInstance(GameServer instance) {
        GameServer.instance = instance;
    }


}
