package company.com.locationfinder.Shopping_Cart_Navigator.Model;

import java.util.Date;

/**
 * Created by root on 4/5/18.
 */

public class Item {

    private String name;
    private String category;
    private double price;
    private Date dateOfPurchace;
    private int[] position = new int[2];

    public String getCategory() {
        return category;

    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDateOfPurchace() {
        return dateOfPurchace;
    }

    public void setDateOfPurchace(Date dateOfPurchace) {
        this.dateOfPurchace = dateOfPurchace;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int[] getPosition() {
        return position;
    }
}
