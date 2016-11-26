package ca.uottawa.cookingwithgarzon.model;

import java.util.ArrayList;

/**
 * Created by joel on 22/11/16.
 * Java RecipeView Object
 */

public class Recipe {

    private long _id;
    private String _name;
    private double _cost;
    private double _difficulty;
    private int _servings;
    private long _meal_type_id;
    private long _cuisine_id;
    private ArrayList<Long> _recipeIngredient_ids;
    private ArrayList<Long> _step_ids;

    public Recipe() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
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

    public ArrayList<Long> get_steps() {
        return _step_ids;
    }

    public void set_steps(ArrayList<Long> _step_ids) {
        this._step_ids = _step_ids;
    }

    public long get_meal_type_id() {
        return _meal_type_id;
    }

    public void set_meal_type_id(long _type_id) {
        this._meal_type_id = _type_id;
    }

    public long get_cuisine_id() {
        return _cuisine_id;
    }

    public void set_cuisine_id(long _cuisine) {
        this._cuisine_id = _cuisine_id;
    }

    public ArrayList<Long> get_recipeIngredient_ids() {
        return _recipeIngredient_ids;
    }

    public void set_recipeIngredients(ArrayList<Long> _recipeIngredient_ids) {
        this._recipeIngredient_ids = _recipeIngredient_ids;
    }

}
