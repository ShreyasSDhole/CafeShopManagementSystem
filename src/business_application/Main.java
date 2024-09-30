package business_application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application 
{
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			StackPane root = (StackPane)FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());
			
			primaryStage.setTitle("'Marvellous Cafe' Management System");
			primaryStage.setMinHeight(400);
			primaryStage.setMinWidth(600);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
