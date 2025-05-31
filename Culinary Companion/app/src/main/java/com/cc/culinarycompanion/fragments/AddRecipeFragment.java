package com.cc.culinarycompanion.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.cc.culinarycompanion.R;

public class AddRecipeFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        AppCompatAutoCompleteTextView categoryAutoComplete = view.findViewById(R.id.category_auto_complete);
        String[] categories = getResources().getStringArray(R.array.category_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, categories);
        categoryAutoComplete.setAdapter(adapter);
        return view.getRootView();
    }
}