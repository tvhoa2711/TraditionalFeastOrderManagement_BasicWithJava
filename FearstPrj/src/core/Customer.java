/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Administrator
 */
public class Customer implements Serializable{
    private String id;
    private String name;
    private String phone;
    private String email;

    public Customer() {
    }

    public Customer(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    @Override    
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.phone);
        hash = 37 * hash + Objects.hashCode(this.email);
        return hash;
    }

    // override equal va hashcode
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
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        return Objects.equals(this.email, other.email);
    }

    //getter
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
    //setter

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        name = name.trim();
        int l = name.length();
        if (l >= 2 && l <= 25){
            this.name = name;
        }
    }

    public void setPhone(String phone) {
        phone = phone.trim();
        if(phone.matches("^0[235789][\\d]{8}$")){
            this.phone = phone;
        }
    }

    public void setEmail(String email) {
        email = email.trim();
        if(email.matches("^[\\w-\\.]+[\\w-\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")){
            this.email = email;
        }
    }
    
    // toString

    @Override
    public String toString() {
        return String.format("%8s|%-20s|%-11s|%-18s", id, name, phone, email);
    }
    
}

