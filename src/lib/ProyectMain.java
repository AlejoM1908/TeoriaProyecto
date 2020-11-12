package lib;

import lib.automatons.AFD;

public class ProyectMain {
    public static void main(String[] args){
        
        AFD prueba = new AFD("C:\\Users\\Armageddon132\\Documents\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFD.txt");
        System.out.println(prueba.toString());
        
        prueba.processString("abab", false);
    }
}
