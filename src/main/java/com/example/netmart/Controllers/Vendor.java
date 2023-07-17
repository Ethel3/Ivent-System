package com.example.netmart.Controllers;

import java.util.HashMap;

public class Vendor {
    private String name;
    private String location;
    private String email;
    private String number;
    private String address;

    public Vendor(String name, String location, String email, String number, String address){
        this.name = name;
        this.location = location;
        this.email = email;
        this.number = number;
        this.address = address;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static Vendor nullVendor(){
        return new Vendor(null, null, null, null, null);
    }

    public boolean isNull(){
        return name == null || location == null || address == null || number == null || email == null;
    }

    public HashMap<String, String> toHashMap(){
        HashMap<String, String> hashMappedVendor = new HashMap<>();
        hashMappedVendor.put("name", name);
        hashMappedVendor.put("location", location);
        hashMappedVendor.put("email", email);
        hashMappedVendor.put("number", number);
        hashMappedVendor.put("address", address);
        return hashMappedVendor;
    }

    @Override
    public String toString(){
        return "Vendor{" +
            "name -> '" + name + '\'' +
            ", address -> '" + location + '\'' +
            ", phone -> '" + email + '\'' +
            ", email -> '" + number + '\'' +
            ", email -> '" + address + '\'' +
            "}";
    }
}
