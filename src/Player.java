import java.util.ArrayList;

class Player {

    private Chessboard chessboard;

    public Record r;

    public boolean humanPlayer;

    public int player;


    public boolean isHumanPlayer(){
        return humanPlayer;
    }







}
class HumanPlayer extends Player{
    public HumanPlayer(int player){
        this.player=player;
        this.humanPlayer=true;
    }
}

class ComputerPlayer extends Player{
    public ComputerPlayer(int player){
        this.player=player;
        this.humanPlayer=false;
    }
}