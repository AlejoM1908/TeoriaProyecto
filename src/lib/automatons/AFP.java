//Java imports
package lib.automatons;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author ImGAMP
 */
public class AFP extends AF {
    
    protected Queue<Character> queuealphabet = new LinkedList();

    //* @param queuealphabet alfabeto de la cola 
            
    public AFP(ArrayList<Character> alphabet, ArrayList<String> statesList, String initialState, ArrayList<String> acceptanceStates, Queue<Character> queuealphabet) {
        super(alphabet, statesList, initialState, acceptanceStates);
        this.queuealphabet = queuealphabet;
    }
    
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
    
}
