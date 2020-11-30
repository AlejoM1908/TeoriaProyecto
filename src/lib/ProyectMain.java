package lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.automatons.AFD;
import lib.automatons.AFPD;

public class ProyectMain {
    public static void main(String[] args){
        /*
        AFD prueba = new AFD("C:\\Users\\Armageddon132\\Documents\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFD.txt");
        System.out.println(prueba.toString());
        
        prueba.processString("aababbab", true);
        /*
        try {
            prueba.processStringList(Arrays.asList("abab", "baba"), "Test.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        AFPD test = new AFPD("C:\\Users\\Armageddon132\\Documents\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFPD4.txt");
        test.processStringWithDetails("ababaa");
        
        /*try {
            test.processStringList(Arrays.asList("aaaabb", "abb", "aabb"), "TestAFPD.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }
}
