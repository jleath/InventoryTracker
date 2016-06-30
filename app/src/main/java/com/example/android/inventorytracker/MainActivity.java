package com.example.android.inventorytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize stetho for debugging
        Stetho.newInitializerBuilder(this)
                .enableDumpapp(
                        Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                        Stetho.defaultInspectorModulesProvider(this))
                .build();

        final InventoryDataController controller = new InventoryDataController(MainActivity.this);

        // build and hook in adapter
        InventoryItemAdapter adapter = new InventoryItemAdapter(MainActivity.this, controller.readAll());
        final ListView listView = (ListView) findViewById(R.id.inventory_list);
        listView.setAdapter(adapter);

        // display "no data" message if there is no data
        if (adapter.getCount() == 0) {
            ((TextView) findViewById(R.id.no_data)).setVisibility(View.VISIBLE);
        } else {
            ((TextView) findViewById(R.id.no_data)).setVisibility(View.GONE);
        }

        // because other screens may change the data in our database, reload the items on the main screen
        // each time it gains focus
        listView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InventoryItemAdapter adapter = new InventoryItemAdapter(MainActivity.this, controller.readAll());
                listView.setAdapter(adapter);
                if (adapter.getCount() == 0) {
                    ((TextView) findViewById(R.id.no_data)).setVisibility(View.VISIBLE);
                } else {
                    ((TextView) findViewById(R.id.no_data)).setVisibility(View.GONE);
                }
            }
        });

        // set up order button to send us to product entry activity
        Button newProductButton = (Button) findViewById(R.id.add_new_product_button);
        newProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewProductActivity.class);
                startActivity(intent);
            }
        });
    }
}
