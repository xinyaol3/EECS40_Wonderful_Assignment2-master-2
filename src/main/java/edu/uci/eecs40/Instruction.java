package main.java.edu.uci.eecs40;

public class Instruction {

    public int getopCode(String RedCode){ //Converting RedCode to int
        int opCode;
        switch(RedCode){
            case "DAT":
                opCode = 0;
                break;
            case "MOV":
                opCode = 1;
                break;
            case "ADD":
                opCode = 2;
                break;
            case "SUB":
                opCode = 3;
                break;
            case "JMP":
                opCode = 4;
                break;
            case "JMZ":
                opCode = 5;
                break;
            case "DJN":
                opCode = 6;
                break;
            case "CMP":
                opCode = 7;
                break;
            case "SPL":
                opCode = 8;
                break;
            default:
                System.out.println("Unknown RedCode"); //cannot identify operation
                opCode = 9;
                break;
        }
        return opCode;
    }

    public String opCodetoRedCode(int opCode){ // Converting int to RedCode
        String RedCode;
        switch(opCode){
            case 0:
                RedCode = "DAT";
                break;
            case 1:
                RedCode = "MOV";
                break;
            case 2:
                RedCode = "ADD";
                break;
            case 3:
                RedCode = "SUB";
                break;
            case 4:
                RedCode = "JMP";
                break;
            case 5:
                RedCode = "JMZ";
                break;
            case 6:
                RedCode = "DJN";
                break;
            case 7:
                RedCode = "CMP";
                break;
            case 8:
                RedCode = "SPL";
                break;
            default:
                System.out.println("Unknown Operation"); //cannot identify operation
                RedCode = "NOP";
                break;
        }
        return RedCode;
    }

    public int Symtoint (char Sym){ //convert mode to int
        int converage;
        if(Sym == '#'){
            converage = 1;
        }else if(Sym == '@') {
            converage = 2;
        }else if(Sym == '*'){
            converage = 3;
        }else{
            converage = 0;
        }
        return converage;
    }

    public char inttoSym (int Symint){ //convert mode int to symbol
        char Sym;
        if(Symint == 1){
            Sym = '#';
        }else if(Symint == 2){
            Sym = '@';
        }else if(Symint == 3){
            Sym = '*';
        }else{
            System.out.println("Unknown Mode"); //cannot identify Mode
            Sym = '-';
        }
        return Sym;
    }


}

//DAT = 0, MOV = 1, ADD = 2, SUB = 3, JMP = 4, JMZ = 5, DJN = 6, CMP = 7, SPL = 8;
//No Mode = 0, # = 1, @ = 2, * = 3;