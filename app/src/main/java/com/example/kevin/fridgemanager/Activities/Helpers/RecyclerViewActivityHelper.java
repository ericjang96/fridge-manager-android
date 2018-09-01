package com.example.kevin.fridgemanager.Activities.Helpers;

import android.support.v7.widget.RecyclerView;

import com.example.kevin.fridgemanager.Adapters.RecyclerViewAdapter;
import com.example.kevin.fridgemanager.DomainModels.RecyclerViewItem;

import java.util.List;

/**
 * Created by kevin on Aug 30, 2018
 **/
public class RecyclerViewActivityHelper {
    public List<RecyclerViewItem> addItem(List<RecyclerViewItem> list, RecyclerViewItem item, RecyclerViewAdapter adapter, RecyclerView recyclerView){
        int position = list.size();
        adapter.insert(item);
        adapter.notifyItemInserted(position);
        recyclerView.smoothScrollToPosition(position);
        return adapter.getList();
    }

    public List<RecyclerViewItem> addItemAlphabetically(List<RecyclerViewItem> list, RecyclerViewItem item, RecyclerViewAdapter adapter, RecyclerView recyclerView){
        int position = positionToInsert(list, item);
        adapter.insertAtPosition(item, position);
        adapter.notifyItemInserted(position);
        recyclerView.smoothScrollToPosition(position);
        return adapter.getList();
    }

    public int findPositionByName(List<RecyclerViewItem> list, String name) {
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    public int positionToInsert(List<RecyclerViewItem> list, RecyclerViewItem item){
        if(list.size() == 0)
            return 0;
        String name = item.getName();
        int comparison = name.compareTo(list.get(0).getName());
        if(comparison < 1){
            return 0;
        }

        for(int i = 1; i < list.size(); i++){
            comparison = name.compareTo(list.get(i).getName());
            // if comparison is less than 1, new ingredient name is less than or equal
            // (i.e. earlier in alphabet)
            if(comparison < 1){
                return i;
            }
        }

        // If you can't find it, return the size of list
        return list.size();
    }
}
