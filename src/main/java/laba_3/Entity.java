package laba_3;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

public class Entity implements Externalizable {
     private static int idCounter = 1;
 final long id;//приравнивается idCounter, после чего idCounter увеличивается на 1
protected String title;
protected double posX;
protected double posZ;
protected boolean agressive;
protected int maxHealth;
protected int health;
protected int attackDamage;
protected Entity target;
protected Double range;
protected boolean alive = true;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public Entity(String title, double posX, double posZ, boolean agressive, int maxHealth, int health, int attackDamage) {
        this.id = idCounter;
        idCounter++;
        this.title = title;
        this.posX = posX;
        this.posZ = posZ;
        this.agressive = agressive;
        this.maxHealth = maxHealth;
        this.health = health;
        this.attackDamage = attackDamage;
    }
    public Entity(){
        id = idCounter++;
    }
    public void searchTarget(){
        List <Entity> searchEntities= GameServer.getInstance().getServerWorld().getEntitiesNearEntity(this, 20);
        for (Entity entity: searchEntities){
            if (!entity.isAgressive() && entity.isAlive()){
                target= entity;
                break;
            }
            else target = null;
        }

    }

    public Double getRange() {
        return range;
    }

    public void setRange(Double x, Double y){
        range =  Math.sqrt(Math.pow(posX - x, 2) + Math.pow(posZ - y, 2));
    }

    public void update(){
        if (agressive){
            if(target==null || !target.isAlive()){
                searchTarget();
            }
            if (target!=null) {
                if (Math.sqrt(Math.pow(target.getPosX()-posX, 2) + Math.pow(target.getPosZ()-posZ, 2))<2) attack(target);
                else {
                    if (target.getPosX() > posX) posX++;
                    else if (target.getPosX() < posX) posX--;
                    if (target.getPosZ() > posZ) posZ++;
                    else if (target.getPosZ() < posZ) posZ--;
                }
            }

        }
    }
    public void attack(Entity entity){
            entity.health-=attackDamage;
            if (entity.health<0){
                entity.setAlive(false);
                target = null;
                System.out.println(this.title + " убил " + entity.getTitle());
            }
            if (entity instanceof EntityPlayer) {
                health -= entity.attackDamage + 0.5 * GameServer.getInstance().getGameConfig().getDifficulty();
                if (health<0){
                    alive = false;
                    System.out.println(entity.getTitle() + " убил " + this.title);
                }
            }


    }
    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", posX=" + posX +
                ", posZ=" + posZ +
                ", agressive=" + agressive +
                ", maxHealth=" + maxHealth +
                ", health=" + health +
                ", attackDamage=" + attackDamage +
                '}';
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        Entity.idCounter = idCounter;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public boolean isAgressive() {
        return agressive;
    }

    public void setAgressive(boolean agressive) {
        this.agressive = agressive;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    @Override
    public void writeExternal(ObjectOutput output) throws IOException {
        output.writeUTF(title);
        output.writeDouble(posX);
        output.writeDouble(posZ);
        output.writeBoolean(agressive);
        output.writeInt(maxHealth);
        output.writeInt(health);
        output.writeInt(attackDamage);
        output.writeBoolean(alive);
    }

    @Override
    public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
        title = input.readUTF();
        posX = input.readDouble();
        posZ = input.readDouble();
        agressive = input.readBoolean();
        maxHealth = input.readInt();
        health = input.readInt();
        attackDamage = input.readInt();
        alive = input.readBoolean();
    }
}
