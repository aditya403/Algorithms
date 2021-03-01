import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
public class sample {
    public static Stack<Integer> add(Stack<Integer> s, int m){
        if(s.peek()>=m){
            s.push(m);
            return s;
        }
        int n = s.pop();
        add(s, m);
        s.push(n);
        return s;
    }
    public static Stack<Integer> fun(Stack<Integer> s){
        if(s.size()==1){
            return s;
        }
        int m = s.pop();
        fun(s);
        
        return(add(s, m));
    }
    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        s.add(5);
        s.add(6);
        s.add(0);
        s.add(1);
        s.add(2);
        s = fun(s);
        System.out.print(s);
    }
}
