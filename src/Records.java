import java.util.Arrays;

public class Records {

    public double[] record;
    Records(double[] record){
        this.record = record;
    }

    public float getGraphLength(int t){
        if(DispMenu.mode == 1) return (float)record[t]/(float)CSVtoGraph.maxValue * GUIbyP5.HEIGHT * 9 / 10;
        if(DispMenu.mode == 2) return (float)record[t]/(getMaxValue() - getMinValue()) * GUIbyP5.HEIGHT * 9 / 10;
            return -1;
    }

    public float[] getArrayData(){
        float arr[] = new float[record.length];
        for(int n=0;n<arr.length;n++){
            arr[n] = (float)record[n];
        }
        return arr;
    }

    public float getMaxValue(){
        return (float)Arrays.stream(record).max().getAsDouble();
    }
    public float getMinValue(){
        return (float)Arrays.stream(record).min().getAsDouble();
    }
}
