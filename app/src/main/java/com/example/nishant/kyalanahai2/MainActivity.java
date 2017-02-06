package com.example.nishant.kyalanahai2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String saveString = "";
    private ListView listView;
    private static List<String> listArray = new ArrayList<String>();
    private SaveListInSharedPreferences saveListInSharedPreferences = new SaveListInSharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        String savedData = settings.getString(saveString, "");
        if (!savedData.isEmpty()){
            listArray = saveListInSharedPreferences.getList(savedData);
            Log.d("isRunning : ", "True" );
        }

        listView = (ListView)findViewById(R.id.itemList);
        Button button = (Button)findViewById(R.id.addButton);

        if (!listArray.isEmpty()) {
            createAndSetAdapter();
        }

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText itemNameView = (EditText)findViewById(R.id.itemName);
                String itemName = itemNameView.getText().toString();
                if (!itemName.isEmpty()){
                    listArray.add(itemName);
                }
                if (!listArray.isEmpty()) {
                    createAndSetAdapter();
                }
                itemNameView.setText("");
            }
        });

        Button clearButton = (Button)findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                listArray.clear();
                listView.setAdapter(null);
            }
        });
    }

    public void createAndSetAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listArray);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (listArray != null){
            String listAsString = saveListInSharedPreferences.putList(listArray);
            SharedPreferences settings = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(saveString, listAsString);
            editor.apply();
        }
    }
}