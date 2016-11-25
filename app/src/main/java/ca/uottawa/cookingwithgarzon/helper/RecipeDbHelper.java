package ca.uottawa.cookingwithgarzon.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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
            "CREATE TABLE " + dbContract.Recipe.TABLE_NAME + " (" +
                    dbContract.Recipe._ID + " INTEGER PRIMARY KEY," +
                    dbContract.Recipe.COLUMN_RECIPE_NAME + REAL_TYPE + COMMA_SEP +
                    dbContract.Recipe.COLUMN_COST + TEXT_TYPE + COMMA_SEP +
                    dbContract.Recipe.COLUMN_DIFFICULTY + REAL_TYPE + COMMA_SEP +
                    dbContract.Recipe.COLUMN_SERVINGS + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for cuisine
                    dbContract.Recipe.COLUMN_CUISINE + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for mealtype
                    dbContract.Recipe.COLUMN_MEALTYPE + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for recipeingredient
                    dbContract.Recipe.COLUMN_RECIPE_INGREDIENT + INTEGER_TYPE + COMMA_SEP +
                    dbContract.Recipe.COLUMN_IMAGE + BLOB_TYPE + " )";

    private static final String SQL_CREATE_STEP_TABLE =
            "CREATE TABLE " + dbContract.Step.TABLE_NAME + " (" +
                    dbContract.Step._ID + " INTEGER PRIMARY KEY," +
                    dbContract.Step.COLUMN_INSTRUCTION + TEXT_TYPE + COMMA_SEP +
                    dbContract.Step.COLUMN_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    dbContract.Step.COLUMN_TIME + INTEGER_TYPE +
                    // foreign key for recipe
                    dbContract.Step.COLUMN_RECIPE + INTEGER_TYPE + " )";

    private static final String SQL_CREATE_INGREDIENT_TABLE =
            "CREATE TABLE " + dbContract.Ingredient.TABLE_NAME + " (" +
                    dbContract.Ingredient._ID + " INTEGER PRIMARY KEY," +
                    dbContract.Ingredient.COLUMN_INGREDIENT_NAME + TEXT_TYPE + COMMA_SEP +
                    dbContract.Ingredient.COLUMN_INGREDIENT_PRICE + REAL_TYPE + " )";

    private static final String SQL_CREATE_RECIPEINGREDIENT_TABLE =
            "CREATE TABLE " + dbContract.RecipeIngredient.TABLE_NAME + " (" +
                    dbContract.RecipeIngredient._ID + " INTEGER PRIMARY KEY," +
                    dbContract.RecipeIngredient.COLUMN_RECIPE_ID + INTEGER_TYPE + COMMA_SEP +
                    dbContract.RecipeIngredient.COLUMN_INGREDIENT_ID + INTEGER_TYPE + COMMA_SEP +
                    dbContract.RecipeIngredient.COLUMN_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                    dbContract.RecipeIngredient.COLUMN_UNIT + TEXT_TYPE + " )";

    private static final String SQL_CREATE_CUISINE_TABLE =
            "CREATE TABLE " + dbContract.Cuisine.TABLE_NAME + " (" +
                    dbContract.Cuisine._ID + " INTEGER PRIMARY KEY," +
                    dbContract.Cuisine.COLUMN_CUISINE_NAME + TEXT_TYPE + " )";

    private static final String SQL_CREATE_MEALTYPE_TABLE =
            "CREATE TABLE " + dbContract.MealType.TABLE_NAME + " (" +
                    dbContract.MealType._ID + " INTEGER PRIMARY KEY," +
                    dbContract.MealType.COLUMN_MEAL_TYPE_NAME + TEXT_TYPE + " )";


    // SQL statements for deleting tables
    private static final String SQL_DELETE_STEP_TABLE =
            "DROP TABLE IF EXISTS " + dbContract.Step.TABLE_NAME;

    private static final String SQL_DELETE_RECIPE_TABLE =
            "DROP TABLE IF EXISTS " + dbContract.Recipe.TABLE_NAME;

    private static final String SQL_DELETE_INGREDIENT_TABLE =
            "DROP TABLE IF EXISTS " + dbContract.Ingredient.TABLE_NAME;

    private static final String SQL_DELETE_RECIPEINGREDIENT_TABLE =
            "DROP TABLE IF EXISTS " + dbContract.RecipeIngredient.TABLE_NAME;

    private static final String SQL_DELETE_CUISINE_TABLE =
            "DROP TABLE IF EXISTS " + dbContract.Cuisine.TABLE_NAME;

    private static final String SQL_DELETE_MEALTYPE_TABLE =
            "DROP TABLE IF EXISTS " + dbContract.MealType.TABLE_NAME;


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
    public long createRecipe(Recipe recipe, Cuisine cuisine, MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.Recipe.COLUMN_RECIPE_NAME, recipe.get_name());
        values.put(dbContract.Recipe.COLUMN_COST, recipe.get_cost());
        values.put(dbContract.Recipe.COLUMN_DIFFICULTY, recipe.get_difficulty());
        values.put(dbContract.Recipe.COLUMN_SERVINGS, recipe.get_servings());
        values.put(dbContract.Recipe.COLUMN_CUISINE, cuisine.get_id());
        values.put(dbContract.Recipe.COLUMN_MEALTYPE, type.get_id());
        long recipe_id = db.insert(dbContract.Recipe.TABLE_NAME, null, values);
        return recipe_id;
    }

    /**  create a recipeIngredient */
    public long createRecipeIngredient(Recipe recipe, Ingredient ingredient, long qty, String unit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.RecipeIngredient.COLUMN_RECIPE_ID, recipe.get_id());
        values.put(dbContract.RecipeIngredient.COLUMN_RECIPE_ID, ingredient.get_id());
        values.put(dbContract.RecipeIngredient.COLUMN_QUANTITY, qty);
        values.put(dbContract.RecipeIngredient.COLUMN_UNIT, unit);
        long recipeIngredient_id = db.insert(dbContract.RecipeIngredient.TABLE_NAME, null, values);
        return recipeIngredient_id;
    }

    /**  create an ingredient */
    public long createIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.Ingredient.COLUMN_INGREDIENT_NAME, ingredient.get_name());
        values.put(dbContract.Ingredient.COLUMN_INGREDIENT_PRICE, ingredient.get_price());
        long ingredient_id = db.insert(dbContract.Ingredient.TABLE_NAME, null, values);
        return ingredient_id;
    }

    /**  create a cuisine */
    public long createCuisine(Cuisine cuisine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.Cuisine.COLUMN_CUISINE_NAME, cuisine.get_name());
        long cuisine_id = db.insert(dbContract.Cuisine.TABLE_NAME, null, values);
        return cuisine_id;
    }

    /**  create a meal type */
    public long createMealType(MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.MealType.COLUMN_MEAL_TYPE_NAME, type.get_name());
        long type_id = db.insert(dbContract.MealType.TABLE_NAME, null, values);
        return type_id;
    }

    /** create a step */
    public long createStep(Step step) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.Step.COLUMN_INSTRUCTION, step.get_instruction());
        values.put(dbContract.Step.COLUMN_NUMBER, step.get_stepNumber());
        values.put(dbContract.Step.COLUMN_TIME, step.get_time());
        long step_id = db.insert(dbContract.Step.TABLE_NAME, null, values);
        return step_id;
    }

    /** create a grocery list */
    // TODO

    /** get a recipe by id */
    public Recipe getRecipe(long recipe_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query for retrieving recipe by recipe_id
        String selectRecipeQuery = "SELECT * FROM " + dbContract.Recipe.TABLE_NAME + " WHERE "
                + dbContract.Recipe._ID + " = " + recipe_id;

        Log.e(LOG, selectRecipeQuery);
        Cursor c = db.rawQuery(selectRecipeQuery, null);
        if (!c.moveToFirst()) return null;
        Recipe recipe = new Recipe();
        recipe.set_id(c.getLong(c.getColumnIndex(dbContract.Recipe._ID)));
        recipe.set_name(c.getString(c.getColumnIndex(dbContract.Recipe.COLUMN_RECIPE_NAME)));
        recipe.set_cost(c.getDouble((c.getColumnIndex((dbContract.Recipe.COLUMN_COST)))));
        recipe.set_difficulty(c.getDouble(c.getColumnIndex(dbContract.Recipe.COLUMN_DIFFICULTY)));
        recipe.set_servings(c.getInt(c.getColumnIndex(dbContract.Recipe.COLUMN_SERVINGS)));
        recipe.set_cuisine_id(c.getLong(c.getColumnIndex(dbContract.Recipe.COLUMN_CUISINE)));
        recipe.set_meal_type_id(c.getLong(c.getColumnIndex(dbContract.Recipe.COLUMN_MEALTYPE)));
        c.close();
        return recipe;
    }

    /** get an ingredient by id */
    public Ingredient getIngredient(long ingredient_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectRecipeQuery = "SELECT * FROM " + dbContract.Ingredient.TABLE_NAME + " WHERE "
                + dbContract.Ingredient._ID + " = " + ingredient_id;
        Log.e(LOG, selectRecipeQuery);
        Cursor c = db.rawQuery(selectRecipeQuery, null);
        if (c == null) return null;
        c.moveToFirst();
        Ingredient ingredient = new Ingredient();
        ingredient.set_id(c.getInt(c.getColumnIndex(dbContract.Ingredient._ID)));
        ingredient.set_name(c.getString(c.getColumnIndex(dbContract.Ingredient.COLUMN_INGREDIENT_NAME)));
        ingredient.set_price(c.getDouble(c.getColumnIndex(dbContract.Ingredient.COLUMN_INGREDIENT_PRICE)));
        c.close();
        return ingredient;
    }

    /** get a RecipeIngredient by id */
    public RecipeIngredient getRecipeIngredient(long recipeingredient_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectRecipeQuery = "SELECT * FROM " + dbContract.RecipeIngredient.TABLE_NAME + " WHERE "
                + dbContract.RecipeIngredient._ID + " = " + recipeingredient_id;
        Log.e(LOG, selectRecipeQuery);
        Cursor c = db.rawQuery(selectRecipeQuery, null);
        if (c == null) return null;
        c.moveToFirst();
        RecipeIngredient recipeingredient = new RecipeIngredient();
        recipeingredient.set_id(c.getInt(c.getColumnIndex(dbContract.RecipeIngredient._ID)));
        recipeingredient.set_quantity(c.getInt(c.getColumnIndex(dbContract.RecipeIngredient.COLUMN_QUANTITY)));
        recipeingredient.set_unit(c.getString(c.getColumnIndex(dbContract.RecipeIngredient.COLUMN_UNIT)));
        c.close();
        return recipeingredient;
    }

    /** get cuisine by id */
    public Cuisine getCuisine(long cuisine_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectRecipeQuery = "SELECT * FROM " + dbContract.Cuisine.TABLE_NAME + " WHERE "
                + dbContract.Cuisine._ID + " = " + cuisine_id;
        Log.e(LOG, selectRecipeQuery);
        Cursor c = db.rawQuery(selectRecipeQuery, null);
        if (c == null) return null;
        c.moveToFirst();
        Cuisine cuisine = new Cuisine();
        cuisine.set_id(c.getInt(c.getColumnIndex(dbContract.Cuisine._ID)));
        cuisine.set_name(c.getString(c.getColumnIndex(dbContract.Cuisine.COLUMN_CUISINE_NAME)));
        c.close();
        return cuisine;
    }

    /** get mealtype by id */
    public MealType getMealType(long type_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectRecipeQuery = "SELECT * FROM " + dbContract.MealType.TABLE_NAME + " WHERE "
                + dbContract.MealType._ID + " = " + type_id;
        Log.e(LOG, selectRecipeQuery);
        Cursor c = db.rawQuery(selectRecipeQuery, null);
        if (c == null) return null;
        c.moveToFirst();
        MealType type = new MealType();
        type.set_id(c.getInt(c.getColumnIndex(dbContract.MealType._ID)));
        type.set_name(c.getString(c.getColumnIndex(dbContract.MealType.COLUMN_MEAL_TYPE_NAME)));
        c.close();
        return type;
    }

    /** get all recipeIngredients by recipe_id */
    public ArrayList<RecipeIngredient> getRecipeIngredients(long recipe_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query for retrieving all recipeIngredients associated to recipe_id
        String selectRecipeIngredient = "SElECT * FROM " + dbContract.RecipeIngredient.TABLE_NAME +
                " WHERE " + dbContract.RecipeIngredient.COLUMN_RECIPE_ID + " = " + recipe_id;

        // populate array list with recipeIngredient objects
        Cursor c = db.rawQuery(selectRecipeIngredient, null);
        ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.set_id(c.getInt(c.getColumnIndex(dbContract.RecipeIngredient._ID)));
                recipeIngredient.set_quantity(c.getInt(c.getColumnIndex(dbContract.RecipeIngredient.COLUMN_QUANTITY)));
                recipeIngredient.set_unit(c.getString(c.getColumnIndex(dbContract.RecipeIngredient.COLUMN_UNIT)));
                recipeIngredients.add(recipeIngredient);
            } while (c.moveToNext());
        }
        c.close();
        return recipeIngredients;
    }

    /** get all recipe steps by recipe_id */
    public ArrayList<Step> getRecipeSteps(long recipe_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query for retrieving all steps associated to recipe_id
        String selectStep = "SElECT * FROM " + dbContract.Step.TABLE_NAME +
                " WHERE " + dbContract.Step.COLUMN_RECIPE+ " = " + recipe_id;

        // populate array list with step ids
        Cursor c = db.rawQuery(selectStep, null);
        ArrayList<Step> steps = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Step step = new Step();
                step.set_id(c.getLong(c.getColumnIndex(dbContract.Step._ID)));
                step.set_instruction(c.getString((c.getColumnIndex(dbContract.Step.COLUMN_INSTRUCTION))));
                step.set_stepNumber(c.getInt(c.getColumnIndex(dbContract.Step.COLUMN_NUMBER)));
                step.set_time(c.getInt(c.getColumnIndex(dbContract.Step.COLUMN_TIME)));
                steps.add(step);
            } while (c.moveToNext());
        }
        c.close();
        return steps;
    }

    /** Update operations */

    public boolean updateRecipe(Recipe recipe, Cuisine cuisine, MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.Recipe.COLUMN_RECIPE_NAME, recipe.get_name());
        values.put(dbContract.Recipe.COLUMN_COST, recipe.get_cost());
        values.put(dbContract.Recipe.COLUMN_DIFFICULTY, recipe.get_difficulty());
        values.put(dbContract.Recipe.COLUMN_SERVINGS, recipe.get_servings());
        values.put(dbContract.Recipe.COLUMN_CUISINE, cuisine.get_id());
        values.put(dbContract.Recipe.COLUMN_MEALTYPE, type.get_id());
        return db.update(dbContract.Recipe.TABLE_NAME, values, "_ID=" + recipe.get_id(), null) > 0;
    }

    public boolean updateIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.Ingredient.COLUMN_INGREDIENT_NAME, ingredient.get_name());
        values.put(dbContract.Ingredient.COLUMN_INGREDIENT_PRICE, ingredient.get_price());
        return db.update(dbContract.Ingredient.TABLE_NAME, values, "_ID=" + ingredient.get_id(), null) > 0;
    }

    public boolean updateRecipeIngredient(RecipeIngredient recipeIngredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.RecipeIngredient.COLUMN_RECIPE_ID, recipeIngredient.get_recipe_id());
        values.put(dbContract.RecipeIngredient.COLUMN_INGREDIENT_ID, recipeIngredient.get_ingredient_id());
        values.put(dbContract.RecipeIngredient.COLUMN_QUANTITY, recipeIngredient.get_quantity());
        values.put(dbContract.RecipeIngredient.COLUMN_UNIT, recipeIngredient.get_unit());
        return db.update(dbContract.RecipeIngredient.TABLE_NAME, values, "_ID="+ recipeIngredient.get_id(), null) > 0;
    }

    public boolean updateStep(Step step) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.Step.COLUMN_INSTRUCTION, step.get_instruction());
        values.put(dbContract.Step.COLUMN_TIME, step.get_time());
        values.put(dbContract.Step.COLUMN_NUMBER, step.get_stepNumber());
        return db.update(dbContract.Step.TABLE_NAME, values, "_ID=" + step.get_id(), null) > 0;
    }

    public boolean updateMealType(MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.MealType.COLUMN_MEAL_TYPE_NAME, type.get_name());
        return db.update(dbContract.MealType.TABLE_NAME, values, "_ID=" + type.get_id(), null) > 0;
    }

    public boolean updateCuisine(Cuisine cuisine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.Cuisine.COLUMN_CUISINE_NAME, cuisine.get_name());
        return db.update(dbContract.MealType.TABLE_NAME, values, "_ID=" + cuisine.get_id(), null) > 0;
    }

    /** Delete operations */

    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(dbContract.Step.TABLE_NAME, dbContract.Step.COLUMN_RECIPE +"="+ recipe.get_id(), null);
        db.delete(dbContract.RecipeIngredient.TABLE_NAME, dbContract.RecipeIngredient.COLUMN_RECIPE_ID +"="+ recipe.get_id(), null);
        db.delete(dbContract.Recipe.TABLE_NAME, "_ID=" + recipe.get_id(), null);
    }

    public void deleteIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(dbContract.RecipeIngredient.TABLE_NAME,
                dbContract.RecipeIngredient.COLUMN_INGREDIENT_ID +"="+ ingredient.get_id(), null);
        db.delete(dbContract.Ingredient.TABLE_NAME, "_ID="+ ingredient.get_id(), null);
    }

    public void deleteRecipeIngredient(RecipeIngredient recipeIngredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(dbContract.RecipeIngredient.TABLE_NAME, "_ID="+ recipeIngredient.get_id(), null);
    }

    public void deleteStep(Step step) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(dbContract.Step.TABLE_NAME, "_ID="+ step.get_id(), null);
    }

    public void deleteMealType(MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(dbContract.MealType.TABLE_NAME, "_ID="+ type.get_id(), null);
    }

    public void deleteCuisine(Cuisine cuisine) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(dbContract.Cuisine.TABLE_NAME, "_ID="+ cuisine.get_id(), null);
    }

}
