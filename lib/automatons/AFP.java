//Java imports
package lib.automatons;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import lib.App.ArchiveReader;
import lib.models.AutomatonModel;


/**
 *
 * @author ImGAMP
 */
public class AFP extends AF {
    
    protected List<Character> stackAlphabet; //= new LinkedList();
    protected Stack<Character> stack; //= new Stack<>();
    
    
    public AFP(String documentName) {
        AutomatonModel model = ArchiveReader.readAF(documentName);
        this.statesList = model.statesList();
        this.initialState = model.initialState();
        this.alphabet = model.alphabet();
        this.acceptanceStates = model.acceptanceStates();
        this.stackAlphabet = model.firstStackAlphabet();
        this.stack = new Stack<>();
    }
    
    //* @param queuealphabet alfabeto de la cola 
       
    public AFP(ArrayList<Character> alphabet, ArrayList<String> statesList, String initialState, ArrayList<String> acceptanceStates, List<Character> stackAlphabet) {
        super(alphabet, statesList, initialState, acceptanceStates);
        this.stackAlphabet = stackAlphabet;
        this.stack = new Stack<>();
    }

    
    /*
    public void setQueueAlphabet(Queue<String> queuealphabet) {
		for (String subSigma : queuealphabet) { // Por cada elemento
		    if(subSigma.length()>1) { // Si es rango
		    	String[] subSigmaArr = subSigma.split("-"); // obener los dos parametros
		    	if (!subSigmaArr[0].matches("[A-Za-z0-9]")) { // verificar que el rango sea valido
		    		throw new IllegalArgumentException("Mal formato de rango sigma");		    		
		    	}		    	
	    		int charCodeInferior = (int)subSigmaArr[0].charAt(0); // obtener el entero ASCII del caracter inferior
	    		int charCodeSuperior = (int)subSigmaArr[1].charAt(0); // obtener el entero ASCII caracter superior
	    		for(int i = charCodeInferior; i <= charCodeSuperior; i++){ //Bucle desde ASCII inferior hasta ASCII superior
				    this.queuealphabet.add((char)(i)); //Agregar caracter a la lista sigma
				}		    	
		    }
		    else { //Si es símbolo
		    	this.queuealphabet.add(subSigma.charAt(0)); // Castear la cadena a caracter y Agregarlo a la lista sigma
		    }
		}
	}
    */
}
