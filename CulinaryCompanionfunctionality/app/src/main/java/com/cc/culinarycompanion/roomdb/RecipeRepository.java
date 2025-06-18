package com.cc.culinarycompanion.roomdb;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cc.culinarycompanion.models.Recipe;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecipeRepository {
    private RecipeDao recipeDao;
    private ExecutorService executorService;

    public RecipeRepository(Application application) {
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDao = database.recipeDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(final Recipe recipe) {
        executorService.execute(() -> recipeDao.insert(recipe));
    }

    public void update(final Recipe recipe) {
        executorService.execute(() -> recipeDao.update(recipe));
    }

    public void delete(final Recipe recipe) {
        executorService.execute(() -> recipeDao.delete(recipe));
    }

    public void deleteAllRecipes() {
        executorService.execute(recipeDao::deleteAllRecipes);
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return recipeDao.getAllRecipes();
    }

    public LiveData<Recipe> getRecipeById(int id) {
        return recipeDao.getRecipeById(id);
    }

}


