package lib.automatons;

import java.util.LinkedList;

class test{
    public static void main(String[] args) {
        AF2P test = new AF2P("C:\\Users\\danie\\Documents\\NetBeansProjects\\ProyectoIntro\\txtTest\\AF2P.msm");

        LinkedList<String> processings = test.showProcessing("c");

        for(String processing: processings){
            System.out.println(processing);
        }

        System.out.println("\n" + test.toString());
    }
}