/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib.mt;

import java.util.*;
/**
 *
 * @author david
 */
public class MT {
    private Set<String> StateSpace;
    private Set<Transition> TransitionSpace;
    private String StartState;
    private String AcceptState;
    private String RejectState;

    private String Tape;
    private String CurrentState;
    private int CurrentSymbol;

    class Transition
    {
        String readState;
        char readSymbol;
        String writeState;
        char writeSymbol;
        boolean moveDirection;	//Hacia la derecha es verdadero, falso a la izquierda

        boolean isConflicting(String state, char symbol)
        {
            if (state.equals(readState) && symbol == readSymbol)
            {
                return true;
            }
            else
            {
                return false;			
            }
        }		
    }


    public MT()
    {
        StateSpace = new HashSet<String>();
        TransitionSpace = new HashSet<Transition>();
        StartState = new String("");
        AcceptState = new String("");
        RejectState = new String("");
        Tape = new String("");
        CurrentState = new String("");
        CurrentSymbol = 0;

    }

    public boolean addState(String newState)
    {
        if (StateSpace.contains(newState))
        {
        return false;
        }
        else
        {
            StateSpace.add(newState);
        return true;
        }
    }

    public boolean setStartState(String newStartState)
    {
        if (StateSpace.contains(newStartState))
        {
            StartState = newStartState;
            return true;
        }
        else
        {
            return false;
        }		
    }

    public boolean setAcceptState(String newAcceptState)
    {
        if (StateSpace.contains(newAcceptState) && !RejectState.equals(newAcceptState))
        {
            AcceptState = newAcceptState;
            return true;
        }
        else
        {
            return false;
        }

    }

    public boolean setRejectState(String newRejectState)
    {
        if (StateSpace.contains(newRejectState) && !AcceptState.equals(newRejectState))
        {
            RejectState = newRejectState;
            return true;
        }
        else
        {
            return false;
        }		
    }

    public boolean addTransition(String rState, char rSymbol, String wState, char wSymbol, boolean mDirection)
    {
        if(!StateSpace.contains(rState) || !StateSpace.contains(wState))
        {
            return false;
        }

        boolean conflict = false;
        Iterator<Transition> TransitionsIterator = TransitionSpace.iterator();
        while ( TransitionsIterator.hasNext() && conflict == false)
        {
            Transition nextTransition = TransitionsIterator.next();
            if (nextTransition.isConflicting(rState, rSymbol))
            {
                conflict = true;
            }

        }
            if (conflict == true)
            {
                return false;
            }
            else
            {
                Transition newTransition = new Transition();
                newTransition.readState = rState;
                newTransition.readSymbol = rSymbol;
                newTransition.writeState = wState;
                newTransition.writeSymbol = wSymbol;
                newTransition.moveDirection = mDirection;
                TransitionSpace.add(newTransition);
                return true;
            }
    }
}
    

