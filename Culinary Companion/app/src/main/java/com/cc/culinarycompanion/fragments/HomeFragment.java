package com.cc.culinarycompanion.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.cc.culinarycompanion.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class HomeFragment extends Fragment {


    ImageButton editRecipe, deleteBtn;
    CardView recipeCard;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        editRecipe = view.findViewById(R.id.btnEdit);
        deleteBtn = view.findViewById(R.id.btnDelete);
        recipeCard = view.findViewById(R.id.recipeCard);

        NavController navController = NavHostFragment.findNavController(this);
        editRecipe.setOnClickListener(v -> {

            navController.navigate(R.id.action_homeFragment_to_editRecipeFragment);

        });

        recipeCard.setOnClickListener(v -> {
            navController.navigate(R.id.action_homeFragment_to_recipeDetailFragment);

        });

        deleteBtn.setOnClickListener(v -> {

            deleteAlert();
        });



        return view.getRootView();
    }

    private void deleteAlert() {
//        new MaterialAlertDialogBuilder(getActivity().getApplicationContext())
//                .setTitle("Delete Recipe")
//                .setMessage("Are you sure you want to delete this recipe?")
//                .setCancelable(false)
//                .setIcon(R.drawable.baseline_warning_24)
//                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // Perform delete operation here
//                    }
//                })
//                .setNegativeButton("Cancel", null)
//                .setCancelable(true)
//                .show();
        new AlertDialog.Builder(requireActivity())
                .setTitle("Delete Recipe")
                .setIcon(R.drawable.baseline_warning_24)
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Delete logic
                })
                .setNegativeButton("No", null)
                .show();
    }
}