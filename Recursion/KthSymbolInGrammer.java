

public class KthSymbolInGrammer {

    public static int power(int N, int P){ 
        if (P == 0) 
            return 1; 
        else
            return N * power(N, P - 1); 
    } 
    public static int find(int n, int k){
        if(n==1&&k==1){
            return 0;
        }
        int mid = power(2, n-1)/2;
        if(power(2, n-1)/2<k){
            if(find(n, k-mid)==1)
                return 0;
            else 
                return 1;
        }
        else{
            return find(n-1, k);
        }
    }
    public static void main(String[] args) {
        int n = 2;
        int k = 1;
        int ans = find(n, k);
        System.out.println(ans);
    }
}
