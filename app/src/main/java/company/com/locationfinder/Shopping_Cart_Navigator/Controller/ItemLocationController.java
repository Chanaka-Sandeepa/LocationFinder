package company.com.locationfinder.Shopping_Cart_Navigator.Controller;

import java.util.ArrayList;
import java.util.List;

import company.com.locationfinder.Constants;

/**
 * Created by root on 5/4/18.
 */

public class ItemLocationController {


    public ItemLocationController() {

    }

    //get the item locations from the constants
    public List<int[]> getItemLocation(){
        List<int[]> itemPosititons = new ArrayList<>();

        itemPosititons.add(Constants.item1_loc);
        itemPosititons.add(Constants.item2_loc);
        itemPosititons.add(Constants.item3_loc);
        System.out.println("itemposList" + itemPosititons);
        return itemPosititons;
    }

//    public int[] getLocationFromDistance(double distance){
//        int loc = (int)(distance/rangeBlockSize) +1;
//        System.out.println("distance" + distance);
//        System.out.println("locccc" + loc);
//        switch (loc){
//            case 1:
//                if(!posLock1) {
//                    posLock1 = true;
//                    return (new int[]{1, 1});
////                }else {
////                    return (new int[]{2, 1});
//                }
//            case 2:
//                if(!posLock2) {
//                    posLock2 = true;
//                    return (new int[]{2, 1});
////                }else{
////                    return new int[]{3,1};
//                }
//            case 3:
//                if(!posLock3) {
//                    posLock3 = true;
//                    return new int[]{3, 1};
////                }else{
////                    return new int[]{1,3};
//                }
//            case 4:
//                if(!posLock4) {
//                    posLock4 = true;
//                    return new int[]{1, 3};
////                }else {
////                    return new int[]{2,3};
//                }
//            case 5:
//                if(!posLock5) {
//                    posLock5 = true;
//                    return new int[]{2, 3};
////                }else {
////                    return new int[]{3,3};
//                }
//            default:
//                if(!posLock6) {
//                    posLock6 = true;
//                    return new int[]{3, 3};
////                }else {
////                    return new int[]{2, 3};
//                }
//        }
//        return null;
//    }

}
