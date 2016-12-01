package ca.uottawa.cookingwithgarzon.model;

/**
 * Created by joel on 22/11/16.
 */

public class Step {
    private long _id;
    private long _recipe_id;

    public long get_recipe_id() {
        return _recipe_id;
    }

    public void set_recipe_id(long _recipe_id) {
        this._recipe_id = _recipe_id;
    }

    private String _instruction;
    private int _stepNumber;
    private int _time;


    public Step() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_instruction() {
        return _instruction;
    }

    public void set_instruction(String instruction) {
        this._instruction = instruction;
    }

    public int get_stepNumber() {
        return _stepNumber;
    }

    public void set_stepNumber(int _stepNumber) {
        this._stepNumber = _stepNumber;
    }

    public int get_time() {
        return _time;
    }

    public void set_time(int _time) {
        this._time = _time;
    }
}
