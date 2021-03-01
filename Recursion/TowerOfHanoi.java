import java.io.ObjectOutputStream.PutField;
import java.util.Stack;

public class TowerOfHanoi{
    static int count = 0;
    public static void put(int s, int h, int d, int n){
        if(n==1){
            //System.out.println("move " + n + " from " + s +  " to " + d );
            count++;
            return;
        }
        put(s, d, h, n-1);
        //System.out.println("move " + n + " from " + s +  " to " + d);
        count++;
        put(h, s, d, n-1);
        return;
    } 
    public static void main(String[] args) {
        int s = 1;
        int d = 3;
        int h = 2;
        int n = 30;
        put(s, h, d, n);
        System.out.println(count);
    }
}
