package com.adnanap.jstore_android_adnan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Response;

import org.json.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Supplier> listSupplier = new ArrayList<>();
    private ArrayList<Item> listItem = new ArrayList<>();
    private HashMap<Supplier, ArrayList<Item>> childMapping = new HashMap<>();

    public void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject item = jsonResponse.getJSONObject(i);
                        JSONObject supplier = item.getJSONObject("supplier");
                        JSONObject location = supplier.getJSONObject("location");

                        listSupplier.add(new Supplier(supplier.getInt("id"), supplier.getString("name"), supplier.getString("email"), supplier.getString("phoneNumber"), new Location(location.getString("province"), location.getString("description"), location.getString("city"))));
                        listItem.add(new Item(item.getInt("id"), item.getInt("price"), item.getString("name"), item.getString("category"), item.getString("status"), new Supplier(supplier.getInt("id"), supplier.getString("name"), supplier.getString("email"), supplier.getString("phoneNumber"), new Location(location.getString("province"), location.getString("description"), location.getString("city")))));
                        childMapping.put(listSupplier.get(i), listItem);
                    }
                }
                catch (JSONException e){
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
