package ca.uottawa.cookingwithgarzon.model;

/**
 * Created by joel on 22/11/16.
 */

public class RecipeIngredient {
    private long _id;
    private long _recipe_id;
    private Recipe _recipe;
    private Ingredient _ingredient;
    private long _quantity;
    private String _unit;

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

    public Recipe get_recipe() {
        return _recipe;
    }

    public void set_recipe(Recipe _recipe) {
        this._recipe = _recipe;
    }

    public Ingredient get_ingredient() {
        return _ingredient;
    }

    public void set_ingredient(Ingredient _ingredient) {
        this._ingredient = _ingredient;
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
