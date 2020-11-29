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

public class AFPD extends AFP {

    private ArrayList<TransitionModel>[][] delta;
    private AutomatonModel model;

    public AFPD(String path) {
        super(path);
        this.model = ArchiveReader.readAF(path);
        initializeAFPD();
    }

    public void initializeDelta(int sizeOfStates, int sizeofSigma) {
        this.delta = new ArrayList[sizeOfStates][sizeofSigma + 1];
        for (int i = 0; i < sizeOfStates; i++) {
            for (int j = 0; j < sizeofSigma; j++) {
                this.delta[i][j] = new ArrayList<TransitionModel>();
            }
        }
    }

    public void initializeAFPD() {
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
        return Arrays.toString(this.stack.toArray());
    }

    public String processStringR(String string, boolean print) {
        String actualState;// este es el estado actual
        int actualStateP;//fila del estado actual
        String actualSymbol; //char a leer
        int actualSymbolP; //columna del char a leer
        String process = ""; //cadena con todo el procesamiento
        Character lamda = '$';
        boolean reject = true;

        actualState = this.initialState;
        while (!string.isEmpty() && reject) {
            actualStateP = this.getRow(actualState);
            actualSymbol = Character.toString(string.charAt(0));
            System.out.println("Actual string:" + string);

            if (string.length() > 1) {
                string = string.substring(1); //Este if es para controlar el caso en que solo quede o sea un string de tamaño 1
            } else {
                string = "";
            }
            process = process.concat("(" + actualState + "," + actualSymbol + string + ", "); //+ ")->");
            actualSymbolP = this.getColumn(actualSymbol);

            if (!this.getDelta()[actualStateP][actualSymbolP].isEmpty()) {
                for (TransitionModel l : this.getDelta()[actualStateP][actualSymbolP]) {
                    if (this.stack.empty() && l.firstStackCharacter().charAt(0) == lamda) {
                        actualState = l.transitionState();
                        this.stack.push(l.firstStackAction().charAt(0));
                        process = process.concat("$)->");
                        System.out.println("1");
                        break;
                    } else if (l.firstStackCharacter().charAt(0) == lamda) {
                        process = process.concat(returnStackasString() + ")->");
                        if (l.firstStackAction().charAt(0) != lamda) {
                            this.stack.push(l.firstStackAction().charAt(0));
                            System.out.println("2");
                        }
                        actualState = l.transitionState();
                        System.out.println("7");
                        break;

                    } else if (!this.stack.isEmpty()) {
                        if (l.firstStackCharacter().charAt(0) == this.stack.peek()) {
                            process = process.concat(returnStackasString() + ")->");
                            if (l.firstStackAction().charAt(0) == lamda) {
                                this.stack.pop();
                                System.out.println("3");
                            } else {
                                this.stack.pop();
                                this.stack.push(l.firstStackAction().charAt(0));
                                System.out.println("4");
                            }
                            actualState = l.transitionState();
                            break;
                        }
                    } else {
                        reject = false;
                        process = process.concat("[$])");
                        System.out.println("5");
                        break;
                    }

                }

            } else {
                reject = false;
                if (this.stack.isEmpty()) {
                    process = process.concat("[$])");
                } else {
                    process = process.concat(returnStackasString() + ")->");
                }
                System.out.println("6");
            }

        }
        if (this.acceptanceStates.contains(actualState) && this.stack.empty() && reject) {
            System.out.print(process);
            System.out.print("(" + actualState + "," + " $, [$])");
            System.out.print("accepted\n");

            return "";
        } else {
            System.out.print(process);
            System.out.print("rejected\n");
            return "";
        }

    }

}
