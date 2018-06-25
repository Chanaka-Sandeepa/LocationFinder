package company.com.locationfinder.Shopping_Cart_Navigator.Controller;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import company.com.locationfinder.Shopping_Cart_Navigator.Model.Node;

/**
 * Created by root on 4/6/18.
 */

public class MazeSolver {

    public Map<Object, Object>[][] mapArray;
    private int dimX;
    private int dimY;
    private int cost = 0;
    int goalItemsCount;
    int collectedItemsCount = 0;
    int startX;
    int startY;
    ArrayList<ArrayList<int[]>> path = new ArrayList<ArrayList<int[]>>();

    public MazeSolver(int dimX, int dimY, int goalItemsCount) {
        this.goalItemsCount = goalItemsCount;
        initMapArray(dimX, dimY);
    }

    public void initMapArray(int x, int y){
        mapArray = new Map[x][y];
        this.dimX = x;
        this.dimY = y;
        for (int i = 0; i <x ; i++) {
            for (int j = 0; j <y ; j++) {
                Map<Object, Object> map = new HashMap<>();
                map.put("visited", false);
                map.put("cost", 1000);
                map.put("pred", null);
                mapArray[i][j] = map;
            }
        }
    }

    public int[][] copyInitialGrid(int[][] local_shopGrod, int[][] initialGrid){
        for(int i=0; i<initialGrid.length; i++) {
            for (int j = 0; j < initialGrid[i].length; j++)
                local_shopGrod[i][j] = initialGrid[i][j];
        }
        return local_shopGrod;
    }

    public Map<Object, Object>[][] getMapArray() {
        return mapArray;
    }

    public boolean gridBFS(int startX, int startY, int[][] shopGrid, Map<Object, Object>[][] solvedGrid){
        int[][] local_shopGrod = new int[shopGrid.length][shopGrid[0].length];
        local_shopGrod = copyInitialGrid(local_shopGrod, shopGrid);
        int[] pred = new int[2];
        pred[0]= startX;
        pred[1]= startY;
        if(shopGrid[startX][startY] == 2)
        {
            markMap(startX, startY, solvedGrid, true, cost, pred);
        }
        else {
            Queue<int[]> q=new LinkedList<int[]>();
            int[]start=pred;//Start Coordinates
            q.add(start);
            markMap(startX, startY, solvedGrid, true, 1000, pred);
            while(q.peek()!=null){
                int[] curr=q.poll();
                int pred_cost = (int)solvedGrid[curr[0]][curr[1]].get("cost");

                if(curr[0] < dimX-1){
                    int xc=curr[0]+1;
                    int yc=curr[1];
//                    if((boolean)solvedGrid[xc][yc].get("visited") == false || (int)solvedGrid[xc][yc].get("cost") > pred_cost+1) {

                        if (shopGrid[xc][yc] == 2)//Destination found
                        {
//                            if((boolean)solvedGrid[xc][yc].get("visited") == true){
//                                collectedItemsCount--;
//                            }
                            local_shopGrod[xc][yc] = 1;
                            System.out.println("found");
                            int curr_cost = pred_cost + 1;
                            markMap(xc, yc, solvedGrid, true, curr_cost, curr);
                            int[] temp = curr;
                            collectedItemsCount++;
                            path.add(backTrack(start, temp, solvedGrid));
                            if(collectedItemsCount == goalItemsCount){
                                return true;
                            }
                            initMapArray(dimX, dimY);
                            gridBFS(curr[0], curr[1], local_shopGrod, mapArray);
                            break;
//                        return true;
                        } else if (shopGrid[xc][yc] == 0)//Movable. Can't return here again so setting it to 'B' now
                        {
                            //System.out.println(xc+" "+yc);
                            int[] temp = {xc, yc};
                            if(((int)solvedGrid[xc][yc].get("cost") > pred_cost+1) || (((int)solvedGrid[xc][yc].get("cost"))== 1000)){
                                int curr_cost = pred_cost + 1;
                                markMap(xc, yc, solvedGrid, true, curr_cost, curr);//now BLOCKED
                            }
                            if(collectedItemsCount == goalItemsCount){
                                return true;
                            }
                            q.add(temp);//Adding current coordinates to the queue
                        }
//                    }
                }
                if(curr[1] < dimY-1){
                    int xc=curr[0];
                    int yc=curr[1]+1;
//                    if((boolean)solvedGrid[xc][yc].get("visited") == false || (int)solvedGrid[xc][yc].get("cost") > pred_cost+1) {

                        if (shopGrid[xc][yc] == 2)//Destination found
                        {
//                            if((boolean)solvedGrid[xc][yc].get("visited") == true){
//                                collectedItemsCount--;
//                            }
                            local_shopGrod[xc][yc] = 1;
                            System.out.println("found");
                            int curr_cost = pred_cost + 1;
                            markMap(xc, yc, solvedGrid, true, curr_cost, curr);
                            int[] temp = curr;
                            collectedItemsCount++;
                            path.add(backTrack(start, temp, solvedGrid));
                            if(collectedItemsCount == goalItemsCount){
                                return true;
                            }
                            initMapArray(dimX, dimY);

                            gridBFS(curr[0], curr[1], local_shopGrod, mapArray);
                            break;

//                        return true;
                        } else if (shopGrid[xc][yc] == 0)//Movable. Can't return here again so setting it to 'B' now
                        {
                            //System.out.println(xc+" "+yc);
                            int[] temp = {xc, yc};
                            if((int)solvedGrid[xc][yc].get("cost") > pred_cost+1 || (((int)solvedGrid[xc][yc].get("cost"))== 1000)){
                                int curr_cost = pred_cost + 1;
                                markMap(xc, yc, solvedGrid, true, curr_cost, curr);//now BLOCKED
                            }
                            if(collectedItemsCount == goalItemsCount){
                                return true;
                            }
                            q.add(temp);//Adding current coordinates to the queue
//                        }
                    }
                }
                if(curr[0] > 0){
                    int xc=curr[0]-1;
                    int yc=curr[1];
//                    if((boolean)solvedGrid[xc][yc].get("visited") == false || (int)solvedGrid[xc][yc].get("cost") > pred_cost+1){
                        if(shopGrid[xc][yc]==2)//Destination found
                        {
//                            if((boolean)solvedGrid[xc][yc].get("visited") == true){
//                                collectedItemsCount--;
//                            }
                            local_shopGrod[xc][yc] = 1;
                            System.out.println("found");
                            int curr_cost = pred_cost + 1;
                            markMap(xc, yc, solvedGrid, true, curr_cost, curr);
                            int[]temp=curr;
                            collectedItemsCount++;
                            path.add(backTrack(start, temp, solvedGrid));
                            if(collectedItemsCount == goalItemsCount){
                                return true;
                            }
                            initMapArray(dimX, dimY);

                            gridBFS(curr[0], curr[1], local_shopGrod, mapArray);
                            break;

//                        return true;
                        }
                        else if(shopGrid[xc][yc]==0)//Movable. Can't return here again so setting it to 'B' now
                        {
                            //System.out.println(xc+" "+yc);
                            int[]temp={xc,yc};
                            if((int)solvedGrid[xc][yc].get("cost") > pred_cost+1 || (((int)solvedGrid[xc][yc].get("cost"))== 1000)){
                                int curr_cost = pred_cost + 1;
                                markMap(xc, yc, solvedGrid, true, curr_cost, curr);//now BLOCKED
                            }
                            if(collectedItemsCount == goalItemsCount){
                                return true;
                            }
                            q.add(temp);//Adding current coordinates to the queue
                        }
//                    }
                }
                if(curr[1] > 0){
                    int xc=curr[0];
                    int yc=curr[1]-1;
//                    if((boolean)solvedGrid[xc][yc].get("visited") == false || (int)solvedGrid[xc][yc].get("cost") > pred_cost+1) {

                        if (shopGrid[xc][yc] == 2)//Destination found
                        {
//                            if((boolean)solvedGrid[xc][yc].get("visited") == true){
//                                collectedItemsCount--;
//                            }
                            local_shopGrod[xc][yc] = 1;
                            System.out.println("found");
                            int curr_cost = pred_cost + 1;
                            markMap(xc, yc, solvedGrid, true, curr_cost, curr);
                            int[] temp = curr;
                            collectedItemsCount++;
                            path.add(backTrack(start, temp, solvedGrid));
                            if(collectedItemsCount == goalItemsCount){
                                return true;
                            }
                            initMapArray(dimX, dimY);

                            gridBFS(curr[0], curr[1], local_shopGrod, mapArray);
                            break;

//                        return true;
                        } else if (shopGrid[xc][yc] == 0)//Movable. Can't return here again so setting it to 'B' now
                        {
                            //System.out.println(xc+" "+yc);
                            int[] temp = {xc, yc};
                            if((int)solvedGrid[xc][yc].get("cost") > pred_cost+1 || (((int)solvedGrid[xc][yc].get("cost"))== 1000)){
                                int curr_cost = pred_cost + 1;
                                markMap(xc, yc, solvedGrid, true, curr_cost, curr);//now BLOCKED
                            }
                            if(collectedItemsCount == goalItemsCount){
                                return true;
                            }
                            q.add(temp);//Adding current coordinates to the queue
                        }
//                    }
                }
            }
        }
        return false;
    }

    public ArrayList<int[]> backTrack(int[] start, int[] dest, Map<Object, Object>[][] solvedGrid){
        ArrayList<int[]> local_path = new ArrayList<int[]>();
        boolean is_in_start = false;
        int[] start_pos = new int[]{startX,startY};
        int[] start_pos_x = new int[]{0,1};
        int[] start_pos_y = new int[]{1,0};
        int[] start_pospred  = new int[]{1,0};
        int[] curr =dest;
//        int cur_cost = (int)solvedGrid[dest[0]][dest[1]].get("cost");
        while (!Arrays.equals(curr, start)){
            if(is_in_start){
                is_in_start = false;
                if(Arrays.equals(start_pos_x, start_pospred)){
                    curr = start_pos_y;
                }else {
                    curr = start_pos_x;
                }
            }else {
                local_path.add(curr);
                int[] temp = curr;
                curr = (int[]) solvedGrid[curr[0]][curr[1]].get("pred");
                if (Arrays.equals(curr, start_pos)) {
                    is_in_start = true;
                    start_pospred = temp;
                }
            }
//            cur_cost = (int)solvedGrid[curr[0]][curr[1]].get("cost");
        }
        local_path.add(curr);
        System.out.println(local_path);
        return local_path;
    }

    public void markMap(int x, int y, Map<Object, Object>[][] solvedGrid, boolean visited, int cost, int[] pred){
        solvedGrid[x][y].put("visited", visited);
        solvedGrid[x][y].put("cost", cost);
        solvedGrid[x][y].put("pred", pred);
    }

    public ArrayList<ArrayList<int[]>> getPath() {
        return path;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void searchForItems(int startX, int startY, int[][] shopGrid){
        int[][] local_shopGrod = new int[shopGrid.length][shopGrid[0].length];
        local_shopGrod = copyInitialGrid(local_shopGrod, shopGrid);
        ArrayList<int[]> local_path = pathExists(local_shopGrod, startX, startY, shopGrid);
        System.out.println("--------------------------------------------------------------------------------------------");
        collectedItemsCount ++;
        printPath(local_path);
        int newX = local_path.get(1)[0];
        int newY = local_path.get(1)[1];
//        local_path.remove(0);
//        local_path.remove(local_path.size()-1);
        path.add(local_path);

        while (collectedItemsCount < goalItemsCount) {

            int[][] local_shopGrod2 = new int[shopGrid.length][shopGrid[0].length];
            local_shopGrod2 = copyInitialGrid(local_shopGrod2, shopGrid);
            ArrayList<int[]> local_path2 = new ArrayList<int[]>();
            local_path2 = pathExists(local_shopGrod2, newX, newY, shopGrid);
            System.out.println("--------------------------------------------------------------------------------------------");
//            local_path2.remove(local_path2.size()-1);
            collectedItemsCount ++;
            newX = local_path2.get(1)[0];
            newY = local_path2.get(1)[1];
//            local_path2.remove(0);
//            local_path2.remove(local_path.size()-1);
            path.add(local_path2);
            printPath(local_path2);
        }
        System.out.print("1111111111111111111111111111111111111111111111111111111");
        System.out.println(path);

    }

    //use bfs to search for items
    public ArrayList<int[]> pathExists(int[][] matrix, int startX, int startY, int[][] shopGrid) {
        int N = matrix.length;

        List<Node> queue = new ArrayList<Node>();
        queue.add(new Node(startX, startY, new int[]{5,5}));
        ArrayList<int[]> local_path=null;

        List<Node> backtrack_queue = new ArrayList<Node>(); //queue to backtrack
        backtrack_queue.add(new Node(startX, startY, new int[]{5,5}));

        boolean pathExists = false;

        while(!queue.isEmpty()) {

            Node current = queue.remove(0);
//            backtrack_queue.add(current);
            if(matrix[current.x][current.y] == 2) {
                shopGrid[current.x][current.y] = 1;
                pathExists = true;
                int index = backtrack_queue.indexOf(current);
                local_path = backtrackFromQueue(backtrack_queue, index);
                break;
            }

            matrix[current.x][current.y] = 9; // mark as visited

            List<Node> neighbors = getNeighbors(matrix, current);
            queue.addAll(neighbors);
            backtrack_queue.addAll(neighbors);
        }

        return local_path;
    }

    public List<Node> getNeighbors(int[][] matrix, Node node) {
        List<Node> neighbors = new ArrayList<Node>();

        if(isValidPoint(matrix, node.x - 1, node.y)) {
            neighbors.add(new Node(node.x - 1, node.y, new int[]{node.x,node.y}));
        }

        if(isValidPoint(matrix, node.x + 1, node.y)) {
            neighbors.add(new Node(node.x + 1, node.y, new int[]{node.x,node.y}));
        }

        if(isValidPoint(matrix, node.x, node.y - 1)) {
            neighbors.add(new Node(node.x, node.y - 1, new int[]{node.x,node.y}));
        }

        if(isValidPoint(matrix, node.x, node.y + 1)) {
            neighbors.add(new Node(node.x, node.y + 1, new int[]{node.x,node.y}));
        }

        return neighbors;
    }

    public boolean isValidPoint(int[][] matrix, int x, int y) {
        return !(x < 0 || x >= matrix.length || y < 0 || y >= matrix.length) && (matrix[x][y] != 1);
    }
    public ArrayList<int[]> backtrackFromQueue(List<Node> queue, int goal_index){
//        System.out.print("queue start--------------------");
//        System.out.print(queue.get(0).x);
//        System.out.println(queue.get(0).y);
        ArrayList<int[]> local_path = new ArrayList<int[]>();
        Node current = queue.remove(goal_index);
        while (queue.indexOf(current) != 0){
//            local_path.add(current.pred);
            int pred_index = 1000;
            local_path.add(new int[]{current.x, current.y});

            for (int i = 0; i < queue.size(); i++) {
                Node tempNode = queue.get(i);
                if(current.pred[0]==tempNode.x && current.pred[1] == tempNode.y ){
                    pred_index = i;
                    local_path.add(current.pred);
                    break;
                }
            }
            current = queue.get(pred_index);
        }
        local_path = removeDuplicateFromPath(local_path);
        return local_path;
    }

    public ArrayList<int []>  removeDuplicateFromPath(ArrayList<int []> path){
        ArrayList<int []> newList = new ArrayList<>(path);
        for(int i = 0; i < path.size()-1; i+=1) {
            int[] cur =path.get(i);
            int[] next =path.get(i+1);
            System.out.println(Arrays.equals(cur, next));
            if (Arrays.equals(cur, next)){
                newList.remove(next);
            }
        }
        return newList;
    }

    public void printPath(ArrayList<int[]> path){
        for (int[] a : path){
            System.out.print(Arrays.toString(a));
            System.out.print(", ");
        }
        System.out.println(" ");
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public static void main(String[] args) {
//        MazeSolver mz = new MazeSolver(5, 5, 4);
//        Map<Object, Object>[][] mapArray = mz.getMapArray();
//        int[][] grid = {{3, 0, 0, 0, 0},
//                        {0, 2, 0, 2, 0},
//                        {0, 2, 0, 1, 0},
//                        {0, 2, 0, 1, 0},
//                        {0, 0, 0, 0, 0}};
//        boolean result =mz.gridBFS(0, 0, grid, mapArray);
//        mapArray = mz.getMapArray();
//        ArrayList<ArrayList<int[]>> path = mz.getPath();
//        for (int i = 0; i < path.size(); i++) {
//            for (int j = 0; j < path.get(i).size(); j++) {
//                System.out.print(Arrays.toString(path.get(i).get(j)));
//            }
//        }
//
//        System.out.println(Arrays.deepToString(mapArray).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//
//
//    }
}
