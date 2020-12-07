package lib.automatons;

import java.util.LinkedList;

class test{
    public static void main(String[] args) {
        /*AF2P test = new AF2P("C:\\Users\\danie\\Documents\\NetBeansProjects\\ProyectoIntro\\txtTest\\AF2P.msm");

        LinkedList<String> strings = new LinkedList<>();
        strings.add("abc");
        strings.add("abbc");
        strings.add("aaabbbccc");
        strings.add("aaabbbcc");

        test.processStringList(strings, "C:\\Users\\danie\\Desktop\\prueba AF2P", "testing", false);*/
        
        AFPD test = new AFPD("C:\\Users\\Armageddon132\\Documents\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFPD4.dpda");
        test.processStringWithDetails("aabbaa");
        
        
        //System.out.println(test.toString());
    }
}