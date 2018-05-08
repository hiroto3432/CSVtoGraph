import processing.core.PApplet;


public class GUIbyP5 extends PApplet{

    private static int page = 1;

    private static final int DATA_COUNT = CSVtoGraph.records.get(0).record.length;
    private static final int BAR_WIDTH = 4;
    private static final int EDGE = 100;
    private static final int X_AXIS_EDGE = 80;
    private static final int Y_AXIS_EDGE = 100;
    private static final int WIDTH = DATA_COUNT * BAR_WIDTH;
    private static final int HEIGHT = 500;

    private static final float Y_AXIS_MAX = (float)CSVtoGraph.maxValue * 10 / 9; // Y軸最大値
    /* プロットの最大値が軸の最大値の90%となるように設定*/

    //private static final int WAVELENGTH_MIN = Integer.parseInt(CSVtoGraph.fills.keySet().toArray()[0].toString());

    private static final int WAVELENGTH_MIN = (int)CSVtoGraph.records.get(0).record[0];


    private static final Colors BLACK = new Colors(0,0,0);
    private static final Colors WHITE = new Colors(255,255,255);


    public void settings(){
        size(200,200);
    }

    public void setup(){

        textAlign(CENTER,CENTER);

        rectMode(CORNERS);

        background(BLACK);

        surface.setSize(WIDTH + EDGE * 2 ,HEIGHT + EDGE * 2);
    }

    public void draw(){

        background(BLACK);

        for(int t = 0; t < DATA_COUNT; t++){
            Colors WaveColor = CSVtoGraph.fills.get(WAVELENGTH_MIN + t*5);

            //if(WaveColor != null) fill(WaveColor);
            //else    fill(255,255,255);
            fill(WHITE);

            stroke(BLACK);
            strokeWeight(1);
            rect(EDGE + t * BAR_WIDTH,HEIGHT + EDGE - CSVtoGraph.records.get(page).getGraphLength(t), EDGE + (t+1) * BAR_WIDTH,HEIGHT + EDGE);
        }

        strokeWeight(2);
        stroke(WHITE);

        line(X_AXIS_EDGE,height - Y_AXIS_EDGE,width - X_AXIS_EDGE,height - Y_AXIS_EDGE);
        line(X_AXIS_EDGE , Y_AXIS_EDGE , X_AXIS_EDGE,height - Y_AXIS_EDGE);


        fill(WHITE);
        textSize(32);
        text(page,40,40);

        textSize(16);

        final int Y_MEMORY_INTERVAL = 5;
        final int MEMORY_Ypos = height - 70;
        final float MEMORY_ROTATION_ANGLE = -PI/3;
        for(int n=0; n < DATA_COUNT / Y_MEMORY_INTERVAL + 1;n++){

            pushMatrix();
            translate(EDGE + n * BAR_WIDTH * Y_MEMORY_INTERVAL , MEMORY_Ypos);
            rotate(MEMORY_ROTATION_ANGLE);
            text((int)CSVtoGraph.records.get(0).record[n * Y_MEMORY_INTERVAL] ,0,0);
            popMatrix();

        }

        final int X_MEMORY_PLOTS = 20;
        final int MEMORY_Xpos = 50;
        for(int n=0; n < X_MEMORY_PLOTS + 1; n++) {
            text(Y_AXIS_MAX / X_MEMORY_PLOTS * n , MEMORY_Xpos, height - EDGE - n * (HEIGHT/X_MEMORY_PLOTS));
        }

        if(keyPressed){
            switch (keyCode){
                case RIGHT:
                    if(page<CSVtoGraph.records.size()-1)   page++;
                    break;

                case LEFT:
                    if(page>1) page--;
                    break;
            }
        }

    }

    private void background(Colors c){
        background(c.r,c.g,c.b);
    }
    private void fill(Colors c){
        fill(c.r,c.g,c.b);
    }
    private void stroke(Colors c){
        stroke(c.r,c.g,c.b);
    }

}

