package lib.models;

public class TransitionModel{
    private String _actualState;
    private char _actualCharacter;
    private String _firstStackCharacter;
    private String _secondStackCharacter;
    private String _transitionState;
    private String _firstStackAction;
    private String _secondStackAction;

    public TransitionModel(String actualState, char actualCharacter, String transitionState){
        this._actualState = actualState;
        this._actualCharacter = actualCharacter;
        this._transitionState = transitionState;
    }

    public TransitionModel(String actualState, char actualCharacter, String firstStackCharacter, 
        String transitionState, String firstStackAction){
        this._actualState = actualState;
        this._actualCharacter = actualCharacter;
        this._firstStackCharacter = firstStackCharacter;
        this._transitionState = transitionState;
        this._firstStackAction = firstStackAction;
    }

    public TransitionModel(String actualState, char actualCharacter, String firstStackCharacter, 
        String secondStackCharacter, String transitionState, String firstStackAction, 
        String secondStackAction){
        this._actualState = actualState;
        this._actualCharacter = actualCharacter;
        this._firstStackCharacter = firstStackCharacter;
        this._secondStackCharacter = secondStackCharacter;
        this._transitionState = transitionState;
        this._firstStackAction = firstStackAction;
        this._secondStackAction = secondStackAction;
    }

    public String toString(){
        if (this._secondStackCharacter == null || this._secondStackAction == null){
            if (this._firstStackCharacter == null || this._firstStackAction == null){
                return this._actualState + ":" + this._actualCharacter + 
                ">" + this._transitionState;
            }
            else{
                return this._actualState + ":" + this._actualCharacter +
                ":" + this._firstStackCharacter + ">" + this._transitionState + 
                ":" + this._firstStackAction;
            }
        }
        else{
            return this._actualState + ":" + this._actualCharacter +
            ":" + this._firstStackCharacter + ":" + this._secondStackCharacter +
            ">" + this._transitionState + ":" + this._firstStackAction + ":" + 
            this._secondStackAction;
        }
    }

    public String actualState() {return _actualState;}
    public char actualCharacter() {return _actualCharacter;}
    public String firstStackCharacter() {return _firstStackCharacter;}
    public String secondStackCharacter() {return _secondStackCharacter;}
    public String transitionState() {return _transitionState;}
    public String firstStackAction() {return _firstStackAction;}
    public String secondStackAction() {return _secondStackAction;}
}