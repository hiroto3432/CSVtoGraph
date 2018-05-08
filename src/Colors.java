public class Colors {

    int r,g,b;

    Colors(float r,float g,float b){

        this.r = (int)(r * 255);
        this.g = (int)(g * 255);
        this.b = (int)(b * 255);

    }

    Colors(int r,int g,int b){

        this.r = r;
        this.g = g;
        this.b = b;

    }

}
