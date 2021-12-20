package for_exam;

import lubarog13.Entetys.Material;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MaterialManager {
    public static List<Material> getAll() throws SQLException {
        try(Connection c = App.getConnection()) {
            String sql = "select * from Material";
            Statement st = c.createStatement();
            ResultSet resultSet = st.executeQuery(sql);
            List<Material> materials = new ArrayList<>();
            while (resultSet.next()){
                materials.add(new Material(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getDouble(6),
                        resultSet.getString(7),
                        resultSet.getDouble(8),
                        resultSet.getString(9),
                        resultSet.getString(10)
                ));
            }
            return materials;
        }
    }
}
