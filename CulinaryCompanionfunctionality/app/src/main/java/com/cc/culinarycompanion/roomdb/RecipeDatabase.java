package com.cc.culinarycompanion.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cc.culinarycompanion.models.Recipe;

@Database(entities = {Recipe.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {

    public static volatile RecipeDatabase instance;

    public abstract RecipeDao recipeDao();

    @SuppressWarnings("deprecation")

    public static RecipeDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (RecipeDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    RecipeDatabase.class, "recipe_database")
                            .fallbackToDestructiveMigration()// safe for now
                            .build();
                }
            }
        }
        return instance;
    }
}
