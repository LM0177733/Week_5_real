//Lorcan Murray
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.*;
/**
 *This is the combined class for up to week 5 of labs
 * 
 * @author Lorcan Murray
 *
 * @param <T>
 */
public class RedBlackTree<T extends Comparable<T>> {
	/**
	 * Reference to the root of the tree
	 */
	public Node root;
	


	public void insert(T value){
		
		Node node = new Node(value); // Create the Node to add

		//Special case that cannot be handled recursively
		if ( root == null ) {
			root = node;
			node.nodeColourRed = false;
            return;
		}

		//Initially we start at the root. Each subsequent recursive call will be to a 
		//left or right subtree.
		insertRec(root, node);
		handleRedBlack(node);

	}

	/**
	 * 
	 * @param subTreeRoot The SubTree to insert into
	 * @param node The Node that we wish to insert
	 */
	protected void insertRec(Node subTreeRoot, Node node){

		//Note the call to the compareTo() method. This is only possible if our objects implement
		//the Comparable interface.
		if ( node.value.compareTo(subTreeRoot.value) < 0){
			System.out.println("sasdasd ");
System.out.println(root.value.compareTo(subTreeRoot.value) < 0);
			//This is our terminal case for recursion. We should be going left but there is 
			//no left node there so that is obviously where we must insert
			
			if ( subTreeRoot.left == null ){
				
				subTreeRoot.left = node;
				node.parent=subTreeRoot;
				
		
				System.out.println("Left parent "+node.parent.value);
				return; //return here is unnecessary
			}
			else{ // Note that this allows duplicates!
				
				//Now our new "root" is the left subTree
				
				
				insertRec(subTreeRoot.left, node);
				
			}
		}
		//Same logic for the right subtree
		else{
			if (subTreeRoot.right == null){
				subTreeRoot.right = node;
				node.parent=subTreeRoot;
				System.out.println("Right parent "+node.parent.value);
				
				return;
			}
			else{
				
				
				insertRec(subTreeRoot.right, node);
			}
		}

	}
	
	
	/**
	 * Should traverse the tree "in-order." See the notes
	 */
	public void inOrderTraversal()
	{
		//start at the root and recurse
		recInOrderTraversal(root);
	}
	
	public void preOrderTraversal()
	{
		//start at the root and recurse
		recPreOrderTraversal(root);
	}
	
	public void postOrderTraversal()
	{
		//start at the root and recurse
		recPostOrderTraversal(root);
	}
	
	/**
	 * This allows us to recursively process the tree "in-order". Note that it is private
	 * @param subTreeRoot
	 */
	protected  void recInOrderTraversal(Node subTreeRoot)
	{
		if(subTreeRoot == null) return;
		
		recInOrderTraversal(subTreeRoot.left);
		processNode(subTreeRoot);
		recInOrderTraversal(subTreeRoot.right);
	}
	
	protected void recPreOrderTraversal (Node subTreeRoot)
	{
		if(subTreeRoot == null) return;
		
		processNode(subTreeRoot);
		recPreOrderTraversal(subTreeRoot.left);
		recPreOrderTraversal(subTreeRoot.right);
	}
	
	protected void recPostOrderTraversal (Node subTreeRoot)
	{
		if(subTreeRoot == null) return;
		
		recPostOrderTraversal(subTreeRoot.left);
		recPostOrderTraversal(subTreeRoot.right);
		processNode(subTreeRoot);
	}
	
	/** 
	 * Do some "work" on the node - here we just print it out 
	 * @param currNode
	 */
	protected void processNode(Node currNode)
	{
		System.out.println(currNode.toString());
	}
	
	/**
	 * 
	 * @return The number of nodes in the tree
	 */
	public int countNodes()
	{
		return recCountNodes(root);
	}
	
	
	/**
	 * Note: This is a practical example of a simple usage of pre-order traversal
	 * @param subTreeRoot
	 * @return
	 */
	protected int recCountNodes(Node subTreeRoot)
	{
		if (subTreeRoot == null) return 0;
		
		//Look at the pre-order. "Count this node and THEN count the left and right 
		//subtrees recursively
		return 1 + recCountNodes(subTreeRoot.left) + recCountNodes(subTreeRoot.right);
	}
	
	public T FindMaximum()
	{
		return recFindMaximum(root);
	}
protected T recFindMaximum(Node subTreeRoot)
	{
		while (subTreeRoot.right==null) { 
			return subTreeRoot.value;
		}
		return recFindMaximum(subTreeRoot.right);
	}

	public T FindMinimum()
	{
		return recFindMinimum(root);
	}
protected T recFindMinimum(Node subTreeRoot)
	{
		if ( subTreeRoot.left == null ){
			return subTreeRoot.value; 
		}
		else
		{ 
			return recFindMinimum(subTreeRoot.left);
		}
	}




	public T find(T searchVal)
	{
		//start at the root and recurse
		return recFind(root, searchVal);
	}
	
	protected T recFind(Node subTreeRoot, T searchVal)
	{
		if(subTreeRoot == null) 
			return null;
         
		if(subTreeRoot.value.equals(searchVal))
		{
			return subTreeRoot.value;
		}
		else if(subTreeRoot.value.compareTo(searchVal) >0 )
		{
         return recFind(subTreeRoot.left, searchVal);
      }
      else
      {
		   return recFind(subTreeRoot.right, searchVal);
      }
	}

	public void RotateTreeLeft()
	{
		root=RotateSubTreeLeft(root);
	}
	
protected Node RotateSubTreeLeft(Node subtreeroot)
	{
		Node pivot =subtreeroot.right;
		Node temp =pivot.left;
		pivot.left =subtreeroot;
		subtreeroot.right=temp;
		return pivot;
	}

	public void RotateTreeRight()
	{
		root=RotateSubTreeRight(root);
	}
	
protected Node RotateSubTreeRight(Node subtreeroot)
	{
		Node pivot =subtreeroot.left;
		Node temp =pivot.right;
		
		pivot.right =subtreeroot;
		subtreeroot.left=temp;
		return pivot;
	}


void handleRedBlack(Node newNode)
{
//terminating case for "back" recursion - e.g. case 3 (video)
if(newNode == root)
{
	newNode.nodeColourRed = false;
	return;
}
Node uncle;
Node parent = newNode.parent;
Node grandParent = parent.parent;
//creating lineage
if (parent!=null&&parent.nodeColourRed) {
	newNode.nodeColourRed=false;
}
else if (parent!=null&&parent.nodeColourRed==false) {
	newNode.nodeColourRed=true;
}
if (grandParent!=null&&grandParent==root) {
	parent.nodeColourRed=true;
	newNode.nodeColourRed=false;
}
if (parent!=null&&grandParent!=null&&grandParent.nodeColourRed) {
	parent.nodeColourRed=false;
	newNode.nodeColourRed=true;
}
else if (parent!=null&&grandParent!=null&&grandParent.nodeColourRed==false) {
	parent.nodeColourRed=true;
	newNode.nodeColourRed=false;
}
//this is just changing the colour when necessary, really this is a boolean but
//it serves the same purpose as it is tied into whether or not the
//the node is red or not
if(parent.nodeColourRed)
{
//important that we figure out where the uncle is
//relative to the current node
if(uncleOnRightTree(newNode))
{
	uncle = getRightUncle(newNode);
}
else
{
	uncle = getLeftUncle(newNode);
}
//Now we need to check if x's uncle is RED (Grandparent must
//have been black)
//This is case 3 according to the video
//(https://www.youtube.com/watch?v=g9SaX0yeneU)
if((uncle != null) && (uncle.nodeColourRed&&grandParent!=root&&grandParent.nodeColourRed!=false))
{
//this case is not too bad.
//it involves recolouring and then recursing
//CODE OMITTED - it's only 4 lines!

	parent.nodeColourRed=false;
uncle.nodeColourRed=false;
grandParent.nodeColourRed=true;
handleRedBlack(newNode);

System.out.println("Yeah the uncle is coloured Red");
	
}
else if((uncle == null) || (uncle.nodeColourRed==false))
{
	if (parent==grandParent.left&&newNode==parent.left) {
	newNode.left_left=true;
	RotateSubTreeRight(grandParent);
	if (grandParent.nodeColourRed==false) {
		grandParent.nodeColourRed=true;
	}
	else if(grandParent.nodeColourRed==true) {
		grandParent.nodeColourRed=false;
	}
	if (parent.nodeColourRed==false) {
		parent.nodeColourRed=true;
	}
	else if(parent.nodeColourRed==true) {
		parent.nodeColourRed=false;
	}
	
	}
	//this is a left_left scenario so the uncle is black
	//so right rotation on grandfather of node
	//then swap colours with grandfather and parent
	else if (parent==grandParent.left&&newNode==parent.right) {
	newNode.left_right=true;
	RotateSubTreeLeft(parent);
	RotateSubTreeRight(grandParent);
	
	}
		//this is a left right scenario
	//so left rotation on parent of node
	//then you would use left left rotation
		else if (parent==grandParent.right&&newNode==parent.left) {
	newNode.right_right=true;
RotateSubTreeLeft(grandParent);
if (grandParent.nodeColourRed==false) {
		grandParent.nodeColourRed=true;
	}
	else if(grandParent.nodeColourRed==true) {
		grandParent.nodeColourRed=false;
	}
	if (parent.nodeColourRed==false) {
		parent.nodeColourRed=true;
	}
	else if(parent.nodeColourRed==true) {
		parent.nodeColourRed=false;
	}
	

	}
}
	//this is a right right scenario
	//so left rotation on grandfather of node
	//then swap colours with grandfather and parent
	else if (parent==grandParent.right&&newNode==parent.left) {
	newNode.right_left=true;
	RotateSubTreeLeft(grandParent);
	}
		//this is a right left scenario
	//so right rotation on parent of node
	//then you would use right right rotation
}
}


    protected Node getLeftUncle(Node newNode) {
		if(newNode.grandParent!=null){
		if(newNode.grandParent!=null&newNode.grandParent.left!=null)
		{
        return newNode.grandParent.left;
		}
		else{
		return null;
		}
	}
	return null;
    }
//This is just checking if grandparent exists then get the node on its left
    protected Boolean uncleOnRightTree(Node newNode) {
        if (newNode.parent==root.left) {
			return true;
		}
		else{
		return false;
		}
    }
//This is just checking if parent is on the left then uncle is on right

    protected Node getRightUncle(Node newNode) {
			if(newNode.grandParent!=null){	
		if(newNode.grandParent!=null&newNode.grandParent.right!=null)
		{
        return newNode.grandParent.right;
			}
		else{
		return null;
		}
    }
	return null;
	//This is just checking if grandparent exists then get the node on its right

}



	protected class Node {
		public T value; //value is the actual object that we are storing
		public Node left;
		public Node right;
        public Node parent;
		public Node grandParent;
		public Boolean nodeColourRed;
		public String colour="";
		public int left_left_value = 0;
		public int right_right_value=0;
		public boolean left_left;
		public boolean right_right;
		public boolean left_right;
		public boolean right_left;
	
		public Node(T value) {
			this.value = value;
			
		}
/*if (newNode.left_left_value>newNode.right_right_value+2) {
					newNode.left_left=true;
				}
				else if(newNode.right_right_value>newNode.left_left_value+2) {
					newNode.right_right=true;
				}/* */
		@Override
		public String toString() {
			if (nodeColourRed==false) {
				colour="Black";
			}
			else if(nodeColourRed)
			{colour="Red";}

				
			return "Node [value = " + value + " Colour= "+colour+" is it right right "+right_right+" is it left left "+left_left+"]";
		}
		
		

	}
	public static class GraphPanel extends JComponent {

    protected  static final int WIDE = 640;
    protected static final int HIGH = 480;
    protected static final int RADIUS = 35;
    protected static final Random rnd = new Random();
    protected ControlPanel control = new ControlPanel();
    protected int radius = RADIUS;
    protected Kind kind = Kind.Circular;
    protected List<DisplayNode> nodes = new ArrayList<DisplayNode>();
    protected List<DisplayNode> selected = new ArrayList<DisplayNode>();
    protected List<Edge> edges = new ArrayList<Edge>();
    protected List<Integer> insertionsInOrder = new ArrayList<Integer>();
    
    protected Point mousePt = new Point(WIDE / 2, HIGH / 2);
    protected Rectangle mouseRect = new Rectangle();
    protected boolean selecting = false;

    protected BinarySearchTreeViewCapable<Integer> myTree = new BinarySearchTreeViewCapable<Integer>();
    
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFrame f = new JFrame("RedBlackTree");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GraphPanel gp = new GraphPanel();
                f.add(gp.control, BorderLayout.NORTH);
                f.add(new JScrollPane(gp), BorderLayout.CENTER);

                //This will be set to the Insert Button (hitting [Enter] will invoke it)
                f.getRootPane().setDefaultButton(gp.control.newDefaultButton);
                f.pack();
                f.setLocationByPlatform(true);
                f.setVisible(true);
            }
        });
    }

    public GraphPanel() {
        this.setOpaque(true);

    }

    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDE, HIGH);
    }

    @Override
    public void paintComponent(Graphics g) {
    	

    	
    	//NB:
    	//We use an inOrderTraversal to find out and populate the 
    	//node positions (this is an overridden method in the subclass
    	myTree.inOrderTraversal();
    	
    	
        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Edge e : edges) {
            e.draw(g);
        }
        for (DisplayNode n : nodes) {
            n.draw(g);
        }

    	String nodeDisplayString = "Insertion Order of Nodes: ";
    	for(Integer currNodeVal: insertionsInOrder)
    	{
    		nodeDisplayString += (currNodeVal + ",");
    	}
    	
    	g.setColor(Color.BLACK);
    	g.setFont(new Font("TimesRoman", Font.BOLD, 16));
    	g.drawString(nodeDisplayString, 5, 20);
        
        //This is not really relevant to Red Black display
        if (selecting) {
            g.setColor(Color.darkGray);
            g.drawRect(mouseRect.x, mouseRect.y,
                mouseRect.width, mouseRect.height);
        }
    }

 
    //This is not really relevant to Red Black display
    protected  class MouseMotionHandler extends MouseMotionAdapter {

        Point delta = new Point();

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selecting) {
                mouseRect.setBounds(
                    Math.min(mousePt.x, e.getX()),
                    Math.min(mousePt.y, e.getY()),
                    Math.abs(mousePt.x - e.getX()),
                    Math.abs(mousePt.y - e.getY()));
                DisplayNode.selectRect(nodes, mouseRect);
            } else {
                delta.setLocation(
                    e.getX() - mousePt.x,
                    e.getY() - mousePt.y);
                DisplayNode.updatePosition(nodes, delta);
                mousePt = e.getPoint();
            }
            e.getComponent().repaint();
        }
    }

    public JToolBar getControlPanel() {
        return control;
    }

    protected class ControlPanel extends JToolBar {

 
        protected Action clearAll = new ClearAction("Clear");
        protected Action kind = new KindComboAction("Kind");       
        protected JTextField insertValue = new JTextField();
        //private Action redisplay = new ReDisplayAction("ReDisplay");
 
        //Our action needs to see the value in the textField.
        protected Action insert = new InsertAction("Insert", insertValue);
        
        
        protected JButton newDefaultButton = new JButton(insert);
        protected JComboBox<Kind> kindCombo = new JComboBox<Kind>();
        
        
        ControlPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setBackground(Color.lightGray);

            this.add(new JButton(clearAll));
            this.add(kindCombo);

            //Our Insert Button is the default
            this.add(newDefaultButton);
 
            insertValue.setColumns(5);
            this.add(insertValue);
            //this.add(new JButton(redisplay));

            JMenu subMenu = new JMenu("Kind");
            for (Kind k : Kind.values()) {
                kindCombo.addItem(k);
                subMenu.add(new JMenuItem(new KindItemAction(k)));
            }
 
            kindCombo.addActionListener(kind);
        }


        class KindItemAction extends AbstractAction {

            protected Kind k;

            public KindItemAction(Kind k) {
                super(k.toString());
                this.k = k;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                kindCombo.setSelectedItem(k);
                
            }
        }
    }

    
    protected class InsertAction extends AbstractAction {
    	protected JTextField insertVal;
        public InsertAction(String name, JTextField insertVal) {
            super(name);
            this.insertVal = insertVal; 

        }

        public void actionPerformed(ActionEvent e) {
        	
        	Integer valToInsert = Integer.valueOf(insertVal.getText().trim());
        	insertionsInOrder.add(valToInsert);
        	
        	myTree.insert(valToInsert);
        	myTree.computeNodePositions();

            repaint();
        }       	
    }
    
    protected class ClearAction extends AbstractAction {

        public ClearAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
        	
        	myTree.root = null;
        	myTree.myRoot = null;
            nodes.clear();
            edges.clear();
            repaint();
        }
    }
    
    


    protected  class KindComboAction extends AbstractAction {

        public KindComboAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            JComboBox combo = (JComboBox) e.getSource();
            kind = (Kind) combo.getSelectedItem();
            DisplayNode.updateKind(nodes, kind);
            repaint();
        }
    }

 

    /**
     * The kinds of node in a graph.
     */
    protected enum Kind {

        Circular, Rounded, Square;
    }

    /**
     * An Edge is a pair of Nodes.
     */
    protected static class Edge {

        protected DisplayNode n1;
        protected DisplayNode n2;

        public Edge(DisplayNode n1, DisplayNode n2) {
            this.n1 = n1;
            this.n2 = n2;
        }

        public void draw(Graphics g) {
            Point p1 = n1.getLocation();
            Point p2 = n2.getLocation();
            g.setColor(Color.darkGray);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * A DisplayNode represents a node in a graph.
     * This was originally called Node but I changed it to
     * avoid confusion with the Node class in the Tree
     */
    protected  static class DisplayNode {

        protected Point p; //has x,y coords to display at
        protected int r;
        protected Color color;
        protected Kind kind; //circle/rounded/square
        protected boolean selected = false; //not important for us
        protected Rectangle b = new Rectangle(); //not important
        
        protected Integer nodeValue; //key/value stored in node
        protected boolean hasNodeValue;

        /**
         * Construct a new node. This came with the code and had no
         * value/key 
         */
        public DisplayNode(Point p, int r, Color color, Kind kind) {
            this.p = p;
            this.r = r;
            this.color = color;
            this.kind = kind;
            hasNodeValue = false;
            setBoundary(b);
        }

        /**
         * Construct a new node. Overloaded to include a value
         */
        public DisplayNode(Point p, int r, Color color, Kind kind, Integer value) {
            this.p = p;
            this.r = r;
            this.color = color;
            this.kind = kind;
            this.nodeValue = value;
            hasNodeValue = true;
            setBoundary(b);
        }
        /**
         * Calculate this node's rectangular boundary.
         */
        protected void setBoundary(Rectangle b) {
            b.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
        }

        /**
         * Draw this node.
         */
        public void draw(Graphics g) {
            g.setColor(this.color);
            if (this.kind == Kind.Circular) {
                g.fillOval(b.x, b.y, b.width, b.height);
            } else if (this.kind == Kind.Rounded) {
                g.fillRoundRect(b.x, b.y, b.width, b.height, r, r);
            } else if (this.kind == Kind.Square) {
                g.fillRect(b.x, b.y, b.width, b.height);
            }
            
            //Now "draw" the key/value on top of the node shape
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.BOLD, 24));
            if(hasNodeValue)
            {
            	int numDigits = nodeValue.toString().length();
            	
            	g.drawString(nodeValue.toString(), p.x - BinarySearchTreeViewCapable.NodeConstants.X_CHAROFFSET * numDigits, p.y + BinarySearchTreeViewCapable.NodeConstants.Y_CHAROFFSET);
            }
            if (selected) {
                g.setColor(Color.darkGray);
                g.drawRect(b.x, b.y, b.width, b.height);
            }
        }
/*************************************************************************************************************************/
        
        /**
         * YOU CAN IGNORE THE REST OF THIS CLASS (irrelevant to RedBlack)
         */
        
        /**
         * Return this node's location.
         */
        public Point getLocation() {
            return p;
        }

        /**
         * Return true if this node contains p.
         */
        public boolean contains(Point p) {
            return b.contains(p);
        }

        /**
         * Return true if this node is selected.
         */
        public boolean isSelected() {
            return selected;
        }

        /**
         * Mark this node as selected.
         */
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        /**
         * Collected all the selected nodes in list.
         */
        public static void getSelected(List<DisplayNode> list, List<DisplayNode> selected) {
            selected.clear();
            for (DisplayNode n : list) {
                if (n.isSelected()) {
                    selected.add(n);
                }
            }
        }

        /**
         * Select no nodes.
         */
        public static void selectNone(List<DisplayNode> list) {
            for (DisplayNode n : list) {
                n.setSelected(false);
            }
        }

        /**
         * Select a single node; return true if not already selected.
         */
        public static boolean selectOne(List<DisplayNode> list, Point p) {
            for (DisplayNode n : list) {
                if (n.contains(p)) {
                    if (!n.isSelected()) {
                        DisplayNode.selectNone(list);
                        n.setSelected(true);
                    }
                    return true;
                }
            }
            return false;
        }

        /**
         * Select each node in r.
         */
        public static void selectRect(List<DisplayNode> list, Rectangle r) {
            for (DisplayNode n : list) {
                n.setSelected(r.contains(n.p));
            }
        }

        /**
         * Toggle selected state of each node containing p.
         */
        public static void selectToggle(List<DisplayNode> list, Point p) {
            for (DisplayNode n : list) {
                if (n.contains(p)) {
                    n.setSelected(!n.isSelected());
                }
            }
        }

        /**
         * Update each node's position by d (delta).
         */
        public static void updatePosition(List<DisplayNode> list, Point d) {
            for (DisplayNode n : list) {
                if (n.isSelected()) {
                    n.p.x += d.x;
                    n.p.y += d.y;
                    n.setBoundary(n.b);
                }
            }
        }

        /**
         * Update each node's radius r.
         */
        public static void updateRadius(List<DisplayNode> list, int r) {
            for (DisplayNode n : list) {
                if (n.isSelected()) {
                    n.r = r;
                    n.setBoundary(n.b);
                }
            }
        }

        /**
         * Update each node's color.
         */
        public static void updateColor(List<DisplayNode> list, Color color) {
            for (DisplayNode n : list) {
                if (n.isSelected()) {
                    n.color = color;
                }
            }
        }

        /**
         * Update each node's kind.
         */
        public static void updateKind(List<DisplayNode> list, Kind kind) {
            for (DisplayNode n : list) {
                if (n.isSelected()) {
                    n.kind = kind;
                }
            }
        }
    }

    
    
	public class BinarySearchTreeViewCapable<T extends Comparable<T>> extends RedBlackTree<T> 
    {
    	protected MyNode myRoot;
  
    	
    	public int totalNodes = 0;
    	public int maxHeight= 0;
    	
    	
        public int treeHeight(Node t){
    	if(t==null) return -1;
              else return 1 + max(treeHeight(t.left),treeHeight(t.right));
        }
        public int max(int a, int b){
    	  if(a>b) return a; else return b;
        }

        /**
         * This method uses an inorder traversal to figure out the relative x and y positions of
         * each node.
         */
        public void computeNodePositions() {
          int depth = 1;
          totalNodes = 0;
          nodes.clear();
          edges.clear();
          recInOrderTraversal(myRoot, depth);
        }

    	protected void recInOrderTraversal(MyNode subTreeRoot, int depth)
    	{
    		
    		if(subTreeRoot == null) return;
    		
    		recInOrderTraversal((MyNode)subTreeRoot.left, depth + 1);
    		processNode(subTreeRoot, depth);
    		recInOrderTraversal((MyNode)subTreeRoot.right, depth + 1);
    	}
   
    	/**
    	 * This performs the actual calculation of the relative x and y positions for
    	 * the node.
    	 * It creates a data structure necessary to display the node. 
    	 * It also creates an edge using the current node and the parent node.
    	 * @param node
    	 * @param depth
    	 */
    	//@Override
    	protected void processNode(MyNode node, int depth)
    	{
    		Color nodeColour = (node.nodeColourRed) ? Color.red : Color.black;
    		
    		node.xpos = totalNodes++;
    		node.ypos = depth;
    		Point p = new Point(NodeConstants.X_OFFSET + node.xpos * NodeConstants.X_OFFSET, NodeConstants.Y_OFFSET + node.ypos * NodeConstants.Y_OFFSET);
    		
    		DisplayNode currDisplayNode = new DisplayNode(p, 30, nodeColour, kind, (Integer)node.value);
    		node.refToDisplayNode = currDisplayNode;
    		
    		if(node.parent != null)
    		{
 
    			DisplayNode parentDisplayNode = ((MyNode)node.parent).refToDisplayNode;
    			Edge currEdge = new Edge(currDisplayNode, parentDisplayNode);
    			edges.add(currEdge);
    		}
    		nodes.add(currDisplayNode);
    	}
        
    	
    	@Override
    	public void insert(T value){
    		MyNode node = new MyNode(value); // Create the Node to add

    		
    		//Special case that cannot be handled recursively
    		if ( myRoot == null ) {
    			myRoot = node;
    			
    			//Remember that new nodes default to Red but
    			//the root must always be black
    			//node.nodeColourRed = false;
    			//return;
			
			node.nodeColourRed = false;
            return;
		
    		}
    		else
    		{

    			//Initially we start at the root. Each subsequent recursive call will be to a 
    			//left or right subtree.
    			super.insertRec(myRoot, node);
    		}
    		
    		super.root = myRoot;
    		//Now that we've inserted we need to make it Red-Black (if necessary)
    		super.handleRedBlack(node);
    		myRoot = (BinarySearchTreeViewCapable<T>.MyNode)root;
 
    		
    		
        	myTree.computeNodePositions(); //finds x,y positions of the tree nodes
        	myTree.maxHeight=myTree.treeHeight(myTree.root); //finds tree height for scaling y axis     
        	
    	}
    	
    	public final class NodeConstants
    	{
    		public static final int X_OFFSET = 100;
    		public static final int Y_OFFSET = 50;
    		
    		public static final int X_CHAROFFSET = 5;
    		public static final int Y_CHAROFFSET = 5;
   		
    	}
    	
    	
    	public class MyNode extends RedBlackTree<T>.Node
    	{
    		public int xpos;
    		public int ypos;
    		
    		public DisplayNode refToDisplayNode;
    		
    		
			public MyNode(T value) {
				super(value);
			}
			
			
			@Override
			public String toString()
			{
				return super.toString() + " xpos: " + xpos + " ypos: " + ypos;
			}
    		
    	}
    }
    
    /*public class MyInteger extends Integer implements Comparable<Integer>
    {

		@Override
		public int compareTo(java.lang.Integer arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
    	
    }*/
}
}