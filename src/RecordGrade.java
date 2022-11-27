import java.util.ArrayList;

public class RecordGrade {
    public int grade;
    public RecordGrade recordGrade;
    public ArrayList<Integer> gradeList=new ArrayList<>();
    private Player player;
    public RecordGrade(Player player){
        this.player=player;
    }

    //当有棋子被吃时，用这个方法加分
    public void plusGrade(Chess chess2) {
        int grade=0;
        if(chess2.getType()==1){
            grade=1;

        }else if(chess2.getType()==2){
            grade=5;

        } else if (chess2.getType()==3) {
            grade=5;


        } else if (chess2.getType()==4) {
            grade=5;

        } else if (chess2.getType()==5) {
            grade=10;

        } else if (chess2.getType()==6) {
            grade=30;

        } else if (chess2.getType()==7) {
            grade=5;
        }
        this.grade += grade;
        this.gradeList.add(grade);
    }
    //撤回时，用这个方法减去list中的值
    public void minusGrade() {
        this.gradeList.remove(gradeList.size()-1);
    }
    //针对于炮吃了自己棋子的特殊情况
    public void otherCase(Chess chess2){
        int grade=0;
        if(chess2.getType()==1){
            grade=1;

        }else if(chess2.getType()==2){
            grade=5;

        } else if (chess2.getType()==3) {
            grade=5;


        } else if (chess2.getType()==4) {
            grade=5;

        } else if (chess2.getType()==5) {
            grade=10;

        } else if (chess2.getType()==6) {
            grade=30;

        } else if (chess2.getType()==7) {
            grade=5;
        }
        this.grade+=grade;
        this.gradeList.add(grade);
    }

    public int getGrade() {
        return grade;
    }
    //用于判断游戏是否结束
    public boolean judge(Player player){
        if(player==null){
            return false;
        }
        if(this.recordGrade.getGrade()>=60){
            return true;
        }
        return false;
    }

    public void setGrade(int grade) {
        this.grade +=grade;
    }
}
