package ca.uottawa.cookingwithgarzon.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ca.uottawa.cookingwithgarzon.model.Cuisine;
import ca.uottawa.cookingwithgarzon.model.Ingredient;
import ca.uottawa.cookingwithgarzon.model.MealType;
import ca.uottawa.cookingwithgarzon.model.Recipe;
import ca.uottawa.cookingwithgarzon.model.RecipeIngredient;
import ca.uottawa.cookingwithgarzon.model.Step;

/**
 * Helper class that facilitates interaction with the SQLite Database
 * Created by joel on 19/11/16.
 */

public class DbHelper extends SQLiteOpenHelper {

    // singleton storing reference to db
    private static DbHelper mInstance = null;

    public static final int DATABASE_VERSION = 8; //

    // constants used to simplify dbHelper
    private static final String DATABASE_NAME = "recipe.db";
    private static final String LOG = "DbHelper";
    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";


    // SQL Statements for creating tables
    private static final String SQL_CREATE_RECIPE_TABLE =
            "CREATE TABLE " + DbContract.Recipe.TABLE_NAME + " (" +
                    DbContract.Recipe._ID + " INTEGER PRIMARY KEY," +
                    DbContract.Recipe.COLUMN_RECIPE_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.Recipe.COLUMN_COST + REAL_TYPE + COMMA_SEP +
                    DbContract.Recipe.COLUMN_DIFFICULTY + TEXT_TYPE + COMMA_SEP +
                    DbContract.Recipe.COLUMN_SERVINGS + INTEGER_TYPE + COMMA_SEP +
                    DbContract.Recipe.COLUMN_RATING + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for cuisine
                    DbContract.Recipe.COLUMN_CUISINE + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for mealtype
                    DbContract.Recipe.COLUMN_MEALTYPE + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for recipeingredient
                    DbContract.Recipe.COLUMN_RECIPE_INGREDIENT + INTEGER_TYPE + COMMA_SEP +
                    DbContract.Recipe.COLUMN_FAVOURITE + INTEGER_TYPE + COMMA_SEP +
                    DbContract.Recipe.COLUMN_IMAGE + BLOB_TYPE + " )";

    private static final String SQL_CREATE_STEP_TABLE =
            "CREATE TABLE " + DbContract.Step.TABLE_NAME + " (" +
                    DbContract.Step._ID + " INTEGER PRIMARY KEY," +
                    DbContract.Step.COLUMN_INSTRUCTION + TEXT_TYPE + COMMA_SEP +
                    DbContract.Step.COLUMN_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    DbContract.Step.COLUMN_TIME + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for recipe
                    DbContract.Step.COLUMN_RECIPE + INTEGER_TYPE + " )";

    private static final String SQL_CREATE_INGREDIENT_TABLE =
            "CREATE TABLE " + DbContract.Ingredient.TABLE_NAME + " (" +
                    DbContract.Ingredient._ID + " INTEGER PRIMARY KEY," +
                    DbContract.Ingredient.COLUMN_INGREDIENT_NAME + TEXT_TYPE + COMMA_SEP +
                    DbContract.Ingredient.COLUMN_INGREDIENT_PRICE + REAL_TYPE + " )";

    private static final String SQL_CREATE_RECIPEINGREDIENT_TABLE =
            "CREATE TABLE " + DbContract.RecipeIngredient.TABLE_NAME + " (" +
                    DbContract.RecipeIngredient._ID + " INTEGER PRIMARY KEY," +
                    DbContract.RecipeIngredient.COLUMN_RECIPE_ID + INTEGER_TYPE + COMMA_SEP +
                    DbContract.RecipeIngredient.COLUMN_INGREDIENT_ID + INTEGER_TYPE + COMMA_SEP +
                    DbContract.RecipeIngredient.COLUMN_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                    DbContract.RecipeIngredient.COLUMN_UNIT + TEXT_TYPE + " )";

    private static final String SQL_CREATE_CUISINE_TABLE =
            "CREATE TABLE " + DbContract.Cuisine.TABLE_NAME + " (" +
                    DbContract.Cuisine._ID + " INTEGER PRIMARY KEY," +
                    DbContract.Cuisine.COLUMN_CUISINE_NAME + TEXT_TYPE + " )";

    private static final String SQL_CREATE_MEALTYPE_TABLE =
            "CREATE TABLE " + DbContract.MealType.TABLE_NAME + " (" +
                    DbContract.MealType._ID + " INTEGER PRIMARY KEY," +
                    DbContract.MealType.COLUMN_MEAL_TYPE_NAME + TEXT_TYPE + " )";

    private static final String SQL_CREATE_SHOPPINGCARTINGREDIENT_TABLE =
            "CREATE TABLE " + DbContract.ShoppingCartIngredient.TABLE_NAME + " (" +
                    DbContract.ShoppingCartIngredient._ID + " INTEGER PRIMARY KEY, "+
                    DbContract.ShoppingCartIngredient.COLUMN_INGREDIENT_ID + INTEGER_TYPE + COMMA_SEP +
                    DbContract.ShoppingCartIngredient.COLUMN_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                    DbContract.ShoppingCartIngredient.COLUMN_UNIT + INTEGER_TYPE + ")";

    // SQL statements for deleting tables
    private static final String SQL_DELETE_STEP_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.Step.TABLE_NAME;

    private static final String SQL_DELETE_RECIPE_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.Recipe.TABLE_NAME;

    private static final String SQL_DELETE_INGREDIENT_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.Ingredient.TABLE_NAME;

    private static final String SQL_DELETE_RECIPEINGREDIENT_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.RecipeIngredient.TABLE_NAME;

    private static final String SQL_DELETE_CUISINE_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.Cuisine.TABLE_NAME;

    private static final String SQL_DELETE_MEALTYPE_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.MealType.TABLE_NAME;

    private static final String SQL_DELETE_SHOPPINGCARTINGREDIENT_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.ShoppingCartIngredient.TABLE_NAME;

    public static DbHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DbHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    // Constructor is private. Must invoke static method getInstance()
    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // DB create/upgrade/downgrade methods
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RECIPE_TABLE);
        db.execSQL(SQL_CREATE_INGREDIENT_TABLE);
        db.execSQL(SQL_CREATE_RECIPEINGREDIENT_TABLE);
        db.execSQL(SQL_CREATE_STEP_TABLE);
        db.execSQL(SQL_CREATE_MEALTYPE_TABLE);
        db.execSQL(SQL_CREATE_CUISINE_TABLE);
        db.execSQL(SQL_CREATE_SHOPPINGCARTINGREDIENT_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not sure when upgrade would be invoked. Just drop everything
        db.execSQL(SQL_DELETE_RECIPEINGREDIENT_TABLE);
        db.execSQL(SQL_DELETE_INGREDIENT_TABLE);
        db.execSQL(SQL_DELETE_RECIPE_TABLE);
        db.execSQL(SQL_DELETE_CUISINE_TABLE);
        db.execSQL(SQL_DELETE_MEALTYPE_TABLE);
        db.execSQL(SQL_DELETE_STEP_TABLE);
        db.execSQL(SQL_DELETE_SHOPPINGCARTINGREDIENT_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // deletes database content for testing
    public static void clearDatabase(Context ctx) {
        ctx.deleteDatabase(DATABASE_NAME);
    }


    /** CRUD */

    /**
     * Create methods
     */

    /** Create a recipe */
    public long createRecipe(Recipe recipe) {
        if (recipe.get_id() != 0) {
            return updateRecipe(recipe);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Recipe.COLUMN_RECIPE_NAME, recipe.get_name());
        values.put(DbContract.Recipe.COLUMN_COST, recipe.get_cost());
        values.put(DbContract.Recipe.COLUMN_DIFFICULTY, recipe.get_difficulty());
        values.put(DbContract.Recipe.COLUMN_SERVINGS, recipe.get_servings());
        values.put(DbContract.Recipe.COLUMN_RATING, recipe.get_rating());
        values.put(DbContract.Recipe.COLUMN_CUISINE, recipe.get_cuisine_id());
        values.put(DbContract.Recipe.COLUMN_MEALTYPE, recipe.get_meal_type_id());
        values.put(DbContract.Recipe.COLUMN_FAVOURITE, recipe.get_favourite());
        values.put(DbContract.Recipe.COLUMN_IMAGE, recipe.get_image());
        long recipe_id = db.insertOrThrow(DbContract.Recipe.TABLE_NAME, null, values);
        return recipe_id;
    }

    /**  create a recipeIngredient */
    public long createRecipeIngredient(RecipeIngredient recipeIngredient) {
        if (recipeIngredient.get_id() != 0) {
            return updateRecipeIngredient(recipeIngredient);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.RecipeIngredient.COLUMN_RECIPE_ID, recipeIngredient.get_recipe_id());
        values.put(DbContract.RecipeIngredient.COLUMN_INGREDIENT_ID, recipeIngredient.get_ingredient_id());
        values.put(DbContract.RecipeIngredient.COLUMN_QUANTITY, recipeIngredient.get_quantity());
        values.put(DbContract.RecipeIngredient.COLUMN_UNIT, recipeIngredient.get_unit());
        long recipeIngredient_id = db.insertOrThrow(DbContract.RecipeIngredient.TABLE_NAME, null, values);
        return recipeIngredient_id;
    }

    /**  create an ingredient */
    public long createIngredient(Ingredient ingredient) {
        if (ingredient.get_id() != 0) {
            return updateIngredient(ingredient);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Ingredient.COLUMN_INGREDIENT_NAME, ingredient.get_name());
        values.put(DbContract.Ingredient.COLUMN_INGREDIENT_PRICE, ingredient.get_price());
        long ingredient_id = db.insertOrThrow(DbContract.Ingredient.TABLE_NAME, null, values);
        return ingredient_id;
    }

    /**  create a cuisine */
    public long createCuisine(Cuisine cuisine) {
        if (cuisine.get_id() != 0) {
            return updateCuisine(cuisine);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Cuisine.COLUMN_CUISINE_NAME, cuisine.get_name());
        long cuisine_id = db.insertOrThrow(DbContract.Cuisine.TABLE_NAME, null, values);
        return cuisine_id;
    }

    /**  create a meal type */
    public long createMealType(MealType type) {
        if (type.get_id() != 0) {
            return updateMealType(type);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.MealType.COLUMN_MEAL_TYPE_NAME, type.get_name());
        long type_id = db.insertOrThrow(DbContract.MealType.TABLE_NAME, null, values);
        return type_id;
    }

    /** create a step */
    public long createStep(Step step) {
        if (step.get_id() != 0) {
            return updateStep(step);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Step.COLUMN_INSTRUCTION, step.get_instruction());
        values.put(DbContract.Step.COLUMN_NUMBER, step.get_stepNumber());
        values.put(DbContract.Step.COLUMN_TIME, step.get_time());
        values.put(DbContract.Step.COLUMN_RECIPE, step.get_recipe_id());
        long step_id = db.insertOrThrow(DbContract.Step.TABLE_NAME, null, values);
        return step_id;
    }

    /** create a shoppingCart item */
    public long createShoppingCartIngredient(RecipeIngredient recipeIngredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.ShoppingCartIngredient.COLUMN_INGREDIENT_ID, recipeIngredient.get_ingredient_id());
        values.put(DbContract.ShoppingCartIngredient.COLUMN_QUANTITY, recipeIngredient.get_quantity());
        values.put(DbContract.ShoppingCartIngredient.COLUMN_UNIT, recipeIngredient.get_unit());
        return db.insertOrThrow(DbContract.ShoppingCartIngredient.TABLE_NAME, null, values);
    }


    /**
     * Get items from database
     */

    /** get a recipe by id */
    public Recipe getRecipe(long recipe_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query for retrieving recipe by recipe_id
        String selectRecipeQuery = "SELECT * FROM " + DbContract.Recipe.TABLE_NAME + " WHERE "
                + DbContract.Recipe._ID + " = " + recipe_id;

        Log.e(LOG, selectRecipeQuery);
        Cursor c = db.rawQuery(selectRecipeQuery, null);
        if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        Recipe recipe = new Recipe();
        recipe.set_id(c.getLong(c.getColumnIndex(DbContract.Recipe._ID)));
        recipe.set_name(c.getString(c.getColumnIndex(DbContract.Recipe.COLUMN_RECIPE_NAME)));
        recipe.set_cost(c.getDouble((c.getColumnIndex((DbContract.Recipe.COLUMN_COST)))));
        recipe.set_difficulty(c.getString(c.getColumnIndex(DbContract.Recipe.COLUMN_DIFFICULTY)));
        recipe.set_servings(c.getInt(c.getColumnIndex(DbContract.Recipe.COLUMN_SERVINGS)));
        recipe.set_rating(c.getInt(c.getColumnIndex(DbContract.Recipe.COLUMN_RATING)));
        recipe.set_cuisine_id(c.getLong(c.getColumnIndex(DbContract.Recipe.COLUMN_CUISINE)));
        recipe.set_meal_type_id(c.getLong(c.getColumnIndex(DbContract.Recipe.COLUMN_MEALTYPE)));
        recipe.set_favourite(c.getInt(c.getColumnIndex(DbContract.Recipe.COLUMN_FAVOURITE)));
        recipe.set_image(c.getBlob(c.getColumnIndex(DbContract.Recipe.COLUMN_IMAGE)));
        c.close();
        return recipe;
    }

    /** get an ingredient by id */
    public Ingredient getIngredient(long ingredient_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectRecipeQuery = "SELECT * FROM " + DbContract.Ingredient.TABLE_NAME + " WHERE "
                + DbContract.Ingredient._ID + " = " + ingredient_id;
        Log.e(LOG, selectRecipeQuery);
        Cursor c = db.rawQuery(selectRecipeQuery, null);
        if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        Ingredient ingredient = new Ingredient();
        ingredient.set_id(c.getInt(c.getColumnIndex(DbContract.Ingredient._ID)));
        ingredient.set_name(c.getString(c.getColumnIndex(DbContract.Ingredient.COLUMN_INGREDIENT_NAME)));
        ingredient.set_price(c.getDouble(c.getColumnIndex(DbContract.Ingredient.COLUMN_INGREDIENT_PRICE)));
        c.close();
        return ingredient;
    }

    /** get a CreateOrEditRecipeIngredientActivity by id */
    public RecipeIngredient getRecipeIngredient(long recipeingredient_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbContract.RecipeIngredient.TABLE_NAME + " WHERE "
                + DbContract.RecipeIngredient._ID + " = " + recipeingredient_id;
        Log.e(LOG, query);
        Cursor c = db.rawQuery(query, null);
        if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        RecipeIngredient recipeingredient = new RecipeIngredient();
        recipeingredient.set_id(c.getInt(c.getColumnIndex(DbContract.RecipeIngredient._ID)));
        recipeingredient.set_ingredient_id(c.getInt(c.getColumnIndex(DbContract.RecipeIngredient.COLUMN_INGREDIENT_ID)));
        recipeingredient.set_quantity(c.getInt(c.getColumnIndex(DbContract.RecipeIngredient.COLUMN_QUANTITY)));
        recipeingredient.set_unit(c.getString(c.getColumnIndex(DbContract.RecipeIngredient.COLUMN_UNIT)));
        c.close();
        return recipeingredient;
    }

    /** get a step by id */
    public Step getStep(long step_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbContract.Step.TABLE_NAME + " WHERE "
                + DbContract.Step._ID + " = " + step_id;
        Log.e(LOG, query);
        Cursor c = db.rawQuery(query, null);
        if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        Step step = new Step();
        step.set_id(c.getInt(c.getColumnIndex(DbContract.Step._ID)));
        step.set_time(c.getInt(c.getColumnIndex(DbContract.Step.COLUMN_TIME)));
        step.set_stepNumber(c.getInt(c.getColumnIndex(DbContract.Step.COLUMN_NUMBER)));
        step.set_instruction(c.getString(c.getColumnIndex(DbContract.Step.COLUMN_INSTRUCTION)));
        c.close();
        return step;
    }

    /** get cuisine by id */
    public Cuisine getCuisine(long cuisine_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectRecipeQuery = "SELECT * FROM " + DbContract.Cuisine.TABLE_NAME + " WHERE "
                + DbContract.Cuisine._ID + " = " + cuisine_id;
        Log.e(LOG, selectRecipeQuery);
        Cursor c = db.rawQuery(selectRecipeQuery, null);
        if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        Cuisine cuisine = new Cuisine();
        cuisine.set_id(c.getInt(c.getColumnIndex(DbContract.Cuisine._ID)));
        cuisine.set_name(c.getString(c.getColumnIndex(DbContract.Cuisine.COLUMN_CUISINE_NAME)));
        c.close();
        return cuisine;
    }

    /** get mealtype by id */
    public MealType getMealType(long type_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectRecipeQuery = "SELECT * FROM " + DbContract.MealType.TABLE_NAME + " WHERE "
                + DbContract.MealType._ID + " = " + type_id;
        Log.e(LOG, selectRecipeQuery);
        Cursor c = db.rawQuery(selectRecipeQuery, null);
        if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        MealType type = new MealType();
        type.set_id(c.getInt(c.getColumnIndex(DbContract.MealType._ID)));
        type.set_name(c.getString(c.getColumnIndex(DbContract.MealType.COLUMN_MEAL_TYPE_NAME)));
        c.close();
        return type;
    }

    /** get all recipeIngredients by recipe_id */
    public ArrayList<RecipeIngredient> getRecipeIngredients(long recipe_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query for retrieving all recipeIngredients associated to recipe_id
        String selectRecipeIngredient = "SELECT * FROM " + DbContract.RecipeIngredient.TABLE_NAME +
                " WHERE " + DbContract.RecipeIngredient.COLUMN_RECIPE_ID + "=" + recipe_id;

        // populate array list with recipeIngredient objects
        Cursor c = db.rawQuery(selectRecipeIngredient, null);
        ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.set_id(c.getInt(c.getColumnIndex(DbContract.RecipeIngredient._ID)));
                recipeIngredient.set_quantity(c.getInt(c.getColumnIndex(DbContract.RecipeIngredient.COLUMN_QUANTITY)));
                recipeIngredient.set_unit(c.getString(c.getColumnIndex(DbContract.RecipeIngredient.COLUMN_UNIT)));
                recipeIngredient.set_recipe_id(c.getLong(c.getColumnIndex(DbContract.RecipeIngredient.COLUMN_RECIPE_ID)));
                recipeIngredient.set_ingredient_id(c.getLong(c.getColumnIndex(DbContract.RecipeIngredient.COLUMN_INGREDIENT_ID)));
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
        String selectStep = "SELECT * FROM " + DbContract.Step.TABLE_NAME +
                " WHERE " + DbContract.Step.COLUMN_RECIPE+ " = " + recipe_id;

        // populate array list with step ids
        Cursor c = db.rawQuery(selectStep, null);
        ArrayList<Step> steps = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Step step = new Step();
                step.set_id(c.getLong(c.getColumnIndex(DbContract.Step._ID)));
                step.set_instruction(c.getString((c.getColumnIndex(DbContract.Step.COLUMN_INSTRUCTION))));
                step.set_stepNumber(c.getInt(c.getColumnIndex(DbContract.Step.COLUMN_NUMBER)));
                step.set_time(c.getInt(c.getColumnIndex(DbContract.Step.COLUMN_TIME)));
                steps.add(step);
            } while (c.moveToNext());
        }
        c.close();
        return steps;
    }

    /**
     * get all items in shopping cart
     */
    public ArrayList<RecipeIngredient> getShoppingCartIngredients() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectShoppingCartIngredients = "SELECT * FROM " + DbContract.ShoppingCartIngredient.TABLE_NAME;
        Log.e(LOG, selectShoppingCartIngredients);
        // populate array list with ingredient ids
        Cursor c = db.rawQuery(selectShoppingCartIngredients, null);
        ArrayList<RecipeIngredient> ingredients = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.set_id(c.getInt(c.getColumnIndex(DbContract.ShoppingCartIngredient._ID)));
                recipeIngredient.set_ingredient_id(c.getInt(c.getColumnIndex(DbContract.ShoppingCartIngredient.COLUMN_INGREDIENT_ID)));
                recipeIngredient.set_quantity(c.getInt(c.getColumnIndex(DbContract.ShoppingCartIngredient.COLUMN_QUANTITY)));
                recipeIngredient.set_unit(c.getString(c.getColumnIndex(DbContract.ShoppingCartIngredient.COLUMN_UNIT)));
                ingredients.add(recipeIngredient);
            } while (c.moveToNext());
        }
        c.close();
        return ingredients;
    }

    /**
     * Update operations
     */

    /** update recipe */
    public long updateRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Recipe.COLUMN_RECIPE_NAME, recipe.get_name());
        values.put(DbContract.Recipe.COLUMN_COST, recipe.get_cost());
        values.put(DbContract.Recipe.COLUMN_DIFFICULTY, recipe.get_difficulty());
        values.put(DbContract.Recipe.COLUMN_SERVINGS, recipe.get_servings());
        values.put(DbContract.Recipe.COLUMN_RATING, recipe.get_rating());
        values.put(DbContract.Recipe.COLUMN_CUISINE, recipe.get_cuisine_id());
        values.put(DbContract.Recipe.COLUMN_MEALTYPE, recipe.get_meal_type_id());
        values.put(DbContract.Recipe.COLUMN_FAVOURITE, recipe.get_favourite());
        values.put(DbContract.Recipe.COLUMN_IMAGE, recipe.get_image());
        db.update(DbContract.Recipe.TABLE_NAME, values,
                DbContract.Recipe._ID +"="+ recipe.get_id(), null);
        return recipe.get_id();
    }

    /** update an ingredient */
    public long updateIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Ingredient.COLUMN_INGREDIENT_NAME, ingredient.get_name());
        values.put(DbContract.Ingredient.COLUMN_INGREDIENT_PRICE, ingredient.get_price());
        db.update(DbContract.Ingredient.TABLE_NAME, values,
                DbContract.Ingredient._ID +"="+ ingredient.get_id(), null);
        return ingredient.get_id();
    }

    /** update recipe Ingredient */
    public long updateRecipeIngredient(RecipeIngredient recipeIngredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.RecipeIngredient.COLUMN_RECIPE_ID, recipeIngredient.get_recipe_id());
        values.put(DbContract.RecipeIngredient.COLUMN_INGREDIENT_ID, recipeIngredient.get_ingredient_id());
        values.put(DbContract.RecipeIngredient.COLUMN_QUANTITY, recipeIngredient.get_quantity());
        values.put(DbContract.RecipeIngredient.COLUMN_UNIT, recipeIngredient.get_unit());
        db.update(DbContract.RecipeIngredient.TABLE_NAME, values,
                DbContract.RecipeIngredient._ID +"="+ recipeIngredient.get_id(), null);
        return recipeIngredient.get_id();
    }

    /** update a step */
    public long updateStep(Step step) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Step.COLUMN_INSTRUCTION, step.get_instruction());
        values.put(DbContract.Step.COLUMN_TIME, step.get_time());
        values.put(DbContract.Step.COLUMN_NUMBER, step.get_stepNumber());
        db.update(DbContract.Step.TABLE_NAME, values,
                DbContract.Step._ID +"=" + step.get_id(), null);
        return step.get_id();
    }

    /** update a meal type */
    public long updateMealType(MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.MealType.COLUMN_MEAL_TYPE_NAME, type.get_name());
        db.update(DbContract.MealType.TABLE_NAME, values,
                DbContract.MealType._ID + "=" + type.get_id(), null);
        return type.get_id();
    }

    /** update a cuisine */
    public long updateCuisine(Cuisine cuisine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Cuisine.COLUMN_CUISINE_NAME, cuisine.get_name());
        db.update(DbContract.Cuisine.TABLE_NAME, values,
                DbContract.Cuisine._ID + "=" + cuisine.get_id(), null);
        return cuisine.get_id();
    }

    /** update a shopping cart */
    public long updateShoppingCart(RecipeIngredient recipeIngredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.ShoppingCartIngredient.COLUMN_INGREDIENT_ID, recipeIngredient.get_ingredient_id());
        values.put(DbContract.ShoppingCartIngredient.COLUMN_QUANTITY, recipeIngredient.get_quantity());
        values.put(DbContract.ShoppingCartIngredient.COLUMN_UNIT, recipeIngredient.get_unit());
        db.update(DbContract.ShoppingCartIngredient.TABLE_NAME, values,
                DbContract.ShoppingCartIngredient._ID + "=" + recipeIngredient.get_id(), null);
        return recipeIngredient.get_id();
    }

    /**
     * Delete operations
     */

    public void deleteInvalidRecipes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbContract.Recipe.TABLE_NAME, DbContract.Recipe.COLUMN_RECIPE_NAME +" is null", null);
    }


    /** delete recipe. propagates to recipeIngredient and steps */
    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<RecipeIngredient> ingredients = getRecipeIngredients(recipe.get_id());
        db.delete(DbContract.Step.TABLE_NAME, DbContract.Step.COLUMN_RECIPE +"="+ recipe.get_id(), null);
        db.delete(DbContract.RecipeIngredient.TABLE_NAME, DbContract.RecipeIngredient.COLUMN_RECIPE_ID +"="+ recipe.get_id(), null);
        db.delete(DbContract.Recipe.TABLE_NAME, "_ID=" + recipe.get_id(), null);
        for (RecipeIngredient ingredient : ingredients) {
            db.delete(DbContract.ShoppingCartIngredient.TABLE_NAME,
                    DbContract.ShoppingCartIngredient.COLUMN_INGREDIENT_ID + "=" + ingredient.get_ingredient_id(),
                    null);
        }
    }

    /** deletes ingredient propagates to recipeIngredient */
    public void deleteIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbContract.RecipeIngredient.TABLE_NAME,
                DbContract.RecipeIngredient.COLUMN_INGREDIENT_ID +"="+ ingredient.get_id(), null);
        db.delete(DbContract.Ingredient.TABLE_NAME, "_ID="+ ingredient.get_id(), null);
    }

    /** delete a recipe ingredient */
    public void deleteRecipeIngredient(RecipeIngredient recipeIngredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbContract.RecipeIngredient.TABLE_NAME, "_ID="+ recipeIngredient.get_id(), null);
    }

    /** delete a recipe step */
    public void deleteStep(Step step) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbContract.Step.TABLE_NAME, "_ID="+ step.get_id(), null);
    }

    /** delete a meal type */
    public void deleteMealType(MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(DbContract.MealType.TABLE_NAME, "_ID="+ type.get_id(), null);
    }

    /** deletea cuisine */
    public void deleteCuisine(Cuisine cuisine) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbContract.Cuisine.TABLE_NAME, "_ID="+ cuisine.get_id(), null);
    }

    /** Clear table operations */
    public void clearShoppingCartIngredient() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbContract.ShoppingCartIngredient.TABLE_NAME, null, null);
    }

    /** Search operations */

    /** Helper method to build result of recipe search */
    private ArrayList<Recipe> buildRecipeList(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Recipe> recipes = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.set_id(c.getLong(c.getColumnIndex(DbContract.Recipe._ID)));
                recipe.set_name(c.getString(c.getColumnIndex(DbContract.Recipe.COLUMN_RECIPE_NAME)));
                recipe.set_cost(c.getDouble((c.getColumnIndex((DbContract.Recipe.COLUMN_COST)))));
                recipe.set_difficulty(c.getString(c.getColumnIndex(DbContract.Recipe.COLUMN_DIFFICULTY)));
                recipe.set_servings(c.getInt(c.getColumnIndex(DbContract.Recipe.COLUMN_SERVINGS)));
                recipe.set_rating(c.getInt(c.getColumnIndex(DbContract.Recipe.COLUMN_RATING)));
                recipe.set_cuisine_id(c.getLong(c.getColumnIndex(DbContract.Recipe.COLUMN_CUISINE)));
                recipe.set_meal_type_id(c.getLong(c.getColumnIndex(DbContract.Recipe.COLUMN_MEALTYPE)));
                recipe.set_favourite(c.getInt(c.getColumnIndex(DbContract.Recipe.COLUMN_FAVOURITE)));
                recipes.add(recipe);
            } while (c.moveToNext());
        }
        c.close();
        return recipes;
    }

    /** Algorithm for searching for recipes by name, ingredient, cuisine, type
     *
     * takes arguments recipe name, recipe ingredients, cuisine and meal type
     * tokenizes search string by "AND" and checks for NOT
     * builds SQL query with intersection of tables, joins where appropriate
     *
     * joel 28-11-2016
     *
     * */
    public ArrayList<Recipe> findRecipe(String name, String ingredient, String cuisine, String meal) {

        boolean started = false;
        StringBuilder query = new StringBuilder();
        List<String> nameArgs = new ArrayList<>();
        List<String> notNameArgs = new ArrayList<>();
        List<String> ingArgs = new ArrayList<>();
        List<String> notIngArgs = new ArrayList<>();
        List<String> cuisineArgs = new ArrayList<>();
        List<String> notCuisineArgs = new ArrayList<>();
        List<String> mealArgs = new ArrayList<>();
        List<String> notMealArgs = new ArrayList<>();


        String[] args = name.trim().toLowerCase().split(" and ");
        for (int i = 0; i < args.length; ++i) {
            if (args[i].toLowerCase().startsWith("not ")) {
                notNameArgs.add(args[i].substring(4));
            } else if (!args[i].equals("")) {
                nameArgs.add(args[i]);
            }
        }
        args = ingredient.trim().toLowerCase().split(" and ");
        for (int i = 0; i < args.length; ++i) {
            if (args[i].toLowerCase().startsWith("not ")) {
                notIngArgs.add(args[i].substring(4));
            } else if (!args[i].equals("")) {
                ingArgs.add(args[i]);
            }
        }

        args = cuisine.trim().toLowerCase().split(" and ");
        for (int i = 0; i < args.length; ++i) {
            if (args[i].toLowerCase().startsWith("not ")) {
                notCuisineArgs.add(args[i].substring(4));
            } else if (!args[i].equals("")) {
                cuisineArgs.add(args[i]);
            }
        }

        args = meal.trim().toLowerCase().split(" and ");
        for (int i = 0; i < args.length; ++i) {
            if (args[i].toLowerCase().startsWith("not ")) {
                notMealArgs.add(args[i].substring(4));
            } else if (!args[i].equals("")) {
                mealArgs.add(args[i]);
            }
        }

        if (!nameArgs.isEmpty()) {
            started = true;
            query.append("SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                    " FROM " + DbContract.Recipe.TABLE_NAME + " WHERE ");
            for (int i = 0; i < nameArgs.size(); i ++ ) {
                query.append(DbContract.Recipe.TABLE_NAME +"."+
                        DbContract.Recipe.COLUMN_RECIPE_NAME);
                query.append(" LIKE \'%" + nameArgs.get(i) + "%\' ");
                if (i + 1 < nameArgs.size()) {
                    query.append(" AND ");
                }
            }
        }
        if (!ingArgs.isEmpty()) {
            if (!started) {
                started=true;
                query.append("SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                        " FROM " + DbContract.Recipe.TABLE_NAME);
            } else {
                query.append(" INTERSECT SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            }
            query.append(" INNER JOIN " + DbContract.RecipeIngredient.TABLE_NAME +
                    " ON " + DbContract.Recipe.TABLE_NAME +"."+
                    DbContract.Recipe._ID+ "="+
                    DbContract.RecipeIngredient.TABLE_NAME + "." +
                    DbContract.RecipeIngredient.COLUMN_RECIPE_ID +
                    " INNER JOIN " + DbContract.Ingredient.TABLE_NAME +
                    " ON " + DbContract.Ingredient.TABLE_NAME +"."+
                    DbContract.Ingredient._ID + "=" +
                    DbContract.RecipeIngredient.TABLE_NAME + "." +
                    DbContract.RecipeIngredient.COLUMN_INGREDIENT_ID + " WHERE ");
            for (int i = 0; i < ingArgs.size(); ++i) {
                query.append(DbContract.Ingredient.TABLE_NAME +"." +
                        DbContract.Ingredient.COLUMN_INGREDIENT_NAME);
                query.append(" LIKE \'%" + ingArgs.get(i) + "%\' ");
                if (i + 1 < ingArgs.size()) {
                    query.append(" AND ");
                }
            }
        }
        if (!cuisineArgs.isEmpty()) {
            if (!started) {
                started=true;
                query.append("SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            } else {
                query.append(" INTERSECT SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            }
            query.append(" INNER JOIN " + DbContract.Cuisine.TABLE_NAME +
                    " ON " + DbContract.Recipe.TABLE_NAME +"."+
                    DbContract.Recipe.COLUMN_CUISINE + "="+
                    DbContract.Cuisine.TABLE_NAME +"."+
                    DbContract.Cuisine._ID + " WHERE ");
            for (int i = 0; i < cuisineArgs.size(); ++i) {
                query.append(DbContract.Cuisine.TABLE_NAME +"."+
                        DbContract.Cuisine.COLUMN_CUISINE_NAME);
                    query.append(" LIKE \'%" + cuisineArgs.get(i) + "%\' ");
                if (i + 1 < cuisineArgs.size()) {
                    query.append(" AND ");
                }
            }
        }
        if (!mealArgs.isEmpty()) {
            if (!started) {
                started=true;
                query.append("SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            } else {
                query.append(" INTERSECT SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            }
            query.append(" INNER JOIN " + DbContract.MealType.TABLE_NAME +
                    " ON " + DbContract.Recipe.TABLE_NAME +"."+
                    DbContract.Recipe.COLUMN_MEALTYPE + "="+
                    DbContract.MealType.TABLE_NAME +"."+
                    DbContract.MealType._ID + " WHERE ");
            for (int i = 0; i < mealArgs.size(); ++i) {
                query.append(DbContract.MealType.TABLE_NAME +"."+
                        DbContract.MealType.COLUMN_MEAL_TYPE_NAME);
                    query.append(" LIKE \'%" + mealArgs.get(i) + "%\' ");
                if (i + 1 < mealArgs.size()) {
                    query.append(" AND ");
                }
            }
        }
        if (!started)
            query.append("SELECT " + DbContract.Recipe.TABLE_NAME +".*" +
                    " FROM " + DbContract.Recipe.TABLE_NAME);

        started = false;

        if (!notNameArgs.isEmpty()) {
            started = true;
            query.append(" EXCEPT SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                    " FROM " + DbContract.Recipe.TABLE_NAME + " WHERE ");
            for (int i = 0; i < notNameArgs.size(); i ++ ) {
                query.append(DbContract.Recipe.TABLE_NAME +"."+
                        DbContract.Recipe.COLUMN_RECIPE_NAME);
                query.append(" LIKE \'%" + notNameArgs.get(i) + "%\' ");
                if (i + 1 < notNameArgs.size()) {
                    query.append(" AND ");
                }
            }
        }
        if (!notIngArgs.isEmpty()) {
            if (!started) {
                started=true;
                query.append(" EXCEPT SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                        " FROM " + DbContract.Recipe.TABLE_NAME);
            } else {
                query.append(" UNION SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            }
            query.append(" INNER JOIN " + DbContract.RecipeIngredient.TABLE_NAME +
                    " ON " + DbContract.Recipe.TABLE_NAME +"."+
                    DbContract.Recipe._ID+ "="+
                    DbContract.RecipeIngredient.TABLE_NAME + "." +
                    DbContract.RecipeIngredient.COLUMN_RECIPE_ID +
                    " INNER JOIN " + DbContract.Ingredient.TABLE_NAME +
                    " ON " + DbContract.Ingredient.TABLE_NAME +"."+
                    DbContract.Ingredient._ID + "=" +
                    DbContract.RecipeIngredient.TABLE_NAME + "." +
                    DbContract.RecipeIngredient.COLUMN_INGREDIENT_ID + " WHERE ");
            for (int i = 0; i < notIngArgs.size(); ++i) {
                query.append(DbContract.Ingredient.TABLE_NAME +"." +
                        DbContract.Ingredient.COLUMN_INGREDIENT_NAME);
                query.append(" LIKE \'%" + notIngArgs.get(i) + "%\' ");
                if (i + 1 < notIngArgs.size()) {
                    query.append(" AND ");
                }
            }
        }
        if (!notCuisineArgs.isEmpty()) {
            if (!started) {
                started=true;
                query.append(" EXCEPT SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            } else {
                query.append(" UNION SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            }
            query.append(" INNER JOIN " + DbContract.Cuisine.TABLE_NAME +
                    " ON " + DbContract.Recipe.TABLE_NAME +"."+
                    DbContract.Recipe.COLUMN_CUISINE + "="+
                    DbContract.Cuisine.TABLE_NAME +"."+
                    DbContract.Cuisine._ID + " WHERE ");
            for (int i = 0; i < notCuisineArgs.size(); ++i) {
                query.append(DbContract.Cuisine.TABLE_NAME +"."+
                        DbContract.Cuisine.COLUMN_CUISINE_NAME);
                query.append(" LIKE \'%" + notCuisineArgs.get(i) + "%\' ");
                if (i + 1 < notCuisineArgs.size()) {
                    query.append(" AND ");
                }
            }
        }
        if (!notMealArgs.isEmpty()) {
            if (!started) {
                started=true;
                query.append(" EXCEPT SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            } else {
                query.append(" UNION SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            }
            query.append(" INNER JOIN " + DbContract.MealType.TABLE_NAME +
                    " ON " + DbContract.Recipe.TABLE_NAME +"."+
                    DbContract.Recipe.COLUMN_MEALTYPE + "="+
                    DbContract.MealType.TABLE_NAME +"."+
                    DbContract.MealType._ID + " WHERE ");
            for (int i = 0; i < notMealArgs.size(); ++i) {
                query.append(DbContract.MealType.TABLE_NAME +"."+
                        DbContract.MealType.COLUMN_MEAL_TYPE_NAME);
                query.append(" LIKE \'%" + notMealArgs.get(i) + "%\' ");
                if (i + 1 < notMealArgs.size()) {
                    query.append(" AND ");
                }
            }
        }
        Log.e(LOG, query.toString());
        return buildRecipeList(query.toString());
    }


    /** get list of ingredients */
    public ArrayList<Ingredient> getIngredients(String str) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query;
        if (str == null || str.isEmpty()) {
            query = "SELECT * FROM " + DbContract.Ingredient.TABLE_NAME;
        }
        else {
            query = "SELECT * FROM " + DbContract.Ingredient.TABLE_NAME
                    + " WHERE " + DbContract.Ingredient.COLUMN_INGREDIENT_NAME + " LIKE \'" + str + "\'";
        }
        Log.e(LOG, "Searching: " +query);
        Cursor c = db.rawQuery(query, null);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.set_id(c.getLong(c.getColumnIndex(DbContract.Ingredient._ID)));
                ingredient.set_name(c.getString(c.getColumnIndex(DbContract.Ingredient.COLUMN_INGREDIENT_NAME)));
                ingredient.set_price((c.getDouble(c.getColumnIndex(DbContract.Ingredient.COLUMN_INGREDIENT_PRICE))));
                ingredients.add(ingredient);
            } while (c.moveToNext());
        }
        c.close();
        return ingredients;
    }

    /** get list of cuisines */
    public ArrayList<Cuisine> getCuisines(String str) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbContract.Cuisine.TABLE_NAME + " WHERE " +
                DbContract.Cuisine.COLUMN_CUISINE_NAME + " LIKE \'%" + str + "%\'";
        Log.e(LOG, query);
        Cursor c = db.rawQuery(query, null);
        ArrayList<Cuisine> cuisines = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Cuisine cuisine = new Cuisine();
                cuisine.set_id(c.getLong(c.getColumnIndex(DbContract.Cuisine._ID)));
                cuisine.set_name(c.getString(c.getColumnIndex(DbContract.Cuisine.COLUMN_CUISINE_NAME)));
                cuisines.add(cuisine);
            } while (c.moveToNext());
        }
        c.close();
        return cuisines;
    }

    /** get list of meal types */
    public ArrayList<MealType> getMealTypes(String str) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbContract.MealType.TABLE_NAME +" WHERE " +
                DbContract.MealType.COLUMN_MEAL_TYPE_NAME+ " LIKE \'%" + str + "%\'";
        Log.e(LOG, query);
        Cursor c = db.rawQuery(query, null);
        ArrayList<MealType> mealtypes = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                MealType type = new MealType();
                type.set_id(c.getLong(c.getColumnIndex(DbContract.MealType._ID)));
                type.set_name(c.getString(c.getColumnIndex(DbContract.MealType.COLUMN_MEAL_TYPE_NAME)));
                mealtypes.add(type);
            } while (c.moveToNext());
        }
        c.close();
        return mealtypes;
    }

    public ArrayList<Recipe> getFavourites() {
        String query = "SELECT " + DbContract.Recipe.TABLE_NAME +".*"+
                " FROM " + DbContract.Recipe.TABLE_NAME + " WHERE " +
                DbContract.Recipe.COLUMN_FAVOURITE + "=1";
        Log.e(LOG, query.toString());
        return buildRecipeList(query.toString());
    }

//    public void cleanRecipes(){
//
//    }

}
