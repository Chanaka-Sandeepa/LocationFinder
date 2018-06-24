package company.com.locationfinder.Shopping_Cart_Navigator.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 4/9/18.
 */

public class PathNavigateController {
    private Character currentDir = 'N';
    private int[] currentLoc =new int[]{4,0};

    public Character getDirection(int[] newLoc) {
        if(newLoc[0] > currentLoc[0]){
            currentDir = 'S';
            return 'S';
        }else if(newLoc[0] < currentLoc[0]){
            currentDir = 'N';
            return 'N';
        }else if(newLoc[1] > currentLoc[1]){
            currentDir = 'E';
            return 'E';
        }else{
            currentDir = 'W';
            return 'W';
        }
    }

    public ArrayList<Map<Character, Integer>> getDirection(ArrayList<int[]> path){
        ArrayList<Character> directions = new ArrayList<>();
        Collections.reverse(path);
        for (int i = 0; i < path.size(); i++) {
            directions.add(getDirection(path.get(i)));
            currentLoc = path.get(i);
        }
        if(directions.size()>1){
            directions.remove(0);
        }
        ArrayList<Map<Character, Integer>> steps = new ArrayList<>();
        steps = reduceDirectionList(directions);
        System.out.println(steps);

        return steps;
    }

    public ArrayList<Map<Character, Integer>> reduceDirectionList(ArrayList<Character> directions){
        ArrayList<Map<Character, Integer>> steps = new ArrayList<>();
        int currDrectionStepCount =1;
        System.out.println(directions);
        Character tempDir = directions.get(0);

        for (int i = 1; i < directions.size(); i++) {
            Map<Character, Integer> step = new HashMap<>();

            if(directions.get(i) == tempDir){
                currDrectionStepCount++;
            } else{
                step.put(tempDir, currDrectionStepCount);
                steps.add(step);
                tempDir = directions.get(i);
                currDrectionStepCount = 1;
            }
        }
        Map<Character, Integer> step = new HashMap<>();

        step.put(tempDir, currDrectionStepCount);
        steps.add(step);
        System.out.println(steps);
        return steps;

    }

    public ArrayList<ArrayList<String>> getDirectionList(ArrayList<ArrayList<int[]>> paths){
        ArrayList<ArrayList<Map<Character, Integer>>> dirList = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            System.out.print("pppppppppppppppppppppppppppppppp");
            System.out.println(paths.get(i));
            dirList.add(getDirection(paths.get(i)));
        }
        System.out.println(dirList);
        return convertToText(dirList);
    }

    public ArrayList<ArrayList<String>> convertToText (ArrayList<ArrayList<Map<Character, Integer>>> pathList){
        ArrayList<ArrayList<String>> textPath = new ArrayList<>();
        Character currentDir = 'N';
        for (ArrayList<Map<Character, Integer>> subPath : pathList){
            ArrayList<String> textSubPath = new ArrayList<>();
            for (Map<Character, Integer> dir : subPath){
                String step ="";
                switch (dir.keySet().iterator().next()){
                    case 'S':
                        switch (currentDir){
                            case 'S':
                                step = step.concat("go straight ");
                                break;
                            case 'E':
                                step = step.concat("turn right and go ");
                                break;
                            case 'N':
                                step = step.concat("turn back and go ");
                                break;
                            case 'W':
                                step = step.concat("turn left and go ");
                                break;
                        }
                        currentDir = 'S';
                        break;
                    case 'E':
                        switch (currentDir){
                            case 'S':
                                step = step.concat("turn left and go ");
                                break;
                            case 'E':
                                step = step.concat("go straight ");
                                break;
                            case 'N':
                                step = step.concat("turn right and go ");
                                break;
                            case 'W':
                                step = step.concat("turn back and go ");
                                break;
                        }
                        currentDir = 'E';
                        break;
                    case 'N':
                        switch (currentDir){
                            case 'S':
                                step = step.concat("turn back and go ");
                                break;
                            case 'E':
                                step = step.concat("turn left and go ");
                                break;
                            case 'N':
                                step = step.concat("go straight ");
                                break;
                            case 'W':
                                step = step.concat("turn right and go ");
                                break;
                        }
                        currentDir = 'N';
                        break;
                    case 'W':
                        switch (currentDir){
                            case 'S':
                                step = step.concat("turn right and go ");
                                break;
                            case 'E':
                                step = step.concat("turn back and go ");
                                break;
                            case 'N':
                                step = step.concat("turn left and go ");
                                break;
                            case 'W':
                                step = step.concat("go straight ");
                                break;
                        }
                        currentDir = 'W';
                        break;
                }
                System.out.println(step);
                int stepCount = dir.get(dir.keySet().iterator().next());
                step = step.concat(stepCount+ " meters");
                textSubPath.add(step);
            }
//            textSubPath.remove(textSubPath.size()-1);

            textPath.add(textSubPath);

        }
        return textPath;
    }

    public static void main(String[] args) {
        ArrayList<int[]> testPath1 = new ArrayList<>();
        int[] tempA = {4,3};
        int[] tempB = {4,2};
        int[] tempC = {4,1};
        int[] tempD = {4,0};
        int[] tempG = {3,0};
        int[] tempE = {2,0};
        int[] tempEF = {1,0};
        testPath1.add(tempA);
        testPath1.add(tempB);
        testPath1.add(tempC);
        testPath1.add(tempD);
        testPath1.add(tempG);
        testPath1.add(tempE);
        testPath1.add(tempEF);

        ArrayList<int[]> testPath2 = new ArrayList<>();
        int[] temp1 = {1,4};
        int[] temp2 = {2,4};
        int[] temp3 = {3,4};
        int[] temp4 = {4,4};
        int[] temp5 = {4,3};

        testPath2.add(temp1);
        testPath2.add(temp2);
        testPath2.add(temp3);
        testPath2.add(temp4);
        testPath2.add(temp5);


        ArrayList<ArrayList<int[]>> paths = new ArrayList<>();
        paths.add(testPath1);
        paths.add(testPath2);

        PathNavigateController pn = new PathNavigateController();
        pn.getDirectionList(paths);
    }

}
