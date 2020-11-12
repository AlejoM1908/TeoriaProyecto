//Java imports
package lib.automatons;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Proyect imports
import lib.App.ArchiveReader;
import lib.models.AutomatonModel;
import lib.models.TransitionModel;

public abstract class AF {
	protected List<Character> alphabet = new ArrayList<>();
	protected List<String> statesList;
	protected String initialState;
	protected List<String> acceptanceStates;
	protected Map<String,Map<Character,TransitionModel>> transitionFunction;

	/**
	 * Constructor(alfabeto, lista de estados, estado inicial, estados de
	 * aceptación) de la clase para inicializar los atributos.
	 * 
	 * @param alphabet         Describe cada simbolo del alfabeto
	 * @param statesList       Conjunto de estados del autómata
	 * @param initialState     Estado inicial
	 * @param acceptanceStates Conjunto estados de aceptacion
	 */
	public AF(ArrayList<Character> alphabet, ArrayList<String> statesList, String initialState,
			ArrayList<String> acceptanceStates) {
		this.alphabet = alphabet;
		this.statesList = statesList;
		this.initialState = initialState;
		this.acceptanceStates = acceptanceStates;
	}

	/**
	 * Constructor(documentName) de la clase para inicializar los atributos a partir
	 * de un archivo cuyo formato es el mismo acordado por todo el curso y aprobado
	 * por el docente (ver archivo \archivoEjemplo.txt).
	 * 
	 * @param documentName archivo para inicializar los atributos.
	 */
	public AF(String documentName) {
		AutomatonModel af = ArchiveReader.readAF(documentName);
		this.alphabet = af.alphabet();
		this.statesList = af.statesList();
		this.initialState = af.initialState();
		this.acceptanceStates = af.acceptanceStates();
	}

	/**
	 * Constructor de la clase para inicialzar los atributos sin ningun valor
	 */
	public AF() {
		this.alphabet = new ArrayList<>();
		this.statesList = new ArrayList<>();
		this.initialState = "";
		this.acceptanceStates = new ArrayList<>();
	}

	public void setAlphabet(List<Character> alphabet) {
		this.alphabet = alphabet;
	}

	public void setStatesList(List<String> statesList) {
		this.statesList = statesList;
	}

	public void setInitialState(String initialState) {
		this.initialState = initialState;
	}

	public void setAcceptaceStates(List<String> acceptanceStates) {
		this.acceptanceStates = acceptanceStates;
	}

	public void setTransitionFunction(Map<String,Map<Character,TransitionModel>> transitionFunction){
		this.transitionFunction = transitionFunction;
	}
	
	//Abstarct Methods
	public abstract boolean processString(String string);
	public abstract void initializeAutomaton();
}
