import com.opencsv.CSVReader;
import com.sun.org.apache.xpath.internal.SourceTree;
import processing.core.PApplet;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class CSVtoGraph{


    public static ArrayList<Records> records = new ArrayList<>();
    public static TreeMap<Integer,Colors> fills = new TreeMap<>();
    public static double maxValue;

    public static void main(String[] args) throws IOException{

        ReadRecords();
        ReadFills();
        System.out.println(maxValue);


        PApplet.main("GUIbyP5");
    }




    private static void ReadRecords() throws IOException{

        List<String []> recordsList = FileRead("src/smp.csv","SJIS");

        int cnt = 0;

        for(String[] record : recordsList){

            cnt++;
            if(cnt > 2){
                double recordData[] = convertToDouble(record);
                records.add(new Records(recordData));

                if(cnt == 3) continue;

                //目盛り以外の行で最大値探索

                double max = Arrays.stream(recordData).max().getAsDouble();
                if(max > maxValue) maxValue = max;


            }

        }
    }

    private static double[] convertToDouble(String data[]){

        double doubleDat[] = new double[data.length];
        int t = 0;
        for(String d : data){
            doubleDat[t++] = Double.valueOf(d.trim());
        }
        return doubleDat;

    }

    private static void ReadFills() throws IOException{

        List<String []> fillsList = FileRead("src/ColorConvert_LtoRGB.csv","UTF-8");

        int cnt = 0;
        for(String[] record : fillsList){

            cnt++;
            if(cnt > 1){

                int wleng; //波長
                final int dleng = 5; //波長差
                float r,g,b;

                wleng = Integer.parseInt(record[0]);
                r = Float.parseFloat(record[1]);
                g = Float.parseFloat(record[2]);
                b = Float.parseFloat(record[3]);

                fills.put(wleng , new Colors(r,g,b));

                if(cnt != 2){

                    float _r,_g,_b;
                    _r = (r + fills.get(wleng - dleng * 2).r) / 2;
                    _g = (g + fills.get(wleng - dleng * 2).g) / 2;
                    _b = (b + fills.get(wleng - dleng * 2).b) / 2; //前後の値の平均をとる

                    fills.put(wleng - dleng, new Colors(r,g,b)); //1の位が5の波長を追加
                }
            }

        }
        System.out.println(fills.toString());
    }

    private static List<String []> FileRead(String fileName,String format) throws IOException{
        FileReader fileReader = null;
        CSVReader  csvReader  = null;

        try {
            FileInputStream fileIS = new FileInputStream(fileName);
            InputStreamReader isReader = new InputStreamReader(fileIS, format);

            csvReader = new CSVReader(isReader);


            List<String[]> list = csvReader.readAll();
            return list;

        }catch(Exception e) {

            return null;

        }finally{

            if(fileReader != null)  fileReader.close();
            if(csvReader  != null)  csvReader.close();
        }
    }

}
