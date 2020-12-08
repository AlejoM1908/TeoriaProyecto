//Java imports
package lib.automatons;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

//Proyect imports
import lib.App.ArchiveReader;
import lib.App.ArchiveWriter;
import lib.models.AutomatonModel;
import lib.models.TransitionModel;

public class AF2P extends AF{
    protected List<Character> firstStackAlphabet;
    protected List<Character> secondStackAlphabet;
    protected Stack<Character> firstStack, secondStack;
    private ArchiveWriter archiveWriter = new ArchiveWriter();

    /**
     * Contructor de la clase que recibe el path al documento que contiene
     * la información para inicializar el AF2P
     * @param documentPath
     */
    public AF2P(String documentPath){
        new ArchiveReader();
        final AutomatonModel model = ArchiveReader.readAF(documentPath);

        this.alphabet = model.alphabet();
        this.statesList = model.statesList();
        this.initialState = model.initialState();
        this.acceptanceStates = model.acceptanceStates();
        this.firstStackAlphabet = model.firstStackAlphabet();
        this.secondStackAlphabet = model.secondStackAlphabet();
        //this.transitionFunction = model.transitionFunction();

        this.alphabet.add('$');
        this.firstStackAlphabet.add('$');
        this.secondStackAlphabet.add('$');
    }

    /**
     * Contructor de la clase que recibe uno a uno todos los parametros que
     * requiere el AF2P para funcionar
     * @param alphabet 
     * @param statesList
     * @param initialState
     * @param acceptanceStates
     * @param transitionFunction
     * @param firstStackAlphabet
     * @param secondStackAlphabet
     */
    public AF2P(List<Character> alphabet, List<String> statesList,
            String initialState, List<String> acceptanceStates, Map<String,Map<Character,ArrayList<TransitionModel>>> transitionFunction,
            List<Character> firstStackAlphabet, List<Character> secondStackAlphabet){
        this.alphabet = alphabet;
        this.statesList = statesList;
        this.initialState = initialState;
        this.acceptanceStates = acceptanceStates;
        this.firstStackAlphabet = firstStackAlphabet;
        this.secondStackAlphabet = secondStackAlphabet;
        this.transitionFunction = transitionFunction;
    }

    /**
     * Función que permite modificar el contenido de la pila dada
     * @param stack
     * @param stackCharacter
     * @param stackAction
     */
    private String modifyStack(String stack, char stackCharacter, char stackAction ){
        try{
            if (stackCharacter == '$' && stackAction != '$'){
                stack = stack.concat(String.valueOf(stackAction));
                return stack;
            }
            else if (stackCharacter != '$' && stackAction == '$'){
                if (stack.length()>1 && stack.charAt(stack.length()-1) == stackCharacter){
                    stack = stack.substring(0 ,stack.length()-1);
                    return stack;
                }
                else if (stack.charAt(0) == stackCharacter){
                    stack = "";
                    return stack;
                }
            }
            else if (stackCharacter != '$' && stackAction != '$'){
                if (stack.length()>1 && stack.charAt(stack.length()-1) == stackCharacter){
                    stack = stack.substring(0 ,stack.length()-1);
                    stack = stack.concat(String.valueOf(stackAction));
                    return stack;
                }
                else if (stack.charAt(0) == stackCharacter){
                    stack = "";
                    return stack;
                }
            }
                
            return stack;
        }
        catch (Exception e){
            return "###";
        }
    }

    /**
     * Función que permite procesar recursivamente una cadena y retorna los procesamientos completos posibles
     * @param string
     * @param operation
     * @param stackOne
     * @param stackTwo
     * @return
     */
    private LinkedList<String> recursiveProcessing(String string, TransitionModel operation, String stackOne, String stackTwo){
        LinkedList<String> result = new LinkedList<>();

        if (operation == null){
            result.add(">>aborted");
            System.out.println("fallo 1");
            return result;
        }
        
        String newStackOne = modifyStack(stackOne, operation.firstStackCharacter().charAt(0), operation.firstStackAction().charAt(0));
        String newStackTwo = modifyStack(stackTwo, operation.secondStackCharacter().charAt(0), operation.secondStackAction().charAt(0));

        if (stackOne.compareTo(newStackOne) == 0 && (operation.firstStackCharacter().charAt(0) != '$' && operation.firstStackAction().charAt(0) != '$')){
            result.add(">>aborted");
            System.out.println("fallo 2");
            return result;
        }

        if (stackTwo.compareTo(newStackTwo) == 0 && (operation.secondStackCharacter().charAt(0) != '$' && operation.secondStackAction().charAt(0) != '$')){
            result.add(">>aborted");
            System.out.println("fallo 3");
            return result;
        }

        if (newStackOne.compareTo("###") == 0 || newStackTwo.compareTo("###") == 0){
            result.add(">>aborted");
            System.out.println("fallo 4");
            return result;
        }
        
        if(string.isEmpty() && acceptanceStates.contains(operation.transitionState()) && newStackOne.isEmpty() && newStackTwo.isEmpty()){
            result.add("(" + operation.transitionState() + ",$,$,$)>>accepted");
            return result;
        }
        else if (string.isEmpty()){
            String resultString = "(" + operation.transitionState() + ",$,";

            if (newStackOne.isEmpty())
                resultString += "$,";
            else
                resultString += newStackOne + ",";

            if (newStackTwo.isEmpty())
                resultString += "$)>>notAccepted";
            else
                resultString += newStackTwo + ")>>notAccepted";

            result.add(resultString);
            return result;
        }

        if (!this.alphabet.contains(string.charAt(0))){
            result.add(">>aborted");
            System.out.println("fallo 5");
            return result;
        }

        char actualCharacter = string.charAt(0);
        ArrayList<TransitionModel> options = this.transitionFunction.get(operation.transitionState()).get(actualCharacter);

        if (options != null){
            for (TransitionModel option: options){
                LinkedList<String> value = recursiveProcessing(string.substring(1,string.length()), option, newStackOne, newStackTwo);

                for (String valueString: value){
                    String resultString = "(" + option.transitionState() + "," + string + ",";

                    if (newStackOne.compareTo("") == 0)
                        resultString +=  "$,";
                    else
                        resultString += newStackOne + ",";

                    if (newStackTwo.compareTo("") == 0)
                        resultString += "$)->";
                    else
                        resultString += newStackTwo + ")->";

                    result.add(resultString + valueString);
                }
            }
        }

        options = this.transitionFunction.get(operation.transitionState()).get('$');

        if (options == null){
            result.add(">>aborted");
            return result;
        }

        for (TransitionModel option: options){
            LinkedList<String> value = recursiveProcessing(string, option, newStackOne, newStackTwo);

            for (String valueString: value){
                String resultString = "(" + option.transitionState() + "," + string + ",";

                if (newStackOne.compareTo("") == 0)
                    resultString +=  "$,";
                else
                    resultString += newStackOne + ",";

                if (newStackTwo.compareTo("") == 0)
                    resultString += "$)->";
                else
                    resultString += newStackTwo + ")->";

                result.add(resultString + valueString);
            }
        }

        return result;
    }

    /**
     * Función que retorna una lista con todos los procesamientos posibles de una cadena
     * @param string
     * @return
     */
    private LinkedList<String> showProcessing(String string){
        LinkedList<String> result = new LinkedList<>();

        if (string == null || string.isEmpty()){
            if (this.acceptanceStates.contains(this.initialState))
                result.add("(" + this.initialState + ",$,$,$)>>accepted");
            else
                result.add(">>aborted");
            return result;
        }

        char actualCharacter = string.charAt(0);

        if (!this.alphabet.contains(actualCharacter)){
            result.add(">>aborted");
            return result;
        }

        ArrayList<TransitionModel> options = this.transitionFunction.get(this.initialState).get(actualCharacter);

        if (options == null){
            result.add(">>aborted");
            return result;
        }

        for (TransitionModel option: options){
            LinkedList<String> value = recursiveProcessing(string.substring(1,string.length()), option, "", "");

            for (String valueString: value){
                result.add("(" + this.initialState + "," + string + ",$,$)->" + valueString);
            }
        }

        options = this.transitionFunction.get(this.initialState).get('$');

        if (options == null){
            return result;
        }

        for (TransitionModel option: options){
            LinkedList<String> value = recursiveProcessing(string.substring(1,string.length()), option, "", "");

            for (String valueString: value){
                result.add("(" + this.initialState + "," + string + ",$,$)->" + valueString);
            }
        }

        return result;
    }

    /**
     * Función que retorna si una cadena es aceptada o no
     * @param string
     * @return
     */
    public boolean isAccepted(String string){
        LinkedList<String> processings = showProcessing(string);

        for (String processing: processings){
            String[] processingCheck = processing.split(">>>|>>");

            for (int i = 0; i<processingCheck.length; i++){
                if (processingCheck[i].compareTo("accepted") == 0)
                    return true;
            }
        }

        return false;
    }

    /**
     * Función que retorna el procesamiento detallado de una cadena y los imprime a la consola
     * @param string
     * @param consolePrint
     * @return
     */
    public LinkedList<String> detailedProcessing(String string, boolean consolePrint){
        LinkedList<String> processings = showProcessing(string);
        LinkedList<String> result = new LinkedList<>();

        for (String processing: processings){
            String[] processingCheck = processing.split(">>>|>>");

            for (String check: processingCheck) {
                if (check.compareTo("accepted") == 0)
                    result.add(processing);

                    if (consolePrint)
                        System.out.println(processing);

                    return result;
            }
        }

        if (consolePrint){
            for (String nonAccepted: processings){
                System.out.println(nonAccepted);
            }
        }

        return processings;
    }

    /**
     * Función que regresa el procesamiento detallado a una carpeta escogida y al la consola
     * @param string
     * @param path
     * @param fileName
     * @param consolePrint
     * @return
     */
    public int completeDetailedProcessing(String string, String path, String fileName, boolean consolePrint){
        LinkedList<String> processings = showProcessing(string);
        LinkedList<String> accepted = new LinkedList<>();
        LinkedList<String> notAccepted = new LinkedList<>();

        for (String processing: processings){
            String[] processingCheck = processing.split(">>>|>>");

            for (String check: processingCheck){
                if (check.compareTo("accepted") == 0)
                    accepted.add(processing);
                else if (check.compareTo("notAccepted") == 0 || check.compareTo("aborted") == 0)
                    notAccepted.add(processing);
            }
        }

        if (consolePrint){

            System.out.println("== Procesamientos aceptados ==");

            if (accepted.size() == 0)
                System.out.println("");
            else{
                for (String acceptedString: accepted){
                    System.out.println(acceptedString);
                }
            }

            System.out.println("== Procesamientos no aceptados ==");

            if (notAccepted.size() == 0)
                System.out.println("");
            else{
                for (String notAcceptedString: notAccepted){
                    System.out.println(notAcceptedString);
                }
            }
        }

        archiveWriter.writeProcessings(accepted, path + "\\" + fileName + "AceptadasAF2P.txt");
        archiveWriter.writeProcessings(notAccepted, path + "\\" + fileName + "RechazadasAF2P.txt");

        return processings.size();
    }

    /**
     * Función que procesa una lista de cadenas
     * @param strings
     * @param path
     * @param fileName
     * @param consolePrint
     */
    public void processStringList(LinkedList<String> strings, String path, String fileName, boolean consolePrint){
        LinkedList<String> processings;
        LinkedList<String> acceptedFinal = new LinkedList<>();
        LinkedList<String> notAcceptedFinal = new LinkedList<>();
        LinkedList<String> accepted;
        LinkedList<String> notAccepted;
        int stringNumber = 0;

        for (String string: strings){
            processings = showProcessing(string);
            acceptedFinal.add("Procesamientos String " + stringNumber +"\n");
            notAcceptedFinal.add("Procesamientos String " + stringNumber +"\n");
            accepted = new LinkedList<>();
            notAccepted = new LinkedList<>();

            for (String processing: processings){
                String[] processingCheck = processing.split(">>>|>>");
                
                for (String check: processingCheck){
                    if (check.compareTo("accepted") == 0){
                        accepted.add(processing);
                        acceptedFinal.add(processing);
                    }
                    else if (check.compareTo("notAccepted") == 0 || check.compareTo("aborted") == 0){
                        notAccepted.add(processing);
                        notAcceptedFinal.add(processing);
                    }
                }
            }

            if (consolePrint){
                System.out.println("== Procesamientos aceptados String " + stringNumber +"==");

                System.out.println(string);
                if (accepted.size() == 0)
                    System.out.println("");
                else{
                    for (String acceptedString: accepted){
                        System.out.println(acceptedString);
                    }
                }

                System.out.println("== Procesamientos no aceptados String " + stringNumber +"==");

                if (notAccepted.size() == 0)
                    System.out.println("");
                else{
                    for (String notAcceptedString: notAccepted){
                        System.out.println(notAcceptedString);
                    }
                }

                System.out.println("Número de procesamientos: " + processings.size());
                System.out.println("Número de procesamientos Aceptados: " + accepted.size());
                System.out.println("Número de procesamientos No Aceptados: " + notAccepted.size());

                if (accepted.size() > 0)
                    System.out.println("La cadena fue acepatada");
                else
                    System.out.println("La cadena no fue aceptada");
            }

            stringNumber++;
        }
        archiveWriter.writeProcessings(acceptedFinal, path + "\\" + fileName + "AceptadasAF2P.txt");
        archiveWriter.writeProcessings(notAcceptedFinal, path + "\\" + fileName + "RechazadasAF2P.txt");
    }

    public String toString(){
        return new AutomatonModel(alphabet, statesList, initialState, acceptanceStates, transitionFunction, firstStackAlphabet, secondStackAlphabet).toString();
    }
}
