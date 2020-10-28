package com.example.allchip.data.model;

import java.sql.Timestamp;

public class Good {
    private int id;
    private String item_index;//合同下序列号
    private String number;//合同号
    private String type; //型号
    private String year;//年份
    private String manufacturer; //生产商
    private String packageName ; //Package
    private float unit_price; //单价
    private String creator; //订单生成人
    private String remark;
    private int count; //订单数量
    private int package_count; //包装数量
    private String package_time; //装包时间
    private float total_price; //总价

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPackage_count() {
        return package_count;
    }

    public void setPackage_count(int package_count) {
        this.package_count = package_count;
    }

    public String getPackage_time() {
        return package_time;
    }

    public void setPackage_time(String package_time) {
        this.package_time = package_time;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPackage() {
        return packageName;
    }

    public void setPackage(String packageName) {
        this.packageName = packageName;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public String getItem_index() {
        return item_index;
    }

    public void setItem_index(String item_index) {
        this.item_index = item_index;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", year='" + year + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", packageName='" + packageName + '\'' +
                ", unitPrice=" + unit_price +
                ", creator='" + creator + '\'' +
                ", remark='" + remark + '\'' +
                ", count=" + count +
                ", package_count=" + package_count +
                ", package_time=" + package_time +
                ", totalPrice=" + total_price +
                '}';
    }
}
