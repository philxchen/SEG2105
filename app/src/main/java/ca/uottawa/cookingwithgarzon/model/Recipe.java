package ca.uottawa.cookingwithgarzon.model;

/**
 * Created by joel on 22/11/16.
 * Java Recipe Object
 */

public class Recipe {

    private int _id;
    private String _name;
    private double _cost;
    private double _difficulty;
    private int _serving;

    public Recipe() {
    }

    public int get_id() {
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

    public int get_serving() {
        return _serving;
    }

    public void set_serving(int _serving) {
        this._serving = _serving;
    }
}
