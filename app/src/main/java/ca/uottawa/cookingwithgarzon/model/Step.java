package ca.uottawa.cookingwithgarzon.model;

/**
 * Created by joel on 22/11/16.
 */

public class Step {
    private int _id;
    private String instruction;
    private int _stepNumber;
    private int _time;

    public Step() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
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
