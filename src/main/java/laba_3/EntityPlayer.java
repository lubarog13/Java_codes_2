package laba_3;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class EntityPlayer extends Entity implements Externalizable {
    private String nickname;

    public EntityPlayer(String title, double posX, double posZ, int maxHealth, int health, int attackDamage, String nickname) {
        super(title, posX, posZ, false, maxHealth, health, attackDamage);
        this.nickname=nickname;
    }
    public EntityPlayer(){}
    public void update(){
        super.update();
        if (GameServer.getInstance().getUpdater()%2==0){
            if(health<maxHealth){
                health++;
            }
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
                ", nickname='" + nickname + '\'' +
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
    }

    @Override
    public void writeExternal(ObjectOutput output) throws IOException {
        output.writeUTF(nickname);
        super.writeExternal(output);
    }
}
