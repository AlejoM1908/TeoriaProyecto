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
        
        AFD prueba = new AFD("D:\\Personal\\Documentos\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFD.txt");
        //System.out.println(prueba.toString());
        
        prueba.processString("aababbabbb", true);
        /*
        try {
            prueba.processStringList(Arrays.asList("abab", "baba"), "Test.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        AFPD test = new AFPD("D:\\Personal\\Documentos\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFPD2.txt");
        test.processStringWithDetails("aaabbbbbb");
        AFPD testCartesian = test.cartesianProductAFD(prueba);
        testCartesian.processStringWithDetails("aabb");
        //System.out.println("Ayuda");
        /*
        try {
            test.processStringList(Arrays.asList("aaaabb", "abb", "aabb"), "TestAFPD.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }
}
