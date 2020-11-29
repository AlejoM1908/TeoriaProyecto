//Java imports
package lib.automatons;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

//Proyect imports
import lib.App.ArchiveReader;
import lib.models.AutomatonModel;
import lib.models.TransitionModel;

public class AF2P extends AF{
    protected List<Character> firstStackAlphabet;
    protected List<Character> secondStackAlphabet;
    protected Stack<Character> firstStack, secondStack;
    private Queue<String> instantStates = new LinkedList<String>();

    /**
     * Contructor de la clase que recibe el path al documento que contiene
     * la información para inicializar el AF2P
     * @param documentPath
     */
    public AF2P(String documentPath){
        new ArchiveReader();
        final AutomatonModel model = ArchiveReader.readAF(documentPath);

        this.alphabet = model.alphabet();
        this.statesList = model.statesList();
        this.initialState = model.initialState();
        this.acceptanceStates = model.acceptanceStates();
        this.firstStackAlphabet = model.firstStackAlphabet();
        this.secondStackAlphabet = model.secondStackAlphabet();
        this.transitionFunction = model.transitionFunction();

        this.alphabet.add('$');
        this.firstStackAlphabet.add('$');
        this.secondStackAlphabet.add('$');
    }

    /**
     * Contructor de la clase que recibe uno a uno todos los parametros que
     * requiere el AF2P para funcionar
     * @param alphabet 
     * @param statesList
     * @param initialState
     * @param acceptanceStates
     * @param transitionFunction
     * @param firstStackAlphabet
     * @param secondStackAlphabet
     */
    public AF2P(List<Character> alphabet, List<String> statesList,
            String initialState, List<String> acceptanceStates, Map<String,Map<Character,TransitionModel>> transitionFunction,
            List<Character> firstStackAlphabet, List<Character> secondStackAlphabet){
        this.alphabet = alphabet;
        this.statesList = statesList;
        this.initialState = initialState;
        this.acceptanceStates = acceptanceStates;
        this.firstStackAlphabet = firstStackAlphabet;
        this.secondStackAlphabet = secondStackAlphabet;
        this.transitionFunction = transitionFunction;
    }

    /**
     * Función que permite modificar el contenido de la pila dada
     * @param stack
     * @param stackActtion
     */
    public boolean modifyStack(Stack<Character> stack, char stackCharacter, char stackAction ){
        if (stackCharacter == '$' && stackAction == '$'){
            return true;
        }
        else if (stackCharacter == '$' && stackAction != '$'){
            stack.add(stackAction);
            return true;
        }
        else if (stackCharacter != '$' && stackAction == '$'){
            if (stack.peek() == stackCharacter)
                return true;
        }
        else if (stackCharacter != '$' && stackAction != '$'){
            if (stack.peek() == stackCharacter){
                stack.pop();
                stack.add(stackAction);
                return true;
            }
        }

        return false;
    }

    public boolean processString(String string, String actualState){
        if (string.compareTo("") == 0 && this.acceptanceStates.contains(actualState))
            return true;
        else if (string.compareTo("") == 0)
            return false;
            
        boolean returnAcceptance = false;
        
        while(string.compareTo("") != 0){
            Map<Character,TransitionModel> entry = this.transitionFunction.get(actualState);
        }
    }

    public Queue<String> detailedProcessString(String string){
        
    }

    public void detailedFileProcessing(String string, String documentPath){

    }

    public String toString(){
        return new AutomatonModel(alphabet, statesList, initialState, acceptanceStates, transitionFunction, firstStackAlphabet, secondStackAlphabet).toString();
    }
}
