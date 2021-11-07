package lubarog13;

import com.google.protobuf.Empty;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static lubarog13.MySqlConnection.getConnection;

public class Main {

    public static void main(String[] args){
       /*Product product = new Product("Moloko", 10, 12.5);
       Film film = new Film("bbbbb", new Date(), true, 100.0);
        try {
            creacteTableFromClass(Film.class);
            //insert(film);
            //update(film);
            System.out.println(select(film.getClass(), film.title));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }*/
        Book book = new Book(1, "Someting", 4.5, 1000);
        Book book1 = new Book(2, "Hello", 3.5, 400);
        Book book2 = new Book(3, "mcnvrqe", 5, 300);
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(book1);
        books.add(book2);
        Consumer<Book> consumer = (b) -> b.setRating(0);
        update(books, consumer);
        System.out.println(books);
        Consumer<Book> consumer1 = (b) -> b.setCost(b.getCost()*1.1);
        update(books, consumer1);
        System.out.println(books);
        Random random = new Random(1000);
        List<Integer> list3 = new ArrayList<>();
        for ( int i=0; i<10000; i++ ){
            list3.add(random.nextInt());
        }
        List<Integer> nelist = list3.stream().parallel()
                .filter(o1 -> o1%2==0)
                .collect(Collectors.toList());
        System.out.println(nelist.size());
        List <String> str_list = new ArrayList<>();
        str_list.add("vrt");
        str_list.add("Jegineuig");
        str_list.add("TYHESG");
        str_list.add("Jfnuiere");
        List<String> n_s_l = str_list.stream()
                .filter(s1 -> s1.charAt(0)=='J')
                .collect(Collectors.toList());
        System.out.println(n_s_l);
        n_s_l = str_list.stream()
                .sorted((o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1, o2))
                .collect(Collectors.toList());
        System.out.println(n_s_l);

    }
    public static Field primaryKey(Class<?> cls) throws SQLException {
        Field t = null;
        for(Field f : cls.getDeclaredFields() ){
            if(f.getAnnotation(TableAnnotation.class)!=null &&f.getAnnotation(TableAnnotation.class).isPrimarykey() && t==null){
                t=f;
            }
            else if(f.getAnnotation(TableAnnotation.class)!=null && f.getAnnotation(TableAnnotation.class).isPrimarykey()) throw new SQLException("Несколько первичных ключей");
        }
        return t;
    }
    public static void creacteTableFromClass(Class<?> cls) throws SQLException {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");

        TableAnnotation databaseEntity = cls.getAnnotation(TableAnnotation.class);
        if(databaseEntity == null || databaseEntity.description().isEmpty()) {
            sb.append(cls.getSimpleName().toLowerCase());
        } else {
            sb.append(databaseEntity.description());
        }
        Field pk = primaryKey(cls);
        if(pk!=null)
        sb.append(" (");
        else
        sb.append(" (id int(4) primary key auto_increment,");

        for(Field f : cls.getDeclaredFields())
        {
            TableAnnotation databaseField = f.getAnnotation(TableAnnotation.class);
            if(databaseField == null || databaseField.description().isEmpty()) {
                sb.append(f.getName());
            } else {
                sb.append(databaseField.description());
            }
            sb.append(" ");

            Class<?> fieldClass = f.getType();
            if(fieldClass.equals(int.class)) {
                sb.append("INT(10), ");
            } else if(fieldClass.equals(double.class)) {
                sb.append("DOUBLE(15,5), ");
            } else if(fieldClass.equals(String.class)) {
                sb.append("VARCHAR(256), ");
            } else if(fieldClass.equals(Date.class)) {
                sb.append("DATETIME(3), ");
            } else if(fieldClass.equals(boolean.class)) {
                sb.append("TINYINT(1), ");
            }
            else {
                System.out.println("Поле не поддерживается " + f);
            }
        }
        if(pk!=null)
        sb.append("primary key(").append(pk.getName()).append(")");
        else
        sb.delete(sb.length()-2, sb.length());
        sb.append(")");

        System.out.println(sb.toString());

        try(Connection c =getConnection()) {
            getConnection().createStatement().executeUpdate(sb.toString());
        }
    }
    public static void insert(Object ob) throws SQLException, IllegalAccessException {
        TableAnnotation tableAnnotation = ob.getClass().getAnnotation(TableAnnotation.class);
        String title="";
        if(tableAnnotation!=null && !tableAnnotation.description().isEmpty()) title = tableAnnotation.description();
        else title = ob.getClass().getSimpleName();
        StringBuilder sb = new StringBuilder("INSERT INTO ").append(title).append("(");
        StringBuilder qs = new StringBuilder("( ");
        for(Field f: ob.getClass().getDeclaredFields()){
            qs.append("?, ");
            tableAnnotation = f.getAnnotation(TableAnnotation.class);
            if (tableAnnotation!=null && !tableAnnotation.description().isEmpty()){
                sb.append(tableAnnotation.description()).append(", ");
            }
            else sb.append(f.getName()).append(", ");
        }
        sb.delete(sb.length()-2, sb.length());
        qs.delete(qs.length()-2, qs.length());
        sb.append(") VALUES ").append(qs).append(")");
        System.out.println(sb);
        try(Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement(sb.toString());
            int i=1;
            for ( Field f: ob.getClass().getDeclaredFields() ){
                psInserter(ps, i, f, ob);
                i++;
            }
            ps.executeUpdate();
        }
    }
    public static void psInserter(PreparedStatement ps, int n, Field f, Object o) throws IllegalAccessException, SQLException {
        Class<?> t = f.getType();
        f.setAccessible(true);
        if(t.equals(String.class)) ps.setString(n, (String) f.get(o));
        else if(t.equals(int.class)) ps.setInt(n, (Integer) f.get(o));
        else if(t.equals(double.class)) ps.setDouble(n, (Double) f.get(o));
        else if(t.equals(Date.class)){
            Date date = (Date) f.get(o);
            ps.setTimestamp(n, (new Timestamp(date.getTime())));
        }
        else if(t.equals(boolean.class)){
            ps.setBoolean(n, (Boolean) f.get(o));
        }
    }
    public static void updater(Object ob) throws SQLException, IllegalAccessException {
        TableAnnotation tableAnnotation = ob.getClass().getAnnotation(TableAnnotation.class);
        String title = "";
        if (tableAnnotation != null && !tableAnnotation.description().isEmpty()) title = tableAnnotation.description();
        else title = ob.getClass().getSimpleName();
        StringBuilder sb = new StringBuilder("UPDATE ").append(title).append(" SET ");
        for ( Field f : ob.getClass().getDeclaredFields() ) {
            tableAnnotation = f.getAnnotation(TableAnnotation.class);
            if(tableAnnotation != null &&!tableAnnotation.isPrimarykey()) {
                if (!tableAnnotation.description().isEmpty()) {
                    sb.append(tableAnnotation.description()).append(" = ?, ");
                }else sb.append(f.getName()).append(" = ?, ");
            }else if (tableAnnotation == null){
                sb.append(f.getName()).append(" = ?, ");
            }
        }
        sb.delete(sb.length() - 2, sb.length());
        Field pk = primaryKey(ob.getClass());
        pk.setAccessible(true);
        if (pk.getType()==String.class || pk.getType()==Date.class)
        sb.append(" WHERE ").append(pk.getName()).append("='").append(pk.get(ob)).append("'");
        else sb.append(" WHERE ").append(pk.getName()).append("=").append(pk.get(ob));
        System.out.println(sb);
        try (Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement(sb.toString());
            int i = 1;
            for ( Field f : ob.getClass().getDeclaredFields() ) {
                if (f.getAnnotation(TableAnnotation.class)!=null && !f.getAnnotation(TableAnnotation.class).isPrimarykey()) {
                    psInserter(ps, i, f, ob);
                    i++;
                }
                else if(f.getAnnotation(TableAnnotation.class)==null){
                    psInserter(ps, i, f, ob);
                    i++;
                }
            }
            System.out.println(ps);
            ps.executeUpdate();
        }
    }
    public static void delete(Object ob, int id) throws SQLException {
        TableAnnotation tableAnnotation = ob.getClass().getAnnotation(TableAnnotation.class);
        String title = "";
        if (tableAnnotation != null && !tableAnnotation.description().isEmpty()) title = tableAnnotation.description();
        else title = ob.getClass().getSimpleName();
        String sql = "DELETE from "+title+" where id="+id;
        try (Connection c = getConnection()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.executeUpdate();
        }
    }
    public static void deleteAlParams(Object ob) throws SQLException, IllegalAccessException {
        TableAnnotation tableAnnotation = ob.getClass().getAnnotation(TableAnnotation.class);
        String title = "";
        if (tableAnnotation != null && !tableAnnotation.description().isEmpty()) title = tableAnnotation.description();
        else title = ob.getClass().getSimpleName();
        StringBuilder sb = new StringBuilder("DELETE from ").append(title).append(" WHERE ");
        for ( Field f : ob.getClass().getDeclaredFields() ) {
            tableAnnotation = f.getAnnotation(TableAnnotation.class);
            if (tableAnnotation != null && !tableAnnotation.description().isEmpty()) {
                sb.append(tableAnnotation.description()).append(" = ? and ");
            } else sb.append(f.getName()).append(" = ? and ");
        }
        sb.delete(sb.length() - 5, sb.length());
        System.out.println(sb);
        try (Connection c = getConnection()){
            PreparedStatement ps = c.prepareStatement(sb.toString());
            int i = 1;
            for ( Field f : ob.getClass().getDeclaredFields() ) {
                psInserter(ps, i, f, ob);
                i++;
            }
            System.out.println(ps);
            ps.executeUpdate();
        }
    }
    public static <T> List<T> selectAll(Class <T> cls) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TableAnnotation tableAnnotation = cls.getAnnotation(TableAnnotation.class);
        String title = "";
        if (tableAnnotation != null && !tableAnnotation.description().isEmpty()) title = tableAnnotation.description();
        else title = cls.getSimpleName();
        String sql = "SELECT * from " + title;
        List<T> list = new ArrayList<>();
        Constructor<T> constructor = cls.getDeclaredConstructor();
        try (Connection c = getConnection()){
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);
            while (resultSet.next()) {
                T t =  constructor.newInstance();
                for ( Field f : t.getClass().getDeclaredFields() ) {
                    if (f.getAnnotation(TableAnnotation.class) != null && !f.getAnnotation(TableAnnotation.class).description().isEmpty()){
                        getBack(resultSet, f.getAnnotation(TableAnnotation.class).description(), f, t);
                    }
                    else getBack(resultSet, f.getName(), f, t);
                }
                list.add(t);
            }

        }
        return list;
    }
    public static void getBack(ResultSet resultSet, String column, Field f, Object ob) throws SQLException, IllegalAccessException {
        Class<?> t = f.getType();
        f.setAccessible(true);
        if(t.equals(String.class)){
            f.set(ob, resultSet.getString(column));
        }
        else if(t.equals(int.class)) f.set(ob, resultSet.getInt(column));
        else if(t.equals(double.class)) f.set(ob, resultSet.getDouble(column));
        else if(t.equals(Date.class)){
            f.set(ob, resultSet.getTimestamp(column));
        }
        else if(t.equals(boolean.class)){
            f.set(ob, resultSet.getBoolean(column));
        }
    }
    public static <T, I> T select(Class<T> cls, I id) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TableAnnotation tableAnnotation = cls.getAnnotation(TableAnnotation.class);
        String title = "";
        if (tableAnnotation != null && !tableAnnotation.description().isEmpty()) title = tableAnnotation.description();
        else title=cls.getSimpleName();
        Field pk = primaryKey(cls);
        StringBuilder sb = new StringBuilder("SELECT * from ").append(title).append(" WHERE ").append(pk.getName());
        if (pk.getType()==String.class || pk.getType()==Date.class)
            sb.append("='").append(id).append("'");
        else sb.append("=").append(id);
        Constructor<T> constructor = cls.getDeclaredConstructor();
        System.out.println(sb);
        try (Connection c = getConnection()) {
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sb.toString());
            resultSet.next();
            T t = constructor.newInstance();
            for ( Field f : t.getClass().getDeclaredFields() ) {
                if (f.getAnnotation(TableAnnotation.class) != null && !f.getAnnotation(TableAnnotation.class).description().isEmpty()){
                    getBack(resultSet, f.getAnnotation(TableAnnotation.class).description(), f, t);
                }
                else getBack(resultSet, f.getName(), f, t);
            }
            return t;
        }
    }
    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/urok",
                "root",
                "1234"
        );
    }
    public static <T> void update(List <T> list, Consumer<T> updater){
        for ( T t : list ) {
            updater.accept(t);
        }
    }
}
