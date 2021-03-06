package ru.spbstu.main;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class Commands
{
  private static Connection connection;
  private static Scanner in;
  private static int prodidValue;

  Commands(Scanner in, Connection connection, int N)
  {
    Commands.in = in;
    Commands.connection = connection;
    Commands.prodidValue = N;
  }

  private static void add() throws SQLException
  {
    try (PreparedStatement preparedStatement = connection.prepareStatement("insert into Items (prodid, title, cost) values (?, ?, ?)"))
    {
      String title = in.next();
      int cost = in.nextInt();
      preparedStatement.setInt(1, ++prodidValue);
      preparedStatement.setString(2, title);
      preparedStatement.setInt(3, cost);
      preparedStatement.executeUpdate();
    }
    catch (InputMismatchException e)
    {
      System.out.println("Invalid command parameters, first must be String, second must be int");
    }
    catch (SQLIntegrityConstraintViolationException e)
    {
      System.out.println("There can`t be two products with the same name or prodid");
    }
  }

  private static void delete() throws SQLException
  {
    String title = in.next();
    PreparedStatement preparedStatement = connection.prepareStatement("delete from Items where title = ?");
    preparedStatement.setString(1, title);
    preparedStatement.executeUpdate();
    preparedStatement.close();
  }

  private static void show_all() throws SQLException
  {
    ResultSet resultSet = connection.createStatement().executeQuery("select * from Items");
    while (resultSet.next())
    {
      System.out.print(resultSet.getInt("id") + "  ");
      System.out.print(resultSet.getInt("prodid") + "  ");
      System.out.print(resultSet.getString("title") + "  ");
      System.out.print(resultSet.getInt("cost"));
      System.out.println();
    }
    resultSet.close();
  }

  private static void price() throws SQLException
  {
    String title = in.next();
    PreparedStatement preparedStatement = connection.prepareStatement("select cost from Items where title = ?");
    preparedStatement.setString(1, title);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next())
    {
      System.out.println(resultSet.getInt("cost"));
    }
    else
    {
      System.out.println("Такого товара нет");
    }
    preparedStatement.close();
    resultSet.close();
  }

  private static void change_price() throws SQLException
  {
    try (PreparedStatement preparedStatement = connection.prepareStatement("update Items SET cost = ? where title = ?"))
    {
      String title = in.next();
      int cost = in.nextInt();
      preparedStatement.setInt(1, cost);
      preparedStatement.setString(2, title);
      preparedStatement.executeUpdate();
    }
    catch (InputMismatchException e)
    {
      System.out.println("Invalid command parameters, first must be String, second must be int");
    }
  }

  private static void filter_by_price() throws SQLException
  {
    try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Items where cost > ? and cost < ?"))
    {
      int firstPrice = in.nextInt();
      int secondPrice = in.nextInt();
      preparedStatement.setInt(1, Math.min(firstPrice, secondPrice));
      preparedStatement.setInt(2, Math.max(firstPrice, secondPrice));
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next())
      {
        System.out.print(resultSet.getInt("id") + "  ");
        System.out.print(resultSet.getInt("prodid") + "  ");
        System.out.print(resultSet.getString("title") + "  ");
        System.out.print(resultSet.getInt("cost"));
        System.out.println();
      }
      resultSet.close();
    }
    catch (InputMismatchException e)
    {
      System.out.println("Invalid command parameters, first must be int, second must be int");
    }
  }
}
