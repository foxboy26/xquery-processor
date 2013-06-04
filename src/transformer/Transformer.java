package transformer;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import parser.*;

public class Transformer {

	public boolean isRewrittable;
	ASTStart root;
	
	int[][] joinMarker;
	ArrayList<ArrayList<Node>> partitions;
	HashMap<String, SimpleNode> astContext;
	Node partitionRoot;

	public Transformer(ASTStart root) {
		this.isRewrittable = false;
		this.root = root;
	}
	
	public ASTStart rewrite() {
		
		XQueryParserVisitor visitor = new RewriteVisitor();
		root.jjtAccept(visitor, null);
		System.out.println();
		
		astContext = ((RewriteVisitor) visitor).astContext;
		partitionRoot = ((RewriteVisitor) visitor).root;
		
		partitions = this.getPartitions(partitionRoot);
		
		this.constructGraph();
		
		ASTStart newRoot = this.rewriteTree();
		
		return newRoot;
	}
	
	
	public ASTStart rewriteTree() {
		ASTStart newRoot = new ASTStart(0);
		
		ASTFLWR flwr = new ASTFLWR(0);
		ASTForClause forNode = rewriteFor();
//		ASTReturnClause returnNode = rewriteReturn();
		
		newRoot.jjtAddChild(flwr, 0);
		flwr.jjtSetParent(newRoot);
		newRoot.jjtAddChild(forNode, 0);
		
		return newRoot;
	}

	public ASTForClause rewriteFor() {
		
		int size = partitions.size();
		int[] isVisit = new int[size];
		
		ASTForClause forNode = new ASTForClause(0);
		
		if(size < 2){
			System.out.println("Unrewritable");
			return null;
		}
		
		int remain = size;
		
		int inNum = 0;
		
		while(remain != 0){
			ASTIn inNode = new ASTIn(0);
			
			forNode.jjtAddChild(inNode, inNum);
			
			ASTVar varNode = new ASTVar("$tuple");
			
			inNode.jjtAddChild(varNode, 0);
		
			ASTJoin join = constructJoin(isVisit, remain);
			
			inNode.jjtAddChild(inNode, 1);
		
			for(int i = 0; i < size; ++i){
				if(isVisit[i] == 0)
					remain++;
			}
			
			if(remain == 1){
				System.out.println("Unrewritable");
				return null;
			}		
		}
		return forNode;
	}
	
	public ASTWhereClause rewriteWhere() {
		return null;
	}
	
	public ASTReturnClause rewriteReturn() {
		
		return null;
	}
	
	private ASTJoin constructJoin(int[] isVisit, int remain) {		
		
		int size = partitions.size();
		
		int first = 0;
		
		for(int i: isVisit){
			if(i == 0){
				first = i;
				break;
			}
		}
		
		
		ASTFLWR flwr1 = constructFLWR(partitions.get(0));
		
		isVisit[first] = 1;
		
		--remain;
		
		int next = getNext(isVisit)[0];
		
		ASTFLWR flwr2 = constructFLWR(partitions.get(next));
		
		isVisit[next] = 1;
		--remain;
		
		ArrayList<Node> firstList = new ArrayList<Node>();
		ArrayList<Node> secList = new ArrayList<Node>();
	
		for(Node n: partitions.get(first)){
			ArrayList<Node> pairs = n.pairs;
			for(Node m: pairs){
				if(partitions.get(next).contains(m)){
					firstList.add(n);
					secList.add(m);				
				}
			}
		}
		
		ASTJoinList firstlist = new ASTJoinList(0);
		int index = 0;
		for(Node n: firstList){
			ASTTagName t = new ASTTagName(n.tagName.substring(1));
			firstlist.jjtAddChild(t, index++);
		}
		ASTJoinList seclist = new ASTJoinList(0);
		index = 0;
		for(Node n: secList){
			ASTTagName t = new ASTTagName(n.tagName.substring(1));
			seclist.jjtAddChild(t, index++);
		}
		
		ASTJoin join = new ASTJoin(0);
		
		join.jjtAddChild(flwr1, 0);
		join.jjtAddChild(flwr2, 1);
		join.jjtAddChild(firstlist, 2);
		join.jjtAddChild(seclist, 3);

		for(int i = 1; i < size; ++i){
			if(remain == 0)
				return join;
			
			
			if(getNext(isVisit) == null){
				return join;
			}
			else{
				first = getNext(isVisit)[1];
				next = getNext(isVisit)[0];
				--remain;
				
				ASTFLWR flwr = constructFLWR(partitions.get(next));
				
				ArrayList<Node> flist = new ArrayList<Node>();
				ArrayList<Node> slist = new ArrayList<Node>();
			
				for(Node n: partitions.get(first)){
					ArrayList<Node> pairs = n.pairs;
					for(Node m: pairs){
						if(partitions.get(next).contains(m)){
							flist.add(n);
							slist.add(m);				
						}
					}
				}
				
				ASTJoinList fList = new ASTJoinList(0);
				index = 0;
				for(Node n: flist){
					ASTTagName t = new ASTTagName(n.tagName.substring(1));
					fList.jjtAddChild(t, index++);
				}
				ASTJoinList sList = new ASTJoinList(0);
				index = 0;
				for(Node n: slist){
					ASTTagName t = new ASTTagName(n.tagName.substring(1));
					sList.jjtAddChild(t, index++);
				}
				
				ASTJoin newJoin = new ASTJoin(0);
				
				newJoin.jjtAddChild(join, 0);
				newJoin.jjtAddChild(flwr, 1);
				newJoin.jjtAddChild(fList, 2);
				newJoin.jjtAddChild(sList, 3);
				
				join = newJoin;						
				
			}
		}
		
		return join;
	}
	 
	
	private int[] getNext(int[] isVisit){
		int size = partitions.size();
		for(int i = 0; i < size; ++i){
			if(isVisit[i] == 0){
				for(int j = 0; j < size; ++j){
					if(joinMarker[i][j] == 1 && isVisit[j] == 1){
						int[] re= new int[2];
						re[0] = i;
						re[1] = j;
						return re;
					}
				}
			}
		}
		return null;
	}
	
	
	private ASTFLWR constructFLWR(ArrayList<Node> partition) {
		ASTFLWR FLWR = new ASTFLWR(0);

		ASTForClause forNode = constructFor(partition);
		ASTWhereClause whereNode = constructWhere(partition);
		ASTReturnClause returnNode = constructReturn(partition);

		FLWR.jjtAddChild(forNode, 0);
		if (whereNode.jjtGetNumChildren() != 0) {
			FLWR.jjtAddChild(whereNode, 1);
			FLWR.jjtAddChild(returnNode, 2);
		} else {
			FLWR.jjtAddChild(returnNode, 1);
		}
		return FLWR;
	}

	private ASTForClause constructFor(ArrayList<Node> nodelist) {

		ASTForClause forNode = new ASTForClause(0);

		int i = 0;
		for (Node n : nodelist) {
			String name = n.tagName;
			SimpleNode snode = astContext.get(name);
			forNode.jjtAddChild(snode, i);
			++i;
		}

		return forNode;
	}

	private ASTWhereClause constructWhere(ArrayList<Node> nodelist) {

		ASTWhereClause whereNode = new ASTWhereClause(0);

		int i = 0;
		for (Node n : nodelist) {
			if (n.type == Node.TEXTNODE) {
				String name = n.parent.tagName;
				ASTString stringNode = new ASTString(0);
				stringNode.strName = n.tagName;
				ASTVar varNode = new ASTVar(0);
				varNode.varName = name;
				ASTCondEq eqNode = new ASTCondEq(0);
				eqNode.jjtAddChild(varNode, 0);
				eqNode.jjtAddChild(stringNode, 1);

				whereNode.jjtAddChild(eqNode, i);
				++i;
			}
		}

		return whereNode;

	}

	private ASTReturnClause constructReturn(ArrayList<Node> nodelist) {

		ASTReturnClause returnNode = new ASTReturnClause(0);
		ASTNewtag newtagNode = new ASTNewtag(0);
		newtagNode.tagName = "tuple";
		returnNode.jjtAddChild(newtagNode, 0);

		ASTXQueryComma xcommaNode = new ASTXQueryComma(0);
		ASTXQueryComma curComma = new ASTXQueryComma(0);
		newtagNode.jjtAddChild(xcommaNode, 0);

		int size = nodelist.size();

		for (int i = 0; i < size - 1; ++i) {
			Node n = nodelist.get(i);

			if (n.isReturn) {
				String name = n.tagName;
				ASTNewtag newtag = new ASTNewtag(0);
				newtag.tagName = name.substring(1);
				ASTVar var = new ASTVar(0);
				var.varName = name;
				newtag.jjtAddChild(var, 0);
				curComma.jjtAddChild(newtag, 0);
				xcommaNode = new ASTXQueryComma(0);
				curComma.jjtAddChild(xcommaNode, 1);
				curComma = xcommaNode;
			}
		}
		Node n = nodelist.get(size - 1);
		String name = n.tagName;
		ASTNewtag newtag = new ASTNewtag(0);
		newtag.tagName = name.substring(1);
		ASTVar var = new ASTVar(0);
		var.varName = name;
		newtag.jjtAddChild(var, 0);
		curComma.jjtAddChild(newtag, 0);

		return returnNode;
	}

	/*
	 * private JoinParas getJoinParas(int i, int j){ JoinParas jp = new
	 * JoinParas(); ArrayList<Node> llist = partitions.get(i); ArrayList<Node>
	 * rlist = partitions.get(j);
	 * 
	 * for(Node n: llist){ if(n.isReturn) jp.firstForParas.add(n); }
	 * 
	 * for(Node n: rlist){ if(n.isReturn) jp.secondForParas.add(n); }
	 * 
	 * for(Node n : llist){ ArrayList<Node> pairs = n.pairs; for(Node m: pairs){
	 * if(rlist.contains(m)){ jp.firstJoinParas.add(n); jp.secondForParas.add(m);
	 * } } } return jp; }
	 */

	private void constructGraph() {
		int size = partitions.size();
		joinMarker = new int[size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = i + 1; j < size; ++j) {
				if (needJoin(partitions.get(i), partitions.get(j))) {
					joinMarker[i][j] = 1;
					joinMarker[j][i] = 1;
				}
			}
		}
	}

	public boolean needJoin(ArrayList<Node> llist, ArrayList<Node> rlist) {
		for (Node n : llist) {
			ArrayList<Node> pairs = n.pairs;
			for (Node m : pairs) {
				if (rlist.contains(m))
					return true;
			}
		}
		return false;
	}

	public void printPartition(ArrayList<ArrayList<Node>> partition) {
		for (ArrayList<Node> list : partition)
			System.out.println(list);
	}

	public ArrayList<ArrayList<Node>> getPartitions(Node node) {
		ArrayList<ArrayList<Node>> res = new ArrayList<ArrayList<Node>>();
		ArrayList<ArrayList<Node>> partitions = new ArrayList<ArrayList<Node>>();
		ArrayList<Node> curPart = node.getDescendents();
		res.add(curPart);
		if (node.isReturn) {
			return res;
		}

		if (!node.hasChildren()) {
			return res;
		}

		for (Node n : node.children) {
			/*
			 * ArrayList<Node> nodelist = n.getDescendents();
			 * if(isPartition(nodelist)){ partitions.addAll(getPartitions(n)); } else
			 * return res;
			 */
			partitions.addAll(getPartitions(n));
		}
		return partitions;
	}

	/*
	 * private static ArrayList<Node> getPartition(Node node){ ArrayList<Node>
	 * nodelist = node.getDescendents();
	 * 
	 * if(isPartition(nodelist)) return nodelist; else return null; }
	 */

	private boolean isPartition(ArrayList<Node> nodelist) {
		for (Node n : nodelist) {
			ArrayList<Node> pairs = n.pairs;
			for (Node m : pairs) {
				if (nodelist.contains(m))
					return false;
			}
		}
		return true;
	}

	public static void main(String args[]) {
		// System.out.println("Reading from standard input...");
		// String str = "doc(\"test.xml\")//book";
		XQueryParser t;
		try {
			t = new XQueryParser(new FileInputStream(new File("join")));
			ASTStart n = t.Start();
			n.dump(">");

			// construct tree for partition.
			XQueryParserVisitor visitor = new RewriteVisitor();
			n.jjtAccept(visitor, null);
			System.out.println();

			XQueryParserVisitor printer = new PrinterVisitor();
			n.jjtAccept(printer, null);

			// Node root = ((RewriteVisitor)visitor).root;
			// root.dump();
			// ArrayList<ArrayList<Node>> partition = Transformer.getPartitions(root);

			// Transformer.printPartition(partition);

		} catch (Exception e) {
			System.out.println("Oops.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
