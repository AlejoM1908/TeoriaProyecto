package lib.automatons;

import lib.App.ArchiveReader;
import lib.models.AutomatonModel;

class test{
    public static void main(String[] args) {
        AutomatonModel test = ArchiveReader.readAF("C:\\Users\\danie\\Documents\\NetBeansProjects\\ProyectoIntro\\txtTest\\AFD.txt");

        System.out.println(test.toString());
    }
}