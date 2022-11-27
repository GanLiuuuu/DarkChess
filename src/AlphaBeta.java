import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class AlphaBeta {
    public class Node {
        private int leval;
        private int value;
        private String pFather;
        private ArrayList<String> pChildren;
        private String name;
        Node(String name)
        {
            this.name=name;
            value=-1;
            pFather=new String();
            pChildren=new ArrayList<String>();
        }
    }
    final int MAX_INT=32767;
    final int MIN_INT=-32768;
    final int MAX=1;   //极大节点
    final int MIN=0;    //极小节点

    private ArrayList<Node> NodeTree;
    private String jianzhi[];
    private int count;

    public void getStrategy(String inputFile){
        NodeTree=new ArrayList<Node>();
        jianzhi=new String[20];
        count=0;
        readTree(inputFile);
        Alph_Beta(NodeTree.get(0).name);
        System.out.println(count);
        String bestRoute="";
        for(int i=0;i<NodeTree.get(0).pChildren.size();i++)
        {
            if(NodeTree.get(0).value==NodeTree.get(search(NodeTree.get(0).pChildren.get(i))).value)
            {
                bestRoute=NodeTree.get(0).name+" "+NodeTree.get(0).value+" "+NodeTree.get(search(NodeTree.get(0).pChildren.get(i))).name;
                break;
            }

        }
        System.out.println(bestRoute);
        for(int i=0;i<count;i++)
        {
            System.out.println(jianzhi[i]);
        }

    }
    void Alph_Beta(String str)
    {
        boolean flag=false;
        Node nNode=NodeTree.get(search(str));
        if(nNode.leval==MAX)
        {
            for(int i=0;i<nNode.pChildren.size();i++)
            {
                Alph_Beta(nNode.pChildren.get(i));
                if(nNode.value<NodeTree.get(search(nNode.pChildren.get(i))).value)
                {
                    nNode.value=NodeTree.get(search(nNode.pChildren.get(i))).value;
                    if(Beta(str))//是否在极大点出执行Beta剪枝
                    {
                        jianzhi[count]=str+":";
                        for(int j=i+1;j<nNode.pChildren.size();j++)
                        {
                            jianzhi[count]=jianzhi[count]+" "+nNode.pChildren.get(j)+" β剪枝 ";
                            flag=true;
                        }
                        if(flag==true)
                        {
                            count++;
                        }
                        return;
                    }
                }
            }
        }
        else
        {
            for(int i=0;i<nNode.pChildren.size();i++)
            {
                Alph_Beta(nNode.pChildren.get(i));
                if(nNode.value>NodeTree.get(search(nNode.pChildren.get(i))).value)
                {
                    nNode.value=NodeTree.get(search(nNode.pChildren.get(i))).value;
                    if(Alpha(str))
                    {
                        jianzhi[count]=str+":";
                        for(int j=i+1;j<nNode.pChildren.size();j++)
                        {
                            jianzhi[count]=jianzhi[count]+" "+nNode.pChildren.get(j)+"   α剪枝";
                            flag=true;
                        }
                        if(flag==true)
                        {
                            count++;
                        }
                        return;
                    }
                }
            }
        }
    }
    boolean Alpha(String str)
    {
        Node nNode=NodeTree.get(search(str));
        if(nNode.pFather==null)
        {
            return false;
        }
        int i=search(nNode.pFather);
        while(i>=0)
        {
            if((NodeTree.get(i).value>=nNode.value)&&
                    (NodeTree.get(i).leval==MAX)&&((NodeTree.get(i).value!=MIN_INT)))
                return true;
            else
            {
                if(i!=0)
                {
                    i=search(NodeTree.get(i).pFather);//其祖先节点
                }
                else
                    break;
            }
        }
        return false;
    }
    boolean Beta(String str)
    {
        Node nNode=NodeTree.get(search(str));
        if(nNode.pFather==null)
        {
            return false;
        }

        int i=search(nNode.pFather);
        while(i>=0)
        {
            if((NodeTree.get(i).value<=nNode.value)&&
                    (NodeTree.get(i).leval==MIN)&&((NodeTree.get(i).value!=MAX_INT)))
                return true;
            else
            {
                if(i!=0)
                {
                    i=search(NodeTree.get(i).pFather);
                }
                else
                    break;
            }
        }
        return false;
    }

    public void readTree(String filename)
    {
        File file=new File(filename);
        String nodename[]=new String[10];
        try
        {
            BufferedReader in=new BufferedReader(new FileReader(file));
            String s;
            s=in.readLine();
            if(s.startsWith("ROOT"))
            {
                nodename=s.split("\\s+");
            }
            NodeTree.add(new Node(nodename[1]));
            NodeTree.get(0).leval=MAX;
            NodeTree.get(0).value=MIN_INT;
            NodeTree.get(0).pFather=null;
            while(!(s=in.readLine()).equals("VALUE"))
            {
                nodename=s.split("\\s+");
                for(int i=1;i<nodename.length-1;i++)
                {
                    NodeTree.get(search(nodename[0])).pChildren.add(nodename[i]);
                    Node nNode=new Node(nodename[i]);  //value为-1；
                    nNode.pFather=nodename[0];
                    if(NodeTree.get(search(nodename[0])).leval==MAX)
                    {
                        nNode.leval=MIN;
                        nNode.value=MAX_INT;
                    }
                    else
                    {
                        nNode.leval=MAX;
                        nNode.value=MIN_INT;
                    }
                    NodeTree.add(nNode);
                }
            }
            String nodeValue[]=new String[10];
            while(!(s=in.readLine()).equals("END"))
            {
                nodeValue=s.split("\\s+");
                NodeTree.get(search(nodeValue[0])).value=Integer.parseInt(nodeValue[1]);
            }
            in.close();
        }catch(Exception e){
            System.out.println("Error!!");}
    }
    int search(String str)
    {
        for(int i=0;i<NodeTree.size();i++)
        {
            if(NodeTree.get(i).name.equals(str))
                return i;
        }
        return -1;
    }


}
