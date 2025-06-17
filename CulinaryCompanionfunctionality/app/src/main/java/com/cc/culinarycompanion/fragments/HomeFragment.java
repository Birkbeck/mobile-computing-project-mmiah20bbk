package com.cc.culinarycompanion.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cc.culinarycompanion.R;
import com.cc.culinarycompanion.adapters.RecipeAdapter;
import com.cc.culinarycompanion.databinding.FragmentHomeBinding;
import com.cc.culinarycompanion.models.Recipe;
import com.cc.culinarycompanion.roomdb.RecipeViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private RecipeAdapter adapter;
    private RecipeViewModel recipeViewModel;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.bind(view);

        navController = NavHostFragment.findNavController(this);
        binding.btnEdit.setOnClickListener(v -> {

            navController.navigate(R.id.action_homeFragment_to_editRecipeFragment);

        });


        // search the recipe
        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


        return view.getRootView();
    }

    @Override
    public void onResume() {
        super.onResume();
        // call the initialized methods
        setupRecyclerView();
        setupViewModel();
    }

    private void setupRecyclerView() {
        adapter = new RecipeAdapter(new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onEditClick(Recipe recipe) {
                Bundle bundle = new Bundle();
                bundle.putString("name", recipe.getRecipeName());
                bundle.putString("description", recipe.getDescription());
                bundle.putString("ingredients", recipe.getIngredients());
                bundle.putString("instructions", recipe.getInstructions());
                bundle.putString("category", recipe.getCategory());
                bundle.putString("image_uri", recipe.getImageUri());
                bundle.putInt("recipeId", recipe.getRecipeId());

                navController.navigate(R.id.action_homeFragment_to_editRecipeFragment, bundle);

            }

            @Override
            public void onDeleteClick(Recipe recipe) {
                deleteAlert(recipe);
            }

            @Override
            public void onCardClick(Recipe recipe) {
                Bundle bundle = new Bundle();
                bundle.putString("name", recipe.getRecipeName());
                bundle.putString("description", recipe.getDescription());
                bundle.putString("ingredients", recipe.getIngredients());
                bundle.putString("instructions", recipe.getInstructions());
                bundle.putString("category", recipe.getCategory());
                bundle.putString("image_uri", recipe.getImageUri());
                bundle.putInt("recipeId", recipe.getRecipeId());

                navController.navigate(R.id.action_homeFragment_to_recipeDetailFragment, bundle);
            }
        });

        binding.recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recipeRecyclerView.setHasFixedSize(true);
        binding.recipeRecyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recipeViewModel.getAllRecipes().observe(getViewLifecycleOwner(), recipes -> {
            adapter.setRecipes(recipes);
        });
    }

    private void deleteAlert(Recipe recipe) {

        new AlertDialog.Builder(requireActivity())
                .setTitle("Delete Recipe!")
                .setIcon(R.drawable.baseline_warning_24)
                .setMessage("Are you sure you want to delete " + recipe.getRecipeName() + " recipe?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Delete logic
                    recipeViewModel.delete(recipe);
                    Toast.makeText(getContext(), "Deleted: " + recipe.getRecipeName(), Toast.LENGTH_SHORT).show();


                })
                .setNegativeButton("No", null)
                .show();
    }
}