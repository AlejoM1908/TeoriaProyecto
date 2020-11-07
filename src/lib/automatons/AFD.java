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
    
    public void initializeAFD(String filePath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        
        if (!file.exists()) {
            System.out.println("File not Found");
            System.exit(1);
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        //StringTokenizer tokenizer;
        
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
                    
                case ("#accepting"):
                    
                case ("#transitions"):
                    
                default:
            }

        }
        
        br.close();
    }
    

    
    
    
    
}
