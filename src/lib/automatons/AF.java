//Java imports
package lib.automatons;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//Proyect imports
import lib.App.ArchiveReader;

public class AF {
	protected List<Character> alphabet = new ArrayList<>();
	protected List<String> statesList;
	protected String initialState;
	protected List<String> acceptanceStates;		
	
	/**
	 * Constructor(alfabeto, lista de estados, estado inicial, estados de aceptación) de la clase para inicializar los atributos.
	 * @param alphabet Describe cada simbolo del alfabeto
	 * @param statesList Conjunto de estados del autómata
	 * @param initialState Estado inicial
	 * @param acceptanceStates Conjunto estados de aceptacion
	 */
	public AF(ArrayList<Character> alphabet, ArrayList<String> statesList, String initialState, ArrayList<String> acceptanceStates) {
            this.alphabet = alphabet;
            this.statesList = statesList;
            this.initialState = initialState;
            this.acceptanceStates = acceptanceStates;	
	}
        
        
	/**
	 * Constructor(nombreArchivo) de la clase para inicializar los atributos a partir de un archivo cuyo formato
	 * es el mismo acordado por todo el curso y aprobado por el docente (ver archivo \archivoEjemplo.txt).
	 * @param documentName archivo para inicializar los atributos.
	 */
	public AF(String documentName) {
		AF af = ArchiveReader.readAF(documentName);
		this.alphabet = af.alphabet;
		this.statesList = af.statesList;
		this.initialState = af.initialState;
		this.acceptanceStates = af.acceptanceStates;	
	}
        //Constructor para llamar en AFD
        public AF(){
            this.alphabet = new ArrayList<>();
	    this.statesList = new ArrayList<>();
	    this.acceptanceStates = new ArrayList<>();
        }


        
	public void setAlphabet(ArrayList<String> alphabet) {
		for (String subSigma : alphabet) { // Por cada elemento
		    if(subSigma.length()>1) { // Si es rango
		    	String[] subSigmaArr = subSigma.split("-"); // obener los dos parametros
		    	if (!subSigmaArr[0].matches("[A-Za-z0-9]")) { // verificar que el rango sea valido
		    		throw new IllegalArgumentException("Mal formato de rango sigma");		    		
		    	}		    	
	    		int charCodeInferior = (int)subSigmaArr[0].charAt(0); // obtener el entero ASCII del caracter inferior
	    		int charCodeSuperior = (int)subSigmaArr[1].charAt(0); // obtener el entero ASCII caracter superior
	    		for(int i = charCodeInferior; i <= charCodeSuperior; i++){ //Bucle desde ASCII inferior hasta ASCII superior
				    this.alphabet.add((char)(i)); //Agregar caracter a la lista sigma
				}		    	
		    }
		    else { //Si es símbolo
		    	this.alphabet.add(subSigma.charAt(0)); // Castear la cadena a caracter y Agregarlo a la lista sigma
		    }
		}
	}
	
	public void setStatesList(ArrayList<String> statesList) {
		this.statesList = statesList; 
	}
	
	public void setInitialState(String initialState) {
		this.initialState = initialState;
	}
	
	public void setAcceptaceStates(ArrayList<String> acceptanceStates) {
		this.acceptanceStates = acceptanceStates; 
	}
	
	/**
	 * Alamcena una lista string con el formato "[Estado actual]:[Simbolo]>[Estado destino 1, Estado destino 2,...]" en el Hashmap Delta
	 * @see #delta
	 * @see HashMap
	 * @see HashMap#computeIfAbsent(Object, java.util.function.Function)
	 * @param deltaList
	 */
        /*
	public void setDelta(ArrayList<String> deltaList) {
		for (String subDelta: deltaList) { // Por cada elemento
			String[] subDeltaArr = subDelta.split(":"); // Dividir (Estado actual) / (Simbolo>Estado destino 1, Estado destino 2,...)
			String key = subDeltaArr[0]; // Llave del mapa padre (Estado actual)
			String valueMapStr = subDeltaArr[1]; // Valor del mapa padre (Simbolo>Estado destino 1, Estado destino 2,...)
			String[] valueMapStrArr = valueMapStr.split(">"); // Dividir (Simbolo) / (Estado destino 1, Estado destino 2,...)
			Character símbolo = valueMapStrArr[0].charAt(0); // Castear caracter Simbolo
			String[] estadosArr = valueMapStrArr[1].replaceAll("\\s+","").split(","); // Castear cadena de estados a arreglo de cadenas	
			ArrayList<String> estados = new ArrayList<>(Arrays.asList(estadosArr));	// Castear arreglo de cadenas a lsita de estados						
			this.delta.computeIfAbsent(key, k -> new HashMap<Character,ArrayList<String>>()).put(símbolo,estados); // Asginar a mapa: <(Estado actual),<(Simbolo),({Estado destino 1, Estado destino 2,...})>>
		}		
	}		
	*/
	
	
	
	/*
	@Override
	public String toString() {
		String string = "[sigma={";
		String sigmaStr = "";
       for (Character ch : alphabet) sigmaStr += ch+",";  
       sigmaStr = sigmaStr.substring(0, sigmaStr.length() - 1);
		string += sigmaStr + "},";
		string += "Q={"+String.join(",", statesList)+"},";
		string += "q0="+initialState+",";
		string += "F={"+String.join(",", acceptanceStates)+"},";
		string += "delta={";
		String deltaStr = "";
	    for (String estado : delta.keySet()) {
	    	deltaStr += estado + "={";
	        for(Character simbolo : delta.get(estado).keySet()) {
	        	deltaStr += simbolo + "=>{";
				for(String s : delta.get(estado).get(simbolo)) deltaStr += s+",";
				deltaStr = deltaStr.substring(0, deltaStr.length() - 1);
				deltaStr += "},";
	        }
	        deltaStr = deltaStr.substring(0, deltaStr.length() - 1);
	        deltaStr += "},";
	    }
	    deltaStr = deltaStr.substring(0, deltaStr.length() - 1);
		string += deltaStr+"].";		
		return string;
	}
        */

}
