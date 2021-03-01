import java.util.Stack;
public class ReverseStack {
    public static void put(Stack<Integer> s, int m){
        if(s.empty()){
            s.add(m);
            System.out.println(s);
            return;
        }
        int n = s.pop();
        put(s, m);
        s.add(n);
        return;
    }
    public static void sort(Stack<Integer> s){
        if(s.size()==1){
            return;
        }
        int m = s.pop();
        sort(s);
        put(s, m);
        return;
    }
    public static void main(String[] args) {
        Stack<Integer> s = new Stack<Integer>();
        s.add(5);
        s.add(4);
        s.add(3);
        s.add(2);
        s.add(1);
        System.out.println(s);
        sort(s);
        System.out.println(s);

        // put(s, 1);
        // System.out.println(s);
    }
}
