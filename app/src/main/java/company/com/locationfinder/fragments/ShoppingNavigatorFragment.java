package company.com.locationfinder.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;

import company.com.locationfinder.Shopping_Cart_Navigator.Controller.PathNavigateController;
import company.com.locationfinder.Shopping_Cart_Navigator.Controller.ShoppingNavigateController;
import company.com.locationfinder.R;
import company.com.locationfinder.Shopping_Cart_Navigator.View.Maze;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class ShoppingNavigatorFragment extends Fragment {

    ShoppingNavigateController sn =new ShoppingNavigateController();
    PathNavigateController pn = new PathNavigateController();
    ArrayList<ArrayList<String>> textPaths;
    ArrayList<ArrayList<int[]>> paths;
    private int pathIndex =0;

    TextToSpeech tts;
    String text;
    View RootView;
    ImageView image;

    public ShoppingNavigatorFragment() throws ParseException {
    }

    public static ShoppingNavigatorFragment newInstance() throws ParseException {
        ShoppingNavigatorFragment assetsFragment = new ShoppingNavigatorFragment();
        return assetsFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);


//        setContentView(R.layout.activity_sopping_navigator_view);



        ShoppingNavigateController sn = null;
        try {
            sn = new ShoppingNavigateController();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        paths = sn.calculatePath();
        System.out.println(paths);
        textPaths = pn.getDirectionList(paths);

        //text to speech
        tts=new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_path_assist, container, false);

        image =(ImageView) RootView.findViewById(R.id.imageView1);

        RootView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                try {
                    getPath();     //get path to the next item
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            loadActivity(sn);       //refresh the view to display path
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startNavigation();

        return RootView;
    }

    //start the path navigation after first tap
    public void startNavigation(){
        text = "tap the screen to go to the first item";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //refresh the view to display path
    public void loadActivity(ShoppingNavigateController sn) throws InterruptedException {

        int[][] grid = sn.getShopGrid();
        Bitmap[] bitmaps = {
                BitmapFactory.decodeResource(getResources(), R.drawable.box),
                BitmapFactory.decodeResource(getResources(), R.drawable.lines),
                BitmapFactory.decodeResource(getResources(), R.drawable.coin2),
                BitmapFactory.decodeResource(getResources(), R.drawable.here),
                BitmapFactory.decodeResource(getResources(), R.drawable.path2),
        };


        // Chance the 480 and 320 to match the screen size of your device
        Maze maze = new Maze(bitmaps, grid, 5, 5, 100, 100);

        Bitmap bitMap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);  //creates bmp
        bitMap = bitMap.copy(bitMap.getConfig(), true);     //lets bmp to be mutable
        Canvas canvas = new Canvas(bitMap);                 //draw a canvas in defined bmp

        Paint paint = new Paint();
        // smooths
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.5f);
        // opacity
        //p.setAlpha(0x80); //
        maze.drawMaze(canvas, 0, 0);
        image.setImageBitmap(bitMap);

    }

    //get the path to next item
    public void getPath() throws ParseException, InterruptedException {
        if(pathIndex < paths.size()) {
            sn.addPathToGrid(paths.get(pathIndex));
            System.out.println(paths);
            System.out.println(Arrays.deepToString(sn.getShopGrid()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
            loadActivity(sn);


            for(String dir: textPaths.get(pathIndex)){
                tts.speak(dir, TextToSpeech.QUEUE_FLUSH, null);
                TimeUnit.SECONDS.sleep(3);
            }
            pathIndex++;
            text = "tap the screen after collecting the item";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        }else{
            System.out.println("Collected all the items");
            text = "You have collected all the items";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
