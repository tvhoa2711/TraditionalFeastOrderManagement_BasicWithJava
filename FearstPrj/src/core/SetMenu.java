/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.text.DecimalFormat;

/**
 *
 * @author Administrator
 */
public class SetMenu {
    private String menuId;
    private String menuName;
    private int price;
    private String ingredients;

    public SetMenu(String menuId, String menuName, int price, String ingredients) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.ingredients = ingredients;
    }
    
    // getter
    
    public SetMenu() {
    }

    public String getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getPrice() {
        return price;
    }

    public String getIngredients() {
        return ingredients;
    }
    
    // setter
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return menuId + "," + menuName + "," + price;
    }
    
    public String toStringScreen(){
        DecimalFormat dfCustom = new DecimalFormat("#, ##0");
        String s = "menuId        :" + menuId + "\n" +
                   "menuName      :" + menuName + "\n" +
                   "Price         :" + dfCustom.format(price) + "Vnd\n" +
                   "Ingredients   :" + "\n";
        String[] items = ingredients.split("#");
        for (String item : items) {
            s += item + "\n";
        }
        s +="-----------------------------------------------------------------";
        return s;
    }
}
