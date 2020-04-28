package spbstu.ru;

import java.net.URL;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import static spbstu.ru.FirstPageController.getN;

public class TableViewController implements Initializable
{
  @FXML
  private TextField firstPriceTextField;
  @FXML
  private TextField secondPriceTextField;
  @FXML
  private Label ErrorLabel;
  @FXML
  private Button filterOk;
  @FXML
  private TableView<Item> tableView;
  @FXML
  private TableColumn<Item, Integer> id;
  @FXML
  private TableColumn<Item, Integer> prodid;
  @FXML
  private TableColumn<Item, String> title;
  @FXML
  private TableColumn<Item, Integer> cost;
  @FXML
  private TextField titleTextField;
  @FXML
  private TextField costTextField;

  private static Connection connection;

  @Override
  public void initialize(URL url, ResourceBundle rb)
  {
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    prodid.setCellValueFactory(new PropertyValueFactory<>("prodid"));
    title.setCellValueFactory(new PropertyValueFactory<>("title"));
    cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    String userName = "Dmitrii";
    String password = "0000";
    String connectionUrl = "jdbc:mysql://localhost/mysql?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    try
    {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(connectionUrl, userName, password);
      Statement statement = connection.createStatement();
      statement.executeUpdate("drop table IF EXISTS Items");
      statement.executeUpdate("CREATE TABLE IF NOT EXISTS Items (id INT NOT NULL AUTO_INCREMENT, prodid INT NOT NULL, title CHAR(60) NOT NULL, cost INT NOT NULL, PRIMARY KEY (id), UNIQUE KEY (title), UNIQUE KEY (prodid))");
      tableView.setItems(getFirstNItems());
    }
    catch (ClassNotFoundException | SQLException e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  private void addToTable() throws SQLException
  {
    try (PreparedStatement preparedStatement = connection.prepareStatement("insert into Items (prodid, title, cost) values (?, ?, ?)"))
    {
      String title = titleTextField.getText();
      int cost = Integer.parseInt(costTextField.getText());
      Item newItem = new Item(title, cost);
      tableView.getItems().add(newItem);
      preparedStatement.setInt(1, newItem.getProdid());
      preparedStatement.setString(2, title);
      preparedStatement.setInt(3, cost);
      preparedStatement.executeUpdate();
    }
    catch (NumberFormatException e)
    {
      ErrorLabel.setVisible(true);
      ErrorLabel.setText("Invalid cost");
    }
    catch (SQLIntegrityConstraintViolationException e)
    {
      tableView.getItems().remove(tableView.getItems().size() - 1);
      ErrorLabel.setVisible(true);
      ErrorLabel.setText("There can`t be two products with the same name or prodid");
    }
    finally
    {
      titleTextField.clear();
      costTextField.clear();
    }
  }

  @FXML
  private void deleteFromTable()
  {
    try (PreparedStatement preparedStatement = connection.prepareStatement("delete from Items where title = ?"))
    {
      String title = titleTextField.getText();
      ObservableList<Item> allItems = tableView.getItems();
      ObservableList<Item> selectedRows = tableView.getSelectionModel().getSelectedItems();
      if (selectedRows.isEmpty())
      {
        for (Item item : allItems)
        {
          if (item.getTitle().equals(title))
          {
            allItems.remove(item);
            preparedStatement.setString(1, item.getTitle());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            titleTextField.clear();
            costTextField.clear();
            return;
          }
        }
        ErrorLabel.setVisible(true);
        ErrorLabel.setText("No item with this title");
      }
      else
      {
        for (Item item : selectedRows)
        {
          preparedStatement.setString(1, item.getTitle());
          preparedStatement.executeUpdate();
        }
        allItems.removeAll(selectedRows);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  private void showAll() throws SQLException
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

  @FXML
  private void getPrice() throws SQLException
  {
    PreparedStatement preparedStatement = connection.prepareStatement("select cost from Items where title = ?");
    preparedStatement.setString(1, titleTextField.getText());
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next())
    {
      costTextField.setText("price = " + resultSet.getInt("cost"));
    }
    else
    {
      ErrorLabel.setVisible(true);
      ErrorLabel.setText("No item with this title");
    }
    resultSet.close();
    preparedStatement.close();
  }

  @FXML
  private void changePrice()
  {
    try (PreparedStatement preparedStatement = connection.prepareStatement("update Items SET cost = ? where title = ?"))
    {
      String title = titleTextField.getText();
      int newCost = Integer.parseInt(costTextField.getText());
      int i = 0;
      for (Item item : tableView.getItems())
      {
        if (item.getTitle().equals(title))
        {
          item.setCost(newCost);
          tableView.getItems().set(i, item);
          preparedStatement.setInt(1, newCost);
          preparedStatement.setString(2, title);
          preparedStatement.executeUpdate();
          titleTextField.clear();
          costTextField.clear();
          return;
        }
        i++;
      }
      ErrorLabel.setVisible(true);
      ErrorLabel.setText("No item with this title");
    }
    catch (NumberFormatException e)
    {
      ErrorLabel.setVisible(true);
      ErrorLabel.setText("Invalid new cost");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  @FXML
  private void filterByPrice()
  {
    firstPriceTextField.setVisible(true);
    secondPriceTextField.setVisible(true);
    filterOk.setVisible(true);
  }

  @FXML
  private void filterOkButtonPushed()
  {
    try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Items where cost >= ? and cost <= ?"))
    {
      int firstPrice = Integer.parseInt(firstPriceTextField.getText());
      int secondPrice = Integer.parseInt(secondPriceTextField.getText());
      ObservableList<Item> allItems = tableView.getItems();
      allItems.removeAll(allItems.filtered(x -> (x.getCost() < firstPrice | x.getCost() > secondPrice)));

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
      firstPriceTextField.clear();
      secondPriceTextField.clear();
      firstPriceTextField.setVisible(false);
      secondPriceTextField.setVisible(false);
      filterOk.setVisible(false);
    }
    catch (NumberFormatException e)
    {
      ErrorLabel.setVisible(true);
      ErrorLabel.setText("Invalid cost values");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  private ObservableList<Item> getFirstNItems() throws SQLException
  {
    ObservableList<Item> items = FXCollections.observableArrayList();
    Statement statement = connection.createStatement();
    statement.executeUpdate("drop table IF EXISTS Items");
    statement.executeUpdate("CREATE TABLE IF NOT EXISTS Items (id INT NOT NULL AUTO_INCREMENT, prodid INT NOT NULL, title CHAR(60) NOT NULL, cost INT NOT NULL, PRIMARY KEY (id), UNIQUE KEY (title), UNIQUE KEY (prodid))");
    statement.close();
    PreparedStatement preparedStatement = connection.prepareStatement("insert into Items (prodid, title, cost) values (?, ?, ?)");
    for (int i = 0; i < getN(); i++)
    {
      String title = "item" + (i + 1);
      int cost = new Random().nextInt(1000);
      preparedStatement.setInt(1, i + 1);
      preparedStatement.setString(2, title);
      preparedStatement.setInt(3, cost);
      preparedStatement.executeUpdate();
      items.add(new Item(title, cost));
    }
    preparedStatement.close();
    return items;
  }

  @FXML
  private void setErrorLabelInvisible()
  {
    ErrorLabel.setVisible(false);
  }

  static void closeConnection()
  {
    try
    {
      connection.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
}
