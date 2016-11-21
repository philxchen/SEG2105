package ca.uottawa.cookingwithgarzon;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ca.uottawa.cookingwithgarzon.RecipeContract.*;

/**
 * Helper class that facilitates interaction with the SQLite Database
 * Created by joel on 19/11/16.
 */

public class RecipeDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Recipe.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";


    // SQL Statements for creating tables
    private static final String SQL_CREATE_RECIPE_TABLE =
            "CREATE TABLE " + Recipe.TABLE_NAME + " (" +
                    Recipe._ID + " INTEGER PRIMARY KEY," +
                    Recipe.COLUMN_NAME_RECIPE_NAME + REAL_TYPE + COMMA_SEP +
                    Recipe.COLUMN_NAME_COST + TEXT_TYPE + COMMA_SEP +
                    Recipe.COLUMN_NAME_DIFFICULTY + REAL_TYPE + COMMA_SEP +
                    Recipe.COLUMN_NAME_SERVINGS + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for cuisine
                    Recipe.COLUMN_NAME_CUISINE + INTEGER_TYPE + COMMA_SEP +
                    // foreign key for mealtype
                    Recipe.COLUMN_NAME_MEALTYPE + INTEGER_TYPE + COMMA_SEP +
                    Recipe.COLUMN_NAME_IMAGE + BLOB_TYPE + " )";

    private static final String SQL_CREATE_STEP_TABLE =
            "CREATE TABLE " + Step.TABLE_NAME + " (" +
                    Step._ID + " INTEGER PRIMARY KEY," +
                    Step.COLUMN_NAME_INSTRUCTION + TEXT_TYPE + COMMA_SEP +
                    Step.COLUMN_NAME_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    Step.COLUMN_NAME_TIME + INTEGER_TYPE +
                    // foreign key for recipe
                    Step.COLUMN_NAME_RECIPE + INTEGER_TYPE + " )";

    private static final String SQL_CREATE_INGREDIENT_TABLE =
            "CREATE TABLE " + Ingredient.TABLE_NAME + " (" +
                    Ingredient._ID + " INTEGER PRIMARY KEY," +
                    Ingredient.COLUMN_NAME_INGREDIENT_NAME + TEXT_TYPE + COMMA_SEP +
                    Ingredient.COLUMN_NAME_INGREDIENT_PRICE + REAL_TYPE + " )";

    private static final String SQL_CREATE_RECIPEINGREDIENT_TABLE =
            "CREATE TABLE " + RecipeIngredient.TABLE_NAME + " (" +
                    RecipeIngredient._ID + " INTEGER PRIMARY KEY," +
                    RecipeIngredient.COLUMN_NAME_RECIPE_ID + INTEGER_TYPE + COMMA_SEP +
                    RecipeIngredient.COLUMN_NAME_INGREDIENT_ID + INTEGER_TYPE + COMMA_SEP +
                    RecipeIngredient.COLUMN_NAME_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                    RecipeIngredient.COLUMN_NAME_UNIT + TEXT_TYPE + " )";

    private static final String SQL_CREATE_CUISINE_TABLE =
            "CREATE TABLE " + Cuisine.TABLE_NAME + " (" +
                    Cuisine._ID + " INTEGER PRIMARY KEY," +
                    Cuisine.COLUMN_NAME_CUISINE_NAME + TEXT_TYPE + " )";

    private static final String SQL_CREATE_MEALTYPE_TABLE =
            "CREATE TABLE " + MealType.TABLE_NAME + " (" +
                    MealType._ID + " INTEGER PRIMARY KEY," +
                    MealType.COLUMN_NAME_MEAL_TYPE_NAME + TEXT_TYPE + " )";


    // SQL statements for deleting tables
    private static final String SQL_DELETE_STEP_TABLE =
            "DROP TABLE IF EXISTS " + Step.TABLE_NAME;

    private static final String SQL_DELETE_RECIPE_TABLE =
            "DROP TABLE IF EXISTS " + Recipe.TABLE_NAME;

    private static final String SQL_DELETE_INGREDIENT_TABLE =
            "DROP TABLE IF EXISTS " + Ingredient.TABLE_NAME;

    private static final String SQL_DELETE_RECIPEINGREDIENT_TABLE =
            "DROP TABLE IF EXISTS " + RecipeIngredient.TABLE_NAME;

    private static final String SQL_DELETE_CUISINE_TABLE =
            "DROP TABLE IF EXISTS " + Cuisine.TABLE_NAME;

    private static final String SQL_DELETE_MEALTYPE_TABLE =
            "DROP TABLE IF EXISTS " + MealType.TABLE_NAME;


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

}
