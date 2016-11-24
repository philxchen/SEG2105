package ca.uottawa.cookingwithgarzon.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ca.uottawa.cookingwithgarzon.model.*;

/**
 * Helper class that facilitates interaction with the SQLite Database
 * Created by joel on 19/11/16.
 */

public class RecipeDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Recipe.db";

    private static final String LOG = "RecipeDbHelper";
    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";


    // SQL Statements for creating tables
    private static final String SQL_CREATE_RECIPE_TABLE =
            "CREATE TABLE " + RecipeContract.Recipe.TABLE_NAME + " (" +
                    RecipeContract.Recipe._ID + " INTEGER PRIMARY KEY," +
                    RecipeContract.Recipe.COLUMN_RECIPE_NAME + REAL_TYPE + COMMA_SEP +
                    RecipeContract.Recipe.COLUMN_COST + TEXT_TYPE + COMMA_SEP +
                    RecipeContract.Recipe.COLUMN_DIFFICULTY + REAL_TYPE + COMMA_SEP +
                    RecipeContract.Recipe.COLUMN_SERVINGS + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for cuisine
                    RecipeContract.Recipe.COLUMN_CUISINE + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for mealtype
                    RecipeContract.Recipe.COLUMN_MEALTYPE + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for recipeingredient
                    RecipeContract.Recipe.COLUMN_RECIPE_INGREDIENT + INTEGER_TYPE + COMMA_SEP +
                    RecipeContract.Recipe.COLUMN_IMAGE + BLOB_TYPE + " )";

    private static final String SQL_CREATE_STEP_TABLE =
            "CREATE TABLE " + RecipeContract.Step.TABLE_NAME + " (" +
                    RecipeContract.Step._ID + " INTEGER PRIMARY KEY," +
                    RecipeContract.Step.COLUMN_INSTRUCTION + TEXT_TYPE + COMMA_SEP +
                    RecipeContract.Step.COLUMN_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    RecipeContract.Step.COLUMN_TIME + INTEGER_TYPE +
                    // foreign key for recipe
                    RecipeContract.Step.COLUMN_RECIPE + INTEGER_TYPE + " )";

    private static final String SQL_CREATE_INGREDIENT_TABLE =
            "CREATE TABLE " + RecipeContract.Ingredient.TABLE_NAME + " (" +
                    RecipeContract.Ingredient._ID + " INTEGER PRIMARY KEY," +
                    RecipeContract.Ingredient.COLUMN_INGREDIENT_NAME + TEXT_TYPE + COMMA_SEP +
                    RecipeContract.Ingredient.COLUMN_INGREDIENT_PRICE + REAL_TYPE + " )";

    private static final String SQL_CREATE_RECIPEINGREDIENT_TABLE =
            "CREATE TABLE " + RecipeContract.RecipeIngredient.TABLE_NAME + " (" +
                    RecipeContract.RecipeIngredient._ID + " INTEGER PRIMARY KEY," +
                    RecipeContract.RecipeIngredient.COLUMN_RECIPE_ID + INTEGER_TYPE + COMMA_SEP +
                    RecipeContract.RecipeIngredient.COLUMN_INGREDIENT_ID + INTEGER_TYPE + COMMA_SEP +
                    RecipeContract.RecipeIngredient.COLUMN_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                    RecipeContract.RecipeIngredient.COLUMN_UNIT + TEXT_TYPE + " )";

    private static final String SQL_CREATE_CUISINE_TABLE =
            "CREATE TABLE " + RecipeContract.Cuisine.TABLE_NAME + " (" +
                    RecipeContract.Cuisine._ID + " INTEGER PRIMARY KEY," +
                    RecipeContract.Cuisine.COLUMN_CUISINE_NAME + TEXT_TYPE + " )";

    private static final String SQL_CREATE_MEALTYPE_TABLE =
            "CREATE TABLE " + RecipeContract.MealType.TABLE_NAME + " (" +
                    RecipeContract.MealType._ID + " INTEGER PRIMARY KEY," +
                    RecipeContract.MealType.COLUMN_MEAL_TYPE_NAME + TEXT_TYPE + " )";


    // SQL statements for deleting tables
    private static final String SQL_DELETE_STEP_TABLE =
            "DROP TABLE IF EXISTS " + RecipeContract.Step.TABLE_NAME;

    private static final String SQL_DELETE_RECIPE_TABLE =
            "DROP TABLE IF EXISTS " + RecipeContract.Recipe.TABLE_NAME;

    private static final String SQL_DELETE_INGREDIENT_TABLE =
            "DROP TABLE IF EXISTS " + RecipeContract.Ingredient.TABLE_NAME;

    private static final String SQL_DELETE_RECIPEINGREDIENT_TABLE =
            "DROP TABLE IF EXISTS " + RecipeContract.RecipeIngredient.TABLE_NAME;

    private static final String SQL_DELETE_CUISINE_TABLE =
            "DROP TABLE IF EXISTS " + RecipeContract.Cuisine.TABLE_NAME;

    private static final String SQL_DELETE_MEALTYPE_TABLE =
            "DROP TABLE IF EXISTS " + RecipeContract.MealType.TABLE_NAME;


    public RecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RECIPE_TABLE);
        db.execSQL(SQL_CREATE_INGREDIENT_TABLE);
        db.execSQL(SQL_CREATE_RECIPEINGREDIENT_TABLE);
        db.execSQL(SQL_CREATE_STEP_TABLE);
        db.execSQL(SQL_CREATE_MEALTYPE_TABLE);
        db.execSQL(SQL_CREATE_CUISINE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not sure when upgrade would be invoked. Just drop everything
        db.execSQL(SQL_DELETE_RECIPEINGREDIENT_TABLE);
        db.execSQL(SQL_DELETE_INGREDIENT_TABLE);
        db.execSQL(SQL_DELETE_RECIPE_TABLE);
        db.execSQL(SQL_DELETE_CUISINE_TABLE);
        db.execSQL(SQL_DELETE_MEALTYPE_TABLE);
        db.execSQL(SQL_DELETE_STEP_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /** CRUD */

    /** Create a recipe */
    public long createRecipe(Recipe recipe, Step[] steps, RecipeIngredient[] recipeIngredients,
                             Cuisine cuisine, MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RecipeContract.Recipe.COLUMN_RECIPE_NAME, recipe.get_name());
        values.put(RecipeContract.Recipe.COLUMN_COST, recipe.get_cost());
        values.put(RecipeContract.Recipe.COLUMN_DIFFICULTY, recipe.get_difficulty());
        values.put(RecipeContract.Recipe.COLUMN_SERVINGS, recipe.get_servings());
        values.put(RecipeContract.Recipe.COLUMN_CUISINE, cuisine.get_id());
        values.put(RecipeContract.Recipe.COLUMN_MEALTYPE, type.get_id());
        for (RecipeIngredient ingredient : recipeIngredients) {
            values.put(RecipeContract.Recipe.COLUMN_RECIPE_INGREDIENT, ingredient.get_id());
        }
        long recipe_id = db.insert(RecipeContract.Recipe.TABLE_NAME, null, values);
        return recipe_id;
    }

    /**  create a recipeIngredient */
    public long createRecipeIngredient(Recipe recipe, Ingredient ingredient, long qty, String unit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RecipeContract.RecipeIngredient.COLUMN_RECIPE_ID, recipe.get_id());
        values.put(RecipeContract.RecipeIngredient.COLUMN_RECIPE_ID, ingredient.get_id());
        values.put(RecipeContract.RecipeIngredient.COLUMN_QUANTITY, qty);
        values.put(RecipeContract.RecipeIngredient.COLUMN_UNIT, unit);
        long recipeIngredient_id = db.insert(RecipeContract.RecipeIngredient.TABLE_NAME, null, values);
        return recipeIngredient_id;
    }

    /**  create an ingredient */
    public long createIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RecipeContract.Ingredient.COLUMN_INGREDIENT_NAME, ingredient.get_name());
        values.put(RecipeContract.Ingredient.COLUMN_INGREDIENT_PRICE, ingredient.get_price());
        long ingredient_id = db.insert(RecipeContract.Ingredient.TABLE_NAME, null, values);
        return ingredient_id;
    }

    /**  create a cuisine */
    public long createCuisine(Cuisine cuisine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RecipeContract.Cuisine.COLUMN_CUISINE_NAME, cuisine.get_name());
        long cuisine_id = db.insert(RecipeContract.Cuisine.TABLE_NAME, null, values);
        return cuisine_id;
    }

    /**  create a meal type */
    public long createMealType(MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RecipeContract.MealType.COLUMN_MEAL_TYPE_NAME, type.get_name());
        long type_id = db.insert(RecipeContract.MealType.TABLE_NAME, null, values);
        return type_id;
    }

    /** create a step */
    public long createStep(Step step) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RecipeContract.Step.COLUMN_INSTRUCTION, step.get_instruction());
        values.put(RecipeContract.Step.COLUMN_NUMBER, step.get_stepNumber());
        values.put(RecipeContract.Step.COLUMN_TIME, step.get_time());
        long step_id = db.insert(RecipeContract.Step.TABLE_NAME, null, values);
        return step_id;
    }

    /** create a grocery list */
    // TODO

    /** get a recipe by id */
    public Recipe getRecipe(long recipe_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectRecipeQuery = "SELECT * FROM " + RecipeContract.Recipe.TABLE_NAME + " WHERE "
                + RecipeContract.Recipe._ID + " = " + recipe_id;

        String selectRecipeIngredient = "SElECT * FROM " + RecipeContract.RecipeIngredient.TABLE_NAME +
                " WHERE " + RecipeContract.RecipeIngredient.COLUMN_RECIPE_ID + " = " + recipe_id;

        Log.e(LOG, selectRecipeQuery);

        Cursor c = db.rawQuery(selectRecipeQuery, null);

        if (c == null) return null;

        c.moveToFirst();
        Recipe recipe = new Recipe();
        recipe.set_id(c.getInt(c.getColumnIndex(RecipeContract.Recipe._ID)));
        recipe.set_name(c.getString(c.getColumnIndex(RecipeContract.Recipe.COLUMN_RECIPE_NAME)));
        recipe.set_cost(c.getDouble((c.getColumnIndex((RecipeContract.Recipe.COLUMN_COST)))));
        recipe.set_difficulty(c.getDouble(c.getColumnIndex(RecipeContract.Recipe.COLUMN_DIFFICULTY)));
        recipe.set_servings(c.getInt(c.getColumnIndex(RecipeContract.Recipe.COLUMN_SERVINGS)));

        c = db.rawQuery(selectRecipeIngredient, null);

        if (c != null)
            while (!c.isNull()) {

            }

        return recipe;
    }

}
