package ca.uottawa.cookingwithgarzon.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RecipeView.db";

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
                    DbContract.Recipe.COLUMN_RECIPE_NAME + REAL_TYPE + COMMA_SEP +
                    DbContract.Recipe.COLUMN_COST + TEXT_TYPE + COMMA_SEP +
                    DbContract.Recipe.COLUMN_DIFFICULTY + REAL_TYPE + COMMA_SEP +
                    DbContract.Recipe.COLUMN_SERVINGS + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for cuisine
                    DbContract.Recipe.COLUMN_CUISINE + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for mealtype
                    DbContract.Recipe.COLUMN_MEALTYPE + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for recipeingredient
                    DbContract.Recipe.COLUMN_RECIPE_INGREDIENT + INTEGER_TYPE + COMMA_SEP +
                    DbContract.Recipe.COLUMN_IMAGE + BLOB_TYPE + " )";

    private static final String SQL_CREATE_STEP_TABLE =
            "CREATE TABLE " + DbContract.Step.TABLE_NAME + " (" +
                    DbContract.Step._ID + " INTEGER PRIMARY KEY," +
                    DbContract.Step.COLUMN_INSTRUCTION + TEXT_TYPE + COMMA_SEP +
                    DbContract.Step.COLUMN_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    DbContract.Step.COLUMN_TIME + INTEGER_TYPE +
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

    private static final String SQL_CREATE_SHOPPINGCART_TABLE =
            "CREATE TABLE " + DbContract.ShoppingCart.TABLE_NAME + " (" +
                    DbContract.RecipeIngredient._ID + " INTEGER PRIMARY KEY )";

    private static final String SQL_CREATE_SHOPPINGCARTINGREDIENT_TABLE =
            "CREATE TABLE " + DbContract.ShoppingCartIngredient.TABLE_NAME + " (" +
                    DbContract.ShoppingCartIngredient._ID + " INTEGER PRIMARY KEY, "+
                    DbContract.ShoppingCartIngredient.COLUMN_SHOPPINGCART_ID + INTEGER_TYPE + COMMA_SEP +
                    DbContract.ShoppingCartIngredient.COLUMN_RECIPEINGREDIENT_ID + INTEGER_TYPE + ")";

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

    private static final String SQL_DELETE_SHOPPINGCART_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.ShoppingCart.TABLE_NAME;

    private static final String SQL_DELETE_SHOPPINGCARTINGREDIENT_TABLE =
            "DROP TABLE IF EXISTS " + DbContract.ShoppingCartIngredient.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RECIPE_TABLE);
        db.execSQL(SQL_CREATE_INGREDIENT_TABLE);
        db.execSQL(SQL_CREATE_RECIPEINGREDIENT_TABLE);
        db.execSQL(SQL_CREATE_STEP_TABLE);
        db.execSQL(SQL_CREATE_MEALTYPE_TABLE);
        db.execSQL(SQL_CREATE_CUISINE_TABLE);
        db.execSQL(SQL_CREATE_SHOPPINGCART_TABLE);
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
        db.execSQL(SQL_DELETE_SHOPPINGCART_TABLE);
        db.execSQL(SQL_DELETE_SHOPPINGCARTINGREDIENT_TABLE);
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
        values.put(DbContract.Recipe.COLUMN_RECIPE_NAME, recipe.get_name());
        values.put(DbContract.Recipe.COLUMN_COST, recipe.get_cost());
        values.put(DbContract.Recipe.COLUMN_DIFFICULTY, recipe.get_difficulty());
        values.put(DbContract.Recipe.COLUMN_SERVINGS, recipe.get_servings());
        values.put(DbContract.Recipe.COLUMN_CUISINE, cuisine.get_id());
        values.put(DbContract.Recipe.COLUMN_MEALTYPE, type.get_id());
        long recipe_id = db.insertOrThrow(DbContract.Recipe.TABLE_NAME, null, values);
        return recipe_id;
    }

    /**  create a recipeIngredient */
    public long createRecipeIngredient(Recipe recipe, Ingredient ingredient, long qty, String unit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.RecipeIngredient.COLUMN_RECIPE_ID, recipe.get_id());
        values.put(DbContract.RecipeIngredient.COLUMN_RECIPE_ID, ingredient.get_id());
        values.put(DbContract.RecipeIngredient.COLUMN_QUANTITY, qty);
        values.put(DbContract.RecipeIngredient.COLUMN_UNIT, unit);
        long recipeIngredient_id = db.insertOrThrow(DbContract.RecipeIngredient.TABLE_NAME, null, values);
        return recipeIngredient_id;
    }

    /**  create an ingredient */
    public long createIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Ingredient.COLUMN_INGREDIENT_NAME, ingredient.get_name());
        values.put(DbContract.Ingredient.COLUMN_INGREDIENT_PRICE, ingredient.get_price());
        long ingredient_id = db.insertOrThrow(DbContract.Ingredient.TABLE_NAME, null, values);
        return ingredient_id;
    }

    /**  create a cuisine */
    public long createCuisine(Cuisine cuisine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Cuisine.COLUMN_CUISINE_NAME, cuisine.get_name());
        long cuisine_id = db.insertOrThrow(DbContract.Cuisine.TABLE_NAME, null, values);
        return cuisine_id;
    }

    /**  create a meal type */
    public long createMealType(MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.MealType.COLUMN_MEAL_TYPE_NAME, type.get_name());
        long type_id = db.insertOrThrow(DbContract.MealType.TABLE_NAME, null, values);
        return type_id;
    }

    /** create a step */
    public long createStep(Step step) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Step.COLUMN_INSTRUCTION, step.get_instruction());
        values.put(DbContract.Step.COLUMN_NUMBER, step.get_stepNumber());
        values.put(DbContract.Step.COLUMN_TIME, step.get_time());
        long step_id = db.insertOrThrow(DbContract.Step.TABLE_NAME, null, values);
        return step_id;
    }
//
//    /** create a shopping cart */
//    public long createShoppingCart(Shopping cart) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        long shoppingcart_id = db.insertOrThrow(DbContract.ShoppingCart.TABLE_NAME, null, values);
//        return shoppingcart_id;
//    }


    /** get a recipe by id */
    public Recipe getRecipe(long recipe_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Query for retrieving recipe by recipe_id
        String selectRecipeQuery = "SELECT * FROM " + DbContract.Recipe.TABLE_NAME + " WHERE "
                + DbContract.Recipe._ID + " = " + recipe_id;

        Log.e(LOG, selectRecipeQuery);
        Cursor c = db.rawQuery(selectRecipeQuery, null);
        if (!c.moveToFirst()) return null;
        Recipe recipe = new Recipe();
        recipe.set_id(c.getLong(c.getColumnIndex(DbContract.Recipe._ID)));
        recipe.set_name(c.getString(c.getColumnIndex(DbContract.Recipe.COLUMN_RECIPE_NAME)));
        recipe.set_cost(c.getDouble((c.getColumnIndex((DbContract.Recipe.COLUMN_COST)))));
        recipe.set_difficulty(c.getDouble(c.getColumnIndex(DbContract.Recipe.COLUMN_DIFFICULTY)));
        recipe.set_servings(c.getInt(c.getColumnIndex(DbContract.Recipe.COLUMN_SERVINGS)));
        recipe.set_cuisine_id(c.getLong(c.getColumnIndex(DbContract.Recipe.COLUMN_CUISINE)));
        recipe.set_meal_type_id(c.getLong(c.getColumnIndex(DbContract.Recipe.COLUMN_MEALTYPE)));
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
        if (c == null) return null;
        c.moveToFirst();
        Ingredient ingredient = new Ingredient();
        ingredient.set_id(c.getInt(c.getColumnIndex(DbContract.Ingredient._ID)));
        ingredient.set_name(c.getString(c.getColumnIndex(DbContract.Ingredient.COLUMN_INGREDIENT_NAME)));
        ingredient.set_price(c.getDouble(c.getColumnIndex(DbContract.Ingredient.COLUMN_INGREDIENT_PRICE)));
        c.close();
        return ingredient;
    }

    /** get a RecipeIngredient by id */
    public RecipeIngredient getRecipeIngredient(long recipeingredient_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectRecipeQuery = "SELECT * FROM " + DbContract.RecipeIngredient.TABLE_NAME + " WHERE "
                + DbContract.RecipeIngredient._ID + " = " + recipeingredient_id;
        Log.e(LOG, selectRecipeQuery);
        Cursor c = db.rawQuery(selectRecipeQuery, null);
        if (c == null) return null;
        c.moveToFirst();
        RecipeIngredient recipeingredient = new RecipeIngredient();
        recipeingredient.set_id(c.getInt(c.getColumnIndex(DbContract.RecipeIngredient._ID)));
        recipeingredient.set_quantity(c.getInt(c.getColumnIndex(DbContract.RecipeIngredient.COLUMN_QUANTITY)));
        recipeingredient.set_unit(c.getString(c.getColumnIndex(DbContract.RecipeIngredient.COLUMN_UNIT)));
        c.close();
        return recipeingredient;
    }

    /** get cuisine by id */
    public Cuisine getCuisine(long cuisine_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectRecipeQuery = "SELECT * FROM " + DbContract.Cuisine.TABLE_NAME + " WHERE "
                + DbContract.Cuisine._ID + " = " + cuisine_id;
        Log.e(LOG, selectRecipeQuery);
        Cursor c = db.rawQuery(selectRecipeQuery, null);
        if (c == null) return null;
        c.moveToFirst();
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
        if (c == null) return null;
        c.moveToFirst();
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
        String selectRecipeIngredient = "SElECT * FROM " + DbContract.RecipeIngredient.TABLE_NAME +
                " WHERE " + DbContract.RecipeIngredient.COLUMN_RECIPE_ID + " = " + recipe_id;

        // populate array list with recipeIngredient objects
        Cursor c = db.rawQuery(selectRecipeIngredient, null);
        ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.set_id(c.getInt(c.getColumnIndex(DbContract.RecipeIngredient._ID)));
                recipeIngredient.set_quantity(c.getInt(c.getColumnIndex(DbContract.RecipeIngredient.COLUMN_QUANTITY)));
                recipeIngredient.set_unit(c.getString(c.getColumnIndex(DbContract.RecipeIngredient.COLUMN_UNIT)));
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
        String selectStep = "SElECT * FROM " + DbContract.Step.TABLE_NAME +
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

    /** Update operations */

    public boolean updateRecipe(Recipe recipe, Cuisine cuisine, MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Recipe.COLUMN_RECIPE_NAME, recipe.get_name());
        values.put(DbContract.Recipe.COLUMN_COST, recipe.get_cost());
        values.put(DbContract.Recipe.COLUMN_DIFFICULTY, recipe.get_difficulty());
        values.put(DbContract.Recipe.COLUMN_SERVINGS, recipe.get_servings());
        values.put(DbContract.Recipe.COLUMN_CUISINE, cuisine.get_id());
        values.put(DbContract.Recipe.COLUMN_MEALTYPE, type.get_id());
        return db.update(DbContract.Recipe.TABLE_NAME, values, "_ID=" + recipe.get_id(), null) > 0;
    }

    public boolean updateIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Ingredient.COLUMN_INGREDIENT_NAME, ingredient.get_name());
        values.put(DbContract.Ingredient.COLUMN_INGREDIENT_PRICE, ingredient.get_price());
        return db.update(DbContract.Ingredient.TABLE_NAME, values, "_ID=" + ingredient.get_id(), null) > 0;
    }

    public boolean updateRecipeIngredient(RecipeIngredient recipeIngredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.RecipeIngredient.COLUMN_RECIPE_ID, recipeIngredient.get_recipe_id());
        values.put(DbContract.RecipeIngredient.COLUMN_INGREDIENT_ID, recipeIngredient.get_ingredient_id());
        values.put(DbContract.RecipeIngredient.COLUMN_QUANTITY, recipeIngredient.get_quantity());
        values.put(DbContract.RecipeIngredient.COLUMN_UNIT, recipeIngredient.get_unit());
        return db.update(DbContract.RecipeIngredient.TABLE_NAME, values, "_ID="+ recipeIngredient.get_id(), null) > 0;
    }

    public boolean updateStep(Step step) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Step.COLUMN_INSTRUCTION, step.get_instruction());
        values.put(DbContract.Step.COLUMN_TIME, step.get_time());
        values.put(DbContract.Step.COLUMN_NUMBER, step.get_stepNumber());
        return db.update(DbContract.Step.TABLE_NAME, values, "_ID=" + step.get_id(), null) > 0;
    }

    public boolean updateMealType(MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.MealType.COLUMN_MEAL_TYPE_NAME, type.get_name());
        return db.update(DbContract.MealType.TABLE_NAME, values, "_ID=" + type.get_id(), null) > 0;
    }

    public boolean updateCuisine(Cuisine cuisine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Cuisine.COLUMN_CUISINE_NAME, cuisine.get_name());
        return db.update(DbContract.MealType.TABLE_NAME, values, "_ID=" + cuisine.get_id(), null) > 0;
    }

    /** Delete operations */

    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbContract.Step.TABLE_NAME, DbContract.Step.COLUMN_RECIPE +"="+ recipe.get_id(), null);
        db.delete(DbContract.RecipeIngredient.TABLE_NAME, DbContract.RecipeIngredient.COLUMN_RECIPE_ID +"="+ recipe.get_id(), null);
        db.delete(DbContract.Recipe.TABLE_NAME, "_ID=" + recipe.get_id(), null);
    }

    public void deleteIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbContract.RecipeIngredient.TABLE_NAME,
                DbContract.RecipeIngredient.COLUMN_INGREDIENT_ID +"="+ ingredient.get_id(), null);
        db.delete(DbContract.Ingredient.TABLE_NAME, "_ID="+ ingredient.get_id(), null);
    }

    public void deleteRecipeIngredient(RecipeIngredient recipeIngredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbContract.RecipeIngredient.TABLE_NAME, "_ID="+ recipeIngredient.get_id(), null);
    }

    public void deleteStep(Step step) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbContract.Step.TABLE_NAME, "_ID="+ step.get_id(), null);
    }

    public void deleteMealType(MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(DbContract.MealType.TABLE_NAME, "_ID="+ type.get_id(), null);
    }

    public void deleteCuisine(Cuisine cuisine) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbContract.Cuisine.TABLE_NAME, "_ID="+ cuisine.get_id(), null);
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
                recipe.set_difficulty(c.getDouble(c.getColumnIndex(DbContract.Recipe.COLUMN_DIFFICULTY)));
                recipe.set_servings(c.getInt(c.getColumnIndex(DbContract.Recipe.COLUMN_SERVINGS)));
                recipe.set_cuisine_id(c.getLong(c.getColumnIndex(DbContract.Recipe.COLUMN_CUISINE)));
                recipe.set_meal_type_id(c.getLong(c.getColumnIndex(DbContract.Recipe.COLUMN_MEALTYPE)));
                recipes.add(recipe);
            } while (c.moveToNext());
        }
        c.close();
        return recipes;
    }

    /** Algorithm for searching for recipes by name, ingredient, cuisine, type */
    public ArrayList<Recipe> findRecipe(String name, String ingredient, String cuisine, String type) {

        boolean started = false;
        StringBuilder query = new StringBuilder();

        if (name == null || !name.isEmpty()) {
            started = true;
            String[] args = name.split(" AND ");
            query.append("SELECT " + DbContract.Recipe.TABLE_NAME +"."+
                    DbContract.Recipe._ID +
                    " FROM " + DbContract.Recipe.TABLE_NAME + " WHERE ");

            for (int i = 0; i < args.length; ++i) {
                query.append(DbContract.Recipe.TABLE_NAME +"."+
                        DbContract.Recipe.COLUMN_RECIPE_NAME);
                if (args[i].toLowerCase().startsWith("not")) {
                    query.append(" NOT LIKE \'" + args[i].substring(4) + "\' ");
                } else {
                    query.append(" LIKE \'" + args[i] + "\' ");
                }
                if (i + 1 < args.length) {
                    query.append(" OR ");
                }
            }
        }
        if (ingredient == null || !ingredient.isEmpty()) {
            if (!started) {
                started=true;
                query.append("SELECT " + DbContract.Recipe.TABLE_NAME +"."+
                        DbContract.Recipe._ID +
                        " FROM " + DbContract.Recipe.TABLE_NAME);
            } else {
                query.append(" INTERSECT SELECT " + DbContract.Recipe.TABLE_NAME +"."+
                        DbContract.Recipe._ID +
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
            String[] args = ingredient.split(" AND ");
            for (int i = 0; i < args.length; ++i) {
                query.append(DbContract.Ingredient.TABLE_NAME +"." +
                        DbContract.Ingredient.COLUMN_INGREDIENT_NAME);
                if (args[i].toLowerCase().startsWith("not")) {
                    query.append(" NOT LIKE \'" + args[i].substring(4) + "\' ");
                } else {
                    query.append(" LIKE \'" + args[i] + "\' ");
                }
                if (i + 1 < args.length) {
                    query.append(" OR ");
                }
            }
        }
        if (cuisine == null || !cuisine.isEmpty()) {
            if (!started) {
                started=true;
                query.append("SELECT " + DbContract.Recipe.TABLE_NAME +"."+
                        DbContract.Recipe._ID +
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            } else {
                query.append(" INTERSECT SELECT " + DbContract.Recipe.TABLE_NAME +"."+
                        DbContract.Recipe._ID +
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            }
            query.append(" INNER JOIN " + DbContract.Cuisine.TABLE_NAME +
                    " ON " + DbContract.Recipe.TABLE_NAME +"."+
                    DbContract.Recipe.COLUMN_CUISINE + "="+
                    DbContract.Cuisine.TABLE_NAME +"."+
                    DbContract.Cuisine._ID + " WHERE ");
            String[] args = ingredient.split(" AND ");
            for (int i = 0; i < args.length; ++i) {
                query.append(DbContract.Cuisine.TABLE_NAME +"."+
                        DbContract.Cuisine.COLUMN_CUISINE_NAME);
                if (args[i].toLowerCase().startsWith("not")) {
                    query.append(" NOT LIKE \'" + args[i].substring(4) + "\' ");
                } else {
                    query.append(" LIKE \'" + args[i] + "\' ");
                }
                if (i + 1 < args.length) {
                    query.append(" OR ");
                }
            }
        }
        if (type == null || !type.isEmpty()) {
            if (!started) {
                query.append("SELECT " + DbContract.Recipe.TABLE_NAME +"."+
                        DbContract.Recipe._ID +
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            } else {
                query.append(" INTERSECT SELECT " + DbContract.Recipe.TABLE_NAME +"."+
                        DbContract.Recipe._ID +
                        " FROM "+ DbContract.Recipe.TABLE_NAME);
            }
            query.append(" INNER JOIN " + DbContract.MealType.TABLE_NAME +
                    " ON " + DbContract.Recipe.TABLE_NAME +"."+
                    DbContract.Recipe.COLUMN_MEALTYPE + "="+
                    DbContract.MealType.TABLE_NAME +"."+
                    DbContract.MealType._ID + " WHERE ");
            String[] args = ingredient.split(" AND ");
            for (int i = 0; i < args.length; ++i) {
                query.append(DbContract.MealType.TABLE_NAME +"."+
                        DbContract.MealType.COLUMN_MEAL_TYPE_NAME);
                if (args[i].toLowerCase().startsWith("not")) {
                    query.append(" NOT LIKE \'" + args[i].substring(4) + "\' ");
                } else {
                    query.append(" LIKE \'" + args[i] + "\' ");
                }
                if (i + 1 < args.length) {
                    query.append(" OR ");
                }
            }
        }
        if (!started)
            query.append("SELECT * FROM " + DbContract.Recipe.TABLE_NAME);
        return buildRecipeList(query.toString());
    }


    /** get list of ingredients */
    public ArrayList<Ingredient> getIngredients() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbContract.Ingredient.TABLE_NAME;
        Log.e(LOG, query);
        Cursor c = db.rawQuery(query, null);
        ArrayList<Ingredient> ingredients= new ArrayList<>();
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
    public ArrayList<Cuisine> getCuisines() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbContract.Cuisine.TABLE_NAME;
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
    public ArrayList<MealType> getMealTypes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DbContract.MealType.TABLE_NAME;
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

}
