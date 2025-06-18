package com.cc.culinarycompanion.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cc.culinarycompanion.models.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert
    void insert(Recipe recipe);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("DELETE FROM recipes")
    void deleteAllRecipes();

    @Query("SELECT * FROM recipes ORDER BY recipeId DESC")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE category = :category")
    LiveData<List<Recipe>> getRecipesByCategory(String category);

    @Query("SELECT * FROM recipes WHERE recipeId = :id")
    LiveData<Recipe> getRecipeById(int id);

}
