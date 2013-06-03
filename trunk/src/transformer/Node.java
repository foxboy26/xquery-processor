package transformer;

import java.util.ArrayList;

public class Node {
	
	public final static int TAGNODE = 1;
	public final static int TEXTNODE = 2;
	
	String tagName;
	ArrayList<Node> children;
	Node parent;
	ArrayList<Node> pairs;
	
	int type;
	
	public Node() {
		this.tagName = "";
		children = new ArrayList<Node> ();
		this.type = TAGNODE;
	}
	
	public Node(String tagName, int type) {
		this.tagName = tagName;
		this.children = new ArrayList<Node> ();
		this.pairs = new ArrayList<Node> ();
		this.type = type;
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
		
		System.out.println(this.tagName);
		for (Node n : this.children)
			n.dumpHelp(level + 1);
	}
	
	public void dump() {
		dumpHelp(0);
	}
	
	
	static public void main(String[] args) {
		Node root = new Node("root", Node.TAGNODE);
		Node child = new Node("a", Node.TAGNODE);
		root.addChild(child);
		root.addChild(new Node("b", Node.TAGNODE));
		root.addChild(new Node("c", Node.TAGNODE));
		child.addChild(new Node("aa", Node.TAGNODE));
		child.addChild(new Node("bb", Node.TAGNODE));

		root.dump();
	}
}
