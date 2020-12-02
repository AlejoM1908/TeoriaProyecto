//Java imports
package lib.automatons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import lib.App.ArchiveReader;
import lib.models.AutomatonModel;
import lib.models.TransitionModel;

public class AFPN extends AFP {

    private ArrayList<TransitionModel>[][] delta;
    private AutomatonModel model;
    private ArrayList<String> procecution; 
    
    
    public AFPN(String path) {
        super(path);
        this.model = ArchiveReader.readAF(path);
        initializeAFPN();
    }

    public void initializeDelta(int sizeOfStates, int sizeofSigma) {
        this.delta = new ArrayList[sizeOfStates][sizeofSigma + 1];
        for (int i = 0; i < sizeOfStates; i++) {
            for (int j = 0; j < sizeofSigma; j++) {
                this.delta[i][j] = new ArrayList<TransitionModel>();
            }
        }
    }

    public void initializeAFPN() {
        this.initializeDelta(this.statesList.size(), this.alphabet.size());
        System.out.println(this.model.toString());

        this.model.transitionFunction().values().stream().forEach((sMap) -> {
            sMap.values().stream().forEach((cmap) -> {
                cmap.forEach((cMap) -> {
                    System.out.println("Estado: " + cMap.actualState() + this.statesList.contains(cMap.actualState()) + "Caracter: " + cMap.actualCharacter() + this.alphabet.contains(cMap.actualState()));

                    if (cMap.actualCharacter() == '$') {
                        this.delta[this.statesList.indexOf(cMap.actualState())][this.alphabet.size() + 1].add(cMap);
                    } else {
                        this.delta[this.statesList.indexOf(cMap.actualState())][this.alphabet.indexOf(cMap.actualCharacter())].add(cMap);
                    }
                }
                );

            }
            );
        }
        );
    }

    public int getRow(String state) {
        //esta función es para obtener la fila en la que se encuentra un estado (se asume columna 0)
        for (int i = 0; i < this.statesList.size(); i++) {
            //solo nos interesa la los elementos de la primera columna entonces por eso la fijamos en [j][0]
            if (state.equals(this.statesList.get(i))) {
                return i;
            }

        }
        return -1; // esto nunca deberia pasar a no se que pas eun error de digitación
    }

    public int getColumn(String symbol) {
        //esta función es para obtener la columna en la que se encuentra un simbolo (se asume fila 0 )
        if (symbol.equals("$")) {
            return this.alphabet.size() + 1;
        }

        for (int i = 0; i < this.alphabet.size(); i++) {
            //solo nos interesa la los elementos de la primera fila entonces por eso la fijamos en [0][i]
            if (symbol.equals(Character.toString(this.alphabet.get(i)))) {
                return i;
            }

        }
        return -1; // esto nunca deberia pasar a no se que pase un error de digitación
    }
    
    public String returnStackasString() {
        Object [] array = this.stack.toArray();
        String stack = "";
        for (int i = array.length-1; i >= 0; i--) {
            stack = stack.concat(array[i].toString());
            
        }
        return stack;
    }
    
    public ArrayList<TransitionModel>[][] getDelta() {
        return delta;
    }

    public List<Character> getStackAlphabet() {
        return stackAlphabet;
    }

    public Stack<Character> getStack() {
        return stack;
    }

    public List<Character> getAlphabet() {
        return alphabet;
    }

    public List<String> getStatesList() {
        return statesList;
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getAcceptanceStates() {
        return acceptanceStates;
    }

    public void modifyStack(String operation, Character parameter) {

    }

    public int procecution(String string, String filename) {
        int procecution = 0;
        ArrayList<String> processings = new ArrayList<String>();
        return procecution;
    }
    
    public int procecutionNumber (String string, String filename) {
        int procecution = this.procecution.size();
        return procecution;
    }
    
    public boolean procecutionState(String state) {
        if (this.acceptanceStates.contains(state)) {
                return true;
        }else {
                return false;
            }
    }
            
            
    public String processStringR(String string, boolean print) {
        String actualState;// este es el estado actual
        int actualStateP;//fila del estado actual
        String actualSymbol; //char a leer
        int actualSymbolP; //columna del char a leer
        String process = "Cadena: "+ string + "\n" + "Salida:"; //cadena con todo el procesamiento
        Character lamda = '$'; //caracter para comparar
        boolean reject = true; 
        String restore;
        actualState = this.initialState;
        this.stack = new Stack<>();
        int tam =this.transitionFunction.size();
        while(tam!=0){
            
                
        }
        return "-1";
        

    }
    
    
}

