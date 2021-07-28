import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class CTCIBuildOrder {
    public static void main(String[] args) {
        try{
            CTCIBuildOrder obj = new CTCIBuildOrder();
            obj.run(args);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void run(String[] args){
        //Creates the data(projects and dependencies)
        ArrayList<String> projects = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f"));
        String[][] dependencies = new String[5][2];
        dependencies[0][0] = "a";
        dependencies[0][1] = "d";
        dependencies[1][0] = "f";
        dependencies[1][1] = "b";
        dependencies[2][0] = "b";
        dependencies[2][1] = "d";
        dependencies[3][0] = "f";
        dependencies[3][1] = "a";
        dependencies[4][0] = "d";
        dependencies[4][1] = "c";
        //Prints the build order
        System.out.println(findBuildOrder(projects,dependencies));
    }
    public ArrayList<String> findBuildOrder(ArrayList<String> projectList,String[][] dependencyList){
        boolean keepGoing = true;
        int indexInList = 0;
        ArrayList<String> order = new ArrayList<String>();
        HashSet<String> visited = new HashSet<String>();
        HashMap<String,Node> map = new HashMap<>();
        Node[] mapArray = new Node[projectList.size()];
        for(int x=0;x<projectList.size();x++){
            Node nodeToAdd = new Node(projectList.get(x));
            map.put(projectList.get(x),nodeToAdd);
            mapArray[x] = nodeToAdd;
        }
        for(int x=0;x<dependencyList.length;x++){
            map.get(dependencyList[x][1]).addChild(map.get(dependencyList[x][0]));
        }
        while(keepGoing){
            depthFirstSearch(order,visited,mapArray[indexInList]);
            if(visited.size() == projectList.size()){
                keepGoing = false;
            }
            indexInList++;
        }
        return order;
    }
    public void depthFirstSearch(ArrayList<String> order,HashSet<String> visited,Node currNode){
        if(!visited.contains(currNode.val)){
            for(int x=0;x<currNode.children.size();x++){
                if((!visited.contains(currNode.children.get(x)))){
                    depthFirstSearch(order,visited,currNode.children.get(x));
                }
            }
            order.add(currNode.val);
            visited.add(currNode.val);
        }
    }
}
class Node {
    public String val;
    public ArrayList<Node> children = new ArrayList<>();
    public Node(String val){
        this.val = val;
    }
    public void addChild(Node child){
        children.add(child);
    }
}