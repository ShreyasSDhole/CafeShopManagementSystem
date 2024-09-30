package business_application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController 
{
	@FXML private Hyperlink si_forgotPass1;

	@FXML private Button si_loginBtn1;

	@FXML private AnchorPane si_loginForm1;

	@FXML private PasswordField si_password1;

	@FXML private TextField si_username1;

	@FXML private AnchorPane side_form;

	@FXML private TextField su_answer;

	@FXML private PasswordField su_password;

	@FXML private ComboBox<String> su_question;

	@FXML private Button su_signupBtn;

	@FXML private AnchorPane su_signupForm;

	@FXML private TextField su_username;
	
	@FXML private PasswordField fp_username;
	
	@FXML private PasswordField fp_answer;

    @FXML private Button fp_backBtn;

    @FXML private Button fp_proceedBtn;

    @FXML private ComboBox<String> fp_question;

    @FXML private AnchorPane fp_question_form;

    @FXML private Button np_backBtn;

    @FXML private Button np_changpPassBtn;

    @FXML private PasswordField np_confirmPassword;

    @FXML private AnchorPane np_newPassForm;

    @FXML private PasswordField np_newPassword;
	 
	@FXML private Button side_CreateBtn;
	 
	@FXML private Button side_alreadyHave;
	 
	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	
	private Alert alert;
	
	public void loginBtn() throws Exception
	{
        if (si_username1.getText().isEmpty() || si_password1.getText().isEmpty()) 
        {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();
        } 
        else 
        {    
            String selectData = "select username, password from employee where username = ? and password = ? ";
            connect = Cafe_Database.connectDB();
            try
            {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, si_username1.getText());
                prepare.setString(2, si_password1.getText());
                
                result = prepare.executeQuery();
                // IF SUCCESSFULLY LOGIN, THEN PROCEED TO ANOTHER FORM WHICH IS OUR MAIN FORM 
                if (result.next()) 
                {
                    // TO GET THE USERNAME THAT USER USED
                	DataOfEmployee.username = si_username1.getText();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();  
                    
                   //  Linking the mainForm.
                    Parent root = FXMLLoader.load(getClass().getResource("Page1.fxml"));
                    
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.setTitle("Marvellous Cafe Management System");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);
                    
                    stage.setScene(scene);
                    stage.show();
                    
                    si_loginBtn1.getScene().getWindow().hide();
                } 
                else
                { // IF NOT, THEN THE ERROR MESSAGE WILL APPEAR
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                } 
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
	
	public void regBtn() throws Exception
	{
		if(su_username.getText().isEmpty() && su_password.getText().isEmpty()
				|| su_question.getSelectionModel().getSelectedItem() == null
				|| su_answer.getText().isEmpty())
		{
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Alert");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all the blanks");
			alert.showAndWait();
		}
		else
		{
			String regData = "insert into employee (username, password, question, answer, date) values(?,?,?,?,?)";
			connect = Cafe_Database.connectDB();
			
			try
			{
				String checkUsername = "select username from employee where username = '"+ su_username.getText() + "'";
                
                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();
				
                if (result.next()) 
                {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText() + " is already taken");
                    alert.showAndWait();
                }
                else if (su_password.getText().length() < 8) 
				{
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Password, atleast 8 characters are needed");
                    alert.showAndWait();
				}
                else
                {
                	prepare = connect.prepareStatement(regData);
    	            prepare.setString(1, su_username.getText());
    	            prepare.setString(2, su_password.getText());
    	            prepare.setString(3, (String) su_question.getSelectionModel().getSelectedItem());
    	            prepare.setString(4, su_answer.getText());
    	            
    	            Date date = new Date();
    	            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
    	            prepare.setString(5, String.valueOf(sqlDate));
    	            prepare.executeUpdate();
    	            
    	            alert = new Alert(AlertType.INFORMATION);
    	            alert.setTitle("Information Message");
    	            alert.setHeaderText(null);
    	            alert.setContentText("Successfully registered Account!");
    	            alert.showAndWait();
    	            
    	            su_username.setText("");
    	            su_password.setText("");
    	            su_question.getSelectionModel().clearSelection();
    	            su_answer.setText("");
    	            
    	            TranslateTransition slider = new TranslateTransition();
    	            
    	            slider.setNode(side_form);
    	            slider.setToX(0);
    	            slider.setDuration(Duration.seconds(.5));
    	            
    	            slider.setOnFinished((ActionEvent e) -> {
    	                side_alreadyHave.setVisible(false);
    	                side_CreateBtn.setVisible(true);
    	            });
    	            
    	            slider.play();
                }
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private String[] questionList = {"What is your favourite Car?","What is your favourite food?"
										,"What is your favourite Color?"};
	public void regLquestionList()
	{
		List<String> listQ = new ArrayList<>();
		 
		for(String data : questionList)
		{
			listQ.add(data);
		}
		
		ObservableList<String> listData = FXCollections.observableArrayList(listQ);
		su_question.setItems(listData);
	}
	
	public void switchForgotPass() 
	{
        fp_question_form.setVisible(true);
        si_loginForm1.setVisible(false);
        
        forgotPassQuestionList();
    }
	
	public void forgotPassQuestionList() 
	{
        List<String> listQ = new ArrayList<>();
        
        for (String data : questionList) 
        {
            listQ.add(data);
        }
        
        ObservableList<String> listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);
    }
	
	public void proceedBtn() 
	{
        if (fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null
                || fp_answer.getText().isEmpty()) 
        {    
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } 
        else 
        {
            String selectData1 = "SELECT username, question, answer FROM employee WHERE username = ? AND question = ? AND answer = ?";
            connect = Cafe_Database.connectDB();
            
            try 
            {
                prepare = connect.prepareStatement(selectData1);
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String) fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());
                
                result = prepare.executeQuery();
                
                if (result.next()) 
                {
                    np_newPassForm.setVisible(true);
                    fp_question_form.setVisible(false);
                } 
                else 
                {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information");
                    alert.showAndWait();
                }
                
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
	}    
	
	public void changePassBtn() 
	{
        if (np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()) 
        {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } 
        else 
        {
            if (np_newPassword.getText().equals(np_confirmPassword.getText())) 
            {
                String getDate = "SELECT date FROM employee WHERE username = '"
                        + fp_username.getText() + "'";
                
                connect = Cafe_Database.connectDB();
                
                try 
                {
                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();
                    
                    String date = "";
                    if (result.next()) 
                    {
                        date = result.getString("date");
                    }
                    
                    String updatePass = "UPDATE employee SET password = '"
                            + np_newPassword.getText() + "', question = '"
                            + fp_question.getSelectionModel().getSelectedItem() + "', answer = '"
                            + fp_answer.getText() + "', date = '"
                            + date + "' WHERE username = '"
                            + fp_username.getText() + "'";
                    
                    prepare = connect.prepareStatement(updatePass);
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully changed Password!");
                    alert.showAndWait();
                    
                    si_loginForm1.setVisible(true);
                    np_newPassForm.setVisible(false);

                    // TO CLEAR FIELDS
                    np_confirmPassword.setText("");
                    np_newPassword.setText("");
                    fp_question.getSelectionModel().clearSelection();
                    fp_answer.setText("");
                    fp_username.setText("");
                    
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
            } 
            else 
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Not match");
                alert.showAndWait();
            }
        }
    }
	
	public void backToLoginForm()
	{
        si_loginForm1.setVisible(true);
        fp_question_form.setVisible(false);
    }
    
    public void backToQuestionForm()
    {
        fp_question_form.setVisible(true);
        np_newPassForm.setVisible(false);
    }
	
	public void switchForm(ActionEvent event)
	{
		TranslateTransition slider = new TranslateTransition();
		  
		if(event.getSource() == side_CreateBtn)
		{
			slider.setNode(side_form);
			slider.setToX(300);
			slider.setDuration(Duration.seconds(0.5));
			
			slider.setOnFinished((ActionEvent e) -> 
			{
				side_alreadyHave.setVisible(true);
				side_CreateBtn.setVisible(false);
				
//				fp_question_form.setVisible(false);
	//			si_loginForm.setVisible(true);
//				np_newPassForm.setVisible(false);
				
				regLquestionList();
				
			});
			slider.play();
		}
		else if(event.getSource() == side_alreadyHave)
		{
			slider.setNode(side_form);
			slider.setToX(0);
			slider.setDuration(Duration.seconds(0.5));
			  
			slider.setOnFinished((ActionEvent e) -> 
			{
				side_alreadyHave.setVisible(false);
				side_CreateBtn.setVisible(true);
				
//				fp_question_form.setVisible(false);
				si_loginForm1.setVisible(true);
//				np_newPassForm.setVisible(false);
				
			});
			slider.play();
		}
	}
	
	
}
