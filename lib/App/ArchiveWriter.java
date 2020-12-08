//Java imports
package lib.App;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;

public class ArchiveWriter {

    public boolean writeProcessings(LinkedList<String> processings, String path) {
        try{
            File file = new File(path);
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String value: processings){
                bufferedWriter.write(value);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
