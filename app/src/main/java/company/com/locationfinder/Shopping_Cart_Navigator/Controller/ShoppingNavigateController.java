package company.com.locationfinder.Shopping_Cart_Navigator.Controller;

import android.os.Build;
import android.support.annotation.RequiresApi;

import company.com.locationfinder.LocationUpdatingService;
import company.com.locationfinder.Shopping_Cart_Navigator.Model.Item;
import company.com.locationfinder.Shopping_Cart_Navigator.Model.ShoppingCart;
import company.com.locationfinder.Shopping_Cart_Navigator.Model.ShoppingMaze;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ShoppingNavigateController {

    List<Item> items;
    List<int[]> itemPositions = new ArrayList<>();
    private int[][] shopGrid;
    private int[][] initialGrid;
    ShoppingMaze sm = new ShoppingMaze(5, 5);
    public static float x_scale = 2;
    public static float y_scale = 2.5f;
    public static float x_slots = 5;
    public static float y_slots = 5;
    int startX = 0;
    int startY = 0;
    int LastUserPosX = 0;
    int LastUserPosY = 0;


    public ShoppingNavigateController() throws ParseException {
        ShoppingCart sc =new ShoppingCart();
        ItemLocationController iLoc = new ItemLocationController();
//        items = sc.getItems();
        itemPositions = iLoc.getItemLocation();
        getUserLocationByFloat(LocationUpdatingService.pointX, LocationUpdatingService.pointY);
        sm.addItemstoMazeFromDetectors(itemPositions, startX, startY);
        shopGrid = sm.getShopGrid();
        System.out.println(Arrays.deepToString(shopGrid).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }

    public int[][] getShopGrid() {
        return shopGrid;
    }

    public ArrayList<String> getItemList(){
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i).getCategory() + "("+ items.get(i).getName() + ")" + " - " + items.get(i).getPrice();
            list.add(item);
        }
        return list;
    }

    //calculate the path to next item
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<ArrayList<int []>> calculatePath(){
        MazeSolver mz = new MazeSolver(5, 5, itemPositions.size());
//        mz.gridBFS(startX, startY, shopGrid, mz.getMapArray());
        mz.searchForItems(startX, startY, shopGrid);
        ArrayList<ArrayList<int[]>> path = mz.getPath();
//        System.out.println(path);
//        addPathToGrid(path);
        return path;

    }

    //display the path in 2d grid
    public void addPathToGrid(ArrayList<int[]> path){
        for (int i = 0; i < path.size(); i++) {
            System.out.print(Arrays.toString(path.get(i)));
        }
        System.out.println("");
        removeLastUserIcon(shopGrid);
        shopGrid = sm.copyInitialGrid();
        for (int i = 0; i < path.size(); i++) {
            int xc = path.get(i)[0];
            int yc = path.get(i)[1];
            if(shopGrid[xc][yc] != 2) {
                shopGrid[xc][yc] = 4;

            }
        }
        shopGrid[path.get(0)[0]][path.get(0)[1]] = 3;
    }

    public void removeLastUserIcon(int[][] shopGrid){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (shopGrid[i][j] == 3){
                    shopGrid[i][j] = 0;
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) throws ParseException {
        ShoppingNavigateController sn = new ShoppingNavigateController();
        sn.calculatePath();
        System.out.println(Arrays.deepToString(sn.getShopGrid()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

    }

    public void getUserLocationByFloat(float pointX, float pointY){
        int[] loc = new int[2];
        float x_size = (float) x_scale/x_slots;
        float y_size = (float) y_scale/y_slots;
        if (pointX>0){
            pointX = pointX;
        }else {
            pointX = Math.abs(pointX);
        }
        int x_box = (int)(pointX/x_size);

        int y_box = (int)(pointY/y_size);
        loc = rotateCordinates(x_box, y_box);
        startX = loc[0];
        startY = loc[1];
        LastUserPosX =startX;
        LastUserPosY = startY;

        int[] newLoc = getValidLocation(new int[]{LastUserPosX, LastUserPosY});
        startX = newLoc[0];
        startY = newLoc[1];
        LastUserPosX = newLoc[0];
        LastUserPosY = newLoc[1];
    }

    public int[] rotateCordinates(int x, int y){
        int rotatedY = x;
        int rotatedX = 4 - y;
        return new int[]{rotatedX, rotatedY};
    }

    public int[] getValidLocation(int[] loc){
        int[] newLoc = null;
        if (loc[1] == 1 || loc[1] == 3){
            if(loc[0] == 1 || loc[0] == 2 || loc[0] == 3){
                int y = 0;
                if(loc[1] == 3){
                    y = 4;
                }
                newLoc = new int[]{loc[0], y};
                System.out.println("------------------------------------------");
                System.out.println(newLoc);

                return newLoc;
            }
        }
        return loc;
    }
}
