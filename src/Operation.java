import java.util.List;

class Operation {
    private Chessboard chessboard;


    public Operation(Chessboard chessboard){
        this.chessboard = chessboard;
    }

    /*
        悔棋
     */
    public void retract(Record r,int round){
            //第一种情况：撤回翻棋操作，逆操作即为change status（chess，0）
        if(r.getRetract().get(round-2).getOperator()==0){
            changeStatus(r.getRetract().get(round-2).getChess(),0);
            r.getRetract().remove(round-2);
            r.getChess2Status().remove(round-2);
            r.chess2Player.remove(round-2);
        }
        //第二种情况：撤回移动操作，逆操作即将原坐标调换位置
        else{
            //首先对chess1进行逆移动
                    moveChess(r.getRetract().get(round-2).getChess1(),null,
                    r.getRetract().get(round-2).getX1(),r.getRetract().get(round-2).getY1());
                    r.chess2Player.remove(round-2);
            chessboard.chessboard[r.getRetract().get(round-2).getY1()][r.getRetract().get(round-2).getX1()]=r.getRetract().get(round-2).getChess1();
            //判断chess2是否被吃并做出相应逆操作
            if(r.getRetract().get(round-2).getChess2()==null){

            }else{
                //判断chess1是否是cannon
                if(r.getRetract().get(round-2).getChess1().getType()==7){
                    //如果chess1是cannon，判断chess2是否被翻开
                    if(r.getChess2Status().get(round-2)==0){
                        chessboard.chessboard[r.getRetract().get(round-2).getY2()][r.getRetract().get(round-2).getX2()]=r.getRetract().get(round-2).getChess2();
                        changeStatus(r.getRetract().get(round-2).getChess2(),0);
                    }else{
                        changeStatus(r.getRetract().get(round-2).getChess2(),1);
                        chessboard.chessboard[r.getRetract().get(round-2).getY2()][r.getRetract().get(round-2).getX2()]=r.getRetract().get(round-2).getChess2();



                    }

                }else{
                    changeStatus(r.getRetract().get(round-2).getChess2(),1);
                    chessboard.chessboard[r.getRetract().get(round-2).getY2()][r.getRetract().get(round-2).getX2()]=r.getRetract().get(round-2).getChess2();
                }
            }
            r.getRetract().remove(round-2);
            r.getChess2Status().remove(round-2);
        }
    }

    /*
        改变棋子状态
     */
    public void changeStatus(Chess chess, int status){
        chess.setStatus(status);
    }


    /*
        判断改变棋子状态是否合法
     */
    public boolean isChangeStatusValid(Chess chess,int status){
        if(chess.getStatus() == 0 && status > 0 && status <= 2){
            //假如未被翻开，则可以翻开和被吃
            return true;
        }
        else if(chess.getStatus() == 1 && status == 2){
            //假如被翻开，只能被吃
            return true;
        }
        else{
            //现在先这样写，之后需要改
            return false;
        }
    }

    //将棋子移动至(x2,y2),若chess2非空,则(x2,y2)即chess2坐标
    public void moveChess(Chess chess1,Chess chess2,int x2,int y2) {
        int x1 = chess1.getX(), y1 = chess1.getY();
        //判断(x2,y2)处是否为空
        if (chess2 != null) {
            chess2.setStatus(2);
        }
        chessboard.chessboard[y1][x1] = null;
        chessboard.chessboard[y2][x2] = chess1;
        chess1.setX(x2);
        chess1.setY(y2);
    }


    /*
        若chess2非空，判断chess1移到chess2位置是否合法;若chess2非空,则(x2,y2)即chess2坐标;
        player为操作的玩家
    */
    public boolean isMoveValid(Chess chess1, Chess chess2, int x2, int y2, Player player){
        //保证传入的chess1非空
        if(chess1 == null)return false;

        if(chess1.getPlayer() != player.player){
            return false;
        }
        //自身为Soldier
        if(chess1.getType() == 1){
            //chess2是否为空
            if(chess2 == null)return true;
            //判断chess2是否被翻开
            if(chess2.getStatus() == 0)return false;
            //判断是否移动一个单位
            if(!isMoveOne(chess1,x2,y2))return false;
            //判断chess1类型和chess2类型的大小关系并判断是否为同一玩家，Soldier可以吃General
            if(chess1.getType() < chess2.getType() && chess2.getType() != 6 || chess1.getPlayer() == chess2.getPlayer())return false;
            //以上条件均不满足说明移动是合法的
            return true;
        }


        //自身为Horse到General中的一个
        if(chess1.getType() >= 2 && chess1.getType() <= 6){
            if(chess2 == null)return true;
            if(chess2.getStatus() == 0)return false;
            if(!isMoveOne(chess1,x2,y2))return false;
            if(chess1.getType() < chess2.getType() && chess2.getType() != 7 || chess1.getPlayer() == chess2.getPlayer()){
                return false;
            }
            return true;
        }


        //自身为Cannon
        if(chess1.getType() == 7){
            //如果chess2为空则返回false
            if(chess2 == null)return false;
            //如果棋子已被翻开并且两种棋子颜色相同，则返回false
            if(chess2.getStatus() == 1 && chess2.getPlayer() == chess1.getPlayer())return false;
            //获取chess1的坐标
            int x1 = chess1.getX(),y1 = chess1.getY();
            //标记两个棋子之间有多少个棋子
            int midNum = 0;
            //判断炮是否隔一个子吃另一个子
            if(Math.abs(x1 - x2) > 1 && y1 == y2){
                if(x1 > x2){
                    for(int i = x1 - 1 ; i > x2 ; i--){
                        if(chessboard.chessboard[y1][i] != null)midNum++;
                    }
                }
                else{

                    for(int i = x1 + 1 ; i < x2 ; i++){
                        if(chessboard.chessboard[y1][i] != null)midNum++;
                    }
                }
            }
            if(Math.abs(y1 - y2) > 1 && x1 == x2){
                if(y1 > y2){
                    for(int i = y1 - 1 ; i > y2 ; i--){
                        if(chessboard.chessboard[i][x1] != null)midNum++;
                    }
                }
                else{
                    for(int i = y1 + 1 ; i < y2 ; i++){
                        if(chessboard.chessboard[i][x1] != null)midNum++;
                    }
                }
            }
            if(midNum == 1)return true;
            else return false;
        }

        return false;//如果自身type不为1-7，那么就输出false。
    }



    //判断是否移动了一个单位,是则返回true，反之返回false
    public boolean isMoveOne(Chess chess1,int x2,int y2){
        int x1 = chess1.getX(),y1 = chess1.getY();
        boolean isNextX = (Math.abs(x1 - x2) == 1 && y1 == y2);
        boolean isNextY = (Math.abs(y1 - y2) == 1 && x1 == x2);
        return (isNextX || isNextY);
    }

}