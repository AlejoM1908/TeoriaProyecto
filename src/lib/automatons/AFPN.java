//Java imports
package lib.automatons;

//--------------------
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
//--------------------
import lib.App.ArchiveWriter;
import lib.App.ArchiveReader;
import lib.models.AutomatonModel;
import lib.models.TransitionModel;

public class AFPN extends AF {

    private List<Character> stackAlphabet;
    private Stack<Character> stack;
    private ArchiveWriter archiveWritter = new ArchiveWriter();
        
    
    public AFPN(String path){
        new ArchiveReader();
        final AutomatonModel model = ArchiveReader.readAF(path); 
        this.alphabet = model.alphabet();
        this.statesList = model.statesList();
        this.initialState = model.initialState();
        this.acceptanceStates = model.acceptanceStates();
        this.stackAlphabet = model.firstStackAlphabet();
        this.transitionFunction = model.transitionFunction();
        
        this.alphabet.add('$');
        this.stackAlphabet.add('$');
    }

    public AFPN(List<Character> alphabet, List<String> statesList,
            String initialState, List<String> acceptanceStates, Map<String,Map<Character,ArrayList<TransitionModel>>> transitionFunction,
            List<Character> stackAlphabet){
        this.alphabet = alphabet;
        this.statesList = statesList;
        this.initialState = initialState;
        this.acceptanceStates = acceptanceStates;
        this.stackAlphabet = stackAlphabet;
        this.transitionFunction = transitionFunction;
    
    }
    
    private String modifyStack(String stack, char character, char action) {
        try {
            if (character == '$' && action != '$') {
                stack = stack.concat(String.valueOf(action));
                return stack;
            } else if (character != '$' && action == '$')  {
                if ( stack.length() > 1 && stack.charAt(stack.length()-1) == character) {
                    stack = stack.substring(0,stack.length()-1);
                    return stack;
                } else if ( stack.charAt(0)== character) {
                    stack ="";
                    return stack;
                }
            }else if (character != '$' && action != '$'){
                if ( stack.length() > 1 && stack.charAt(stack.length()-1) == character) {
                    stack = stack.substring(0,stack.length()-1);
                    stack = stack.concat(String.valueOf(action));
                    return stack;
                } else if ( stack.charAt(0)== character) {
                    stack ="";
                    return stack;
                }
                }
        return stack;

    }catch( Exception e){
        return "-1";
      }
    }
    
    private LinkedList<String> procedure(String string, TransitionModel operation, String stack){
        LinkedList<String> result = new LinkedList<>();
        if(operation == null){
            result.add(">>aborted");
            return result;
        }        
        
        String newStack = modifyStack(stack,operation.firstStackCharacter().charAt(0),operation.firstStackAction().charAt(0));
        if(stack.compareTo(newStack) == 0 && (operation.firstStackCharacter().charAt(0) != '$' && operation.firstStackAction().charAt(0) != '$')){
            result.add(">>aborted");
            return result; 
        }
        if(newStack.compareTo("-1")== 0){
            result.add(">>aborted");
            return result;
        } 
        if(string.length() == 0 && acceptanceStates.contains(operation.transitionState()) && newStack.isEmpty()){
            result.add("(" + operation.transitionState() +" ,$,$) >> accepted");
            return result;
        } else if (string.length() == 0){
            String newResult = "("+operation.transitionState()+",$,";
            if(newStack.isEmpty()){ newResult += "$)"; } else{ newResult += newStack +")"; }
            newResult += " >> notAccepted";
            result.add(newResult);
            return result;
        } 
        if(!this.alphabet.contains(string.charAt(0))){
            result.add(">>aborted");
            return result;
        }
        char actualCharacter = string.charAt(0);
        ArrayList<TransitionModel> options = this.transitionFunction.get(operation.transitionState()).get(actualCharacter);
        
        if(options == null){
            result.add(">>aborted");
            return result;
        }
        
        for(TransitionModel op : options){
            LinkedList<String> value = procedure(string.substring(1,string.length()),op, newStack);
            
            for(String val : value){
                String newResult = "("+ op.transitionState()+","+ string +",";
                if(newStack.compareTo("")==0){ newResult += "$)";} else { newResult += newStack+" )"; }
                newResult += "->";
                result.add(newResult + val);
            }
        }
        options = this.transitionFunction.get(operation.transitionState()).get('$');
        
        if(options == null){
            return result;
        }
        
        for(TransitionModel op : options){
            LinkedList<String> value = procedure(string.substring(1,string.length()),op, newStack);
            for(String val: value){
                String newResult = "("+ op.transitionState()+","+ string +",";
                if(newStack.compareTo("")==0){ newResult += "$)";} else { newResult += newStack+" )"; }
                newResult += "->";
                result.add(newResult + val);
            }
        }
        
        return result;
        
    }
    
    private LinkedList<String> showProcedure(String string){
        LinkedList<String> result = new LinkedList<>();
        if(string.compareTo("")==0 || string == null){
            if(this.acceptanceStates.contains(this.initialState)){
                result.add("("+ this.initialState + ",$,$)>>Accepted");
            } else { result.add(">> notAccepted");}
            return result;
        }
        
        char actualCharacter = string.charAt(0);
        if(!this.alphabet.contains(actualCharacter)){
            result.add(">>aborted");
            return result;
        }
        
        ArrayList<TransitionModel> options = this.transitionFunction.get(this.initialState).get(actualCharacter);
        if(options == null){
            result.add(">>aborted");
            return result;
        }
        for(TransitionModel op: options){
            LinkedList<String> value = procedure(string.substring(1,string.length()),op,"");
            for(String val: value){
                result.add("(" + this.initialState +  "," + string + ",$)->" + val);
            }
        }
        
        options = this.transitionFunction.get(this.initialState).get('$');
        
        if(options == null){
            result.add(">>aborted");
            return result;
        }
        
        for(TransitionModel op: options){
            LinkedList<String> value = procedure(string.substring(1,string.length()),op,"");
            for(String val: value){
                result.add("(" + this.initialState +  "," + string + ",$)->" + val);
            }
        }
                       
        return result;
    }
    
    public boolean isAccepted(String string){
        LinkedList<String> processing = showProcedure(string);
        
        for (String pro : processing){
            String[] check = pro.split(">>>|>>");
            for( int i =0; i<check.length;i++){
                if(check[i].contains("accepted") == true ){ return true;     }
            }
            
        }
        return false;
    }
    
    public LinkedList<String> detailedProcessig(String string, boolean consolePrint){
        LinkedList<String> procedures = showProcedure(string);
        LinkedList<String> result= new LinkedList<>();
        
        for(String pro: procedures){
            String[] checks = pro.split(">>>|>>");
            for(String check : checks){
                if(check.contains("accepted") == true){
                    result.add(pro);
                    if(consolePrint){ System.out.println(pro);   } return result;
                }
            }
        }
        if(consolePrint){
            for(String nonAccepted: procedures){
                System.out.println(nonAccepted);
            }
        }
        
        return procedures;
    }
    
    public int completedProcedure(String string, String path, String fileName, boolean consolePrint){
        LinkedList<String> procedures = showProcedure(string);
        LinkedList<String> accepted = new LinkedList<>();
        LinkedList<String> notAccepted = new LinkedList<>();
        
        //Processing string
        for(String pro : procedures){
            String[] checks = pro.split(">>>|>>");
            for(String check: checks ){
                if(check.contains("accepted") == true){
                    accepted.add(pro);
                }else if(check.contains("notAccepted") == true || check.compareTo("aborted") == 0){
                    notAccepted.add(pro);
                }
            }
        }
        if(consolePrint){
            System.out.println("****Accepted Prosecution****");
            if(accepted.isEmpty()){
                System.out.println("0");
            } else {
                for(String A: accepted){
                    System.out.println(A);
                }
            }
            System.out.println("****Unapproved Prosecution****");
            if(notAccepted.isEmpty()){
                System.out.println("0");
            } else {
                for(String N: notAccepted){
                    System.out.println(N);
                }
            }
        }
        //archiveWritter.writeProcessings(accepted, path + "\\" +fileName + "AcceptedAFNP.txt");
        //archiveWritter.writeProcessings(notAccepted, path + "\\" +fileName + "UnapprovedAFNP.txt");
        return procedures.size();
    }
    
    public void processList(LinkedList<String> strings, String path, String fileName, boolean consolePrint) {
        LinkedList<String> procedures;
        LinkedList<String> aFinal = new LinkedList<>();
        LinkedList<String> nFinal = new LinkedList<>();
        LinkedList<String> accepted;
        LinkedList<String> notAccepted;
        int stringNum = 0;

        for (String string : strings) {
            procedures = showProcedure(string);
            aFinal.add("Prosecution from string " + stringNum + "\n");
            nFinal.add("Prosecution from string " + stringNum + "\n");
            accepted = new LinkedList<>();
            notAccepted = new LinkedList<>();

            for (String pro : procedures) {
                String[] checks = pro.split(">>>|>>");
                for (String check : checks) {
                    if (check.contains("accepted") == true) {
                        accepted.add(pro);
                        aFinal.add(pro);
                    } else if (check.contains("notAccepted") == true || check.contains("aborted") == true) {
                        notAccepted.add(pro);
                        nFinal.add(pro);
                    }
                }
            }
            if (consolePrint) {
                System.out.println("****Accepted Prosecution****");
                if (accepted.isEmpty()) {
                    System.out.println("0");
                } else {
                    for (String A : accepted) {
                        System.out.println(A);
                    }
                }
                System.out.println("****Unapproved Prosecution****");
                if (notAccepted.isEmpty()) {
                    System.out.println("0");
                } else {
                    for (String N : notAccepted) {
                        System.out.println(N);
                    }
                }

                System.out.println("Prosecution:" + procedures.size());
                System.out.println("Accepted Prosecution :" + accepted.size());
                System.out.println("Unaproved Prosecution :" + notAccepted.size());

                if (accepted.size() > 0) {
                    System.out.println("Accepted String");
                } else {
                    System.out.println("Unaproved String");
                }
            }
            stringNum++;
        }
        
        //archiveWritter.writeProcessings(aFinal, path + "\\" +fileName + "AcceptedAFNP.txt");
        //archiveWritter.writeProcessings(nFinal, path + "\\" +fileName + "UnapprovedAFNP.txt");
    }
    
    public String toString(){
        return new AutomatonModel(alphabet,statesList, initialState, acceptanceStates, transitionFunction, stackAlphabet ,null).tostring();
    }
    
}
