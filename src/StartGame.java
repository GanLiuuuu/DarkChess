import java.util.Scanner;

class StartGame{
    Chessboard chessboard;
    Operation operation;//储存对棋盘和棋子的操作

    Record record=new Record();//储存棋盘记录

    HumanPlayer p1,p2;
    RecordGrade recordGrade1 = new RecordGrade(p1);

    RecordGrade recordGrade2 = new RecordGrade(p2);




    int round;//标记回合数

    public StartGame() {
        init();
        Start();
    }

    public void init() {
        chessboard = new Chessboard();
        operation = new Operation(chessboard);
        round = 0;
    }

    public void Start() {
        Scanner sc = new Scanner(System.in);
        int operator = 0;
        int x1, x2, y1, y2;
        Player presentPlayer = p1;

        while(!recordGrade1.judge(p1)||!recordGrade2.judge(p2)){
            while(operator != -1) {

            round++;


            //把棋盘输出到命令行中
            outputChessboard();

            //输入操作符
            operator = sc.nextInt();

            //如果round为奇数，则presentPlayer为p1,反之为p2
            if(round % 2 == 1){
                presentPlayer = p1;
            }
            else{
                presentPlayer = p2;
            }


            //根据输入的operator执行不同的操作
            if (operator == 0){
                /*
                    翻子
                */
                x1 = sc.nextInt();
                y1 = sc.nextInt();
                Chess chess = chessboard.chessboard[y1][x1];

                //尝试翻子
                if(operation.isChangeStatusValid(chess,1)){
                    operation.changeStatus(chess,1);
                    record.recordRetract(0,chess,x1,y1);
                    record.setChess2Status();
                    record.chess2Player.add(0);
                }
                else{
                    //若操作不合法，则回合数减1,因为每次进入while循环的时候回合数都会加1，所以这里减一用来抵消上面的加1
                    round--;
                    System.out.println("The change of status is not valid");
                    continue;
                }

                //如果是第一轮，则需要对p1和p2初始化
                if(round == 1){
                    int chosenChessPlayer = chess.getPlayer();
                    if(chosenChessPlayer == 1){
                        p1 = new HumanPlayer(1);
                        p2 = new HumanPlayer(2);
                    }
                    else if(chosenChessPlayer == 2){
                        p1 = new HumanPlayer(2);
                        p2 = new HumanPlayer(1);

                    }

                }

                } else if (operator == 1) {
            	/*
            		执行移动棋子的操作
            		若移动棋子是合法的则移动并输出棋盘，进行下一次循环;
            		否则提示不合法，重新进行本轮循环
            	*/
                x1 = sc.nextInt();
                y1 = sc.nextInt();
                x2 = sc.nextInt();
                y2 = sc.nextInt();

                Chess chess1 = chessboard.chessboard[y1][x1],chess2 = chessboard.chessboard[y2][x2];

                //尝试移动棋子
                if(operation.isMoveValid(chess1,chess2,x2,y2,presentPlayer)){
                    if(chess2==null){
                        record.setChess2Status();
                        record.chess2Player.add(0);
                        operation.moveChess(chess1,chess2,x2,y2);
                        record.recordRetract(operator,chess1, chess2,x1,y1,x2,y2);
                        }else{
                        record.setChess2Status(chess2);
                        record.chess2Player.add(chess2.getPlayer());
                        operation.moveChess(chess1,chess2,x2,y2);
                        record.recordRetract(operator,chess1, chess2,x1,y1,x2,y2);
                        if(round%2==1){
                           if(chess1.getType()==7&&chess2.getPlayer()==p1.player){

                                   recordGrade2.otherCase(chess2);

                           }else {
                               recordGrade1.plusGrade(chess2);

                           }


                        } else if (round%2==0) {
                            if(chess1.getType()==7&&chess2.getPlayer()==p2.player){

                                    recordGrade1.otherCase(chess2);

                            }else{
                                recordGrade2.plusGrade(chess2);
                            }

                        }
                        }

                    }
                    else{
                    System.out.println("The Move is not Valid");
                    round--;
                }


            } else if (operator == 2) {
            	/*
            		悔棋
            	*/
                //用户可能在开局就点击撤回，返回错误
                if(record.getRetract().size()==0){
                    System.out.println("error");
                    round--;
                }else{

                    if(record.getRetract().get(round-2).getOperator() ==0){

                    }else if (record.getRetract().get(round-2).getOperator()==1){
                        if(round%2==0){
                            if(record.getRetract().get(round-2).getChess2()!=null){
                                if(record.chess2Player.get(round-2)==p2.player){
                                    recordGrade1.setGrade(-recordGrade1.gradeList.get(recordGrade1.gradeList.size()-1));
                                    recordGrade1.minusGrade();

                                }
                            }else{
                                recordGrade2.setGrade(-recordGrade2.gradeList.get(recordGrade2.gradeList.size()-1));
                                recordGrade2.minusGrade();
                            }


                        }
                        else {
                            if(record.getRetract().get(round-2).getChess2()!=null){
                                if(record.chess2Player.get(round-2)==p1.player){
                                    recordGrade2.setGrade(-recordGrade2.gradeList.get(recordGrade2.gradeList.size()-1));
                                    recordGrade2.minusGrade();

                                }
                            }else {
                                recordGrade1.setGrade(-recordGrade1.gradeList.get(recordGrade1.gradeList.size()-1));
                                recordGrade1.minusGrade();

                            }

                        }
                    }

                    operation.retract(record,round);
                    round -= 2;
                }
            } else {
                System.out.println("The operation is not valid");
                round--;
                }
            }
        }
        if(recordGrade1.judge(p1)==true){
            System.out.println("Game is over,the winner is p1.");
        }
        if(recordGrade2.judge(p2)==true){
            System.out.println("Game is over, the winner is p2.");
        }
    }

    public void outputChessboard(){
        System.out.printf("round:%d\n",round);
        if(round>=2){

            System.out.printf("the score of p1 is %d,the score of p2 is %d\n",recordGrade1.getGrade(),recordGrade2.getGrade());

        }
        for(int i = 1 ;i <= 8 ;i++){
            for (int j = 1; j <= 4; j++) {
                Chess chess = chessboard.chessboard[i][j];
                if(chess == null){
                    System.out.printf("NU\t");
                }
                else if(chess.getStatus() == 0){
                    System.out.printf("**\t");
                }
                else{
                    System.out.printf("%d%d\t",chess.getPlayer(),chess.getType());
                }

            }
            System.out.println();
        }
    }
}

