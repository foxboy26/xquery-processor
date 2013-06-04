package transformer;

import java.util.ArrayList;

public class Node {
	
	public final static int TAGNODE = 1;
	public final static int TEXTNODE = 2;
	
	String tagName;
	ArrayList<Node> children;
	Node parent;
	ArrayList<Node> pairs;
	boolean isReturn;
	int type;
	
	public Node() {
		this.tagName = "";
		children = new ArrayList<Node> ();
		this.type = TAGNODE;
		this.isReturn = false;
	}
	
	public Node(String tagName, int type) {
		this.tagName = tagName;
		this.children = new ArrayList<Node> ();
		this.pairs = new ArrayList<Node> ();
		this.type = type;
		this.isReturn = false;
	}
	
	public void addChild(Node child) {
		this.children.add(child);
		child.parent = this;
	}
	
	public void addPair(Node pair) {
		this.pairs.add(pair);
		pair.pairs.add(this);
	}
	
	public int numOfChildren() {
		return this.children.size();
	}
	
	public boolean hasChildren() {
		return this.children != null && this.numOfChildren() > 0;
	}
	
	private void dumpHelp(int level) {
		for (int i = 0; i < level; i++)
			System.out.print("  ");
		
		System.out.println(this.tagName + ((this.isReturn)? "[return]" : ""));
		for (Node n : this.children)
			n.dumpHelp(level + 1);
	}
	
	public void dump() {
		dumpHelp(0);
	}
	
	public ArrayList<Node> getDescendents(){
		ArrayList<Node> decs = new ArrayList<Node>();
		decs.add(this);
		if(!this.hasChildren()){
			return decs;
		}
		for(Node tmp: this.children){
			decs.addAll(tmp.getDescendents());
		}
		return decs;
	}
	
	public String toString() {
		return this.tagName;
	}
	
	static public void main(String[] args) {
		Node root = new Node("root", Node.TAGNODE);
		Node child = new Node("a", Node.TAGNODE);
		root.addChild(child);
		root.addChild(new Node("b", Node.TAGNODE));
		root.addChild(new Node("c", Node.TAGNODE));
		child.addChild(new Node("aa", Node.TAGNODE));
		child.addChild(new Node("bb", Node.TAGNODE));
		child.isReturn = true;
		//root.dump();
		System.out.println(root.getDescendents());
	}
}
