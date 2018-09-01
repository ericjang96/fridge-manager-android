package com.example.kevin.fridgemanager.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.kevin.fridgemanager.Adapters.RecyclerViewAdapter;
import com.example.kevin.fridgemanager.DomainModels.RecyclerViewItem;

import java.util.List;

/**
 * Created by kevin on Aug 30, 2018
 **/
public interface RecyclerViewActivity {
    void init();

    void refresh(View view);

    void showAddItemPrompt(View view);
}
