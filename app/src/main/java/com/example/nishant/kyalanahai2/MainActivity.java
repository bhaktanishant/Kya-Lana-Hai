package com.example.nishant.kyalanahai2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String saveString = "";
    private ListView listView;
    private static List<String> listArray = new ArrayList<>();
    private SaveListInSharedPreferences saveListInSharedPreferences = new SaveListInSharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        String savedData = settings.getString(saveString, "");
        if (!savedData.isEmpty()){
            listArray = saveListInSharedPreferences.getList(savedData);
        }

        listView = (ListView)findViewById(android.R.id.list);
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

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                        final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                        popupMenu.inflate(R.menu.popup_menu);
                        popupMenu.setOnMenuItemClickListener(
                                new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        switch (menuItem.getItemId()){
                                            case R.id.popup_menu_mil_gaya:
                                                view.setBackgroundColor(Color.parseColor("#AAFF7F"));
                                                return true;
                                            case R.id.popup_menu_nahi_mila:
                                                view.setBackgroundColor(Color.parseColor("#FFAAAA"));
                                                return true;
                                            case R.id.popup_menu_delete_saamaan:
                                                listArray.remove(position);
                                                createAndSetAdapter();
                                                return true;
                                            default:
                                                return false;
                                        }
                                    }
                                }
                        );
                        popupMenu.show();
                    }
                }
        );
    }

    public void createAndSetAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listArray);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.main_menu_creator){
            Toast.makeText(getApplicationContext(), "__/\\__Hello! This is Nishant Bhakta", Toast.LENGTH_SHORT).show();
            return true;
        }else if(menuItem.getItemId() == R.id.clearButton){
            listArray.clear();
            listView.setAdapter(null);
            return true;
        }else {
            return false;
        }
    }
}