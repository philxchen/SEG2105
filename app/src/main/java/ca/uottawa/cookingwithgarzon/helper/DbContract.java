package ca.uottawa.cookingwithgarzon.helper;

import android.provider.BaseColumns;

/**
 * This contract class defines the relations and attributes of the SQLite database
 * Based on documentation from
 *  https://developer.android.com/training/basics/data-storage/databases.html
 * Created by joel on 19/11/16.
 */

public final class DbContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DbContract() {}

    /* Inner classes that define table contents */
    public static class Recipe implements BaseColumns {
        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_RECIPE_NAME = "name";
        public static final String COLUMN_COST = "cost";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_SERVINGS = "servings";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_CUISINE = "cuisine";
        public static final String COLUMN_MEALTYPE = "mealtype";
        public static final String COLUMN_RECIPE_INGREDIENT = "recipeingredient";
    }

    public static class Step implements BaseColumns {
        public static final String TABLE_NAME = "step";
        public static final String COLUMN_INSTRUCTION = "instruction";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_NUMBER = "number";
        public static final String COLUMN_RECIPE = "recipe";
    }

    public static class Ingredient implements BaseColumns {
        public static final String TABLE_NAME = "ingredient";
        public static final String COLUMN_INGREDIENT_NAME = "name";
        public static final String COLUMN_INGREDIENT_PRICE = "price";
    }

    public static class RecipeIngredient implements BaseColumns {
        public static final String TABLE_NAME = "recipeIngredient";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_INGREDIENT_ID = "ingredient_id";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_UNIT = "unit";
    }

    public static class Cuisine implements BaseColumns {
        public static final String TABLE_NAME = "cuisine";
        public static final String COLUMN_CUISINE_NAME = "name";
    }

    public static class MealType implements BaseColumns {
        public static final String TABLE_NAME = "type";
        public static final String COLUMN_MEAL_TYPE_NAME = "name";
    }

    public static class ShoppingCart implements BaseColumns {
        public static final String TABLE_NAME = "shoppingcart";
    }

    public static class ShoppingCartIngredient implements BaseColumns {
        public static final String TABLE_NAME = "shoppingcartingredient";
        public static final String COLUMN_SHOPPINGCART_ID = "shoppingcart_id";
        public static final String COLUMN_RECIPEINGREDIENT_ID = "recipeingredient_id";
    }

}



