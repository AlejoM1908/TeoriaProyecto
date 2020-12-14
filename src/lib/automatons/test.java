package lib.automatons;
import java.util.LinkedList;

class test{
    public static void main(String[] args) {
        
        //AFD prueba = new AFD("D:\\Personal\\Documentos\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFD.txt");
        //System.out.println(prueba.toString());
        
        //prueba.processString("aababbabbb", true);
        /*
        try {
            prueba.processStringList(Arrays.asList("abab", "baba"), "Test.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        /*
        AFPD test = new AFPD("D:\\Personal\\Documentos\\NetBeansProjects\\ClonDelProyecto\\TeoriaProyecto\\txtTest\\AFPD4.dpda");
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
        
        AFPD afpd = new AFPD("C:\\Users\\Armageddon132\\Documents\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFPD.dpda");

        System.out.println(afpd.processStringR("aabba", true));
        //test.detailedProcessing("abc", true);
    }
}