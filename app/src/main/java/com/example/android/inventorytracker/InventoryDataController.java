package com.example.android.inventorytracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * A class with utitlity methods for working with the inventory database.
 */
public class InventoryDataController {
    private InventoryDbHelper dbHelper;

    public InventoryDataController(Context context) {
        dbHelper = new InventoryDbHelper(context);
    }

    /**
     * Add the inventory item into the database.
     */
    public void insert(InventoryItem newProduct) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (newProduct != null) {
            values.put(InventoryContract.Inventory.COLUMN_NAME_PRODUCT, newProduct.getName());
            values.put(InventoryContract.Inventory.COLUMN_NAME_SUPPLIER, newProduct.getSupplier());
            values.put(InventoryContract.Inventory.COLUMN_NAME_QUANTITY, newProduct.getQuantity());
            values.put(InventoryContract.Inventory.COLUMN_NAME_PRICE, newProduct.getPrice());
            values.put(InventoryContract.Inventory.COLUMN_NAME_IMAGE, newProduct.getImageUrl());
            db.insert(InventoryContract.Inventory.TABLE_NAME, null, values);
        } else {
            Log.w("dataController insert", "Attempt to insert null object into database");
        }
        db.close();
    }

    /**
     * Returns a cursor with the values of the columns for a given row id.
     */
    public InventoryItem read(String rowId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT ROWID, * FROM " + InventoryContract.Inventory.TABLE_NAME + " WHERE ROWID = " + rowId;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return buildInventoryItem(cursor);
    }

    /**
     * Return an arraylist of all the InventoryItems stored in the database.
     */
    public ArrayList<InventoryItem> readAll() {
        ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT ROWID, * FROM " + InventoryContract.Inventory.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                items.add(buildInventoryItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }

    /**
     * Delete the row associated with the id productId from the table.
     */
    public void delete(int productId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "ROWID =? ";
        String[] selectArgs = {Integer.toString(productId)};
        db.delete(InventoryContract.Inventory.TABLE_NAME, selectQuery, selectArgs);
        db.close();
    }

    /**
     * The app only requires the ability to update the quantity of an item, any other changes to a product
     * should be entered as a new product using the insert method.
     */
    public void updateQuantity(String itemId, int newQuantity) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryContract.Inventory.COLUMN_NAME_QUANTITY, newQuantity);
        String selection = "ROWID =?";
        String[] selectionArgs = {itemId};
        db.update(InventoryContract.Inventory.TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    /**
     * a helper function that takes a cursor and builds an InventoryItem from the data stored in it
     */
    public InventoryItem buildInventoryItem(Cursor cursor) {
        int itemId = cursor.getInt(0);
        String itemName = cursor.getString(2);
        String itemSupplier = cursor.getString(3);
        int itemQuantity = cursor.getInt(4);
        double itemPrice = cursor.getDouble(5);
        String itemImage = cursor.getString(6);
        InventoryItem result = new InventoryItem(itemName, itemSupplier, itemImage, itemPrice, itemQuantity);
        result.setId(itemId);
        return result;
    }

    // Helper method for clearing the database. This is only useful for testing and debugging. Not used in the app.
    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(InventoryContract.Inventory.TABLE_NAME, null, null);
        db.close();
    }
}
