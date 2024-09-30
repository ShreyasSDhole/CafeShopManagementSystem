package business_application;

import java.io.File;
import java.net.URL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
//import java.util.HashMap;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import net.sf.jasperreports.view.JasperViewer;
import javafx.stage.Stage;

public class Form1Controller implements Initializable
{
	@FXML private Button customers_Btn;

	@FXML private Button dashboard_Btn;

	@FXML private AnchorPane dashboard_Form;

	@FXML private ScrollPane dashboard_IncomeChart;

	@FXML private Label dashboard_NC;

	@FXML private Label dashboard_TI;
	
	@FXML private Label dashboard_NSP;
	
	@FXML private Label dashboard_TotalI;
	
    @FXML private AreaChart<?, ?> dashboard_incomeChart;
    
    @FXML private BarChart<?, ?> dashboard_CustomerChart;

    @FXML private Button inventory_Btn;

    @FXML private AnchorPane inventory_Form;

    @FXML private TableView<Product_Data> inventory_TableView;

    @FXML private Button inventory_addBtn;

    @FXML private Button inventory_clearBtn;

    @FXML private TableColumn<Product_Data, String> inventory_col_Date;

    @FXML private TableColumn<Product_Data, String> inventory_col_Price;

    @FXML private TableColumn<Product_Data, String> inventory_col_Status;

    @FXML private TableColumn<Product_Data, String> inventory_col_Stock;

    @FXML private TableColumn<Product_Data, String> inventory_col_Type;

    @FXML private TableColumn<Product_Data, String> inventory_col_productId;

    @FXML private TableColumn<Product_Data, String> inventory_col_productName;

    @FXML private Button inventory_deleteBtn;

    @FXML private ImageView inventory_imageView;

    @FXML private Button inventory_importBtn;

    @FXML private Button inventory_updateBtn;

    @FXML private Button logout_Btn;

    @FXML private AnchorPane main_Form;

    @FXML private Button menu_Btn;

    @FXML private Label username_Field;
    
    @FXML private TextField inventory_price;

    @FXML private TextField inventory_productID;

    @FXML private TextField inventory_productName;

    @FXML private ComboBox<String> inventory_status;

    @FXML private TextField inventory_stock;

    @FXML private ComboBox<String> inventory_type;
    
    @FXML
    private TextField menu_amount;

    @FXML
    private Label menu_change;

    @FXML
    private TableColumn<Product_Data, String> menu_col_price;

    @FXML
    private TableColumn<Product_Data, String> menu_col_productName;

    @FXML
    private TableColumn<Product_Data, String> menu_col_productQuantity;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_payBtn;

    @FXML
    private Button menu_receiptBtn;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private TableView<Product_Data> menu_tableView;

    @FXML
    private Label menu_total;
    
    @FXML
    private AnchorPane customers_form;
    
    @FXML
    private TableView<Customers_Data> customers_tableView;
    
    @FXML
    private TableColumn<Customers_Data, String> customers_col_customerID;
    
    @FXML
    private TableColumn<Customers_Data, String> customers_col_total;
    
    @FXML
    private TableColumn<Customers_Data, String> customers_col_date;
    
    @FXML
    private TableColumn<Customers_Data, String> customers_col_cashier;
	
    private Alert alert;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    private Image image;
    
    private ObservableList<Product_Data> cardListData = FXCollections.observableArrayList();
	
    public void dashboardDisplayNC() 
    {
        String sql = "SELECT COUNT(id) FROM receipt";
        connect = Cafe_Database.connectDB();
        
        try 
        {
            int nc = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) 
            {
                nc = result.getInt("COUNT(id)");
            }
            dashboard_NC.setText(String.valueOf(nc));
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void dashboardDisplayTI() 
    {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        String sql = "SELECT SUM(total) FROM receipt WHERE date = '"+ sqlDate + "'";
        
        connect = Cafe_Database.connectDB();
        
        try 
        {
            double ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) 
            {
                ti = result.getDouble("SUM(total)");
            }
            
            dashboard_TI.setText("₹" + ti);
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void dashboardTotalI() 
    {
        String sql = "SELECT SUM(total) FROM receipt";
        
        connect = Cafe_Database.connectDB();
        
        try
        {
            float ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) 
            {
                ti = result.getFloat("SUM(total)");
            }
            dashboard_TotalI.setText("₹" + ti);
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void dashboardNSP() 
    {
        String sql = "SELECT COUNT(quantity) FROM customer";
        
        connect = Cafe_Database.connectDB();
        
        try 
        {
            int q = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) 
            {
                q = result.getInt("COUNT(quantity)");
            }
            dashboard_NSP.setText(String.valueOf(q));
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void dashboardIncomeChart() 
    {
        dashboard_incomeChart.getData().clear();
        
        String sql = "SELECT date, SUM(total) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = Cafe_Database.connectDB();
        XYChart.Series chart = new XYChart.Series();
        try 
        {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while (result.next()) 
            {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getFloat(2)));
            }
            
            dashboard_incomeChart.getData().add(chart);
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void dashboardCustomerChart()
    {
        dashboard_CustomerChart.getData().clear();
        
        String sql = "SELECT date, COUNT(id) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = Cafe_Database.connectDB();
        XYChart.Series chart = new XYChart.Series();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }
            
            dashboard_CustomerChart.getData().add(chart);
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void inventoryAddBtn()
    {
	    if (inventory_productID.getText().isEmpty()
	            || inventory_productName.getText().isEmpty()
	            || inventory_type.getSelectionModel().getSelectedItem() == null
	            || inventory_stock.getText().isEmpty()
	            || inventory_price.getText().isEmpty()
	            || inventory_status.getSelectionModel().getSelectedItem() == null
	            || DataOfEmployee.path == null) 
	    {
	        alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error Message");
	        alert.setHeaderText(null);
	        alert.setContentText("Please fill all blank fields");
	        alert.showAndWait();
	    }
	    else
	    {
	        // CHECK PRODUCT ID
	        String checkProdID = "SELECT prod_id FROM product WHERE prod_id = '"
	                + inventory_productID.getText() + "'";
	        
	        connect = Cafe_Database.connectDB();
	        
	        try 
	        {
	            statement = connect.createStatement();
	            result = statement.executeQuery(checkProdID);
	            
	            if (result.next()) 
	            {
	                alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Error Message");
	                alert.setHeaderText(null);
	                alert.setContentText(inventory_productID.getText() + " is already taken");
	                alert.showAndWait();
	            } 
	            else 
	            {
	                String insertData = "INSERT INTO product "
	                        + "(prod_id, prod_name, type, stock, price, status, image, date) "
	                        + "VALUES(?,?,?,?,?,?,?,?)";
	                
	                prepare = connect.prepareStatement(insertData);
	                prepare.setString(1, inventory_productID.getText());
	                prepare.setString(2, inventory_productName.getText());
	                prepare.setString(3, (String) inventory_type.getSelectionModel().getSelectedItem());
	                prepare.setString(4, inventory_stock.getText());
	                prepare.setString(5, inventory_price.getText());
	                prepare.setString(6, (String) inventory_status.getSelectionModel().getSelectedItem());
	                
	                String path = DataOfEmployee.path;
	                path = path.replace("\\", "\\\\");
	                
	                prepare.setString(7, path);
	
	                // TO GET CURRENT DATE
	                Date date = new Date();
	                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	                
	                prepare.setString(8, String.valueOf(sqlDate));
	                
	                prepare.executeUpdate();
	                
	                alert = new Alert(AlertType.INFORMATION);
	                alert.setTitle("Information Message");
	                alert.setHeaderText(null);
	                alert.setContentText("Successfully Added!");
	                alert.showAndWait();
	                
	                inventoryShowData();
	                inventoryClearBtn();
	            }
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	    }
	}
    
    public void inventoryUpdateBtn() 
    {
        if (inventory_productID.getText().isEmpty()
                || inventory_productName.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || DataOfEmployee.path == null || DataOfEmployee.id == 0) 
        {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } 
        else 
        {
            String path = DataOfEmployee.path;
            path = path.replace("\\", "\\\\");
            
            String updateData = "UPDATE product SET "
                    + "prod_id = '" + inventory_productID.getText() + "', prod_name = '"
                    + inventory_productName.getText() + "', type = '"
                    + inventory_type.getSelectionModel().getSelectedItem() + "', stock = '"
                    + inventory_stock.getText() + "', price = '"
                    + inventory_price.getText() + "', status = '"
                    + inventory_status.getSelectionModel().getSelectedItem() + "', image = '"
                    + path + "', date = '"
                    + DataOfEmployee.date + "' WHERE id = " + DataOfEmployee.id;
            
            connect = Cafe_Database.connectDB();
            
            try 
            {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Product ID: " + inventory_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if (option.get().equals(ButtonType.YES)) 
                {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();
                } 
                else 
                {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
    
    public void inventoryClearBtn() 
    {
        inventory_productID.setText("");
        inventory_productName.setText("");
        inventory_type.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();
        DataOfEmployee.path = "";
        DataOfEmployee.id = 0;
        inventory_imageView.setImage(null);
    }
    
    public void inventoryDeleteBtn() 
    {
        if (DataOfEmployee.id == 0) 
        {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } 
        else 
        {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Product ID: " + inventory_productID.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            
            if (option.get().equals(ButtonType.YES)) 
            {
                String deleteData = "DELETE FROM product WHERE id = " + DataOfEmployee.id;
                try 
                {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();
                    
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
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }
    }
    
    public void inventoryImportBtn() 
    {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg"));
        
        File file = openFile.showOpenDialog(main_Form.getScene().getWindow());
        
        if (file != null) 
        {
            DataOfEmployee.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 144, 161, false, true);
            
            inventory_imageView.setImage(image);
        }
    }
    
    public ObservableList<Product_Data> inventoryDataList() 
    {
        ObservableList<Product_Data> listData = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM product";
        connect = Cafe_Database.connectDB();
        
        try 
        {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            Product_Data prodData;
            
            while (result.next()) 
            {
                prodData = new Product_Data(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));
                
                listData.add(prodData);  
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Product_Data> inventoryListData;
    
    public void inventoryShowData() 
    {
        inventoryListData = inventoryDataList();
        
        inventory_col_productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_col_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_Stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        inventory_TableView.setItems(inventoryListData);
    }
    
    public void inventorySelectData()
    {
    	Product_Data productData = inventory_TableView.getSelectionModel().getSelectedItem();
    	int num = inventory_TableView.getSelectionModel().getSelectedIndex();
    	
    	if ((num - 1) < -1) 
    	{
            return;
        }
        
        inventory_productID.setText(productData.getProductId());
        inventory_productName.setText(productData.getProductName());
        inventory_stock.setText(String.valueOf(productData.getStock()));
        inventory_price.setText(String.valueOf(productData.getPrice()));
        
        DataOfEmployee.path = productData.getImage();
        
        String path = "File:" + productData.getImage();
        DataOfEmployee.date = String.valueOf(productData.getDate());
        DataOfEmployee.id = productData.getId();
        
        image = new Image(path, 144, 161, false, true);
        inventory_imageView.setImage(image);
    }
    
    private String[] typeList = {"Meals", "Drinks"};
    public void inventoryTypeList() 
    {
        List<String> typeL = new ArrayList<>();
        
        for (String data : typeList) 
        {
            typeL.add(data);
        }
        
        ObservableList<String> listData = FXCollections.observableArrayList(typeL);
        inventory_type.setItems(listData);
    }
    
    private String[] statusList = {"Available", "Unavailable"};
    public void inventoryStatusList() 
    {
        List<String> statusL = new ArrayList<>();
        
        for (String data : statusList) 
        {
            statusL.add(data);
        }
        
        ObservableList<String> listData = FXCollections.observableArrayList(statusL);
        inventory_status.setItems(listData);
        
    }
    
    public ObservableList<Product_Data> menuGetData() 
    {
        String sql = "SELECT * FROM product";
        
        ObservableList<Product_Data> listData = FXCollections.observableArrayList();
        connect = Cafe_Database.connectDB();
        
        try 
        {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            Product_Data prod;
            
            while (result.next()) 
            {
                prod = new Product_Data(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));
                
                listData.add(prod);
            }
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return listData;
    }
    
    public void menuDisplayCard() 
    {
        cardListData.clear();
        cardListData.addAll(menuGetData());
        
        int row = 0;
        int column = 0;
        
        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();
        
        for (int q = 0; q < cardListData.size(); q++) 
        {
            try 
            {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("CardProduct.fxml"));
                AnchorPane pane = load.load();
                CardProduct_Controller cardC = load.getController();
                cardC.setData(cardListData.get(q));
                
                if (column == 3) 
                {
                    column = 0;
                    row += 1;
                }
                
                menu_gridPane.add(pane, column++, row);
                
                GridPane.setMargin(pane, new Insets(17.5));
                
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
    
    public ObservableList<Product_Data> menuGetOrder() 
    {
        customerID();
        ObservableList<Product_Data> listData = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM customer WHERE customer_id = " + cID;
        connect = Cafe_Database.connectDB();
        
        try 
        {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            Product_Data prod;
            
            while (result.next()) 
            {
                prod = new Product_Data(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(prod);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return listData;
    }
    
    private ObservableList<Product_Data> menuOrderListData;
    public void menuShowOrderData() 
    {
        menuOrderListData = menuGetOrder();
        
        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_productQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        menu_tableView.setItems(menuOrderListData);
    }
    
    public void menuSelectOrder() 
    {
        Product_Data prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();
        
        if ((num - 1) < -1) 
        {
            return;
        }
        // TO GET THE ID PER ORDER
        getid = prod.getId();
    }
    
    private double totalP;
    public void menuGetTotal() 
    {
        customerID();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;
        
        connect = Cafe_Database.connectDB();
        
        try 
        {
            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();
            
            if (result.next())
            {
                totalP = result.getDouble("SUM(price)");
            }
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void menuDisplayTotal() 
    {
        menuGetTotal();
        menu_total.setText("₹" + totalP);
    }
    
    private double amount;
    private double change;
    public void menuAmount() 
    {
        menuGetTotal();
        if (menu_amount.getText().isEmpty() || totalP == 0) 
        {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Amount!");
            alert.showAndWait();
        } 
        else 
        {
            amount = Double.parseDouble(menu_amount.getText());
            if (amount < totalP) 
            {
                menu_amount.setText("");
            } 
            else 
            {
                change = (amount - totalP);
                menu_change.setText("₹" + change);
            }
        }
    }
    
    public void menuPayBtn() 
    {
        if (totalP == 0) 
        {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        } 
        else 
        {
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username)"
            		+ " VALUES(?,?,?,?)";
            connect = Cafe_Database.connectDB();
            
            try 
            {
                if (amount == 0) 
                {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Messaged");
                    alert.setHeaderText(null);
                    alert.setContentText("Something wrong!");
                    alert.showAndWait();
                } 
                else 
                {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");
                    Optional<ButtonType> option = alert.showAndWait();
                    
                    if (option.get().equals(ButtonType.YES)) 
                    {
                        customerID();
                        menuGetTotal();
                        prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));
                        
                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        
                        prepare.setString(3, String.valueOf(sqlDate));
                        prepare.setString(4, DataOfEmployee.username);
                        
                        prepare.executeUpdate();
                        
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful.");
                        alert.showAndWait();
                        
                        menuShowOrderData();
                        menuRestart();
                    } 
                    else 
                    {
                        alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Warning Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled.");
                        alert.showAndWait();
                    }
                }
                
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
    
    private int getid;
    public void menuRemoveBtn() 
    {
        if (getid == 0) 
        {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the order item you want to remove");
            alert.showAndWait();
            
        } 
        else 
        {
            String deleteData = "DELETE FROM customer WHERE id = " + getid;
            connect = Cafe_Database.connectDB();
            try 
            {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this item?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if (option.get().equals(ButtonType.YES)) 
                {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }
                menuShowOrderData();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
    
    public void menuRestart() 
    {
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("₹0.0");
        menu_amount.setText("");
        menu_change.setText("₹0.0");
    }
    
//  public void menuReceiptBtn() 
//  {    
//      if (totalP == 0 || menu_amount.getText().isEmpty()) 
//      {
//          alert = new Alert(AlertType.ERROR);
//          alert.setTitle("Error Message");
//          alert.setContentText("Please order first");
//          alert.showAndWait();
//      } 
//      else 
//      {
//          HashMap map = new HashMap();
//          map.put("getReceipt", (cID - 1));
//          
//          try 
//          {
//              
//              JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\WINDOWS 10\\Documents\\NetBeansProjects\\cafeShopManagementSystem\\src\\cafeshopmanagementsystem\\report.jrxml");
//              JasperReport jReport = JasperCompileManager.compileReport(jDesign);
//              JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);
//              
//              JasperViewer.viewReport(jPrint, false);
//              
//              menuRestart();
//              
//          } 
//          catch (Exception e) 
//          {
//              e.printStackTrace();
//          }
//          
//      }
//  }
    
    private int cID;
    public void customerID() 
    {
        
        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = Cafe_Database.connectDB();
        
        try 
        {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) 
            {
                cID = result.getInt("MAX(customer_id)");
            }
            
            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) 
            {
                checkID = result.getInt("MAX(customer_id)");
            }
            
            if (cID == 0) 
            {
                cID += 1;
            } 
            else if (cID == checkID) 
            {
                cID += 1;
            }
            
            DataOfEmployee.cID = cID;
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    
    public ObservableList<Customers_Data> customersDataList() 
    {    
        ObservableList<Customers_Data> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM receipt";
        connect = Cafe_Database.connectDB();
        
        try 
        {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            Customers_Data cData;
            
            while (result.next()) 
            {
                cData = new Customers_Data(result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getString("em_username"));
                
                listData.add(cData);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return listData;
    }
    
    private ObservableList<Customers_Data> customersListData;
    public void customersShowData() 
    {
        customersListData = customersDataList();
        
        customers_col_customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customers_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customers_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customers_col_cashier.setCellValueFactory(new PropertyValueFactory<>("emUsername"));
        
        customers_tableView.setItems(customersListData);
    }
    
    public void switchForm(ActionEvent event) 
   	{
        
        if (event.getSource() == dashboard_Btn) 
        {
            dashboard_Form.setVisible(true);
            inventory_Form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(false);
           
            dashboardDisplayNC();
            dashboardDisplayTI();
            dashboardTotalI();
            dashboardNSP();
            dashboardIncomeChart();
            dashboardCustomerChart();
        } 
        else if (event.getSource() == inventory_Btn) 
        {
            dashboard_Form.setVisible(false);
            inventory_Form.setVisible(true);
            menu_form.setVisible(false);
            customers_form.setVisible(false);
            
            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();
        } 
        else if (event.getSource() == menu_Btn) 
        {
            dashboard_Form.setVisible(false);
            inventory_Form.setVisible(false);
            menu_form.setVisible(true);
            customers_form.setVisible(false);
            
            menuDisplayCard();
            menuDisplayTotal();
            menuShowOrderData();
        } 
        else if (event.getSource() == customers_Btn) 
        {
            dashboard_Form.setVisible(false);
            inventory_Form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(true);
            
            customersShowData();
        }
        
    }
    
    public void logout() 
    {
    	try 
        {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Warning Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            
            if (option.get().equals(ButtonType.OK)) 
            {
                // TO HIDE MAIN FORM 
                logout_Btn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM AND SHOW IT 
                Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                
                stage.setTitle("Marvellous Cafe Management System");
                
                stage.setScene(scene);
                stage.show();
                
            }
        } 
    	catch (Exception e) 
    	{
            e.printStackTrace();
        }
    }
    
	public void displayUsername() 
	{
        String user = DataOfEmployee.username;
        user = user.substring(0,1) + user.substring(1);
        
        username_Field.setText(user);
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		  displayUsername();
	    
		  dashboardDisplayNC();  
		  dashboardDisplayTI();
	      dashboardTotalI();
	      dashboardNSP();
	      dashboardIncomeChart();
	      dashboardCustomerChart();
	        
	      inventoryTypeList();
	      inventoryStatusList();
	      inventoryShowData();
	        
	      menuDisplayCard();
	      menuGetOrder();
	      menuDisplayTotal();
	      menuShowOrderData();
	        
	      customersShowData();
		
	}

}
