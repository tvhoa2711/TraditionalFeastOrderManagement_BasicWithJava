/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class SetMenuList extends ArrayList<SetMenu> {
    // Funtion: Read File
    public void readFile(String fName){
        File f = new File(fName);
        if(!f.exists()){
            System.out.println("The File" + fName + "does not exist");
            return;
        }else{
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                br.readLine(); // bo qua tieu de
                String line = br.readLine() ;
                while(line != null){
                    String[] parts = line.split(",");
                    if(parts.length == 4){
                        int price = Integer.parseInt(parts[2]);
                        parts[3] = parts[3].substring(1, parts[3].length() - 1);
                        SetMenu m = new SetMenu(parts[0], parts[1], price, parts[3]);
                        this.add(m);  // ← SỬA: thêm menu vào list
                    }
                    line = br.readLine();  // ← SỬA: đọc dòng tiếp theo để thoát vòng lặp
                }
                br.close();
                fr.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    // Function: Print
    public void print(){
        if(this.isEmpty()){
            System.out.println("Set menu list is empty");
            return;
        }
        // Khung
        String header = "---------------------------------------------------\n"+
                        " List of menus for ordering party:\n" +
                        "---------------------------------------------------\n";
        System.out.println(header);
        // xuat cac menu
        for (SetMenu m : this) {
            System.out.println(m.toStringScreen());
        }
    }
    
    //Function: SearchById
    public SetMenu searchById(String menuId){
        for (SetMenu m : this) {
            if(m.getMenuId().equalsIgnoreCase(menuId)){
                return m;
            }
        }
        return null;
    }
}
