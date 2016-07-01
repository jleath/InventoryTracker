package com.example.android.inventorytracker;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The Add New Product screen.
 */
public class NewProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_product_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // handle the add product button
        Button submitButton = (Button) findViewById(R.id.add_product_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pull in views
                EditText nameField = (EditText) findViewById(R.id.name_edit_text);
                EditText quantityField = (EditText) findViewById(R.id.quantity_edit_text);
                EditText supplierField = (EditText) findViewById(R.id.supplier_edit_text);
                EditText priceField = (EditText) findViewById(R.id.price_edit_text);
                EditText imageField = (EditText) findViewById(R.id.image_edit_text);

                // get values
                String name = nameField.getText().toString();
                String supplier = supplierField.getText().toString();
                String image = imageField.getText().toString();
                String quantity = quantityField.getText().toString();
                String price = priceField.getText().toString();

                // check for valid input
                if (name.length() == 0) {
                    Toast.makeText(NewProductActivity.this, "No product name entered", Toast.LENGTH_SHORT).show();
                } else if (supplier.length() == 0) {
                    Toast.makeText(NewProductActivity.this, "No supplier entered", Toast.LENGTH_SHORT).show();
                } else if (image.length() == 0) {
                    Toast.makeText(NewProductActivity.this, "No image url entered", Toast.LENGTH_SHORT).show();
                } else if (quantity.length() == 0) {
                    Toast.makeText(NewProductActivity.this, "No quantity entered", Toast.LENGTH_SHORT).show();
                } else if (price.length() == 0) {
                    Toast.makeText(NewProductActivity.this, "No price entered", Toast.LENGTH_SHORT).show();
                } else {
                    // build the inventory item
                    InventoryItem newProduct = new InventoryItem(name, supplier, image, Double.parseDouble(price), Integer.parseInt(quantity));
                    InventoryDataController controller = new InventoryDataController(NewProductActivity.this);
                    // insert into database
                    controller.insert(newProduct);
                    // notify user
                    Toast.makeText(NewProductActivity.this, name + " added to inventory", Toast.LENGTH_SHORT).show();
                    // return to main screen
                    NavUtils.navigateUpFromSameTask(NewProductActivity.this);
                }
            }
        });
    }

    // give us the ability to use the arrow at the top left to return to previous screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
