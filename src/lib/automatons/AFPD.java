//Java imports
package lib.automatons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        Object [] array = this.stack.toArray();
        String stack = "";
        for (int i = array.length-1; i >= 0; i--) {
            stack = stack.concat(array[i].toString());
            
        }
        return stack;
    }

    public boolean processString(String string){
        string = processStringR(string, false);
        if(string.equals("accepted")){
            return true;
        }else{
            return false;
        }
    }
    
    public void processStringWithDetails(String string){
        System.out.println(processStringR(string, true));
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
                if(string.isEmpty() && reject){
                    process = process.concat("(" + actualState + "," + " $, " + returnStackasString() + ") -> rejected");
                }
                return process;
            } else {
                return "rejected";
            }
        }

    }
    
    public void processStringList(List<String> stringList, String fileName, boolean print) throws IOException{
        File file = new File(System.getProperty("user.dir") + "\\resultadosProcesamiento\\" + fileName);
        String line;
        if (!file.exists()) {
            file.createNewFile();
        }
        
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        
        for(String actual : stringList){
            line = processStringR (actual, print);
            if(print){
                System.out.println(line);
            }
            if(line.contains("accepted")){
                bw.write(line.concat("\nYes \n\n"));;
            }else{
                bw.write(line.concat("\nNo \n\n"));;
            }
        }
        
        bw.close();
        
    }

}
