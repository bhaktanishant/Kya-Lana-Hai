package com.example.nishant.kyalanahai2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private static List<String> listArray = new ArrayList<String>();
    private static final String LIST_SHARED_PREFERENCES = "Default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        String savedData = settings.getString(LIST_SHARED_PREFERENCES, null);

        listView = (ListView)findViewById(R.id.itemList);
        Button button = (Button)findViewById(R.id.addButton);

        createAndSetAdapter();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText itemNameView = (EditText)findViewById(R.id.itemName);
                String itemName = itemNameView.getText().toString();
                if (!itemName.isEmpty()){
                    listArray.add(itemName);
                }

                createAndSetAdapter();
                itemNameView.setText("");
            }
        });
    }

    public void createAndSetAdapter(){
        if (!listArray.isEmpty()){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listArray);
            listView.setAdapter(adapter);
        }
    }
}