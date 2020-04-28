package spbstu.ru;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstPageController
{
  private static int N;
  @FXML
  private TextField TextFieldForN;
  @FXML
  private Label ErrorLabel;

  @FXML
  private void changeScreenButtonPushed(ActionEvent event)
  {
    try
    {
      N = Integer.parseInt(TextFieldForN.getText());
      Parent tableViewParent = FXMLLoader.load(getClass().getResource("TableView.fxml"));
      Scene tableViewScene = new Scene(tableViewParent);
      Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
      window.setScene(tableViewScene);
      window.show();
    }
    catch (NumberFormatException e)
    {
      System.out.println("Invalid N");
      ErrorLabel.setVisible(true);
      TextFieldForN.clear();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  public void setErrorLabelInvisible()
  {
    ErrorLabel.setVisible(false);
  }
  static int getN()
  {
    return N;
  }
}
