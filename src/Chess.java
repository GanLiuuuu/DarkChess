import java.util.ArrayList;

class Chess {
        private int x;//棋子的列号(横坐标)
        private int y;//棋子的行号(纵坐标)
        //坐标轴中心为左上角
        private int type;
        /*type表示棋子的类型，1,2,3,4,5,6,7分别代表Soldier,Horse,Chariot,Minister,Advisor,General,Cannon;*/
        private int player;//1代表红方，2代表黑方
        private int status;//标记棋子的状态，0代表未翻开，1代表已翻开，2代表已死亡

    /*
    可以再加入其他属性
    */

        public Chess(int x,int y,int type,int player,int status){
            this.x = x;
            this.y = y;
            this.type = type;
            this.player = player;
            this.status = status;
        }

        //setters and getters
        public int getX(){
            return x;
        }
        public void setX(int x){
            this.x = x;
        }

        public int getY(){
            return y;
        }
        public void setY(int y){
            this.y = y;
        }

        public int getType(){
            return type;
        }
        public void setType(int type){
            this.type = type;
        }

        public int getPlayer(){
            return player;
        }
        public void setPlayer(int player){
            this.player = player;
        }

        public int getStatus(){
            return this.status;
        }
        public void setStatus(int status){
            this.status = status;
        }


    }

