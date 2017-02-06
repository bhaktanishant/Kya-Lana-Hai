package com.example.nishant.kyalanahai2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nishant on 2/5/2017.
 */

public class SaveListInSharedPreferences {

    public static String putList(List arrayList){

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < arrayList.size(); i++){
            if (i != arrayList.size()-1){
                stringBuilder.append(arrayList.get(i)).append("\n");
            }else {
                stringBuilder.append(arrayList.get(i));
            }
        }
        return stringBuilder.toString();
    }

    public static List getList(String listAsString){
        ArrayList listArray = new ArrayList();
        String[] stringArray = listAsString.split("\n");
        for (String singleString : stringArray){
            listArray.add(singleString);
        }
        return listArray;
    }
}
