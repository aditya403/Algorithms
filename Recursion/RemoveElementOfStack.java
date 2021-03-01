import java.util.Stack;

public class RemoveElementOfStack {
    public static void remove(Stack<Integer> s, int n){
        if(n==s.size()){
            s.pop();
            return;
        }
        int m = s.pop();
        remove(s, n);
        s.add(m);
        
    }
    public static void main(String[] args) {
        Stack<Integer> s = new Stack<Integer>();
        s.add(5);
        s.add(4);
        s.add(3);
        s.add(2);
        s.add(1);
        remove(s, 3);
        System.out.print(s);
    }
}
