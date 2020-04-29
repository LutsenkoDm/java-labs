package ru.spbstu.main;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        String userName = "Dmitrii";
        String password = "0000";
        String connectionUrl = "jdbc:mysql://localhost/mysql?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try (Scanner in = new Scanner(System.in);
             Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into Items (prodid, title, cost) values (?, ?, ?)");
             )
        {
            System.out.print("Введите N: ");
            final int N = in.nextInt();
            Class.forName("com.mysql.cj.jdbc.Driver");
            statement.executeUpdate("drop table IF EXISTS Items");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Items (id INT NOT NULL AUTO_INCREMENT, prodid INT NOT NULL, title CHAR(60) NOT NULL, cost INT NOT NULL, PRIMARY KEY (id), UNIQUE KEY (title), UNIQUE KEY (prodid))");
            for (int i = 0; i < N; i++)
            {
                preparedStatement.setInt(1, i + 1);
                preparedStatement.setString(2, "item" + (i + 1));
                preparedStatement.setInt(3, new Random().nextInt(1000));
                preparedStatement.executeUpdate();
            }
            Class<Commands> commands = Commands.class;
            Constructor<Commands> constructor = commands.getDeclaredConstructor(Scanner.class, Connection.class, int.class);
            constructor.newInstance(in, connection, N);
            Method[] methods = commands.getDeclaredMethods();
            for (var m : methods)
            {
                m.setAccessible(true);
            }
            while (in.hasNext())
            {
                String command = in.next();
                Optional<Method> foundCommand = Arrays.stream(methods).filter(m -> m.getName().equals(command)).findAny();
                if (foundCommand.isPresent())
                {
                    foundCommand.get().invoke(commands);
                }
                else
                {
                    System.out.println("Invalid command");
                    in.nextLine();
                }
            }
        }
        catch (SQLException | InputMismatchException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
