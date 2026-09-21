/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import tool.ConsoleInputter;
/**
 *
 * @author Administrator
 */
public class OrderList extends ArrayList<Order> {
    public static final String DATE_PAT = "dd/MM/yyyy";
    public static final String ORDER_CODE_PAT = "^[cCgGkK][\\d]{4}$";
    private int newOrderCode = 1;
    
    SetMenuList setMenuList;
    CustList custList;

    public OrderList(SetMenuList setMenuList, CustList custList) {
        this.setMenuList = setMenuList;
        this.custList = custList;
    }
    
    // Funtion: Read File
    public void readFile(String fName){
        File f = new File(fName);
        if(!f.exists()){
            System.out.println("The File" + fName + "Does not exists: Ignored.");
            return; 
        }else{
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Order anOrder;
                while(true){
                    anOrder = (Order)ois.readObject();
                    this.add(anOrder);
                    if( newOrderCode <= anOrder.getOrderCode()){
                        newOrderCode = anOrder.getOrderCode() + 1;
                    }
                }
            } catch (EOFException e) {
                System.out.println("All data in the file were read");
            } catch(IOException e) { // Input/Output
                System.err.println(e.getMessage());
            } 
            catch(ClassNotFoundException e){ // không tìm thấy định nghĩa của class
                System.err.println(e.getMessage()); 
            }  
        }
    }
    
    // Function: Write File
    public void writeFile(String fName){
        if(this.isEmpty()){
            System.out.println("List Empty");
        }else{
            try {
                FileOutputStream fo = new FileOutputStream(fName);
                ObjectOutputStream os = new ObjectOutputStream(fo);
                for (Order anOrder : this) {
                    os.writeObject(anOrder);
                }
                os.close();
                fo.close();
                System.out.println("Data are saved to file");
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    // Function: Print (add and update)
    public void printOrder(int orderCode, Customer cust, SetMenu setMnu, int numOfTables, Date PreferedDate){
        int total = numOfTables * setMnu.getPrice();
        //Xuat Khung
        String frame ="-----------------------------------------------------\n" +
                      " Customer order Information [Order ID:" + orderCode + "]\n" +
                      "------------------------------------------------------\n";
        System.out.println(frame);
        //Xuat du lieu khach hang
        String cusInfo = "CustomerID        :" + cust.getId()   + "\n" +
                         "Customer name     :" + cust.getName() + "\n" +
                         "Phone number      :" + cust.getPhone()+ "\n" +
                         "Email             :" + cust.getEmail()+ "\n" +
                         "----------------------------------------------------";
        System.out.println(cusInfo);
        //Xuat set menu
        DecimalFormat dfCustom = new DecimalFormat("#, #0");
        String menuInfo =
        "Code of set menu :" + setMnu.getMenuId()   + "\n" +
        "Set menu name    :" + setMnu.getMenuName() + "\n" +
        "Even date        :" + ConsoleInputter.dateStr(PreferedDate, DATE_PAT) + "\n"+
        "Number of tables :" + numOfTables + "\n" +
        "Price            :" + dfCustom.format(setMnu.getPrice()) + "\n";
        System.out.println(menuInfo);
         
        //Xuat ingredients
        String ingredientsInfo = "Ingredients:\n";
        String[] items = setMnu.getIngredients().split("#");
        ingredientsInfo += ( items[0] + "\n" + items[1] + "\n" + items[2]);
        System.out.println(ingredientsInfo);
        System.out.println("---------------------------------------------------");
        System.out.println("Total Cost      " + dfCustom.format(total) + "Vnd");
        System.out.println("---------------------------------------------------");
    }
    
    // function: add Order
    public void addOrder(){
        String custId;
        String SetMenuId;
        int numTable;
        Date preferedDate;
        Customer cust;
        SetMenu setMnu;
        
        while(true){
            custId = ConsoleInputter.getStr("Cust. code", ORDER_CODE_PAT, "Code : [(C/G/K) + 4 digits.]").toUpperCase();
            cust = custList.SearchById(custId);
            if(cust == null){
                System.out.println("The Customer does not exist");
            }else{
                break;
            }
        }
        
        setMnu = (SetMenu)ConsoleInputter.objMenu(this.setMenuList);
        SetMenuId = setMnu.getMenuId();
        numTable = ConsoleInputter.getInt("Number of table:", 1, 100);
        boolean before = true;
        do{
            preferedDate = ConsoleInputter.getDate("PreferedDate date-d/m/y", DATE_PAT);
            before = preferedDate.before(new Date());
            if(before == true){
                System.out.println("Prefered date must be after today");
            }
        }while(before == true);
        
        // xuat thong tin order truoc khi luu
        printOrder(newOrderCode, cust, setMnu, numTable, preferedDate);
        // hoi user co muon luu hay khong
        boolean response = ConsoleInputter.getBoolean("Save order ?  Y/N");
        if(response == true){
            Order newOrder = new Order(newOrderCode, custId, custId, SetMenuId, SetMenuId, numTable, preferedDate);
            this.add(newOrder);
            newOrderCode++;
            System.out.println("New Order was added");
        }
    }
    
    //function: update order
    public void updateOrder(){
        int orderCode;
        String custId;
        String setMenuId;
        int numTable;
        Date preferedDate;
        Customer cust;
        SetMenu setMnu;
        Order order;
        // nhap ma order can update
        orderCode = Integer.parseInt(ConsoleInputter.getStr("Update order code:"));
        order = this.searchByOrderId(orderCode);
        
        if(order == null){
            System.out.println("This order does not exist");
        }else{
            cust = custList.SearchById(order.getCustomerId());
            setMnu = (SetMenu)ConsoleInputter.objMenu(this.setMenuList);
            setMenuId = setMnu.getMenuId();
            numTable = ConsoleInputter.getInt("Number of table:", 1, 100);
            boolean before = true;
            do{
                preferedDate = ConsoleInputter.getDate("PreferedDate date-d/m/y", DATE_PAT);
                before = preferedDate.before(new Date());
                if(before == true){
                    System.out.println("Prefered date must be after today");
                }
            }while(before == true);
            // xuat thong tin
            printOrder(orderCode, cust, setMnu, numTable, preferedDate);
            // hoi user co muon luu hay khong
            boolean response = ConsoleInputter.getBoolean("Save order ? Y/N");
            if(response == true){
                order.setSetMenuCode(setMenuId);
                order.setNumOfTables(numTable);
                order.setPreferedDate(preferedDate);
                System.out.println("The order " + orderCode + "was updated");
            }
        }
        
    }
    
    //Function: searchByOrderId
    public Order searchByOrderId(int orderCode){
        for (Order o : this) {
            if(o.getOrderCode() == orderCode){
                return o;
            }
        }
        return null;
    }
    
    //Function: printOrderList
    public void printOrderList(){
        DecimalFormat dfCustom = new DecimalFormat("#, #0");
        if(this.isEmpty()){
            System.out.println("The order list is empty!");
        }else{
            // xuat khung
            String header;
            header = "-----------------------------------------------------------------------------------\n" +
                     "ID        |Event Date | Customer Id| Set Menu|        Price| Tables |         Cost|\n" +
                     "-----------------------------------------------------------------------------------";
            System.out.println(header);
            String footer;
            footer = "-----------------------------------------------------------------------------------\n";
            // Xuat cac order
            String setMenuCode;
            SetMenu setMnu;
            int total;
            for (Order order : this) {
                setMenuCode = order.getSetMenuCode(); // lay ra setMenu
                setMnu = setMenuList.searchById(setMenuCode);
                total = order.getNumOfTables() * setMnu.getPrice();
                Date preferedDate = order.getPreferedDate();
                String line = String.format("%-7s|%-10s| %-11s| %-8s|%10s|%7s|%11s|", 
                                            order.getOrderCode(),
                                            ConsoleInputter.dateStr(preferedDate, DATE_PAT),
                                            order.getCustomerId(),
                                            order.getSetMenuCode(),
                                            dfCustom.format(setMnu.getPrice()),
                                            order.getNumOfTables(),
                                            dfCustom.format(total));
                System.out.println(line);
            }
            System.out.println(footer);
        }
    }
}
