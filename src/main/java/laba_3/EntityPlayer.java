package laba_3;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.SQLException;

public class EntityPlayer extends Entity implements Externalizable {
    private String nickname;
    private int xp=0;

    public EntityPlayer(String title, double posX, double posZ, int maxHealth, int health, int attackDamage, String nickname) throws SQLException {
        super(title, posX, posZ, false, maxHealth, health, attackDamage);
        this.nickname=nickname;
        DatabaseUtils.insert_new_player(this);

    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
    public void setExtraXp(int xp){
        this.xp+=xp;
    }
    public EntityPlayer(){}
    public void update() throws SQLException {
        super.update();
        if (GameServer.getInstance().getUpdater()%2==0){
            if(health<maxHealth){
                health++;
            }
        }
        if (GameServer.getInstance().getUpdater()%5==0){
            xp+=10*GameServer.getInstance().getGameConfig().getDifficulty();
            DatabaseUtils.update_player(this);
        }
    }

    @Override
    public String toString() {
        return "EntityPlayer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", posX=" + posX +
                ", posZ=" + posZ +
                ", agressive=" + agressive +
                ", maxHealth=" + maxHealth +
                ", health=" + health +
                ", attackDamage=" + attackDamage +
                ", target=" + target +
                ", range=" + range +
                ", alive=" + alive +
                ", nickname='" + nickname + '\'' +
                ", xp=" + xp +
                '}';
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
        nickname = input.readUTF();
        super.readExternal(input);
        try {
            DatabaseUtils.select_player_xp(this);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void writeExternal(ObjectOutput output) throws IOException {
        output.writeUTF(nickname);
        super.writeExternal(output);
    }
}
