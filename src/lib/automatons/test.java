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
        
        AF2P test_02 = new AF2P("C:\\Users\\danie\\Documents\\NetBeansProjects\\ProyectoIntro\\txtTest\\AF2P_02.msm");

        LinkedList<String> strings = new LinkedList<>();
        strings.add("abc");
        strings.add("abbc");
        strings.add("aaabbbbbbccc");
        strings.add("aaabbbcc");

        System.out.println(test_02.toString());

        test_02.processStringList(strings, "C:\\Users\\danie\\Desktop\\prueba AF2P", "testing", true);
        //test.detailedProcessing("abc", true);
    }
}