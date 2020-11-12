package lib.automatons;

import lib.App.ArchiveReader;
import lib.models.AutomatonModel;

class test{
    public static void main(String[] args) {
        AutomatonModel test = ArchiveReader.readAF("C:\\Users\\Armageddon132\\Documents\\NetBeansProjects\\TeoriaProyecto\\txtTest\\AFD.txt");

        System.out.println(test.toString());
    }
}