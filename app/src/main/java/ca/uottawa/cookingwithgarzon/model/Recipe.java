package ca.uottawa.cookingwithgarzon.model;

/**
 * Created by joel on 22/11/16.
 * Java Recipe Object
 */

public class Recipe {

    private long _id;
    private String _name;
    private double _cost;
    private double _difficulty;
    private int _servings;
    private MealType _type;
    private Cuisine _cuisine;
    private RecipeIngredient[] _recipeIngredients;
    private Step[] _steps;

    public Recipe() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public double get_cost() {
        return _cost;
    }

    public void set_cost(double _cost) {
        this._cost = _cost;
    }

    public double get_difficulty() {
        return _difficulty;
    }

    public void set_difficulty(double _difficulty) {
        this._difficulty = _difficulty;
    }

    public int get_servings() {
        return _servings;
    }

    public void set_servings(int _servings) {
        this._servings = _servings;
    }

    public Step[] get_steps() {
        return _steps;
    }

    public void set_steps(Step[] _steps) {
        this._steps = _steps;
    }

    public MealType get_type() {
        return _type;
    }

    public void set_type(MealType _type) {
        this._type = _type;
    }

    public Cuisine get_cuisine() {
        return _cuisine;
    }

    public void set_cuisine(Cuisine _cuisine) {
        this._cuisine = _cuisine;
    }

    public RecipeIngredient[] get_recipeIngredients() {
        return _recipeIngredients;
    }

    public void set_recipeIngredients(RecipeIngredient[] _recipeIngredients) {
        this._recipeIngredients = _recipeIngredients;
    }


}
