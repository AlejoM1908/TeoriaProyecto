package lib;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.automatons.AFD;

public class ProyectMain {
    public static void main(String[] args){
        
        AFD prueba = new AFD("C:\\Users\\Armageddon132\\Documents\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFD.txt");
        System.out.println(prueba.toString());
        
        prueba.processString("abab", true);
        try {
            prueba.processStringList(Arrays.asList("abab", "baba"), "Test.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
