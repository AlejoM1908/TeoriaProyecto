package lib.models;

public class TuringTransitionModel {
    private String _actualState;
    private char _actualCharacter;
    private String _transitionState;
    private char _transitionCharacter;
    private String _direction;

    public TuringTransitionModel(String _actualState, char _actualCharacter, String _transitionState,
            char _transitionCharacter, String _direction){
        this._actualState = _actualState;
        this._actualCharacter = _actualCharacter;
        this._transitionState = _transitionState;
        this._transitionCharacter = _transitionCharacter;
        this._transitionCharacter = _transitionCharacter;
        this._direction = _direction;
    }

    public String toString(){
        return null;
    }

    public String actualState() {return this._actualState;};
    public char actualCharacter() {return this._actualCharacter;};
    public String transitionState() {return this._transitionState;};
    public char transitionCharacter() {return this._transitionCharacter;};
    public String direction() {return this._direction;};
}
