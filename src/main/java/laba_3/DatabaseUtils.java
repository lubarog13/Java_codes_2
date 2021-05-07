package laba_3;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;
import java.util.List;

public class DatabaseUtils {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/gameserver",
                "root",
                "1234"
        );
    }
    public static int insert_new_entity(Entity entity) throws SQLException {
        try (Connection c = getConnection()){
            String sql = "INSERT INTO entities(title, init_time) values (?, ?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getTitle());
            ps.setTimestamp(2, new Timestamp(new Date().getTime()));
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            throw new SQLException("entity not added");
        }
    }
    public static void update_entity(Entity entity) throws SQLException {
        try (Connection c = getConnection()){
            String sql = "UPDATE entities set dead_time=? where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp(new Date().getTime()));
            ps.setInt(2, entity.getId());
            ps.executeUpdate();
        }
    }
    public static void insert_new_player(EntityPlayer entityPlayer) throws SQLException {
        try (Connection c = getConnection()){
            String sql = "INSERT INTO players(id, nickname, xp) values (?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, entityPlayer.getId());
            ps.setString(2, entityPlayer.getNickname());
            ps.setInt(3, entityPlayer.getXp());
            ps.executeUpdate();
        }
    }
    public static void update_player(EntityPlayer entityPlayer) throws SQLException {
        try (Connection c = getConnection()){
            String sql = "UPDATE players set xp=? where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, entityPlayer.getXp());
            ps.setInt(2, entityPlayer.getId());
            ps.executeUpdate();
        }
    }
    public static void insert_battle_log(Entity attacked, Entity killed) throws SQLException {
        try (Connection c = getConnection()){
            String sql = "INSERT INTO battle_logs(id_attacked, id_killed, kill_date) values (?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, attacked.getId());
            ps.setInt(2, killed.getId());
            ps.setTimestamp(3, new Timestamp(new Date().getTime()));
            ps.executeUpdate();
        }
    }
    public static void select_player_xp(EntityPlayer entityPlayer) throws SQLException {
        try(Connection c = getConnection()) {
            String sql = "SELECT xp from players where id=" + entityPlayer.getId();
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);
            if(resultSet.next()) entityPlayer.setXp(resultSet.getInt("xp"));
        }
    }
}
