package main.java.edu.uci.eecs40;

public class Operation {
    private static boolean live, Split, Compare;
    private static int NextIndex, SPLIndex;
    private static int board = CoreWar.getBoard();


    public static void Operation(int index) {
        int printIndex = index;
        int op = CoreWar.getOperation(index);
        int modeA = CoreWar.getAMode(index);
        int valueA = CoreWar.getAValue(index);
        int modeB = CoreWar.getBMode(index);
        int valueB = CoreWar.getBValue(index);
        int newindex, newvalueB, newvalueA;

        Split = false;
        Compare = false;
        live = true;

        switch (op) {
            case (0)://DAT       B
                CoreWar.loadmem(index, op, modeA, valueA, modeB, valueB);
                live = false;
                break;
            case (1)://MOV   A   B
                switch (modeB){
                    case(0):
                        switch(modeA) {
                            case (0):
                                CoreWar.loadmem((index+valueB+board)% board, CoreWar.getOperation((index+valueA+board)% board),
                                        CoreWar.getAMode((index+valueA+board)% board), CoreWar.getAValue((index+valueA+board)% board),
                                        CoreWar.getBMode((index+valueA+board)% board), CoreWar.getBValue((index+valueA+board)% board));
                                break;
                            case (1):
                                CoreWar.loadmem((index+valueB+board)% board, CoreWar.getOperation((index+valueB+board)% board),
                                        CoreWar.getAMode((index+valueB+board)% board), CoreWar.getAValue((index+valueB+board)% board),
                                        CoreWar.getBMode((index+valueB+board)% board), valueA);
                                break;
                            case (2):
                                System.out.println("Error! Mode A = @!");
                                System.exit(0);
                                break;
                            case (3):
                                int aindirect=(CoreWar.getAValue((index+valueA+board)% board));
                                int b=(aindirect+index+valueA+board)% board;
                                CoreWar.loadmem((index+valueB+board)% board, CoreWar.getOperation(b),
                                        CoreWar.getAMode(b), CoreWar.getAValue(b),
                                        CoreWar.getBMode(b), CoreWar.getBValue(b));
                                break;
                        }
                        break;
                    case(1):
                        System.out.println("Error! Move Mode Cannot be immediate!");
                        System.exit(0);
                        break;
                    case(2): {
                        int locationb = (index + valueB + CoreWar.getBValue((index + valueB+board)%board)+board)%board;
                        switch(modeA){
                            case (0):
                                CoreWar.loadmem(locationb % board, CoreWar.getOperation((index + valueA+board)% board),
                                        CoreWar.getAMode((index + valueA+board)% board), CoreWar.getAValue((index + valueA+board)% board),
                                        CoreWar.getBMode((index + valueA+board)% board), CoreWar.getBValue((index + valueA+board)% board));
                                break;
                            case (1):
                                CoreWar.loadmem(locationb% board, CoreWar.getOperation((index + valueB+board)% board),
                                        CoreWar.getAMode((index + valueB+board)% board), CoreWar.getAValue((index + valueB+board)% board),
                                        CoreWar.getBMode((index + valueB+board)% board), valueA);
                                break;
                            case (2):
                                System.out.println("Error!Mode A = @!");
                                System.exit(0);
                                break;
                            case (3):
                                int aindirect2 = CoreWar.getAValue((index + valueA+board)% board);
                                int b2 = (aindirect2 + index + valueA+board)% board;
                                CoreWar.loadmem(locationb% board, CoreWar.getOperation(b2),
                                        CoreWar.getAMode(b2), CoreWar.getAValue(b2),
                                        CoreWar.getBMode(b2), CoreWar.getBValue(b2));

                                break;
                        }
                        break;
                    }
                    case(3):
                        System.out.println("Error! Mode B = *!");
                        System.exit(0);
                        break;
                }
                NextIndex = (index+1+board) % board;
                break;
            case (2)://ADD   A   B
                switch(modeB){
                    case(0)://relative
                        newindex=(index+valueB+board)% board;

                        switch(modeA){

                            case(0)://relative
                                newvalueB=(CoreWar.getBValue((index+valueA+board)%board)+CoreWar.getBValue((newindex+board)%board));
                                newvalueA=(CoreWar.getAValue((index+valueA+board)%board)
                                        +CoreWar.getAValue(((newindex)+board)%board));
                                CoreWar.loadmem(newindex, CoreWar.getOperation(newindex),
                                        CoreWar.getAMode(newindex), newvalueA,
                                        CoreWar.getBMode(newindex), newvalueB);
                                break;

                            case(1)://immediate
                                newvalueB=(valueA+CoreWar.getBValue(newindex));
                                CoreWar.loadmem(newindex, CoreWar.getOperation(newindex),
                                        CoreWar.getAMode(newindex), CoreWar.getAValue(newindex),
                                        CoreWar.getBMode(newindex), newvalueB);
                                break;
                            case(2)://@
                                System.out.println("Error! Mode A = @!");
                                System.exit(0);
                                break;
                            case(3)://* amode-indirect
                                int c=(index+valueA+CoreWar.getAValue((index+valueA+board)%board)+board)%board;

                                newvalueB=CoreWar.getBValue(newindex)+CoreWar.getBValue((c+board)%board);
                                newvalueA=(CoreWar.getAValue(newindex)+CoreWar.getAValue((c+board)%board));

                                CoreWar.loadmem(newindex, CoreWar.getOperation(newindex),
                                        CoreWar.getAMode(newindex), newvalueA,
                                        CoreWar.getBMode(newindex), newvalueB);
                                break;

                        }


                        break;
                    case(1):
                        System.out.println("Error! ADD B Mode Cannot be immediate!");
                        System.exit(0);
                        break;
                    case(2)://@     B-filed Indirect  2
                        int newindex2=(((index+valueB+CoreWar.getBValue((index+valueB+board)% board))+board)% board);

                        switch(modeA){

                            case(0)://none
                                newvalueB=(CoreWar.getBValue(((newindex2)+board)%board)+
                                        CoreWar.getBValue((index+valueA+board)%board));
                                newvalueA=(CoreWar.getAValue((newindex2+board)%board)+CoreWar.getAValue((index+valueA+board)%board));
                                CoreWar.loadmem(newindex2, CoreWar.getOperation(newindex2),
                                        CoreWar.getAMode(newindex2), newvalueA,
                                        CoreWar.getBMode(newindex2), newvalueB);
                                break;

                            case(1)://immediate

                                newvalueB=(CoreWar.getBValue(newindex2)+valueA);
                                CoreWar.loadmem(newindex2, CoreWar.getOperation(newindex2),
                                        CoreWar.getAMode(newindex2), CoreWar.getAValue(newindex2),
                                        CoreWar.getBMode(newindex2), newvalueB);
                                break;
                            case(2)://@
                                System.out.println("Error! Mode A = @!");
                                System.exit(0);
                                break;
                            case(3)://* amode-indirect
                                int n1=(index+valueA+CoreWar.getAValue((index+valueA+board)%board)+board)%board;

                                newvalueB=CoreWar.getBValue((newindex2+board)%board)+CoreWar.getBValue(n1);
                                newvalueA=CoreWar.getAValue(newindex2)+CoreWar.getAValue(n1);

                                CoreWar.loadmem(newindex2, CoreWar.getOperation(newindex2),
                                        CoreWar.getAMode(newindex2), newvalueA,
                                        CoreWar.getBMode(newindex2), newvalueB);
                                break;

                        }
                        break;

                    case(3):
                        System.out.println("Error! ADD B Cannot be A-filed Indirect!");
                        System.exit(0);
                        break;

                }
                NextIndex = (index+1+board) % board;
                break;

            case (3)://SUB   A   B
                switch(modeB){
                    case(0)://relative
                        newindex=(index+valueB+board)% board;

                        switch(modeA){

                            case(0)://relative
                                newvalueB=CoreWar.getBValue((newindex+board)%board)-CoreWar.getBValue((index+valueA+board)%board);
                                newvalueA=CoreWar.getAValue(((newindex)+board)%board)-CoreWar.getAValue((index+valueA+board)%board);
                                CoreWar.loadmem(newindex, CoreWar.getOperation(newindex),
                                        CoreWar.getAMode(newindex), newvalueA,
                                        CoreWar.getBMode(newindex), newvalueB);
                                break;

                            case(1)://immediate
                                newvalueB=CoreWar.getBValue(newindex)-valueA;
                                CoreWar.loadmem(newindex, CoreWar.getOperation(newindex),
                                        CoreWar.getAMode(newindex), CoreWar.getAValue(newindex),
                                        CoreWar.getBMode(newindex), newvalueB);
                                break;
                            case(2)://@
                                System.out.println("Error! Mode A = @!");
                                System.exit(0);
                                break;
                            case(3)://* amode-indirect
                                int c=(index+valueA+CoreWar.getAValue((index+valueA+board)%board)+board)%board;

                                newvalueB=CoreWar.getBValue(newindex)-CoreWar.getBValue((c+board)%board);
                                newvalueA=(CoreWar.getAValue(newindex)-CoreWar.getAValue((c+board)%board));

                                CoreWar.loadmem(newindex, CoreWar.getOperation(newindex),
                                        CoreWar.getAMode(newindex), newvalueA,
                                        CoreWar.getBMode(newindex), newvalueB);
                                break;

                        }


                        break;
                    case(1):
                        System.out.println("Error! ADD B Mode Cannot be immediate!");
                        System.exit(0);
                        break;
                    case(2)://@     B-filed Indirect  2
                        int newindex2=(((index+valueB+CoreWar.getBValue((index+valueB+board)% board))+board)% board);

                        switch(modeA){

                            case(0)://none
                                newvalueB=(CoreWar.getBValue(((newindex2)+board)%board)-
                                        CoreWar.getBValue((index+valueA+board)%board));
                                newvalueA=(CoreWar.getAValue((newindex2+board)%board)-CoreWar.getAValue((index+valueA+board)%board));
                                CoreWar.loadmem(newindex2, CoreWar.getOperation(newindex2),
                                        CoreWar.getAMode(newindex2), newvalueA,
                                        CoreWar.getBMode(newindex2), newvalueB);
                                break;

                            case(1)://immediate

                                newvalueB=(CoreWar.getBValue(newindex2)-valueA);
                                CoreWar.loadmem(newindex2, CoreWar.getOperation(newindex2),
                                        CoreWar.getAMode(newindex2), CoreWar.getAValue(newindex2),
                                        CoreWar.getBMode(newindex2), newvalueB);
                                break;
                            case(2)://@
                                System.out.println("Error! Mode A = @!");
                                System.exit(0);
                                break;
                            case(3)://* amode-indirect
                                int n1=(index+valueA+CoreWar.getAValue((index+valueA+board)%board)+board)%board;

                                newvalueB=CoreWar.getBValue((newindex2+board)%board)-CoreWar.getBValue(n1);
                                newvalueA=CoreWar.getAValue(newindex2)-CoreWar.getAValue(n1);

                                CoreWar.loadmem(newindex2, CoreWar.getOperation(newindex2),
                                        CoreWar.getAMode(newindex2), newvalueA,
                                        CoreWar.getBMode(newindex2), newvalueB);
                                break;

                        }
                        break;

                    case(3):
                        System.out.println("Error! ADD B Cannot be A-filed Indirect!");
                        System.exit(0);
                        break;

                }
                NextIndex = (index+1+board) % board;
                break;
            case (4)://JMP   A
                switch(modeA){
                    case(0):
                        NextIndex =(index+CoreWar.getAValue(index)+board)%board;
                        break;
                    case(1):
                        System.out.println("Error! Mode A = #!");
                        System.exit(0);
                        break;
                    case(2):
                        System.out.println("Error! Mode A = @!");
                        System.exit(0);
                        break;
                    case(3):
                        NextIndex=(CoreWar.getAValue((index+valueA+board)%board)+index + valueA +board)%board;
                        break;
                }
                //NextIndex =(index+CoreWar.getAValue(index)+board)%board;


                break;
            case (5)://JMZ   A    B
                int ifjump=0;
                switch(modeB){
                    case(0):
                        if(CoreWar.getBValue((index+valueB+board)%board) == 0){
                            ifjump=1;
                        }
                        break;
                    case(1):
                        if (valueB==0){
                            ifjump=1;

                        }
                        break;
                    case(2):
                        int b=CoreWar.getBValue((index+valueB+board)%board);
                        if(CoreWar.getBValue((index+valueB+b+board)%board) == 0){
                            ifjump=1;
                        }
                        break;
                    case(3):
                        System.out.println("Error! Mode B = *!");
                        System.exit(0);
                        break;
                }
                if(ifjump==1){
                    switch(modeA){
                        case(0):
                            NextIndex =(index+CoreWar.getAValue(index)+board)%board;
                            break;
                        case(1):
                            System.out.println("Error! Mode A = #!");
                            System.exit(0);
                            break;
                        case(2):
                            System.out.println("Error! Mode A = @!");
                            System.exit(0);
                            break;
                        case(3):
                            NextIndex=(CoreWar.getAValue((index+valueA+board)%board)+index + valueA +board)%board;
                            break;
                    }
                    //NextIndex =(index+CoreWar.getAValue(index)+board)%board;

                }
                else{
                    NextIndex =(index+1+board)%board;
                }


                break;
            case (6)://DJN   A    B
                int ifjump2=0;
                switch(modeB){
                    case(0):
                        if(CoreWar.getBValue((index+valueB+board)%board) -1 != 0){
                            ifjump2=1;
                        }
                        CoreWar.setBValue(((index+valueB+board) % board), CoreWar.getBValue((index+valueB+board)%board) -1);
                        break;
                    case(1):
                        if (valueB-1!=0){
                            ifjump2=1;
                        }
                        CoreWar.setBValue(index, valueB-1);
                        break;
                    case(2):
                        int b=CoreWar.getBValue((index+valueB+board)%board);
                        if(CoreWar.getBValue((index+valueB+b+board)%board)-1 != 0){
                            ifjump2=1;
                        }
                        CoreWar.setBValue(((index+valueB+b+board)%board), CoreWar.getBValue((index+valueB+b+board)%board)-1);
                        break;
                    case(3):
                        System.out.println("Error! Mode B = *!");
                        System.exit(0);
                        break;
                }
                if(ifjump2==1){
                    switch(modeA){
                        case(0):
                            NextIndex =(index+CoreWar.getAValue(index)+board)%board;
                            break;
                        case(1):
                            System.out.println("Error! Mode A = #!");
                            System.exit(0);
                            break;
                        case(2):
                            System.out.println("Error! Mode A = @!");
                            System.exit(0);
                            break;
                        case(3):
                            NextIndex=(CoreWar.getAValue((index+valueA+board)%board)+index + valueA +board)%board;
                            break;
                    }
                    //NextIndex =(index+CoreWar.getAValue(index)+board)%board;

                }
                else{
                    NextIndex =(index+1+board)%board;
                }


                break;
            case (7)://CMP   A    B
                int skip =0;
                switch (modeB){
                    case (0): //relative
                        int instr1;
                        int instr2;
                        switch(modeA){
                            case(0)://relative
                                instr1=(index+valueA+board)%board;     //change this at case 2
                                instr2=(index+valueB+board)%board;
                                if (compareinstruction(instr1,instr2)==1){
                                    skip=1;
                                }
                                break;
                            case(1)://immediate
                                if(valueA==CoreWar.getBValue((index+valueB+board)%board)){
                                    skip=1;
                                }
                                break;
                            case(2):
                                System.out.println("Error! Mode A = @!");
                                System.exit(0);
                                break;
                            case(3): //a indirect
                                instr1=(index+CoreWar.getAValue((index+valueA+board)%board)+valueA+board)%board;     //change this at case 2
                                instr2=(index+valueB+board)%board;
                                if (compareinstruction(instr1,instr2)==1){
                                    skip=1;
                                }
                                break;
                        }
                        break;
                    case(1):
                        System.out.println("Error! Mode B = #!");
                        System.exit(0);
                        break;
                    case(2): // bindirect
                        switch (modeA){
                            case(0):
                                instr1=(index+valueA+board)%board;     //change this at case 2
                                instr2=(index+CoreWar.getAValue((index+valueB+board)%board)+valueB+board)%board;
                                if (compareinstruction(instr1,instr2)==1){
                                    skip=1;
                                }
                                break;
                            case(1):
                                int n=(index+CoreWar.getAValue((index+valueB+board)%board)+valueB+board)%board;
                                if(valueA==CoreWar.getBValue(n)){
                                    skip=1;
                                }
                                break;
                            case(2):
                                System.out.println("Error! Mode A = @!");
                                System.exit(0);
                                break;
                            case(3):
                                instr1=(index+CoreWar.getAValue((index+valueA+board)%board)+valueA+board)%board;     //change this at case 2
                                instr2=(index+CoreWar.getAValue((index+valueB+board)%board)+valueB+board)%board;
                                if (compareinstruction(instr1,instr2)==1){
                                    skip=1;
                                }
                                break;

                        }

                        break;
                    case(3):
                        System.out.println("Error! Mode B = *!");
                        System.exit(0);
                        break;

                }

                if(skip==1){
                    NextIndex =(index+2+board)%board;
                    //Compare = true;
                }
                else{
                    NextIndex =(index+1+board)%board;
                }

                break;
            case (8)://SPL   A
                Split = true;
                NextIndex = (index+1+board) % board;
                switch(modeA){
                    case(0):
                        SPLIndex =(index+CoreWar.getAValue(index)+board)%board;
                        break;
                    case(1):
                        System.out.println("Error! Mode A = #!");
                        System.exit(0);
                        break;
                    case(2):
                        System.out.println("Error! Mode A = @!");
                        System.exit(0);
                        break;
                    case(3):
                        SPLIndex=(CoreWar.getAValue((index+valueA+board)%board)+index + valueA +board)%board;
                        break;
                }
                break;
            default:
                System.out.println("Unknown Opcode");
                System.exit(0);
                break;

        }

        CoreWar.PrintInstruction(NextIndex);
    }

    public static int compareinstruction(int index1,int index2){
        int same=0;
        if(CoreWar.getOperation(index1)==CoreWar.getOperation(index2)){
            same=1; }
        else{same=0;}
        if(CoreWar.getAMode(index1)==CoreWar.getAMode(index2)){
            same=1; }
        else{same=0;}
        if(CoreWar.getAValue(index1)==CoreWar.getAValue(index2)){
            same=1; }
        else{same=0;}
        if(CoreWar.getBMode(index1)==CoreWar.getBMode(index2)){
            same=1; }
        else{same=0;}
        if(CoreWar.getBValue(index1)==CoreWar.getBValue(index2)){
            same=1; }
        else{same=0;}

        return same;

    }

    public static boolean isLive() {
        return live;
    }

    public static boolean isSplit() {
        return Split;
    }

    public static boolean isCompare() {
        return Compare;
    }

    public static int getNextIndex() {
        return NextIndex;
    }

    public static int getSPLIndex() {
        return SPLIndex;
    }
}
//DAT = 0, MOV = 1, ADD = 2, SUB = 3, JMP = 4, JMZ = 5, DJN = 6, CMP = 7, SPL = 8;
//No Mode = 0, # = 1, @ = 2, * = 3;
//datamemory[index][0] = Operation
//datamemory[index][1] = Mode of A
//datamemory[index][2] = Value of A
//datamemory[index][3] = Mode of B
//datamemory[index][4] = Value of B

//none  Relative  0
//#     Immediate  1

//@     B-filed Indirect  2
//*     A-filed Indirect  3
//none = 0, # = 1, @ = 2, * = 3;