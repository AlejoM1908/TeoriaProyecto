package lib;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.automatons.AFD;

public class ProyectMain {
    public static void main(String[] args){
        
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
