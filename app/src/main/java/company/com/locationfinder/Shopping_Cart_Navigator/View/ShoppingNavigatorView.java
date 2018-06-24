package company.com.locationfinder.Shopping_Cart_Navigator.View;//package com.beacon.shopping.assistant.Shopping_Cart_Navigator.View;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.speech.tts.TextToSpeech;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//
//import com.beacon.shopping.assistant.Shopping_Cart_Navigator.Controller.PathNavigateController;
//import com.beacon.shopping.assistant.Shopping_Cart_Navigator.Controller.ShoppingNavigateController;
//import com.beacon.shopping.assistant.Shopping_Cart_Navigator.R;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Locale;
//import java.util.concurrent.TimeUnit;
//
//public class ShoppingNavigatorView extends AppCompatActivity {
//    ShoppingNavigateController sn =new ShoppingNavigateController();
//    PathNavigateController pn = new PathNavigateController();
//    ArrayList<ArrayList<String>> textPaths;
//    ArrayList<ArrayList<int[]>> paths;
//    private int pathIndex =0;
//
//    TextToSpeech tts;
//    String text;
//
//    public ShoppingNavigatorView() throws ParseException {
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sopping_navigator_view);
//
//        RelativeLayout lay = (RelativeLayout) findViewById(R.id.activity_sopping_navigator_view);
//        lay.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                try {
//                    getPath();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        ShoppingNavigateController sn = null;
//        try {
//            sn = new ShoppingNavigateController();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        paths = sn.calculatePath();
//        textPaths = pn.getDirectionList(paths);
//
//        //text to speech
//        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if(status != TextToSpeech.ERROR) {
//                    tts.setLanguage(Locale.UK);
//                }
//            }
//        });
//
//        try {
//            loadActivity(sn);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        startNavigation();
//
//
//    }
//    public void startNavigation(){
//        text = "tap the screen to go to the first item";
//        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void loadActivity(ShoppingNavigateController sn) throws InterruptedException {
//
//        ImageView image=(ImageView)findViewById(R.id.imageView1);
//
//        int[][] grid = sn.getShopGrid();
//        Bitmap[] bitmaps = {
//                BitmapFactory.decodeResource(getResources(), R.drawable.box),
//                BitmapFactory.decodeResource(getResources(), R.drawable.lines),
//                BitmapFactory.decodeResource(getResources(), R.drawable.coin2),
//                BitmapFactory.decodeResource(getResources(), R.drawable.here),
//                BitmapFactory.decodeResource(getResources(), R.drawable.path2),
//        };
//
//
//        // Chance the 480 and 320 to match the screen size of your device
//        Maze maze = new Maze(bitmaps, grid, 5, 5, 100, 100);
//
//        Bitmap bitMap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);  //creates bmp
//        bitMap = bitMap.copy(bitMap.getConfig(), true);     //lets bmp to be mutable
//        Canvas canvas = new Canvas(bitMap);                 //draw a canvas in defined bmp
//
//        Paint paint = new Paint();
//        // smooths
//        paint.setAntiAlias(true);
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(4.5f);
//        // opacity
//        //p.setAlpha(0x80); //
//        maze.drawMaze(canvas, 0, 0);
//        image.setImageBitmap(bitMap);
//
//    }
//
//    public void getPath() throws ParseException, InterruptedException {
//        if(pathIndex < paths.size()) {
//            sn.addPathToGrid(paths.get(pathIndex));
//            System.out.println(paths);
//            System.out.println(Arrays.deepToString(sn.getShopGrid()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//            loadActivity(sn);
//
//
//            for(String dir: textPaths.get(pathIndex)){
//                tts.speak(dir, TextToSpeech.QUEUE_FLUSH, null);
//                TimeUnit.SECONDS.sleep(3);
//            }
//            pathIndex++;
//            text = "tap the screen after collecting the item";
//            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//
//        }else{
//            System.out.println("Collected all the items");
//            text = "You have collected all the items";
//            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//        }
//    }
//
//
//
//}