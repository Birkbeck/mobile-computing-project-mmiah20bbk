package com.cc.culinarycompanion.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    public int recipeId;

    @ColumnInfo(name = "recipe_name")
    public String recipeName;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "ingredients")
    public String ingredients;

    @ColumnInfo(name = "instructions")
    public String instructions;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "recipe_image")
    public String imageUri;


    public Recipe(int recipeId, String recipeName, String description, String ingredients, String instructions, String category, String imageUri) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.category = category;
        this.imageUri = imageUri;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}


