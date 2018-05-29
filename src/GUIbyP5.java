import processing.core.PApplet;

import java.awt.*;


public class GUIbyP5 extends PApplet implements ColorDatas{

    private static final int BAR_WIDTH = 4;
    private static final int EDGE = 100;
    private static final int X_AXIS_EDGE = 80;
    private static final int Y_AXIS_EDGE = 100;
    private static final int WIDTH = DispMenu.DATA_COUNT * BAR_WIDTH;
    protected static final int HEIGHT = 500;


    private static final int WAVELENGTH_MIN = (int)CSVtoGraph.records.get(0).record[0];

    private static Records dif_records;
    private static int y_STAND_POS;


    public void settings(){
        size(200,200);
    }

    public void setup(){

        textAlign(CENTER,CENTER);

        rectMode(CORNERS);

        background(BLACK);

        surface.setSize(WIDTH + EDGE * 2 ,HEIGHT + EDGE * 2);
    }

    public void draw() throws NullPointerException{

        background(BLACK);

        /*スペクトル描画*/


        for(int t = 0; t < DispMenu.DATA_COUNT; t++){
             Colors WaveColor = CSVtoGraph.fills.get(WAVELENGTH_MIN + t*5);

            //if(WaveColor != null) fill(WaveColor);
            //else    fill(255,255,255);
            fill(WHITE);

            stroke(BLACK);
            strokeWeight(1);
            if(DispMenu.mode == 1) rect(EDGE + t * BAR_WIDTH,HEIGHT + EDGE - CSVtoGraph.records.get(DispMenu.page).getGraphLength(t), EDGE + (t+1) * BAR_WIDTH,HEIGHT + EDGE);
            if(DispMenu.mode == 2 && dif_records != null){
                rect(EDGE + t * BAR_WIDTH,y_STAND_POS - dif_records.getGraphLength(t), EDGE + (t+1) * BAR_WIDTH,y_STAND_POS);
            }
        }


        /*軸描画*/

        strokeWeight(2);
        stroke(WHITE);

        line(X_AXIS_EDGE,height - Y_AXIS_EDGE,width - X_AXIS_EDGE,height - Y_AXIS_EDGE);
        line(X_AXIS_EDGE , Y_AXIS_EDGE , X_AXIS_EDGE,height - Y_AXIS_EDGE);

        if(DispMenu.mode == 2){
            line(X_AXIS_EDGE, y_STAND_POS, width - X_AXIS_EDGE, y_STAND_POS);
        }


        /*ページ数描画 */

        fill(WHITE);
        textSize(32);
        text(DispMenu.page,40,40);



        /*X軸目盛り描画*/

        final int X_SCALE_INTERVAL = 5; //X軸目盛り表示間隔
        final int X_SCALE_Ypos = height - 70; //X軸目盛り表示位置
        final float X_SCALE_ROTATION_ANGLE = -PI/3; //X軸目盛り回転角度

        textSize(16);
        for(int n=0; n < DispMenu.DATA_COUNT / X_SCALE_INTERVAL + 1;n++){

            pushMatrix();
            translate(EDGE + n * BAR_WIDTH * X_SCALE_INTERVAL , X_SCALE_Ypos);
            rotate(X_SCALE_ROTATION_ANGLE);
            text((int)CSVtoGraph.records.get(0).record[n * X_SCALE_INTERVAL] ,0,0);
            popMatrix();

        }


        /*Y軸目盛描画*/

        final int Y_SCALE_PLOTS = 20; //Y軸目盛りプロット数
        final int Y_SCALE_Xpos = 50; //Y軸目盛り表示位置

        final float Y_AXIS_MAX = (float)CSVtoGraph.maxValue * 10 / 9; // Y軸最大値
        /* プロットの最大値が軸の最大値の90%となるように設定*/

        for(int n=0; n < Y_SCALE_PLOTS + 1; n++) {
            if(DispMenu.mode == 1) text(Y_AXIS_MAX / Y_SCALE_PLOTS * n , Y_SCALE_Xpos, height - EDGE - n * (HEIGHT/Y_SCALE_PLOTS));
            if(DispMenu.mode == 2) {
                float stand = (dif_records.getMaxValue() - dif_records.getMinValue())/Y_SCALE_PLOTS;

                text(dif_records.getMinValue() + (stand * n) , Y_SCALE_Xpos, height - EDGE - n * (HEIGHT/Y_SCALE_PLOTS));
            }
        }


        /*キーボード処理*/

        if(keyPressed){
            switch (keyCode){
                case RIGHT:
                    if(DispMenu.page<CSVtoGraph.records.size()-1)   DispMenu.page++;
                    break;

                case LEFT:
                    if(DispMenu.page>1) DispMenu.page--;
                    break;

                case UP:
                    DispMenu.mode = 2;

                    break;
            }
            DispMenu.setPage();
        }

    }

    protected static void createDiffData(){

        float list1[] = CSVtoGraph.records.get(Integer.parseInt(DispMenu.t1_diff.getText())).getArrayData();
        float list2[] = CSVtoGraph.records.get(Integer.parseInt(DispMenu.t2_diff.getText())).getArrayData();
        double diff[] = new double[list1.length];

        for(int n=0;n<diff.length;n++){
            diff[n] = (double)list1[n] - (double)list2[n];
        }

        dif_records = new Records(diff);

        y_STAND_POS = Y_AXIS_EDGE + (int)(dif_records.getMaxValue()/(dif_records.getMaxValue() - dif_records.getMinValue()) * HEIGHT);
    }
    public void background(Colors c){
        background(c.r,c.g,c.b);
    }
    public void fill(Colors c){
        fill(c.r,c.g,c.b);
    }
    public void stroke(Colors c){
        stroke(c.r,c.g,c.b);
    }

}

