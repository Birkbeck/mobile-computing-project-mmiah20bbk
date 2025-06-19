package com.cc.culinarycompanion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cc.culinarycompanion.models.Recipe;
import com.cc.culinarycompanion.roomdb.RecipeDao;
import com.cc.culinarycompanion.roomdb.RecipeDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class RecipeDaoTest {

    private RecipeDatabase database;
    private RecipeDao recipeDao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, RecipeDatabase.class)
                .allowMainThreadQueries()
                .build();
        recipeDao = database.recipeDao();
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void insertRecipe_andGetById() throws Exception {
        Recipe recipe = new Recipe(0, "Pasta", "Delicious", "Noodles", "Boil", "Italian", "image_uri");
        recipeDao.insert(recipe);

        // Wait for the DB to auto-increment the ID
        LiveData<List<Recipe>> liveData = recipeDao.getAllRecipes();
        List<Recipe> list = getOrAwaitValue(liveData);
        Recipe inserted = list.get(0);

        LiveData<Recipe> fetchedLiveData = recipeDao.getRecipeById(inserted.getRecipeId());
        Recipe fetched = getOrAwaitValue(fetchedLiveData);

        assertEquals("Pasta", fetched.getRecipeName());
        assertEquals("Noodles", fetched.getIngredients());
    }

    @Test
    public void updateRecipe() throws Exception {
        Recipe recipe = new Recipe(0, "Pasta", "Delicious", "Noodles", "Boil", "Italian", "image_uri");
        recipeDao.insert(recipe);
        Recipe inserted = getOrAwaitValue(recipeDao.getAllRecipes()).get(0);

        inserted.setRecipeName("Pizza");
        recipeDao.update(inserted);

        Recipe updated = getOrAwaitValue(recipeDao.getRecipeById(inserted.getRecipeId()));
        assertEquals("Pizza", updated.getRecipeName());
    }

    @Test
    public void deleteRecipe() throws Exception {
        Recipe recipe = new Recipe(0, "Pasta", "Delicious", "Noodles", "Boil", "Italian", "image_uri");
        recipeDao.insert(recipe);
        Recipe inserted = getOrAwaitValue(recipeDao.getAllRecipes()).get(0);

        recipeDao.delete(inserted);
        List<Recipe> result = getOrAwaitValue(recipeDao.getAllRecipes());

        assertTrue(result.isEmpty());
    }

    @Test
    public void getRecipesByCategory() throws Exception {
        recipeDao.insert(new Recipe(0, "Pasta", "desc", "ingr", "inst", "Italian", "uri"));
        recipeDao.insert(new Recipe(0, "Tacos", "desc", "ingr", "inst", "Mexican", "uri"));
        recipeDao.insert(new Recipe(0, "Pizza", "desc", "ingr", "inst", "Italian", "uri"));

        List<Recipe> italian = getOrAwaitValue(recipeDao.getRecipesByCategory("Italian"));
        assertEquals(2, italian.size());
    }

    @Test
    public void deleteAllRecipes() throws Exception {
        recipeDao.insert(new Recipe(0, "A", "desc", "ingr", "inst", "X", "uri"));
        recipeDao.insert(new Recipe(0, "B", "desc", "ingr", "inst", "X", "uri"));

        recipeDao.deleteAllRecipes();
        List<Recipe> all = getOrAwaitValue(recipeDao.getAllRecipes());

        assertEquals(0, all.size());
    }

    // Helper to get value from LiveData synchronously
    public static <T> T getOrAwaitValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);

        liveData.observeForever(o -> {
            data[0] = o;
            latch.countDown();
        });

        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw new TimeoutException("LiveData value was never set.");
        }

        //noinspection unchecked
        return (T) data[0];
    }

    public static class TimeoutException extends RuntimeException {
        TimeoutException(String message) {
            super(message);
        }
    }
}

