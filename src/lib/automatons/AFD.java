//Java imports
package lib.automatons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AFD extends AF{
    
    private ArrayList<String>[][] delta;
    private ArrayList<String> limboStates;
    private ArrayList<String> inaccessibleStates = new ArrayList<>();

    public AFD() {
        super();
        this.limboStates = new ArrayList<>();
    }

    public void initializeDelta(int sizeOfStates, int sizeofSigma) {
        this.delta = new ArrayList[sizeOfStates][sizeofSigma];
        for (int i = 0; i < sizeOfStates; i++) {
            for (int j = 0; j < sizeofSigma; j++) {
                this.delta[i][j] = new ArrayList<String>();
            }
        }
    }
    
    public void showAlphabet() {
        System.out.println("#Alphabet:");
        if(this.alphabet.size()>1){
        System.out.println(this.alphabet.get(0)+"-"+this.alphabet.get(this.alphabet.size()-1));
        }else{
        System.out.println(this.alphabet.get(0));
        }
    }

    public void showStates() {
        System.out.println("#States:");
        for (int i = 0; i < this.statesList.size(); i++) {
            System.out.println(this.statesList.get(i));
        }
    }

    public void showFinalStates() {
        System.out.println("#Accepting:");
        for (int i = 0; i < this.acceptanceStates.size(); i++) {
            System.out.println(this.acceptanceStates.get(i));
        }
    }

    public void showInitialState() {
        System.out.println("#Initial: ");
        System.out.println(this.initialState);
    }
    
    public void showDelta() {
        System.out.println("#Transitions:");
        for (int i = 0; i < this.statesList.size(); i++) {
            for (int j = 0; j < this.alphabet.size(); j++) {
                if(!this.delta[i][j].isEmpty()){
                System.out.print(this.statesList.get(i)+":");
                System.out.print(this.alphabet.get(j)+">");
                for(int k=0;k<this.delta[i][j].size();k++){
                    System.out.print(this.delta[i][j].get(k));
                    if(k<this.delta[i][j].size()-1){
                    System.out.print(";");
                    }
                }
                }
                if(!this.delta[i][j].isEmpty()){
                    System.out.println("");
                }
            }
        }

    }
    
    public void initializeAFD(String filePath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        
        if (!file.exists()) {
            System.out.println("File not Found");
            System.exit(1);
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringTokenizer tokenizer;
        
        String line;
        
        while ((line = br.readLine()) != null) {
            switch (line) {
                case ("#alphabet"):
                    while (!(line = br.readLine()).startsWith("#")) {
                        
                        if (line.contains("-")) { //La linea contiene un rango de caracteres
                            char ch1 = line.charAt(0);//Se guarda el caracter 1
                            
                            //Ciclo for para ir agregando cada uno de los caracteres del rango por medio del codigo ASCII del primero (ch1)
                            while (ch1 != line.charAt(2)){
                                
                                this.alphabet.add(ch1);
                                ch1 = (char) ((int) ch1 + 1);//Pasar al siguiente ASCII
                                
                                if (ch1 == line.charAt(2)) { //Ultimo termino
                                    this.alphabet.add(ch1); //Se guarda el caracter en el alfabeto
                                }
                            }
                        }else {
                            char ch = line.charAt(0);
                            this.alphabet.add(ch);
                        }
                    }
                
                case ("#states"):
                    
                    while (!(line = br.readLine()).startsWith("#")) {
                        this.statesList.add(line);
                    }
                    //Despues de insertar el alfabeto y los estados podemos crear la matriz de transicion
                    this.initializeDelta(this.statesList.size(), this.alphabet.size());
                    
                case ("#initial"):
                    while (!(line = br.readLine()).startsWith("#")) {
                        this.initialState = line;
                    }
                    
                case ("#accepting"):
                    while ((!(line = br.readLine()).startsWith("#")) && this.statesList.contains(line)) {
                        this.acceptanceStates.add(line);
                    }
                    
                case ("#transitions"):
                     while ((line = br.readLine()) != null) {
                        tokenizer = new StringTokenizer(line, " :>");
                        
                        String currentState = tokenizer.nextToken();
                        Character currentChar = tokenizer.nextToken().charAt(0);
                        String transition;
                        
                        if (this.statesList.contains(currentState) && this.alphabet.contains(currentChar)) {
                            transition = tokenizer.nextToken();
                            this.delta[this.statesList.indexOf(currentState)][this.alphabet.indexOf(currentChar)].add(transition);
                            
                        }

                     }
                    
                default:
            }

        }
        
        br.close();
    }
    

    
    
    
    
}
