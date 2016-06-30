package com.example.android.inventorytracker;

/**
 * Created by jleath on 6/28/2016.
 */
public class InventoryItem {

    private String mName;
    private String mSupplier;
    private String mImageUrl;
    private double mPrice;
    private int mProductId;
    private int mQuantity;

    public InventoryItem(String name, String supplier, String imageUrl, double price, int quantity) {
        mName = name;
        mSupplier = supplier;
        mImageUrl = imageUrl;
        mPrice = price;
        mQuantity = quantity;
    }

    public String getName() {
        return mName;
    }

    public String getSupplier() {
        return mSupplier;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public double getPrice() {
        return mPrice;
    }

    public int getId() {
        return mProductId;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setId(int id) {
        mProductId = id;
    }

    public String toString() {
        return mProductId + " " + mName + " " + mPrice + " " + mQuantity + " " + mSupplier + " " + mImageUrl;
    }
}
