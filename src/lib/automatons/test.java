package lib.automatons;
import java.util.LinkedList;

class test{
    public static void main(String[] args) {
        
        AFD prueba = new AFD("D:\\Personal\\Documentos\\NetBeansProjects\\ImprovedTry\\TeoriaProyecto\\txtTest\\AFD.dfa");
        //System.out.println(prueba.toString());
        
        //prueba.processString("aababbabbb", true);
        /*
        try {
            prueba.processStringList(Arrays.asList("abab", "baba"), "Test.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(ProyectMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        
        AFPD test = new AFPD("D:\\Personal\\Documentos\\NetBeansProjects\\ImprovedTry\\TeoriaProyecto\\txtTest\\AFPD4.dpda");
        test.processStringWithDetails("aaabbbbbb");
        System.out.println(test.toString());
        AFPD testCartesian = test.cartesianProductAFD(prueba);
        System.out.println(testCartesian.toString());
        testCartesian.processStringWithDetails("aabb");
        
        /*AFPD test2 = new AFPD("D:\\Personal\\Documentos\\NetBeansProjects\\ImprovedTry\\TeoriaProyecto\\txtTest\\cartesian.dpda");
        System.out.println(test2.toString());
        test2.processStringWithDetails("aabb");*/
        //System.out.println("Ayuda");      
    }
}
