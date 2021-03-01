
class Node 
{ 
	int key; 
	Node left, right; 

	public Node(int item) 
	{ 
		key = item; 
		left = right = null; 
	} 
} 
class BinaryTree 
{ 
	// Root of Binary Tree 
	Node root; 

	// Constructors 
	BinaryTree(int key) 
	{ 
		root = new Node(key); 
	} 

	BinaryTree() 
	{ 
		root = null; 
    } 
    
    public static int height(Node node){
        if(node==null){
            return 0;
        }
        int lh = height(node.left);
        int rh = height(node.right);
        int ans = 1+ Math.max(lh, rh);
        return ans;
    }

	public static void main(String[] args) 
	{ 
		BinaryTree tree = new BinaryTree(); 

		tree.root = new Node(1); 

		tree.root.left = new Node(2); 
		tree.root.right = new Node(3);
		tree.root.left.left = new Node(4); 
        System.out.println(height(tree.root));
	} 
} 



