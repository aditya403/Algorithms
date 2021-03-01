class numbers{
    public static void recurse(int n){
        if(n==1){
            System.out.println(n);
            return;
        }
        System.out.println(n);
        recurse(n-1);
        System.out.println(n);
    }
    public static void main(String[] args) {
        recurse(8);
    }
}