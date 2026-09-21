    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Administrator
 */
public class Order implements Serializable{
    private int orderCode;
    private String customerId;
    private String setMenuCode;
    private int numOfTables;
    private Date preferedDate;
    private int price;

    public Order(int orderCode) {
        this.orderCode = orderCode;
    }

    public Order(int orderCode, String customerId, String province, String menuId, String setMenuCode, int numOfTables, Date preferedDate) {
        this.orderCode = orderCode;
        this.customerId = customerId;
        this.setMenuCode = setMenuCode;
        this.numOfTables = numOfTables;
        this.preferedDate = preferedDate;
    }

    /*
    (1): o day lam vay de lam gi
    vi ngay la khong bao gio trung nen khi chung ta dat id,.. hay nhung key dac biet khong trung
    thi chúng ta có the dùng den no mà không can user nhap
    vi sao Private ? => vi method nay chi dung ben tron class order bên ngoài không can goi nên Private
    */
    
    /*
    b1: truoc tien phai khai bao tao 1 bien date truoc
    b2: sau do dùng SimpleDateFormat có san trong java de tao ra 1 kieu ngay yyMMMdd,...
    b3: sau dó tra ve voi dung format la duoc
    */
//    private String generateOrderCode(){
//        Date now = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//        return sdf.format(now);
//    }
    
    
    // getter

    public int getOrderCode() {
        return orderCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public Date getPreferedDate() {
        return preferedDate;
    }

    public String getSetMenuCode() {
        return setMenuCode;
    }
    
    
    // làm riêng cái lây tong tien
    public double getTotalCost(){
        return price * numOfTables;
    }
    
    
    // set

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    public void setPreferedDate(Date preferedDate) {
        this.preferedDate = preferedDate;
    }

    public void setSetMenuCode(String setMenuCode) {
        this.setMenuCode = setMenuCode;
    }
    
    
    @Override
    public int hashCode() {
        return Objects.hash(customerId, preferedDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (!Objects.equals(this.customerId, other.customerId)) {
            return false;
        }
        return Objects.equals(this.preferedDate, other.preferedDate);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%s | %s | %s | %d", orderCode, sdf.format(preferedDate), customerId, numOfTables);
    }   
}
