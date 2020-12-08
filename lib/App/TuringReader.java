//Java import
package lib.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Proyect import
import lib.models.TuringTransitionModel;
import lib.models.TuringModel;

public class TuringReader {

    public TuringModel readTM(String documentName){
        ArrayList<Character> _alphabet = new ArrayList<>();
        ArrayList<String> _statesList = new ArrayList<>();
        String _initialState = "";
        ArrayList<String> _acceptanceList = new ArrayList<>();
        ArrayList<Character> _stringAlphabet = new ArrayList<>();
        Map<String,Map<Character,TuringTransitionModel>> _transitionFunction = new HashMap<>();

        try{
            File file = new File(documentName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            while (line != null){
                switch(line){

                }
            }

            br.close();
            return new TuringModel(_alphabet, _statesList, _initialState, 
                    _acceptanceList, _stringAlphabet, _transitionFunction);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } 
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
