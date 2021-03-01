import java.util.Stack;

public class StackSorting {
    public static Stack<Integer> merge(Stack<Integer> s, int n){
        if(s.peek()>n){
            return s;
        }
        int m = s.pop();
        merge(s, n);
        s.add(m);
        return s;
    }
    public static Stack<Integer> sort(Stack<Integer> s){
        if(s.size()==1){
            return s;
        }
        int n = s.pop();
        sort(s);
        return(merge(s, n));
    }
    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        s.add(5);
        s.add(0);
        s.add(2);
        s.add(6);
        s.add(1);
        s = sort(s);
        System.out.println(s);
    }
}
