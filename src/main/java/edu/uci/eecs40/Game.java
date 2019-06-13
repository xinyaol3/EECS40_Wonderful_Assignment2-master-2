package main.java.edu.uci.eecs40;
import java.lang.String;
import java.util.ArrayList;


public class Game {
    private static int MaxCycle, NextIndex, SplIndex;
    private static int NewBoard = CoreWar.getBoard();
    private static boolean Turn;
    private static String Result;
    public static String Winner1, Winner2;

    //two player game starter
    public static String GameStart(String Player1, String Player2){
        Winner1 = Player1;
        Winner2 = Player2;
        MaxCycle = 8 * NewBoard;
        Turn = true;
        Boolean GameEnded = false;
        Warriors warrior1 = new Warriors(Player1, CoreWar.getInitial1());
        Warriors warrior2 = new Warriors(Player2, CoreWar.getInitial2());
        warrior1.setHeadname(Winner1);
        warrior2.setHeadname(Winner2);

        while (GameEnded == false){//while game is not finish loop this portion

            Warriors NextUp = WhoTurn(warrior1, warrior2); //get warrior that is up next

            RunOperations (NextUp); //run the warrior's instruction

            if(!warrior1.getAlive() || !warrior2.getAlive()){ //while only one alive game ends
                Boolean Alive = warrior1.getAlive();
                GameEnded = true;
                if (Alive){
                    Result = warrior1.getName() + " WINS";
                }else{
                    Result = warrior2.getName() + " WINS";
                }
            }
            MaxCycle--;
            if (MaxCycle == 0 && GameEnded == false){ //over 8*boards turn call DRAW
                GameEnded = true;
                 Result = "The Game End in DRAW!";
            }
        }
        return Result;
    }

    //one player game
    public static String GameForOne(String Player1){
        Warriors NextUp;
        MaxCycle =8*NewBoard;
        //MaxCycle = 100;
        Turn = true;
        Boolean GameEnded = false;
        Warriors warrior1 = new Warriors(Player1, CoreWar.getInitial1());
        warrior1.setHeadname(Player1);

        while (GameEnded == false){//read one line of instruction and save the value For JMP SPL

            if(warrior1.getSplit()){
                NextUp = WhoTurnSplit(warrior1, warrior1.getOriginalWarrior(), warrior1.getNewWarrior()); //if split before see who is up next
            }else{
                NextUp = warrior1;
            }
            RunOperations (NextUp); //run

            if(!warrior1.getAlive()){ // if dead end game
                GameEnded = true;
                Result = "The Game End!";
            }

            MaxCycle--;
            if (MaxCycle == 0 && GameEnded != true){ // over max cycles
                GameEnded = true;
                Result = "The Game End!";
            }
        }
        return Result;
    }

    //see which player are next and if split before
    private static Warriors WhoTurn(Warriors Player1, Warriors Player2){
        if (Turn == true){
            Turn = false;
            if (Player1.getSplit() == true){
                return WhoTurnSplit(Player1, Player1.getOriginalWarrior(), Player1.getNewWarrior());
            }else{
                return Player1;
            }

        }else{
            Turn = true;
            if (Player2.getSplit() == true){
                return WhoTurnSplit(Player2, Player2.getOriginalWarrior(), Player2.getNewWarrior());
            }else{
                return Player2;
            }
        }
    }

    // if split for look for who is next
    private static Warriors WhoTurnSplit(Warriors Head, Warriors OriginalWarrior, Warriors NewWarrior){
        Boolean SplitTurn = Head.getTurn();
        //Warrior must be alive to be here
        if (!Head.getAlive()){
            System.out.println(Head.getName() + " not Alive error");
            System.exit(0);
        }

        //If one of the split was dead call the next one
        if (OriginalWarrior.getAlive() == false){
            //System.out.println("q");
            if(NewWarrior.getSplit()){
                return WhoTurnSplit(NewWarrior, NewWarrior.getOriginalWarrior(), NewWarrior.getNewWarrior());
            }else{
                return NewWarrior;
            }
        }

        if (NewWarrior.getAlive() == false){
            //System.out.println("w");
            if(OriginalWarrior.getSplit()){
                return WhoTurnSplit(OriginalWarrior, OriginalWarrior.getOriginalWarrior(), OriginalWarrior.getNewWarrior());
            }else{
                return OriginalWarrior;
            }
        }

        //Decide which of the split should be next and if that split have splits
        if (SplitTurn){
            //System.out.println("e");
            Head.setTurn(false);
            if(OriginalWarrior.getSplit()){
                return WhoTurnSplit(OriginalWarrior, OriginalWarrior.getOriginalWarrior(), OriginalWarrior.getNewWarrior());
            }else{
                return OriginalWarrior;
            }
        } else {
            //System.out.println("r");
            Head.setTurn(true);
            if(NewWarrior.getSplit()){
                return WhoTurnSplit(NewWarrior, NewWarrior.getOriginalWarrior(), NewWarrior.getNewWarrior());
            }else{
                return NewWarrior;
            }
        }

    }

    //Run the instruction and save the Next index check if warrior are still alive
    private static void RunOperations(Warriors Whatever){
        int index = Whatever.getIndex();
        System.out.println(Whatever.getHeadname());
        //System.out.println(Whatever.getName());

        //if compare is true skip one instruction and it takes a turn
        /*if (Whatever.getCompare()){
            System.out.println(Whatever.getIndex());
            CoreWar.PrintInstruction(index);
            NextIndex = (index+1+CoreWar.getBoard())%CoreWar.getBoard();
            Whatever.setIndex(NextIndex);
            Whatever.setCompare(false);
            System.out.println(Whatever.getIndex());
        } else*/ {
            Operation.Operation(index);
            //If Still Alive
            if (Operation.isLive()) {
                if (Operation.isSplit()) {
                    NextIndex = Operation.getNextIndex();
                    SplIndex = Operation.getSPLIndex();
                    Warriors OriginalWarrior = new Warriors(Whatever.getName() + "1", NextIndex);
                    Warriors NewWarrior = new Warriors(Whatever.getName() + "2", SplIndex);
                    OriginalWarrior.setHeadname(Whatever.getHeadname());
                    NewWarrior.setHeadname(Whatever.getHeadname());
                    OriginalWarrior.setHead(Whatever);
                    NewWarrior.setHead(Whatever);
                    Whatever.setSplit(true);
                    Whatever.setTurn(false);
                    Whatever.setOriginalWarrior(OriginalWarrior);
                    Whatever.setNewWarrior(NewWarrior);
                    System.out.println("Split of "+Whatever.getHeadname());
                    CoreWar.PrintInstruction(SplIndex);
                } /*else if (Operation.isCompare()) {
                    Whatever.setCompare(true);
                    NextIndex = Operation.getNextIndex();
                    Whatever.setIndex(NextIndex);
                }*/ else {
                    NextIndex = Operation.getNextIndex();
                    Whatever.setIndex(NextIndex);
                }
            } else {
                //System.out.println("Here Split dead");
                Whatever.setAlive(false);
                if(Whatever.getHead() != null) {
                    Warriors Head = Whatever.getHead();
                    if (Head.getSplit()) {
                        Warriors UpperLayer = Whatever.getHead();
                        UpperStatus(UpperLayer);
                    }
                }
            }
        }

    }

    //If split check if warriors are still alive
    private static void UpperStatus(Warriors UpperLayer){
        UpperLayer.setAlive(UpperLayer.getOriginalWarrior().getAlive() || UpperLayer.getNewWarrior().getAlive());
        //System.out.println(UpperLayer.getAlive());
        if (!UpperLayer.getAlive() && UpperLayer.getHead() != null){
            UpperStatus(UpperLayer.getHead());
        }
    }
}
