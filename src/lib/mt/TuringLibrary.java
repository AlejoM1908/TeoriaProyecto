package lib.mt;
 
import java.util.*;
import java.io.*;

public final class TuringLibrary
{
	private TuringLibrary () {}

	public static MT EqualBinaryWords(Set<String> statelist, String StartState, String AcceptState, Set<String[]> transitions)
	{
        MT newMT = new MT();

        for(String c:statelist){
            newMT.addState(c);
        }

		newMT.setStartState(StartState);
		newMT.setAcceptState(AcceptState);
		newMT.setRejectState(null);
        
        for(String[] t:transitions){
            newMT.addTransition(t[0], t[1].charAt(0), t[2], t[3].charAt(0), Boolean.parseBoolean(t[4]));
        }

		return newMT;		
    }
    
    public MT ReadMT(String DocumentName)
    {
        Set<String> statelist = new HashSet<String>(); String StartState = ""; String AcceptState = ""; Set<String[]> transitions = new HashSet<String[]>();
        try {
            File file = new File(DocumentName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            while (line != null) {
                switch (line) {                                                                

                    case ("#states"):

                        while (!(line = br.readLine()).startsWith("#")) {
                            if (line.contains(",")) {
                                String[] strings = line.split(",");

                                for (String s : strings) {
                                    statelist.add(s);
                                }
                            } else
                                statelist.add(line);
                        }

                        break;

                    case ("#initial"):
                        while (!(line = br.readLine()).startsWith("#")) {
                            StartState = line;
                        }

                        break;

                    case ("#accepting"):
                        while (!(line = br.readLine()).startsWith("#")) {
                            if (statelist.contains(line))
                                    AcceptState = line;
                            }
                        
                        break;

                    case ("#inputAlphabet"):
                        
                        break;

                    case ("#tapeAlphabet"):
                        
                        break;

                    case ("#transitions"):
                        while ((line = br.readLine()) != null) {
                            if (line.contains(",")) {
                                String[] strings = line.split(",");

                                for (String s : strings) {
                                    String[] dividedString = s.split(":?");                                 
                                    String readState = dividedString[0];
                                    String readSymbol = dividedString[1];
                                    String writeState = dividedString[2];
                                    String writeSymbol  = dividedString[3];
                                    String moveDirection  = dividedString[4];	//Hacia la derecha es verdadero, falso a la izquierda
                                    String[] t = new String[5];
                                    t[0] = readState;
                                    t[1] = readSymbol;
                                    t[2] = writeState;
                                    t[3] = writeSymbol;

                                    if(moveDirection.contains(">")){
                                        t[4] = "true";
                                    }
                                    else if(moveDirection.contains("<")){
                                        t[4] = "false";
                                    }

                                    transitions.add(t);
                                }
                                
                            } else {
                                String[] dividedString = line.split(":?");
                                String readState = dividedString[0];
                                String readSymbol = dividedString[1];
                                String writeState = dividedString[2];
                                String writeSymbol  = dividedString[3];
                                String moveDirection  = dividedString[4];	//Hacia la derecha es verdadero, falso a la izquierda
                                    String[] t = new String[5];
                                    t[0] = readState;
                                    t[1] = readSymbol;
                                    t[2] = writeState;
                                    t[3] = writeSymbol;

                                    if(moveDirection.contains(">")){
                                        t[4] = "true";
                                    }
                                    else if(moveDirection.contains("<")){
                                        t[4] = "false";
                                    }

                                    transitions.add(t);                                
                                }
                        }
                        break;
                }
            }

            br.close();
            return EqualBinaryWords(statelist, StartState, AcceptState, transitions);
            
        } 
            catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    

    }

}