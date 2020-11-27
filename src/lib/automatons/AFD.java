//Java imports
package lib.automatons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lib.App.ArchiveReader;
import lib.models.AutomatonModel;
import lib.models.TransitionModel;

public class AFD extends AF{
    
    private ArrayList<String>[][] delta;
    private AutomatonModel model;
    //private ArrayList<String> limboStates;
    //private ArrayList<String> inaccessibleStates = new ArrayList<>();

    public AFD(String path){
        this.model = ArchiveReader.readAF(path);
        this.alphabet = model.alphabet();
        this.initialState = model.initialState();
        this.statesList = model.statesList();
        this.acceptanceStates = model.acceptanceStates();
        initializeAFD();
    }

    public void initializeDelta(int sizeOfStates, int sizeofSigma) {
        this.delta = new ArrayList[sizeOfStates][sizeofSigma];
        for (int i = 0; i < sizeOfStates; i++) {
            for (int j = 0; j < sizeofSigma; j++) {
                this.delta[i][j] = new ArrayList<String>();
            }
        }
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
    
    public ArrayList<String>[][] getDelta() {
        return delta;
    }
    
    public void initializeAFD(){
        Map<String,Map<Character,TransitionModel>> deltaModel = this.model.transitionFunction(); 
        this.initializeDelta(this.statesList.size(), this.alphabet.size());
        
        deltaModel.values().stream().forEach((sMap) -> {
            sMap.values().stream().forEach((cMap)->{
                this.delta[this.statesList.indexOf(cMap.actualState())][this.alphabet.indexOf(cMap.actualCharacter())].add(cMap.transitionState());
            }
            );
        }
        );
    }
    
    public boolean processString(String string, boolean print){
        if(this.processStringR(string, print).contains("accepted")){
            return true;
        }else{
            return false;
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
            line = processStringR(actual, print);
            if(line.contains("accepted")){
                bw.write(line.concat("Yes \n\n"));;
            }else{
                bw.write(line.concat("No \n\n"));;
            }
        }
        
        bw.close();
        
    }
    
    public String processStringR(String string, boolean print) {
        String actualState;// este es el estado actual
        int actualStateP;//fila del estado actual
        String actualSymbol; //char a leer
        int actualSymbolP; //columna del char a leer
        String process; //cadena con todo el procesamiento
        
        actualState = this.initialState;
        
        process = "Cadena: "+string + "\n" + "Salida: \n";
        
        while (!string.isEmpty()) {
            actualStateP = this.getRow(actualState);     
            actualSymbol = Character.toString(string.charAt(0));
            
            if (string.length() > 1) {
                string = string.substring(1); //Este if es para controlar el caso en que solo quede o sea un string de tamaño 1
            } else {
                string = "";
            }
            
            process = process.concat("(" + actualState + "," + actualSymbol + string + ")->");
            
            actualSymbolP = this.getColumn(actualSymbol);
            
            if (!this.getDelta()[actualStateP][actualSymbolP].get(0).isEmpty()) {
                actualState = this.getDelta()[actualStateP][actualSymbolP].get(0);//Ya que esto es un AFD, siempre habra como maximo un elemento en esa posición
            }else {
                System.out.print("Usted no ingresó los estados limbo");
                break;
            }
        }
        
        process = process.concat("(" + actualState + ",$" + ")" + ">>");
        
        if (print == true) {
            System.out.print(process);
        }
            
        if(this.getAcceptanceStates().contains(actualState)){
            process = process.concat("accepted\n");
            System.out.print("accepted\n");
            return process;   
        }
        process = process.concat("rejected\n");
        System.out.print("rejected\n");
        return process;  
           
    }
    

    @Override
    public String toString() {
        return this.model.toString();
    }
    
    
    
    
}
