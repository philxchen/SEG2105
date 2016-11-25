package ca.uottawa.cookingwithgarzon.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import ca.uottawa.cookingwithgarzon.model.*;

/**
 * Helper class that facilitates interaction with the SQLite Database
 * Created by joel on 19/11/16.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Recipe.db";

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
        values.put(DbContract.Recipe.COLUMN_RECIPE_NAME, recipe.get_name());
        values.put(DbContract.Recipe.COLUMN_COST, recipe.get_cost());
        values.put(DbContract.Recipe.COLUMN_DIFFICULTY, recipe.get_difficulty());
        values.put(DbContract.Recipe.COLUMN_SERVINGS, recipe.get_servings());
        values.put(DbContract.Recipe.COLUMN_CUISINE, cuisine.get_id());
        values.put(DbContract.Recipe.COLUMN_MEALTYPE, type.get_id());
        long recipe_id = db.insert(DbContract.Recipe.TABLE_NAME, null, values);
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
        long recipeIngredient_id = db.insert(DbContract.RecipeIngredient.TABLE_NAME, null, values);
        return recipeIngredient_id;
    }

    /**  create an ingredient */
    public long createIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Ingredient.COLUMN_INGREDIENT_NAME, ingredient.get_name());
        values.put(DbContract.Ingredient.COLUMN_INGREDIENT_PRICE, ingredient.get_price());
        long ingredient_id = db.insert(DbContract.Ingredient.TABLE_NAME, null, values);
        return ingredient_id;
    }

    /**  create a cuisine */
    public long createCuisine(Cuisine cuisine) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Cuisine.COLUMN_CUISINE_NAME, cuisine.get_name());
        long cuisine_id = db.insert(DbContract.Cuisine.TABLE_NAME, null, values);
        return cuisine_id;
    }

    /**  create a meal type */
    public long createMealType(MealType type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.MealType.COLUMN_MEAL_TYPE_NAME, type.get_name());
        long type_id = db.insert(DbContract.MealType.TABLE_NAME, null, values);
        return type_id;
    }

    /** create a step */
    public long createStep(Step step) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.Step.COLUMN_INSTRUCTION, step.get_instruction());
        values.put(DbContract.Step.COLUMN_NUMBER, step.get_stepNumber());
        values.put(DbContract.Step.COLUMN_TIME, step.get_time());
        long step_id = db.insert(DbContract.Step.TABLE_NAME, null, values);
        return step_id;
    }

    /** create a grocery list */
    // TODO

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

    private String parseQueryString(String str) {

        if (str.isEmpty()) return null;

        ArrayList<String> select = new ArrayList<>();
        ArrayList<String> selectnot = new ArrayList<>();
        String[] args = str.split(" ");
        ArrayList<String> params = new ArrayList<>();
        for (int i=0; i< args.length; i++) {
            if (args[i].toLowerCase().equals("and") && i != 0 && i+1 <= args.length) {
                select.add(args[i+1]);
                i++;
            }
            if (args[i].toLowerCase().equals("not") && i+1 <= args.length) {
                selectnot.add(args[i+1]);
                i++;
            }
            else {
                select.add(args[i]);
            }
        }
        StringBuilder query = new StringBuilder();
        for (int j=0; j < select.size(); j++) {
            query.append(" LIKE \'" + select.get(j) + "\'");
            if (j < select.size() -1 ) {
                query.append(" AND ");
            }
        }
        for (int k=0; k < selectnot.size(); k++) {
            query.append(" NOT LIKE \'" + selectnot.get(k) + "\'");
            if (k < select.size() -1 ) {
                query.append(" AND ");
            }
        }
        return query.toString();

    }

}
