package com.example.android.inventorytracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by jleath on 6/28/2016.
 */
public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Pull in and load selected product information
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String productId = extras.getString("PRODUCT_ID");
            final int prodId = Integer.parseInt(productId);
            InventoryDataController controller = new InventoryDataController(this);
            InventoryItem currentItem = controller.read(productId);

            // get the views
            TextView name = (TextView) findViewById(R.id.detail_name);
            ImageView image = (ImageView) findViewById(R.id.detail_image);
            TextView quantity = (TextView) findViewById(R.id.detail_quantity);
            TextView supplier = (TextView) findViewById(R.id.detail_supplier);
            TextView price = (TextView) findViewById(R.id.detail_price);
            TextView idLabel = (TextView) findViewById(R.id.id_label);

            // set the values of the views
            name.setText(currentItem.getName());
            quantity.setText(Integer.toString(currentItem.getQuantity()));
            supplier.setText(currentItem.getSupplier());
            price.setText(String.format("%.2f", currentItem.getPrice()));
            idLabel.setText(productId);
            Picasso.with(this).load(currentItem.getImageUrl()).resize(150, 150).centerCrop().into(image);

            // special view handling so that the user at least gets some kind of notification if
            // the details fail to load
            TextView error = (TextView) findViewById(R.id.no_data);
            error.setVisibility(View.GONE);
        } else {
            TextView error = (TextView) findViewById(R.id.no_data);
            error.setVisibility(View.VISIBLE);
            Button sellButton = (Button) findViewById(R.id.detail_sale_button);
            Button orderButton = (Button) findViewById(R.id.detail_order_button);
            Button receiveButton = (Button) findViewById(R.id.detail_receive_button);
            Button deleteButton = (Button) findViewById(R.id.detail_delete_button);

            sellButton.setVisibility(View.GONE);
            orderButton.setVisibility(View.GONE);
            receiveButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        }

        // build functionality for delete button
        Button deleteButton = (Button) findViewById(R.id.detail_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // give the user a chance to reconsider
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailActivity.this);
                builder.setMessage("Are you sure you want to delete this product?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        InventoryDataController controller = new InventoryDataController(ItemDetailActivity.this);
                        TextView idLabel = (TextView) findViewById(R.id.id_label);
                        int prodId = Integer.parseInt(idLabel.getText().toString());
                        controller.delete(prodId);
                        Toast.makeText(ItemDetailActivity.this, "Inventory item deleted", Toast.LENGTH_SHORT).show();

                        NavUtils.navigateUpFromSameTask(ItemDetailActivity.this);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });

        // increment the inventory of this item
        Button receiveButton = (Button) findViewById(R.id.detail_receive_button);
        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InventoryDataController controller = new InventoryDataController(ItemDetailActivity.this);
                TextView quantity = (TextView) findViewById(R.id.detail_quantity);
                TextView idLabel = (TextView) findViewById(R.id.id_label);
                int newQuantity = Integer.parseInt(quantity.getText().toString()) + 1;
                controller.updateQuantity(idLabel.getText().toString(), newQuantity);
                quantity.setText(String.valueOf(newQuantity));
            }
        });

        // send email intent with prepopulated subject, addressee, and body
        Button orderButton = (Button) findViewById(R.id.detail_order_button);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView email = (TextView) findViewById(R.id.detail_supplier);
                TextView name = (TextView) findViewById(R.id.detail_name);
                String[] addresses = {email.getText().toString()};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, name.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, "I NEED MORE " + name.getText().toString().toUpperCase());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(ItemDetailActivity.this, "No email app available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // pull in sell item button and hook in onClickListener to update database
        Button sellButton = (Button) findViewById(R.id.detail_sale_button);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InventoryDataController controller = new InventoryDataController(ItemDetailActivity.this);
                TextView idLabel = (TextView) findViewById(R.id.id_label);
                TextView quantityLabel = (TextView) findViewById(R.id.detail_quantity);
                int newQuantity = Integer.parseInt(quantityLabel.getText().toString()) - 1;
                if (newQuantity < 0) {
                    Toast.makeText(ItemDetailActivity.this, "Out of stock", Toast.LENGTH_SHORT).show();
                } else {
                    controller.updateQuantity(idLabel.getText().toString(), newQuantity);
                    quantityLabel.setText(String.valueOf(newQuantity));
                }
            }
        });
    }
}
