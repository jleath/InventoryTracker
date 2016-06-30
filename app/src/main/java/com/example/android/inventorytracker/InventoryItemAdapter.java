package com.example.android.inventorytracker;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class InventoryItemAdapter extends ArrayAdapter<InventoryItem> {

    private Activity parentContext;

    public InventoryItemAdapter(Activity context, ArrayList<InventoryItem> inventoryItems) {
        super(context, 0, inventoryItems);
        parentContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // build a new view if we don't have any lying around
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inventory_list_item, parent, false);
        }
        final View listItemView = convertView;
        // have to declare this as final so that we can use it in inner anonymous class to build
        // the detail activity intent feature
        final InventoryItem currentInventoryItem = getItem(position);

        // grab views to populate
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name_label);
        TextView quantityTextView = (TextView) listItemView.findViewById(R.id.quantity_label);
        TextView priceTextView = (TextView) listItemView.findViewById(R.id.price_label);
        TextView idTextView = (TextView) listItemView.findViewById(R.id.id_label);

        // populate appropriate fields of views
        nameTextView.setText(currentInventoryItem.getName());
        priceTextView.setText(String.format("%.2f", currentInventoryItem.getPrice()));
        quantityTextView.setText(Integer.toString(currentInventoryItem.getQuantity()));
        idTextView.setText(Integer.toString(currentInventoryItem.getId()));

        // if a user clicks on the list item, open item details screen
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView idLabel = (TextView) listItemView.findViewById(R.id.id_label);
                Intent intent = new Intent(parentContext, ItemDetailActivity.class);
                intent.putExtra("PRODUCT_ID", idLabel.getText().toString());
                Log.v("id", idLabel.getText().toString());
                parentContext.startActivity(intent);
            }
        });

        // pull in sell item button and hook in onClickListener to update database
        Button sellButton = (Button) listItemView.findViewById(R.id.list_item_sale_button);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InventoryDataController controller = new InventoryDataController(parentContext);
                TextView idLabel = (TextView) listItemView.findViewById(R.id.id_label);
                TextView quantityLabel = (TextView) listItemView.findViewById(R.id.quantity_label);
                int newQuantity = Integer.parseInt(quantityLabel.getText().toString()) - 1;
                if (newQuantity < 0) {
                    Toast.makeText(parentContext, "Out of stock", Toast.LENGTH_SHORT).show();
                } else {
                    controller.updateQuantity(idLabel.getText().toString(), newQuantity);
                    quantityLabel.setText(String.valueOf(newQuantity));
                }
            }
        });

        return listItemView;
    }
}