/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_application;

import java.sql.Date;

public class Customers_Data 
{

    private Integer id;
    private Integer customerID;
    private Double total;
    private Date date;
    private String emUsername;

    public Customers_Data(Integer id, Integer customerID, Double total,
             Date date, String emUsername) 
    {
        this.id = id;
        this.customerID = customerID;
        this.total = total;
        this.date = date;
        this.emUsername = emUsername;
    }

    public Integer getId() 
    {
        return id;
    }

    public Integer getCustomerID() 
    {
        return customerID;
    }

    public Double getTotal() 
    {
        return total;
    }

    public Date getDate() 
    {
        return date;
    }

    public String getEmUsername() 
    {
        return emUsername;
    }

}
