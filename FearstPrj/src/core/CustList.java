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
import java.util.ArrayList;
import tool.ConsoleInputter;

/**
 *
 * @author Administrator
 */
public class CustList extends ArrayList<Customer> {
    static String CODE_PAT = "^[cCgGkK][\\d]{4}$";
    static String PHONE_PAT = "^0[235789][\\d]{8}$";
    static String EMAIL_PAT = "^[\\w-\\.]+[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    static String NAME_PAT = "^[\\w][\\w ]{1,24}$";
    private String pathFile = "customers.dat";
    /*
        customers.dat là lúc dâu là nó không có khi nào chay xong hàm save thì nó moi duoc tao lan dau tien
    */
    private boolean isSaved = true;
    /* Cờ (flag) đánh dấu dữ liệu đã được lưu vào file chưa
     true  = dữ liệu trong RAM giống với file (đã lưu hoặc chưa thay đổi gì)
     false = có thay đổi chưa được lưu (thêm mới, cập nhật, ...)
    */

    public CustList() {
    }

    public boolean isIsSaved() {
        return isSaved;
    }
    
    // Function 1 : Thêm Khách Hàng
    /*
    Idea:
        1. Kiem tra xem mã khách hàng tôn t?i chua
        2. nêu tt thì báo lôi => không thêm
        3. nêu chua tôn tai => thêm vào danh sách, dánh dau chua luu
    
    Nhung muon kiem tra xem khach hang ton tai chua thi phai can ham search => tao ham search
    sau do khoi tao cac bien cusId, name, phone, email
    roi chay vong lap de kiem tra xem custId no co ton tai khong bang vong while
    sau do nhap thong tin con lai name, phone, email
    sau do tao customer moi de chua nhung thong tin do
    xong them vao bang this.add()
    xong thong bao là duoc
    */
    public void addNewCustomer(){
        String custId, name, phone, email;
        while(true){
            custId = ConsoleInputter.getStr("Cust. code (CGK 4 digits)", CODE_PAT, "Format: [CGK]0000").toUpperCase();
            if(SearchById(custId) != null){
                System.out.println("The Customer Id is duplcated!");
            }else{
                break;
            }
        }
        // nhap nhung thong tin con lai
        name = ConsoleInputter.getStr("Cust.name", NAME_PAT, "Name contains at least 2 characters");
        phone = ConsoleInputter.getStr("Phone no.", PHONE_PAT, "VN Phone: 10 digits");
        email = ConsoleInputter.getStr("Email", EMAIL_PAT, "Email: any@any.any");
        
        Customer newCust = new Customer(custId, name, phone, email);
        this.add(newCust);
        isSaved = false;
        System.out.println("Customer Registered Successfully");
    }
    
    public Customer SearchById(String id){
        for (Customer c : this) {
            if(c.getId().equalsIgnoreCase(id)){
                return c;
            }
        }
        return null;
    }
    
    // Function 2: Update
    public void updateCust(){
        if(this.isEmpty()) System.out.println("Empty List");
        else{
            String custId, newName, phone, email;
            custId = ConsoleInputter.getStr("Search code").trim().toUpperCase();
            
            Customer c = SearchById(custId);
            if(c != null){
                newName = ConsoleInputter.getStr("New Name", NAME_PAT, "Name contains at least 2 characters");
                phone = ConsoleInputter.getStr("Phone no.", PHONE_PAT, "VN Phone: 10 digits");
                email = ConsoleInputter.getStr("Email", EMAIL_PAT, "Email: any@any.any");
                
                c.setName(newName);
                c.setPhone(phone);
                c.setEmail(email);
                
                isSaved = false; 
                System.out.println("Update Successful!");
            }else{
                System.out.println("This customer does not exist.");
            }
        }
    }
    // Funtion: Print
    public void print(){
        if(this.isEmpty()){
            System.out.println("Does not have any customer information.");
        }else{
            String header =
            "-----------------------------------------------------------------\n" +
            "CustId    | Customer Name      | Phone         |Email            \n" +
            "-----------------------------------------------------------------\n";
            String footer =
            "-----------------------------------------------------------------\n";
            System.out.println(header);
            for (Customer cust : this) {
                System.out.println(cust);
            }
            System.out.println(footer);
        }
    
    
}
    // Function 3: SearchByName
    /*
    b1:Check coi danh sach do co trong hay khong
    b2:nhap cai name muon tim bang ConsoleInputter kem theo do nho trim và IN HOA len de de so sanh
    b3:Nho tao 1 list danh sach vi o day co the co 3 den 4 name trung nhau
    b4:Sau do chay vong lap kem dieu kien rôi add customer do vao danh sach moi tao
    b5:Kiem tra xem danh sach do co rong khong, Khong rõng thi in
    */
    public void searchName(){
        if(this.isEmpty()){
            System.out.println("Empty List");
        }else{
            String searchName = ConsoleInputter.getStr("Search Name").trim().toUpperCase();
            CustList resultList = new CustList();
            for (Customer cust : this) {
                if(cust.getName().toUpperCase().contains(searchName)){
                    resultList.add(cust);
                }
            }
            if(resultList.isEmpty()){
                System.out.println("No one matches the search criteria!");
            }else{
                resultList.print();
            }
        }
    }
    
    // Funtion: Read File
    public void readFile(String fName){
        File f = new File(fName);
        if(!f.exists()){
            System.out.println("The File" + fName + "Does not exists: Ignored.");
        }else{
            try {
                FileInputStream fis = new FileInputStream(f); // Lớp 1: kết nối vật lý tới file
                ObjectInputStream ois = new ObjectInputStream(fis); // Lớp 2: "bọc" lớp 1, hiểu định dạng Object
                Customer cust;
                while(true){
                    cust = (Customer)ois.readObject();
                    this.add(cust);
                }
            }
            catch(EOFException  e) { //End Of File
                System.out.println("All data in the file were read");
            } 
            catch(IOException e) { // Input/Output
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
            System.out.println("Empty List.");
        }else{
            try {
                FileOutputStream fo = new FileOutputStream(fName);
                ObjectOutputStream os = new ObjectOutputStream(fo);
                for (Customer cust : this) {
                    os.writeObject(cust);
                }
                isSaved = true;
                os.close();
                fo.close();
                System.out.println("Data are saved to file");
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}
