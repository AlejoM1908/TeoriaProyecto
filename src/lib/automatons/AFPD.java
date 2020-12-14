//Java imports
package lib.automatons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import lib.App.ArchiveReader;
import lib.models.AutomatonModel;
import lib.models.TransitionModel;

public class AFPD extends AFP {

    private ArrayList<TransitionModel>[][] delta;
    private AutomatonModel model;

    public AFPD(String path) {
        super(path);
        this.model = ArchiveReader.readAF(path);
        initializeAFPD();
    }

    public AFPD(ArrayList<Character> alphabet, ArrayList<String> statesList, String initialState,
            ArrayList<String> acceptanceStates, List<Character> stackAlphabet, ArrayList<TransitionModel>[][] delta) {
        super(alphabet, statesList, initialState, acceptanceStates, stackAlphabet);
        this.delta = delta;
    }

    public void initializeDelta(int sizeOfStates, int sizeofSigma) {
        this.delta = new ArrayList[sizeOfStates][sizeofSigma];
        for (int i = 0; i < sizeOfStates; i++) {
            for (int j = 0; j < sizeofSigma; j++) {
                this.delta[i][j] = new ArrayList<TransitionModel>();
            }
        }
    }

    public void initializeAFPD() {
        this.initializeDelta(this.statesList.size(), this.alphabet.size());
        //System.out.println(this.model.toString());

        this.model.transitionFunction().values().stream().forEach((sMap) -> {
            sMap.values().stream().forEach((cmap) -> {
                cmap.forEach((cMap) -> {
                    //System.out.println("Estado: " + cMap.actualState() + this.statesList.contains(cMap.actualState()) + "Caracter: " + cMap.actualCharacter() + this.alphabet.contains(cMap.actualState()));
                    this.delta[this.statesList.indexOf(cMap.actualState())][this.alphabet.indexOf(cMap.actualCharacter())].add(cMap);
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
        for (int i = 0; i < this.alphabet.size(); i++) {
            //solo nos interesa la los elementos de la primera fila entonces por eso la fijamos en [0][i]
            if (symbol.equals(Character.toString(this.alphabet.get(i)))) {
                return i;
            }

        }
        return -1; // esto nunca deberia pasar a no se que pase un error de digitación
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

    public String returnStackasString() {
        if(this.stack.isEmpty()){
            return "$";
        }
        Object[] array = this.stack.toArray();
        String stack = "";
        for (int i = array.length - 1; i >= 0; i--) {
            stack = stack.concat(array[i].toString());

        }
        return stack;
    }

    public boolean processString(String string) {
        string = processStringR(string, false);
        if (string.equals("accepted")) {
            return true;
        } else {
            return false;
        }
    }

    public void processStringWithDetails(String string) {
        System.out.println(processStringR(string, true));
    }

    public String processStringR(String string, boolean print) {
        String actualState;// este es el estado actual
        int actualStateP;//fila del estado actual
        String actualSymbol; //char a leer
        int actualSymbolP; //columna del char a leer
        String process = "Cadena: " + string + "\n" + "Salida:"; //cadena con todo el procesamiento
        Character lamda = '$'; //caracter para comparar
        boolean reject = true;
        String restore;
        actualState = this.initialState;
        this.stack = new Stack<>();

        while (!string.isEmpty() && reject) {
            actualStateP = this.getRow(actualState);
            actualSymbol = Character.toString(string.charAt(0));
            restore = string;

            if (string.length() > 1) {
                string = string.substring(1); //Este if es para controlar el caso en que solo quede o sea un string de tamaño 1
            } else {
                string = "";
            }
            process = process.concat("(" + actualState + "," + actualSymbol + string + ", "); //+ ")->");
            actualSymbolP = this.getColumn(actualSymbol);
                if (!this.getDelta()[actualStateP][actualSymbolP].isEmpty()) {
                for (TransitionModel l : this.getDelta()[actualStateP][actualSymbolP]) {
                    if (l.firstStackCharacter().charAt(0) == lamda) {
                        if (l.firstStackAction().charAt(0) != lamda) {
                            if (this.stack.isEmpty()) {
                                process = process.concat("$)->");
                            } else {
                                process = process.concat(returnStackasString() + ")->");
                            }
                            this.stack.push(l.firstStackAction().charAt(0));
                        }else{
                            process = process.concat(returnStackasString()  + ")->");
                        }
                        actualState = l.transitionState();
                        break;
                    } else if (!this.stack.isEmpty()) {
                        if (l.firstStackCharacter().charAt(0) == this.stack.peek()) {
                            process = process.concat(returnStackasString() + ")->");
                            if (l.firstStackAction().charAt(0) == lamda) {
                                this.stack.pop();
                            } else {
                                this.stack.pop();
                                this.stack.push(l.firstStackAction().charAt(0));
                            }
                            actualState = l.transitionState();
                            break;
                        }
                    }else{
                        reject = false;
                        process = process.concat("$) -> rejected");
                        //break;
                    }
                }
            } else if (!this.getDelta()[actualStateP][getColumn("$")].isEmpty()) {
                for (TransitionModel l : this.getDelta()[actualStateP][getColumn("$")]) {
                    if (l.firstStackCharacter().charAt(0) == lamda) {
                        if (l.firstStackAction().charAt(0) != lamda) {
                            if (this.stack.isEmpty()) {
                                process = process.concat("$)->");
                            } else {
                                process = process.concat(returnStackasString() + ")->");
                            }
                            this.stack.push(l.firstStackAction().charAt(0));
                        }else{
                            process = process.concat(returnStackasString()  + ")->");
                        }
                        actualState = l.transitionState();
                        string = restore;
                        break;
                    } else if (!this.stack.isEmpty()) {
                        if (l.firstStackCharacter().charAt(0) == this.stack.peek()) {
                            process = process.concat(returnStackasString() + ")->");
                            if (l.firstStackAction().charAt(0) == lamda) {
                                this.stack.pop();
                            } else {
                                this.stack.pop();
                                this.stack.push(l.firstStackAction().charAt(0));
                            }
                            string = restore;
                            actualState = l.transitionState();
                            break;
                        }
                    }else{
                        reject = false;
                        process = process.concat("$) -> rejected");
                        //break;
                    }
                }
            } else {
                reject = false;
                if (this.stack.isEmpty()) {
                    process = process.concat("$) -> rejected");
                } else {
                    process = process.concat(returnStackasString() + ") -> rejected");
                }
            }
        }
        if (this.acceptanceStates.contains(actualState) && this.stack.empty() && reject) {
            process = process.concat("(" + actualState + "," + " $, $) -> accepted");
            if (print) {
                return process;
            } else {
                return "accepted";
            }
        } else {
            if (print) {
                if (string.isEmpty() && reject) {
                    process = process.concat("(" + actualState + "," + " $, " + returnStackasString() + ") -> rejected");
                    
                }
                return process;
            } else {
                return "rejected";
            }
        }

    }

    public void processStringList(List<String> stringList, String fileName, boolean print) throws IOException {
        File file = new File(System.getProperty("user.dir") + "\\resultadosProcesamiento\\" , fileName);
        String line;
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        for (String actual : stringList) {
            line = processStringR(actual, print);
            if (print) {
                System.out.println(line);
            }
            if (line.contains("accepted")) {
                bw.write(line.concat("\nYes \n\n"));;
            } else {
                bw.write(line.concat("\nNo \n\n"));;
            }
        }

        bw.close();

    }

    public AFPD cartesianProductAFD(AFD automatonAFD) {
        AFPD returnAFPD;    //AFPD que se retorna si el producto cartesiano es exitoso
        Character lamda = '$'; //caracter para comparar
        ArrayList<Character> cartesianAlphabet = new ArrayList<>(); //Alfabeto del AFPD resultado del producto cartesiano
        ArrayList<String> cartesianStatesList = new ArrayList<>();  //Lista de nuevos estados del AFPD resultado del producto cartesiano
        String cartesianInitialState;   //Estado Inicial del AFPD resultado del producto cartesiano
        ArrayList<String> cartesianAcceptanceStates = new ArrayList<>();    //Lista de Nuevos Estado de Aceptacion del AFPD resultado del producto cartesiano
        List<Character> cartesianStackAlphabet; // Alfabeto de Pila del AFPD resultado del producto cartesiano
        Map<String, Map<Character, ArrayList<TransitionModel>>> transitionFunction = new HashMap<>(); //Funcion de Trasition para el Automata Model de AFPD resultado del producto cartesiano
        AutomatonModel cartesianAutomaton;
        ArrayList<TransitionModel>[][] cartesianDelta;  //Delta Interno del AFPD
        Stack<Character> cartesianStack = new Stack<>();
        List<Character> secondStackAlphabet = new ArrayList<>(); //Lista Vacio no Usada para evitar un fallo en ToString() de AutomatonModel
        List<Character> auxAlphabet = new ArrayList<>(this.getAlphabet());
        if(auxAlphabet.indexOf(lamda)==-1){
            auxAlphabet = new ArrayList<>(this.getAlphabet());
        }else{
            auxAlphabet.remove(auxAlphabet.indexOf(lamda));
        }
        
        //Verifica si los automatas tienen el mismo alfabeto para realizar el producto cartesiano
        if (!automatonAFD.getAlphabet().toString().equals(auxAlphabet.toString())) {
            System.out.println("Los automatas no tiene el mismo alfabeto, no se puede hacer producto cartesiano");
            return null;
        } else {
            cartesianAlphabet = (ArrayList<Character>) this.getAlphabet();
            cartesianInitialState = (automatonAFD.getInitialState() + "~" + this.getInitialState());
            cartesianStackAlphabet = this.getStackAlphabet();

            for (int i = 0; i < this.getStatesList().size(); i++) {
                for (int j = 0; j < automatonAFD.getStatesList().size(); j++) {
                    cartesianStatesList.add((this.getStatesList().get(i) + "~" + automatonAFD.getStatesList().get(j)));
                }
            }

            for (int i = 0; i < this.getAcceptanceStates().size(); i++) {
                for (int j = 0; j < automatonAFD.getAcceptanceStates().size(); j++) {
                    cartesianAcceptanceStates.add((this.getAcceptanceStates().get(i) + "~" + automatonAFD.getAcceptanceStates().get(j)));
                }
            }

            //Ciclo que construye la matriz que almacena la funcion Delta
            cartesianDelta = new ArrayList[cartesianStatesList.size()][cartesianAlphabet.size()];
            for (int i = 0; i < cartesianStatesList.size(); i++) {
                for (int j = 0; j < cartesianAlphabet.size(); j++) {
                    cartesianDelta[i][j] = new ArrayList<>();
                }
            }

            //Ciclo que llena la funcion delta
            for (int i = 0; i < cartesianStatesList.size(); i++) {
                for (int j = 0; j < cartesianAlphabet.size(); j++) {
                    String[] currentCartesianState = cartesianStatesList.get(i).split("~");
                    String afpdState = currentCartesianState[0];
                    String afdState = currentCartesianState[1];
                    Character currentSymbolofCartesianAlfabet = cartesianAlphabet.get(j);
                    int currentAFDStateP = automatonAFD.getRow(afdState);
                    int currentAFPDStateP = this.getRow(afpdState);
                    int currentAFDSymbolP;
                    int currentAFPDSymbolP;
                    TransitionModel newTransModel;
                    if (currentSymbolofCartesianAlfabet.equals(lamda)) {
                        currentAFPDSymbolP = this.getColumn(Character.toString(currentSymbolofCartesianAlfabet));
                        if (!this.getDelta()[currentAFPDStateP][currentAFPDSymbolP].isEmpty()) {
                            for (TransitionModel l : this.getDelta()[currentAFPDStateP][currentAFPDSymbolP]) {
                                newTransModel = new TransitionModel(cartesianStatesList.get(i), currentSymbolofCartesianAlfabet,
                                        l.firstStackCharacter(), (l.transitionState() + "~" + afdState), l.firstStackAction());
                                cartesianDelta[i][j].add(newTransModel);
                            }
                        }
                    } else {
                        currentAFPDSymbolP = this.getColumn(Character.toString(currentSymbolofCartesianAlfabet));
                        currentAFDSymbolP = automatonAFD.getColumn(Character.toString(currentSymbolofCartesianAlfabet));
                        if (!this.getDelta()[currentAFPDStateP][currentAFPDSymbolP].isEmpty() && !automatonAFD.getDelta()[currentAFDStateP][currentAFDSymbolP].isEmpty()) {
                            for (TransitionModel l : this.getDelta()[currentAFPDStateP][currentAFPDSymbolP]) {
                                newTransModel = new TransitionModel(cartesianStatesList.get(i), currentSymbolofCartesianAlfabet,
                                        l.firstStackCharacter(), (l.transitionState() + "~" + automatonAFD.getDelta()[currentAFDStateP][currentAFDSymbolP].get(0)), l.firstStackAction());
                                cartesianDelta[i][j].add(newTransModel);
                            }
                        }
                    }
                }
            }
            
            for (int i = 0; i < cartesianStatesList.size(); i++) {
                for (int j = 0; j < cartesianAlphabet.size(); j++) {
                    if (!transitionFunction.containsKey(cartesianStatesList.get(i))) {
                        transitionFunction.put(cartesianStatesList.get(i),
                                new HashMap<Character, ArrayList<TransitionModel>>());
                    }
                    if (!transitionFunction.get(cartesianStatesList.get(i)).containsKey(cartesianAlphabet.get(j))) {
                        if (!cartesianDelta[i][j].isEmpty()) {
                            ArrayList<TransitionModel> transitionModel = new ArrayList<>();
                            for (TransitionModel l : cartesianDelta[i][j]) {
                                transitionModel.add(l);
                            }
                            transitionFunction.get(cartesianStatesList.get(i)).put(cartesianAlphabet.get(j), transitionModel);
                        }
                    }
                }
            }
            
            cartesianAutomaton = new AutomatonModel(cartesianAlphabet, cartesianStatesList, cartesianInitialState, cartesianAcceptanceStates, transitionFunction, cartesianStackAlphabet, secondStackAlphabet);
            returnAFPD = new AFPD(cartesianAlphabet, cartesianStatesList, cartesianInitialState, cartesianAcceptanceStates, cartesianStackAlphabet,
                    cartesianDelta);                                
            returnAFPD.model = cartesianAutomaton;
        }
        return returnAFPD;
    }
    
    @Override
    public String toString(){
        return this.model.toString();
    }
}
