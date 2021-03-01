import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArraySort {
    public static int[] remove(int[] a, int i){
        List<Integer> arrayList = IntStream.of(a).boxed().collect(Collectors.toList()); 
        int n = arrayList.remove(i);
        return(arrayList.stream().mapToInt(Integer::intValue).toArray());
    }
    public static int[] add(int[] a, int m){
        List<Integer> arraylist = IntStream.of(a).boxed().collect(Collectors.toList());
        arraylist.add(m);
        return(arraylist.stream().mapToInt(Integer::intValue).toArray());
    }
    public static void merge(int[] a, int n){
        if(a[a.length-1]<=n||a.length==0){
            add(a, n);
            return;
        }
        int m = a[a.length-1];
        remove(a, a.length-1);
        merge(a, n);
        List<Integer> arraylist = IntStream.of(a).boxed().collect(Collectors.toList());
        arraylist.add(m);
        a = arraylist.stream().mapToInt(Integer::intValue).toArray();

    }
    public static void sort(int[] a){
        if(a.length==1){
            return;
        }
        int n = a[a.length-1];
        a = remove(a, a.length-1);
        sort(a);
        merge(a, n);
    }
    public static void main(String[] args) {
        int[] a = {5, 0, 2, 6, 1};
        sort(a);
        for(int i =0; i<a.length; i++){
            System.out.println(a[i]);
        }
    }
}
