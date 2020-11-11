package lib;

import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.automatons.AFD;

public class ProyectMain {
    
    public static void main(String[] args){
        
        
        /*Scanner sc = new Scanner(System.in);
        
        
        String line = sc.nextLine();
        
        StringTokenizer tokenizer = new StringTokenizer(line, " :>,");
        while( tokenizer.hasMoreElements()){
            System.out.println(tokenizer.nextToken());
        }*/
        
        
        AFD prueba = new AFD();
        try {
            prueba.initializeAFD("C:\\Users\\Armageddon132\\Documents\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFD.txt");
        } catch (IOException ex) {
            Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        prueba.showAlphabet();
        prueba.showDelta();
        prueba.showFinalStates();
        prueba.showInitialState();
        prueba.showStates();
        */
        prueba.processString("abab", true);
    }
    
}
