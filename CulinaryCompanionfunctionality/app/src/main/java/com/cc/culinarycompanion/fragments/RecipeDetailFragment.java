package com.cc.culinarycompanion.fragments;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cc.culinarycompanion.R;
import com.cc.culinarycompanion.databinding.FragmentRecipeDetailBinding;

public class RecipeDetailFragment extends Fragment {


    private FragmentRecipeDetailBinding binding;

    ImageView backBtn;
    String name, description, ingredients, instructions, category, imageUri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        binding = FragmentRecipeDetailBinding.bind(view);

        // retrieve data from home fragment
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        description = bundle.getString("description");
        ingredients = bundle.getString("ingredients");
        instructions = bundle.getString("instructions");
        category = bundle.getString("category");
        imageUri = bundle.getString("image_uri");
//        recipeId = getArguments().getInt("recipeId", -1);

        // display data in fields
        binding.recipeTitle.setText(name);
        binding.recipeDescription.setText(description);
        binding.ingredientsList.setText(ingredients);
        binding.instructionsList.setText(instructions);
        binding.recipeCategory.setText("Category: " +category);
        binding.recipeImage.setImageURI(Uri.parse(imageUri));

        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });

        return view.getRootView();
    }
}