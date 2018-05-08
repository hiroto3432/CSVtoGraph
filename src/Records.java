public class Records {

    public double[] record;
    Records(double[] record){
        this.record = record;
    }

    public float getGraphLength(int t){
        return (float)record[t] * 2000;
    }
}
