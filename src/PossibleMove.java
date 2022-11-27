public class PossibleMove {

    //没有注释



    //没有注释



    //没有注释



    //没有注释
    PossibleMove possibleMove;
    private int operator;
    private int x1,y1,x2,y2;
    private Chess chess,chess1,chess2;
    public PossibleMove(Chess chess, int x, int y){
        this.operator=0;
        this.x1=x;
        this.y1=y;
        this.chess=chess;
    }
    public PossibleMove(Chess chess1,Chess chess2,int x1,int y1,int x2, int y2){
        this.operator=1;
        this.chess1=chess1;
        this.chess2=chess2;
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
    }
    public PossibleMove getPossibleMove(){
        return possibleMove;
    }
    public int getOperator(){
        return this.operator;
    }

    public Chess getChess() {
        return chess;
    }

    public Chess getChess2() {
        return chess2;
    }

    public Chess getChess1() {
        return chess1;
    }

    public int getY2() {
        return y2;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getX1() {
        return x1;
    }

}
