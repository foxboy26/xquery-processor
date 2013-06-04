package transformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.dom.TextImpl;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

import parser.ASTStart;
import parser.XQueryParser;
import parser.XQueryParserVisitor;

public class Transformer {
	
	
		
		
		public ArrayList<ArrayList<Node>> getPartitions(Node node){
			ArrayList<ArrayList<Node>> res = new ArrayList<ArrayList<Node>>();
			ArrayList<ArrayList<Node>> partitions = new ArrayList<ArrayList<Node>>();
			ArrayList<Node> curPart = node.getDescendents();
			res.add(curPart);
			if(node.isReturn){		
				return res;
			}
			
			if(!node.hasChildren()){
				return res;
			}
				
			
			for(Node n: node.children){
				ArrayList<Node> nodelist = n.getDescendents();
				if(isPartition(nodelist)){
					partitions.addAll(getPartitions(n));
				}
				else
					return res;
			}
			return partitions;							
		}
	
		private ArrayList<Node> getPartition(Node node){
			ArrayList<Node> nodelist = node.getDescendents();
						
			if(isPartition(nodelist))
				return nodelist;
			else
				return null;			
		}
		
		private boolean isPartition(ArrayList<Node> nodelist){
			for(Node n: nodelist){
				ArrayList<Node> pairs = n.pairs;
				for(Node m: pairs){
					if(nodelist.contains(m));
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
				t = new XQueryParser(new FileInputStream(new File("rewrite.txt")));
				ASTStart n = t.Start();
				n.dump(">");
				
				XQueryParserVisitor visitor = new RewriteVisitor();
				System.out.println(((RewriteVisitor)visitor).root);
				n.jjtAccept(visitor, null);
				System.out.println();
				
				((RewriteVisitor)visitor).root.dump();
				
			} catch (Exception e) {
				System.out.println("Oops.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
}
