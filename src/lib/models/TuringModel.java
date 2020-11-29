//Java imports
package lib.models;
import java.util.ArrayList;
import java.util.Map;

public class TuringModel {
    private ArrayList<Character> _alphabet;
    private ArrayList<String> _statesList;
    private String _initialState;
    private ArrayList<String> _acceptanceList;
    private ArrayList<Character> _stringAlphabet;
    private Map<String,Map<Character,TuringTransitionModel>> _transitionFunction;

    public TuringModel(ArrayList<Character> _alphabet, ArrayList<String> _statesList, String _initialState,
            ArrayList<String> _acceptanceList, ArrayList<Character> _stringAlphabet, Map<String,Map<Character,
            TuringTransitionModel>> _transitionFunction){
        this._alphabet = _alphabet;
        this._statesList = _statesList;
        this._initialState = _initialState;
        this._acceptanceList = _acceptanceList;
        this._stringAlphabet = _stringAlphabet;
        this._transitionFunction = _transitionFunction;
    }

    public String toString(){
        return null;
    }

    public ArrayList<Character> alphabet() {return this._alphabet;};
    public ArrayList<String> statesList() {return this._statesList;};
    public String initialState() {return this._initialState;};
    public ArrayList<String> acceptanceList() {return this._acceptanceList;};
    public ArrayList<Character> stringAlphabet() {return this._stringAlphabet;};
    public Map<String,Map<Character,TuringTransitionModel>> transitionFunction() {return this._transitionFunction;};
}
