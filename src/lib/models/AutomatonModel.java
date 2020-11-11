//Java imports
package lib.models;

import java.util.List;
import java.util.Map;

public class AutomatonModel {
    private List<Character> _alphabet;
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

    public String toString(){
        String[] alphabet = this._alphabet.toString().split("\\[|,|\\]| ");
        String[] statesList = this._statesList.toString().split("\\[|,|\\]| ");
        String[] acceptanceStates = this._acceptanceStates.toString().split("\\[|,|\\]| ");
        String[] firstStackAlphabet = this._firstStackAlphabet.toString().split("\\[|,|\\]| ");
        String[] secondStackAlphabet = this._secondStackAlphabet.toString().split("\\[|,|\\]| ");
        String resultString = "";

        resultString += "#alphabet\n";
        for (String s: alphabet){
            if (s.compareTo("") != 0) resultString += s + "\n";
        }

        resultString += "states\n";
        for (String s: statesList){
            if (s.compareTo("") != 0) resultString += s + "\n";
        }

        resultString += "#initial\n";
        resultString += this._initialState + "\n";

        resultString += "#accepting\n";
        for (String s: acceptanceStates){
            if (s.compareTo("") != 0) resultString += s + "\n";
        }

        if (firstStackAlphabet.length > 0){
            resultString += "#firstStack\n";
            for (String s: firstStackAlphabet){
                if (s.compareTo("") != 0) resultString += s + "\n";
            }
        }

        if (secondStackAlphabet.length > 0){
            resultString += "#secondStack\n";
            for (String s: firstStackAlphabet){
                if (s.compareTo("") != 0) resultString += s + "\n";
            }
        }

        resultString += "#transitions\n";
        for (Map.Entry<String, Map<Character,TransitionModel>> entry1: this._transitionFunction.entrySet()){
            for (Map.Entry<Character,TransitionModel> entry2: entry1.getValue().entrySet()){
                resultString += entry2.getValue().toString() + "\n";
            }
        }

        return resultString;
    }

    public List<Character> alphabet() {return _alphabet;}
    public List<String> statesList() {return _statesList;}
    public String initialState() {return _initialState;}
    public List<String> acceptanceStates() {return _acceptanceStates;}
    public Map<String,Map<Character,TransitionModel>> transitionFunction() {return _transitionFunction;}
    public List<Character> firstStackAlphabet() {return _firstStackAlphabet;}
    public List<Character> secondStackAlphabet() {return _secondStackAlphabet;}
}
