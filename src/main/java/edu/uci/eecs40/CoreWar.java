package main.java.edu.uci.eecs40;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import java.lang.String;

public class CoreWar {

    public static int numofTurns;
    private static int board = 8000;
    private static int items = 5;
    private static int spaceAbove, spaceBelow;
    private static int initial1, initial2;
    public static int k;
    private static int opCode;
    private static String RedCode;
    private static char aSym;
    private static int aInt;
    private static int aSource;
    private static char bSym;
    private static int bInt;
    private static int bSource;
    private static int length1, length2;
    private static String Warriorname;
    private static int datamemory[][] = new int[board][items];

    private static boolean space;


    public static void main(String[] args) {
        String Outcome;
        if (args.length == 2) {
            //initialize array testing
        /*for(int i = 0; i < board; i++){
            for(int j =0; j < 5; j++){
                System.out.println(datamemory[i][j]);
            }
        }*/

            //input files from path
            File fileInput1 = new File(args[0]);
            File fileInput2 = new File(args[1]);

            //Print inputs
            length1 = printFileContents(fileInput1);
            length2 = printFileContents(fileInput2);

            //get first random starts
            initial1 = (int) ((Math.random() * (board)) % (board));
            randomStarts();

            String warrior1 = initializeWarrior(fileInput1, initial1);
            String warrior2 = initializeWarrior(fileInput2, initial2);

        /*//Test
        printint(initial1,length1);
        System.out.println("\n");
        printint(initial2,length2);

        //Test2
        PrintInstruction(initial1);*/

            //Starting Game
            Outcome = Game.GameStart(warrior1, warrior2);
        } else if(args.length == 1){
            File fileInput3 = new File(args[0]);
            length1 = printFileContents(fileInput3);
            initial1 = (int) ((Math.random() * (board)) % (board));
            String warrior1 = initializeWarrior(fileInput3, initial1);
            //Starting Game
            Outcome = Game.GameForOne(warrior1);
        } else{
            Outcome = "More than 2 player or No Player are not supported";
        }
        System.out.println(Outcome);
    }

    private static void printint(int index, int length){ //print input Instructions in int
        for (int ihere =index; ihere < (index+length) ; ihere++){
            int i = ihere % (board);
            System.out.println(datamemory[i][0] + " " + datamemory[i][1]+ " " + datamemory[i][2]+ " " + datamemory[i][3]+ " " + datamemory[i][4]);
        }
        return;
    }

    /*private static void printRedCode(int index){
        for (int i =0; i < 11 ; i++){
            System.out.println(datamemory[i][0] + " " + datamemory[i][1]+ " " + datamemory[i][2]+ " " + datamemory[i][3]+ " " + datamemory[i][4]);
        }
    }*/ //printing current status

    private static void randomStarts(){ //get random start for warrior2
        do{
            initial2 = (int) ((Math.random() * (board))% (board));
            space = (((initial1 >= initial2) && (((initial1-initial2))>=(length2))) ||
                    ((initial1 < initial2) && ((initial2-initial1)>=(length1))));
        }while( space == false);
        return;
    }

    public static void loadmem(int index, int op, int amode, int a, int bmode, int b){
        //I am not sure if I need to save for unused field or not
        //Simulator saved. Therefore I am saving as well
        /*if(op == 0){
            datamemory[index][0] = op;
            datamemory[index][3] = bmode;
            datamemory[index][4] = b;
        }else if(op == 4 || op == 8){
            datamemory[index][0] = op;
            datamemory[index][1] = amode;
            datamemory[index][2] = a;
        }else*/ {
            datamemory[index][0] = op;
            datamemory[index][1] = amode;
            datamemory[index][2] = a;
            datamemory[index][3] = bmode;
            datamemory[index][4] = b;
        }
        return;
    }

    private static void readinstructiononeA(int index, String splited[]){ //read one line of instruction and save the value For JMP SPL
        Instruction instr = new Instruction();
        opCode = instr.getopCode(RedCode);
        datamemory[index][0] = opCode;
        datamemory[index][1] = instr.Symtoint(splited[1].charAt(0));
        if (instr.Symtoint(splited[1].charAt(0)) == 0) {
            datamemory[index][2] = Integer.valueOf(splited[1]);
        }else{
            datamemory[index][2] = Integer.valueOf(splited[1].substring(1));
        }
        datamemory[index][3] = 0;
        datamemory[index][4] = 0;
        return;
    }

    private static void readinstructiontwo(int index, String splited[]){ //read one line of instruction and save the value FOR MOV ADD SUB JMZ DJN CMP
        Instruction instr = new Instruction();
        opCode = instr.getopCode(RedCode);
        datamemory[index][0] = opCode;
        datamemory[index][1] = instr.Symtoint(splited[1].charAt(0));
        if (instr.Symtoint(splited[1].charAt(0)) == 0) {
            datamemory[index][2] = Integer.valueOf(splited[1].substring(0,splited[1].length()-1));
        }else{
            datamemory[index][2] = Integer.valueOf(splited[1].substring(1,splited[1].length()-1));
        }
        datamemory[index][3] = instr.Symtoint(splited[2].charAt(0));
        if (instr.Symtoint(splited[2].charAt(0)) == 0) {
            datamemory[index][4] = Integer.valueOf(splited[2]);
        }else{
            datamemory[index][4] = Integer.valueOf(splited[2].substring(1));
        }
        if (getAMode(index) == 2 || getBMode(index) == 3){
            System.out.println("Incorrect Mode");
            System.exit(0);
        }
        return;
    }

    private static int printFileContents(File fileIn){ //Print input File
        int length =0;
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(fileIn.getAbsolutePath()));

            String stringLine = "";
            while(reader.ready()){
                stringLine = reader.readLine();
                System.out.println(stringLine);
                length++;
            }
            reader.close();
        } catch(IOException err){
            System.out.println("Couldn't open file.\n Reason being " + err.getMessage());
            err.printStackTrace();
        }
        //System.out.println("\n");
        return length-1;
    }

    private static String initializeWarrior(File fileIn, int index){ //save instructions in a random location
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(fileIn.getAbsolutePath()));

            String stringLine = "";
            int firstline = 0;
            while(reader.ready()){
                stringLine = reader.readLine();
                String[] splited = stringLine.split("\\s+");
                if (firstline == 0){
                    firstline++;
                    Warriorname = stringLine;
                }else{
                //System.out.println(splited[0]);
                RedCode = splited[0];
                Instruction instr = new Instruction();
                opCode = instr.getopCode(RedCode);
                switch (opCode) {
                    case 0:
                        datamemory[index][0] = opCode;
                        datamemory[index][1] = 0;
                        datamemory[index][2] = 0;
                        datamemory[index][3] = instr.Symtoint(splited[1].charAt(0));
                        if (instr.Symtoint(splited[1].charAt(0)) == 1 || instr.Symtoint(splited[1].charAt(0)) == 2 || instr.Symtoint(splited[1].charAt(0)) == 3) {
                            datamemory[index][4] = Integer.valueOf(splited[1].substring(1));
                        }else{
                            datamemory[index][4] = Integer.valueOf(splited[1]);
                        }
                        break;
                    case 1:
                        readinstructiontwo(index, splited);
                        break;
                    case 2:
                        readinstructiontwo(index, splited);
                        break;
                    case 3:
                        readinstructiontwo(index, splited);
                        break;
                    case 4:
                        readinstructiononeA(index, splited);
                        break;
                    case 5:
                        readinstructiontwo(index, splited);
                        break;
                    case 6:
                        readinstructiontwo(index, splited);
                        break;
                    case 7:
                        readinstructiontwo(index, splited);
                        break;
                    case 8:
                        readinstructiononeA(index, splited);
                        break;
                    default:
                        System.out.println("Operation Not Found");
                        System.exit(0);
                }
                if(getAMode(index) == 2 || getBMode(index) ==3){
                    System.out.println("Initializing Wrong Mode for A or B Field");
                    System.exit(0);
                }
                index = (index+1) % (board);
            }}
            reader.close();
        } catch(IOException err){
            System.out.println("Couldn't open file.\n Reason being " + err.getMessage());
            err.printStackTrace();
        }
        return Warriorname;
    }

    /*private static File openFile(){
        JFileChooser fileWindow = new JFileChooser();
        fileWindow.setFileSelectionMode(JFileChooser.FILES_ONLY);

        fileWindow.showOpenDialog(null);

        return fileWindow.getSelectedFile();
    }*/ //unused open file

    //Print 5 Previous and 5 Next Operation
    public static void PrintInstruction (int instruction){
        for (int i = instruction-5; i < instruction+6; i++){
            int ihere =((i % board) + board) % board;
            //System.out.println(ihere);
            String instr = getInstuction((ihere));
            System.out.println(instr);
        }
    }

    private static String getInstuction(int dataneeded){ // convert the int instruction back to regular instruction
        Instruction instr = new Instruction();
        String RedCode = instr.opCodetoRedCode(getOperation(dataneeded));
        if (RedCode == "DAT") {
            if (getBMode(dataneeded) != 0) {
                return (RedCode + " " + instr.inttoSym(getBMode(dataneeded)) + getBValue(dataneeded));
            } else{
                return (RedCode + " " + getBValue(dataneeded));
            }
        }else if (RedCode == "JMP" || RedCode == "SPL") {
            if (getAMode(dataneeded) != 0){
                return (RedCode + " " + instr.inttoSym(getAMode(dataneeded))+getAValue(dataneeded));
            }else {
                return (RedCode + " " + getAValue(dataneeded));
            }
        }else{
            if(getAMode(dataneeded) != 0 && getBMode(dataneeded) != 0) {
                return (RedCode + " " + instr.inttoSym(getAMode(dataneeded)) + getAValue(dataneeded) + ", " + instr.inttoSym(getBMode(dataneeded)) + getBValue(dataneeded));
            }else if(getAMode(dataneeded) == 0 && getBMode(dataneeded) == 0){
                return (RedCode + " " + getAValue(dataneeded) + ", " + getBValue(dataneeded));
            }else if(getAMode(dataneeded) == 0){
                return (RedCode + " " + getAValue(dataneeded) + ", " + instr.inttoSym(getBMode(dataneeded)) + getBValue(dataneeded));
            }else if (getBMode(dataneeded) == 0){
                return (RedCode + " " + instr.inttoSym(getAMode(dataneeded)) + getAValue(dataneeded) + ", " + getBValue(dataneeded));
            }else{
                System.out.println("Operational Error");
                System.exit(0);
                return "Operational Error";
            }
        }
    }

    //getData for other class
    public static int getOperation(int index){
        return datamemory[index][0];
    }

    public static int getAMode(int index){
        return datamemory[index][1];
    }

    public static int getAValue(int index){
        return datamemory[index][2];
    }

    public static int getBMode(int index){
        return datamemory[index][3];
    }

    public static int getBValue(int index){
        return datamemory[index][4];
    }

    public static int getInitial1() {
        return initial1;
    }

    public static int getInitial2() {
        return initial2;
    }

    public static int getBoard() {
        return board;
    }

    public static void setBValue(int index, int Bvalue){
        datamemory[index][4] = Bvalue;
    }
}

//DAT = 0, MOV = 1, ADD = 2, SUB = 3, JMP = 4, JMZ = 5, DJN = 6, CMP = 7, SPL = 8;
//No Mode = 0, # = 1, @ = 2, * = 3;
//datamemory[index][0] = Operation
//datamemory[index][1] = Mode of A
//datamemory[index][2] = Value of A
//datamemory[index][3] = Mode of B
//datamemory[index][4] = Value of B