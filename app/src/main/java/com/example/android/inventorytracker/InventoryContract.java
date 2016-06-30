package com.example.android.inventorytracker;

import android.provider.BaseColumns;

/**
 * Created by jleath on 6/28/2016.
 */
public final class InventoryContract {
    public InventoryContract() {
    }

    /** Schema contract for the inventory table */
    public static abstract class Inventory implements BaseColumns {
        public static final String TABLE_NAME = "inventory";
        public static final String COLUMN_NAME_ID = "productid";
        public static final String COLUMN_NAME_PRODUCT = "product";
        public static final String COLUMN_NAME_SUPPLIER = "supplier";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_IMAGE = "image";

        private static final String TEXT_TYPE = " TEXT";
        private static final String REAL_TYPE = " REAL";
        private static final String INTEGER_TYPE = " INTEGER";
        private static final String COMMA_SEP = ",";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                        COLUMN_NAME_PRODUCT + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_SUPPLIER + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_NAME_PRICE + REAL_TYPE + COMMA_SEP +
                        COLUMN_NAME_IMAGE + TEXT_TYPE + " )";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
