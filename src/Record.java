import java.util.ArrayList;
public class Record {
    //chess2satus部分是用于connon吃子撤回时的特殊field
    private ArrayList<Integer> chess2Status=new ArrayList<>();
    public ArrayList<Integer> chess2Player=new ArrayList<>();
    public ArrayList<Integer> chess2Type= new ArrayList<>();


    public ArrayList<Integer> getChess2Status() {
        return chess2Status;
    }

    public void setChess2Status(Chess chess) {
        this.chess2Status.add((Integer) chess.getStatus());
    }

    public void setChess2Status() {
        this.chess2Status.add(3);
    }



    private ArrayList<retractForEveryStep> retract=new ArrayList();
        //建立历史记录list
        //并准备好对应的读取和设置method

        //用overloading的思路，对不同的操作进行不同的记录
        public void recordRetract(int operator, Chess chess, int x, int y){
            retractForEveryStep r=new retractForEveryStep(operator,chess,x,y);
            this.getRetract().add(r);
        }
        public void recordRetract(int operator, Chess chess1, Chess chess2, int x1 , int y1, int x2, int y2){
            retractForEveryStep r=new retractForEveryStep(operator,chess1,chess2,x1,y1,x2,y2);
            this.getRetract().add(r);
        }
        public void setRetract(ArrayList<retractForEveryStep> retract) {
            this.retract = retract;
        }
        public ArrayList<retractForEveryStep> getRetract() {
            return retract;
        }
        //retractForEveryStep:inner class to record operation of every step.


        public class retractForEveryStep {
            private int x1,y1,x2,y2;
            //chess1记录选中的棋子，chess2记录被吃的棋子（若没有棋子被吃，则chess2为null）
            private int operator;
            private Chess chess,chess1,chess2;







            //constructors
            public retractForEveryStep(int operator, Chess chess, int x1, int y1){
                this.operator=operator;
                this.x1=x1;
                this.y1=y1;
                this.chess=chess;
            }
            public retractForEveryStep(int operator, Chess chess1, Chess chess2, int x1 , int y1, int x2, int y2){
                this.operator=operator;
                this.x1=x1;
                this.y1=y1;
                this.y2=y2;
                this.x2=x2;
                this.chess1=chess1;
                this.chess2=chess2;
            }
            //setter and getter
            public int getOperator() {
                return operator;
            }

            public Chess getChess() {
                return chess;
            }

            public Chess getChess1() {
                return chess1;
            }

            public Chess getChess2() {
                return chess2;
            }

            public int getX1() {
                return x1;
            }

            public int getX2() {
                return x2;
            }

            public int getY1() {
                return y1;
            }

            public int getY2() {
                return y2;
            }

            public void setOperator(int operator) {
                this.operator = operator;
            }

            public void setX1(int x1) {
                this.x1 = x1;
            }

            public void setX2(int x2) {
                this.x2 = x2;
            }

            public void setY1(int y1) {
                this.y1 = y1;
            }

            public void setY2(int y2) {
                this.y2 = y2;
            }

        }

    }


