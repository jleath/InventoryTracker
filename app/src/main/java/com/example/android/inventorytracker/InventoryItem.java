package com.example.android.inventorytracker;

/**
 * An item in inventory. Each item has a name, price, id, quantity, and supplier's email.
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

    public void setId(int id) {
        mProductId = id;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public String toString() {
        return mProductId + " " + mName + " " + mPrice + " " + mQuantity + " " + mSupplier + " " + mImageUrl;
    }
}
