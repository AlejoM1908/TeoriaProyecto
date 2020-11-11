//Java imports
package lib.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AutomatonModel {
    private List<Character> _alphabet = new ArrayList<>();
    private List<String> _statesList;
    private String _initialState;
    private List<String> _acceptanceStates;
    private Map<String,Map<Character,TransitionModel>> _transitionFunction;
    private List<Character> _firstStackAlphabet;
    private List<Character> _secondStackAlphabet;
    
    public AutomatonModel(List<Character> alphabet, List<String> statesList,
            String initialState, List<String> acceptanceStates, Map<String,Map<Character,TransitionModel>> transitionFunction,
            List<Character> firstStackAlphabet, List<Character> secondStackAlphabet){
        this._alphabet = alphabet;
        this._statesList = statesList;
        this._initialState = initialState;
        this._acceptanceStates = acceptanceStates;
        this._transitionFunction = transitionFunction;
        this._transitionFunction = transitionFunction;
        this._firstStackAlphabet = firstStackAlphabet;
        this._secondStackAlphabet = secondStackAlphabet;
    }

    public List<Character> alphabet() {return _alphabet;}
    public List<String> statesList() {return _statesList;}
    public String initialState() {return _initialState;}
    public List<String> acceptanceStates() {return _acceptanceStates;}
    public Map<String,Map<Character,TransitionModel>> transitionFunction() {return _transitionFunction;}
    public List<Character> firstStackAlphabet() {return _firstStackAlphabet;}
    public List<Character> secondStackAlphabet() {return _secondStackAlphabet;}
}
