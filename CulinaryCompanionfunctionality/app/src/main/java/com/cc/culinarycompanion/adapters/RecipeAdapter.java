package com.cc.culinarycompanion.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cc.culinarycompanion.R;
import com.cc.culinarycompanion.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList = new ArrayList<>();
    private List<Recipe> fullRecipeList = new ArrayList<>(); // full list unfiltered
    private final OnRecipeClickListener listener;

    public interface OnRecipeClickListener {
        void onEditClick(Recipe recipe);
        void onDeleteClick(Recipe recipe);
        void onCardClick(Recipe recipe);
    }

    public RecipeAdapter(OnRecipeClickListener listener) {
        this.listener = listener;
    }

    public void setRecipes(List<Recipe> recipes) {

        fullRecipeList.clear();
        fullRecipeList.addAll(recipes);

        recipeList.clear();
        recipeList.addAll(recipes);

        notifyDataSetChanged();
    }

    // Filter method for searching by ingredients
    public void filter(String query) {
        recipeList.clear();

        if (query == null || query.trim().isEmpty()) {
            recipeList.addAll(fullRecipeList);
        } else {
            String lowerQuery = query.toLowerCase();
            for (Recipe recipe : fullRecipeList) {
                if (recipe.getIngredients() != null &&
                        recipe.getIngredients().toLowerCase().contains(lowerQuery)) {
                    recipeList.add(recipe);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.textTitle.setText(recipe.getRecipeName());
        holder.textDesc.setText(recipe.getDescription());
        holder.category.setText(recipe.getCategory());

        if (recipe.getImageUri() != null && !recipe.getImageUri().isEmpty()) {
            Uri uri = Uri.parse(recipe.getImageUri());
            holder.imageRecipe.setImageURI(uri);
        } else {
            holder.imageRecipe.setImageResource(R.drawable.cake); // default
        }

        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(recipe));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(recipe));
        holder.recipeCard.setOnClickListener(v -> listener.onCardClick(recipe));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageRecipe;
        TextView textTitle, textDesc, category;
        ImageButton btnEdit, btnDelete;
        CardView recipeCard;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRecipe = itemView.findViewById(R.id.imageRecipe);
            textTitle = itemView.findViewById(R.id.textRecipeTitle);
            textDesc = itemView.findViewById(R.id.textRecipeDesc);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            category = itemView.findViewById(R.id.textRecipeCategory);
            recipeCard = itemView.findViewById(R.id.recipeCard);
        }
    }
}

