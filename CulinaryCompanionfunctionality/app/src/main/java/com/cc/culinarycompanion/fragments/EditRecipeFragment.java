package com.cc.culinarycompanion.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.cc.culinarycompanion.R;
import com.cc.culinarycompanion.databinding.FragmentEditRecipeBinding;
import com.cc.culinarycompanion.models.Recipe;
import com.cc.culinarycompanion.roomdb.RecipeViewModel;

public class EditRecipeFragment extends Fragment {

    private FragmentEditRecipeBinding binding;
    private NavController navController;
    RecipeViewModel recipeViewModel;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private Uri selectedImageUri;

    private ActivityResultLauncher<String> permissionLauncher;
    String name, description, ingredients, instructions, category, imageUri;
    int recipeId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_recipe, container, false);
        binding = FragmentEditRecipeBinding.bind(view);

        // retrieve data from home fragment
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        description = bundle.getString("description");
        ingredients = bundle.getString("ingredients");
        instructions = bundle.getString("instructions");
        category = bundle.getString("category");
        imageUri = bundle.getString("image_uri");
        recipeId = getArguments().getInt("recipeId", -1);

        // display data in fields
        binding.titleTIET.setText(name);
        binding.descriptionTIET.setText(description);
        binding.ingredientTIET.setText(ingredients);
        binding.instructionTIET.setText(instructions);
        binding.categoryAutoComplete.setText(category);
        binding.recipeImage.setImageURI(Uri.parse(imageUri));

        String[] categories = getResources().getStringArray(R.array.category_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, categories);
        binding.categoryAutoComplete.setAdapter(adapter);

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        // permission launcher to check the gallery permissions
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        pickImage();
                    } else {
                        Toast.makeText(getContext(), "Permission required to pick image", Toast.LENGTH_SHORT).show();
                    }
                });

        // image picker launcher to pick the image from gallery using intent launcher
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        getActivity().getContentResolver().takePersistableUriPermission(
                                selectedImageUri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );
                        binding.recipeImage.setImageURI(selectedImageUri);
                    }
                }
        );

        binding.recipeImage.setOnClickListener(v -> {
            pickImageWithPermission();
        });

        binding.btnEdit.setOnClickListener(v -> {
            checkEmptyFields();
        });

        navController = NavHostFragment.findNavController(this);
        binding.backBtn.setOnClickListener(v -> {
            navController.popBackStack();
        });
        return view.getRootView();
    }

    private void pickImageWithPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ needs READ_MEDIA_IMAGES
            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
        } else {
            // Android 12 and below
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        imagePickerLauncher.launch(intent);
    }

    private void checkEmptyFields() {

        String name = binding.titleTIET.getText().toString();
        String description = binding.descriptionTIET.getText().toString();
        String ingredients = binding.ingredientTIET.getText().toString();
        String instructions = binding.instructionTIET.getText().toString();
        String category = binding.categoryAutoComplete.getText().toString();

        if (name.isEmpty() && description.isEmpty() && ingredients.isEmpty() && instructions.isEmpty() && category.isEmpty()) {
            binding.titleTIET.setError("Required");
            binding.descriptionTIET.setError("Required");
            binding.ingredientTIET.setError("Required");
            binding.instructionTIET.setError("Required");
            binding.categoryAutoComplete.setError("Required");
        } else if (category.isEmpty()) {
            binding.categoryAutoComplete.setError("Required");

        } else if (name.isEmpty()) {
            binding.titleTIET.setError("Required");

        } else if (description.isEmpty()) {
            binding.descriptionTIET.setError("Required");

        } else if (ingredients.isEmpty()) {
            binding.ingredientTIET.setError("Required");

        } else if (instructions.isEmpty()) {
            binding.instructionTIET.setError("Required");

        } else {
            editRecipe(recipeId, name, description, ingredients, instructions, category);
        }
    }

    private void editRecipe(int recipeId, String name, String description, String ingredients, String instructions,
                            String category) {

        Recipe updatedRecipe;
        if (selectedImageUri != null) {
            updatedRecipe = new Recipe(recipeId, name, description, ingredients, instructions, category, selectedImageUri.toString()); // put imageUri if needed
        } else {
            updatedRecipe = new Recipe(recipeId, name, description, ingredients, instructions, category, imageUri); // put imageUri if needed
        }
        recipeViewModel.update(updatedRecipe);

        Toast.makeText(getContext(), "Recipe updated", Toast.LENGTH_SHORT).show();

        binding.titleTIET.setText("");
        binding.descriptionTIET.setText("");
        binding.ingredientTIET.setText("");
        binding.instructionTIET.setText("");
        binding.categoryAutoComplete.setText("");
        binding.recipeImage.setImageResource(R.drawable.baseline_add_a_photo_24);
        selectedImageUri = null;

        navController.popBackStack();


    }
}