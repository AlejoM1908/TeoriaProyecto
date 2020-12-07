//Java imports
package lib.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.models.AutomatonModel;
import lib.models.TransitionModel;

public class ArchiveReader {

    public static AutomatonModel readAF(String DocumentName) {
        List<Character> alphabet = new ArrayList<>();
        List<String> statesList = new ArrayList<>();
        String initialState = "";
        List<String> acceptanceStates = new ArrayList<>();
        Map<String, Map<Character, ArrayList<TransitionModel>>> transitionFunction = new HashMap<>();
        List<Character> firstStackAlphabet = new ArrayList<>();
        List<Character> secondStackAlphabet = new ArrayList<>();

        try {
            File file = new File(DocumentName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            while (line != null) {
                switch (line) {
                    case ("#alphabet"):
                        while (!(line = br.readLine()).startsWith("#")) {

                            if (line.contains(",")) { // La linea contiene una serie de caracteres
                                String[] characters = line.split(",");

                                for (String c : characters) {
                                    if (c.contains("-")) {
                                        String[] range = c.split("-"); // Obtener el rango de caracteres
                                        char first = range[0].charAt(0), second = range[1].charAt(0); // Los dos
                                                                                                      // caracteres en
                                                                                                      // el rango
                                        int characterAmount = Math.abs(first - second);

                                        for (int i = 0; i <= characterAmount; i++) {
                                            alphabet.add((char) (first + i));
                                        }
                                    } else
                                        alphabet.add(c.charAt(0));
                                }
                            } else if (line.contains("-")) { // La linea contiene un rango de caracteres
                                String[] range = line.split("-"); // Obtener el rango de caracteres
                                char first = range[0].charAt(0), second = range[1].charAt(0); // Los dos caracteres en
                                                                                              // el rango
                                int characterAmount = Math.abs(first - second);

                                for (int i = 0; i <= characterAmount; i++) {
                                    alphabet.add((char) (first + i));
                                }
                            } else { // La linea contiene un unico caracter
                                char ch = line.charAt(0);
                                alphabet.add(ch);
                            }
                        }
                        break;

                    case ("#states"):

                        while (!(line = br.readLine()).startsWith("#")) {
                            if (line.contains(",")) {
                                String[] strings = line.split(",");

                                for (String s : strings) {
                                    statesList.add(s);
                                }
                            } else
                                statesList.add(line);
                        }

                        break;

                    case ("#initial"):
                        while (!(line = br.readLine()).startsWith("#")) {
                            initialState = line;
                        }

                        break;

                    case ("#accepting"):
                        while (!(line = br.readLine()).startsWith("#")) {
                            if (line.contains(",")) {
                                String[] strings = line.split(",");

                                for (String s : strings) {
                                    if (statesList.contains(s))
                                        acceptanceStates.add(s);
                                }
                            } else {
                                if (statesList.contains(line))
                                    acceptanceStates.add(line);
                            }
                        }

                        break;

                    case ("#firstStackAlphabet"):
                        while (!(line = br.readLine()).startsWith("#")) {

                            if (line.contains(",")) { // La linea contiene una serie de caracteres
                                String[] characters = line.split(",");

                                for (String c : characters) {
                                    if (c.contains("-")) {
                                        String[] range = c.split("-"); // Obtener el rango de caracteres
                                        char first = range[0].charAt(0), second = range[1].charAt(0); // Los dos
                                                                                                      // caracteres en
                                                                                                      // el rango
                                        int characterAmount = Math.abs(first - second);

                                        for (int i = 0; i <= characterAmount; i++) {
                                            firstStackAlphabet.add((char) (first + i));
                                        }
                                    } else
                                        firstStackAlphabet.add(c.charAt(0));
                                }
                            } else if (line.contains("-")) { // La linea contiene un rango de caracteres
                                String[] range = line.split("-"); // Obtener el rango de caracteres
                                char first = range[0].charAt(0), second = range[1].charAt(0); // Los dos caracteres en
                                                                                              // el rango
                                int characterAmount = Math.abs(first - second);

                                for (int i = 0; i <= characterAmount; i++) {
                                    firstStackAlphabet.add((char) (first + i));
                                }
                            } else { // La linea contiene un unico caracter
                                char ch = line.charAt(0);
                                firstStackAlphabet.add(ch);
                            }
                        }
                        break;

                    case ("#secondStackAlphabet"):
                        while (!(line = br.readLine()).startsWith("#")) {

                            if (line.contains(",")) { // La linea contiene una serie de caracteres
                                String[] characters = line.split(",");

                                for (String c : characters) {
                                    if (c.contains("-")) {
                                        String[] range = c.split("-"); // Obtener el rango de caracteres
                                        char first = range[0].charAt(0), second = range[1].charAt(0); // Los dos
                                                                                                      // caracteres en
                                                                                                      // el rango
                                        int characterAmount = Math.abs(first - second);

                                        for (int i = 0; i <= characterAmount; i++) {
                                            secondStackAlphabet.add((char) (first + i));
                                        }
                                    } else
                                        secondStackAlphabet.add(c.charAt(0));
                                }
                            } else if (line.contains("-")) { // La linea contiene un rango de caracteres
                                String[] range = line.split("-"); // Obtener el rango de caracteres
                                char first = range[0].charAt(0), second = range[1].charAt(0); // Los dos caracteres en
                                                                                              // el rango
                                int characterAmount = Math.abs(first - second);

                                for (int i = 0; i <= characterAmount; i++) {
                                    secondStackAlphabet.add((char) (first + i));
                                }
                            } else { // La linea contiene un unico caracter
                                char ch = line.charAt(0);
                                secondStackAlphabet.add(ch);
                            }
                        }
                        break;

                    case ("#transitions"):
                        while ((line = br.readLine()) != null) {
                            if (line.contains(",")) {
                                String[] strings = line.split(",");

                                for (String s : strings) {
                                    String[] dividedString = s.split(":|>");
                                    String currentState = dividedString[0];
                                    Character transitionChar = dividedString[1].charAt(0);
                                    String transitionState;
                                    String firstStackCharacter;
                                    String secondStackCharacter;
                                    String firstStackAction;
                                    String secondStackAction;

                                    if (dividedString.length == 3) {
                                        transitionState = dividedString[2];

                                        if (transitionChar == '$' && !alphabet.contains('$'))
                                            alphabet.add('$');

                                        if (statesList.contains(currentState) && alphabet.contains(transitionChar)) {
                                            if (!transitionFunction.containsKey(currentState)) {
                                                transitionFunction.put(currentState,
                                                        new HashMap<Character, ArrayList<TransitionModel>>());
                                            }

                                            ArrayList<TransitionModel> transitionModel = new ArrayList<>();
                                            transitionModel.add(new TransitionModel(currentState, transitionChar, transitionState));

                                            transitionFunction.get(currentState).put(transitionChar,
                                                transitionModel);
                                        }
                                    } else if (dividedString.length == 5) {
                                        firstStackCharacter = dividedString[2];
                                        transitionState = dividedString[3];
                                        firstStackAction = dividedString[4];

                                        if (transitionChar == '$' && !alphabet.contains('$'))
                                            alphabet.add('$');

                                        if (statesList.contains(currentState) && alphabet.contains(transitionChar)) {
                                            if (!transitionFunction.containsKey(currentState)) {
                                                transitionFunction.put(currentState,
                                                        new HashMap<Character, ArrayList<TransitionModel>>());
                                            }
                                            
                                            if (!transitionFunction.get(currentState).containsKey(transitionChar)){
                                                ArrayList<TransitionModel> transitionModel = new ArrayList<>();
                                                transitionModel.add(new TransitionModel(currentState, transitionChar,
                                                firstStackCharacter, transitionState, firstStackAction));

                                                transitionFunction.get(currentState).put(transitionChar,
                                                    transitionModel);
                                            }
                                            else{
                                                transitionFunction.get(currentState).get(transitionChar).add(
                                                    new TransitionModel(currentState, transitionChar,
                                                            firstStackCharacter, transitionState, firstStackAction));
                                            }
                                        }
                                       
                                    
                                    } else if (dividedString.length == 7) {
                                        firstStackCharacter = dividedString[2];
                                        secondStackCharacter = dividedString[3];
                                        transitionState = dividedString[4];
                                        firstStackAction = dividedString[5];
                                        secondStackAction = dividedString[6];

                                        if (transitionChar == '$' && !alphabet.contains('$'))
                                            alphabet.add('$');

                                        if (statesList.contains(currentState) && alphabet.contains(transitionChar)) {
                                            if (!transitionFunction.containsKey(currentState)) {
                                                transitionFunction.put(currentState,
                                                        new HashMap<Character, ArrayList<TransitionModel>>());
                                            }

                                            if (!transitionFunction.get(currentState).containsKey(transitionChar)){
                                                ArrayList<TransitionModel> transitionModel = new ArrayList<>();
                                                transitionModel.add(new TransitionModel(currentState, transitionChar,
                                                firstStackCharacter, secondStackCharacter, transitionState,
                                                firstStackAction, secondStackAction));

                                                transitionFunction.get(currentState).put(transitionChar,
                                                    transitionModel);
                                            }
                                            else{
                                                transitionFunction.get(currentState).get(transitionChar).add(
                                                    new TransitionModel(currentState, transitionChar,
                                                            firstStackCharacter, secondStackCharacter, transitionState,
                                                            firstStackAction, secondStackAction));
                                            }
                                        }
                                    }
                                }
                            } else {
                                String[] dividedString = line.split(":|>");
                                String currentState = dividedString[0];
                                Character transitionChar = dividedString[1].charAt(0);
                                String transitionState;
                                String firstStackCharacter;
                                String secondStackCharacter;
                                String firstStackAction;
                                String secondStackAction;

                                if (dividedString.length == 3) {
                                    transitionState = dividedString[2];

                                    if (transitionChar == '$' && !alphabet.contains('$'))
                                            alphabet.add('$');

                                    if (statesList.contains(currentState) && alphabet.contains(transitionChar)) {
                                        if (!transitionFunction.containsKey(currentState)) {
                                            transitionFunction.put(currentState,
                                                    new HashMap<Character, ArrayList<TransitionModel>>());
                                        }

                                        ArrayList<TransitionModel> transitionModel = new ArrayList<>();
                                        transitionModel.add(new TransitionModel(currentState, transitionChar, transitionState));

                                        transitionFunction.get(currentState).put(transitionChar,
                                                transitionModel);
                                    }
                                } else if (dividedString.length == 5) {
                                    firstStackCharacter = dividedString[2];
                                    transitionState = dividedString[3];
                                    firstStackAction = dividedString[4];

                                    if (transitionChar == '$' && !alphabet.contains('$'))
                                            alphabet.add('$');

                                    if (statesList.contains(currentState) && alphabet.contains(transitionChar)) {
                                        if (!transitionFunction.containsKey(currentState)) {
                                            transitionFunction.put(currentState,
                                                    new HashMap<Character, ArrayList<TransitionModel>>());
                                        }
                                        if (!transitionFunction.get(currentState).containsKey(transitionChar)){
                                            ArrayList<TransitionModel> transitionModel = new ArrayList<>();
                                            transitionModel.add(new TransitionModel(currentState, transitionChar,
                                            firstStackCharacter, transitionState, firstStackAction));

                                            transitionFunction.get(currentState).put(transitionChar,
                                                transitionModel);
                                        }
                                        else {
                                            transitionFunction.get(currentState).get(transitionChar).add(
                                                new TransitionModel(currentState, transitionChar,
                                                        firstStackCharacter, transitionState, firstStackAction));
                                        }
                                    }
                                } else if (dividedString.length == 7) {
                                    firstStackCharacter = dividedString[2];
                                    secondStackCharacter = dividedString[3];
                                    transitionState = dividedString[4];
                                    firstStackAction = dividedString[5];
                                    secondStackAction = dividedString[6];

                                    if (transitionChar == '$' && !alphabet.contains('$'))
                                            alphabet.add('$');

                                    if (statesList.contains(currentState) && alphabet.contains(transitionChar)) {
                                        if (!transitionFunction.containsKey(currentState)) {
                                            transitionFunction.put(currentState,
                                                    new HashMap<Character, ArrayList<TransitionModel>>());
                                        }
                                        if (!transitionFunction.get(currentState).containsKey(transitionChar)){
                                            ArrayList<TransitionModel> transitionModel = new ArrayList<>();
                                            transitionModel.add(new TransitionModel(currentState, transitionChar,
                                            firstStackCharacter, secondStackCharacter, transitionState,
                                            firstStackAction, secondStackAction));
                                            transitionFunction.get(currentState).put(transitionChar,
                                                transitionModel);
                                        }
                                        else{
                                            transitionFunction.get(currentState).get(transitionChar).add(
                                                new TransitionModel(currentState, transitionChar,
                                                        firstStackCharacter, secondStackCharacter, transitionState,
                                                        firstStackAction, secondStackAction));
                                        }
                                    }
                                }
                            }
                        }

                        break;
                }
            }

            br.close();
            return new AutomatonModel(alphabet, statesList, initialState, acceptanceStates, transitionFunction,
                    firstStackAlphabet, secondStackAlphabet);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
