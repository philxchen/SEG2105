package ca.uottawa.cookingwithgarzon.model;

/**
 * Created by joel on 22/11/16.
 */

public class RecipeIngredient {
    private long _id;
    private long _recipe_id;
    private long _ingredient_id;
    private long _quantity;
    private String _unit;
//    private Ingredient _ingredient;
//    private Recipe _recipe;

    public RecipeIngredient() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long get_recipe_id() {
        return _recipe_id;
    }

    public void set_recipe_id(long _recipe_id) {
        this._recipe_id = _recipe_id;
    }

    public void set_recipe(long _recipe_id) {
        this._recipe_id = _recipe_id;
    }

    public long get_ingredient_id() {
        return _ingredient_id;
    }

    public void set_ingredient_id(long _ingredient_id) {
        this._ingredient_id = _ingredient_id;
    }

    public long get_quantity() {
        return _quantity;
    }

    public void set_quantity(long _quantity) {
        this._quantity = _quantity;
    }

    public String get_unit() {
        return _unit;
    }

    public void set_unit(String _unit) {
        this._unit = _unit;
    }

}
