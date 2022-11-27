import java.util.ArrayList;
import java.util.Random;

class Chessboard{



    /*
        chessTypes中存了三位数,第一位标记玩家(1代表红方，2代表黑方)，第二位标记棋子状态，第三位标记棋子的类型
    */
    private static final int[] chessTypes = {101,101,101,101,101,201,201,201,201,201,102,102,202,202,103,103,203,203,104,104,204,204,105,105,205,205,106,206,107,107,207,207};
    Chess[][] chessboard = new Chess[9][5];



    public Chessboard() {
        init();
    }

    //获取i,j位置上的棋子状态
    public Chess getChess(int i, int j) {
        return chessboard[i][j];
    }

    //初始化棋盘
    public void init(){
        Chess chess;
        for(int i = 1;i <= 8;i++){
            for(int j = 1;j <= 4;j++){
                chessboard[i][j] = null;//置空棋盘
            }
        }
        int[] rndChessTypes = randomize();
        putChess(rndChessTypes);

    }

    //随机化chessTypes并输出
    public int[] randomize(){
        Random random = new Random();
        int[] rndChessTypes = new int[32];
        for(int i = 31 ; i >= 0 ; i--){
            int pos = random.nextInt(i + 1);
            rndChessTypes[31 - i] = chessTypes[pos];
            //覆盖pos位置
            for(int j = pos ; j <= i - 1 ; j++){
                chessTypes[j] = chessTypes[j + 1];
            }
        }
        return rndChessTypes;
    }

    //在chessboard上放置棋子
    public void putChess(int[] ChessTypes){
        for(int i = 0 ; i <= 31 ; i++){
            int x = (i % 4) + 1;
            int y = (i / 4) + 1;
            chessboard[y][x] = new Chess(x,y,ChessTypes[i] % 10,ChessTypes[i] / 100,(ChessTypes[i] / 10) % 10);
        }
    }
}

