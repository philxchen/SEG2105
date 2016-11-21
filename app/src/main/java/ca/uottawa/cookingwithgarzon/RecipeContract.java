package ca.uottawa.cookingwithgarzon;

import android.provider.BaseColumns;

/**
 * This contract class defines the relations and attributes of the SQLite database
 * Based on documentation from
 *  https://developer.android.com/training/basics/data-storage/databases.html
 * Created by joel on 19/11/16.
 */

public final class RecipeContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private RecipeContract() {}

    /* Inner classes that define table contents */
    public static class Recipe implements BaseColumns {
        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_NAME_RECIPE_NAME = "name";
        public static final String COLUMN_NAME_COST = "cost";
        public static final String COLUMN_NAME_DIFFICULTY = "difficulty";
        public static final String COLUMN_NAME_SERVINGS = "servings";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_CUISINE = "cuisine";
        public static final String COLUMN_NAME_MEALTYPE = "mealtype";
    }

    public static class Step implements BaseColumns {
        public static final String TABLE_NAME = "step";
        public static final String COLUMN_NAME_INSTRUCTION = "instruction";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_NUMBER = "number";
        public static final String COLUMN_NAME_RECIPE = "recipe";
    }

    public static class Ingredient implements BaseColumns {
        public static final String TABLE_NAME = "ingredient";
        public static final String COLUMN_NAME_INGREDIENT_NAME = "name";
        public static final String COLUMN_NAME_INGREDIENT_PRICE = "price";
    }

    public static class RecipeIngredient implements BaseColumns {
        public static final String TABLE_NAME = "recipeIngredient";
        public static final String COLUMN_NAME_RECIPE_ID = "recipe_id";
        public static final String COLUMN_NAME_INGREDIENT_ID = "ingredient_id";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_UNIT = "unit";
    }

    public static class Cuisine implements BaseColumns {
        public static final String TABLE_NAME = "cuisine";
        public static final String COLUMN_NAME_CUISINE_NAME = "name";
    }

    public static class MealType implements BaseColumns {
        public static final String TABLE_NAME = "type";
        public static final String COLUMN_NAME_MEAL_TYPE_NAME = "name";
    }

//    public static class GroceryList implements BaseColumns {
//        public static final String TABLE_NAME = "groceryList";
//        public static final String COLUMN_NAME_TITLE = "title";
//        // public static final String COLUMN_NAME_SUBTITLE = "subtitle";
//    }
}



