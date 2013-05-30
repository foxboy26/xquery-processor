package traverser;

import parser.ASTAbsDSlash;
import parser.ASTAbsSlash;
import parser.ASTAssign;
import parser.ASTComma;
import parser.ASTCondAnd;
import parser.ASTCondEmpty;
import parser.ASTCondEq;
import parser.ASTCondIs;
import parser.ASTCondOr;
import parser.ASTCondSome;
import parser.ASTDdot;
import parser.ASTDoc;
import parser.ASTDot;
import parser.ASTFLWR;
import parser.ASTFilterAnd;
import parser.ASTFilterEq;
import parser.ASTFilterIs;
import parser.ASTFilterNot;
import parser.ASTFilterOr;
import parser.ASTFilterParen;
import parser.ASTForClause;
import parser.ASTIn;
import parser.ASTLX;
import parser.ASTLetClause;
import parser.ASTNewtag;
import parser.ASTParen;
import parser.ASTRelComma;
import parser.ASTRelDSlash;
import parser.ASTRelFilter;
import parser.ASTRelSlash;
import parser.ASTReturnClause;
import parser.ASTSlash;
import parser.ASTStar;
import parser.ASTStart;
import parser.ASTString;
import parser.ASTTagName;
import parser.ASTText;
import parser.ASTVar;
import parser.ASTWhereClause;
import parser.SimpleNode;
import parser.XQueryParserVisitor;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.dom.TextImpl;
import org.apache.xerces.parsers.DOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class XQueryVisitor implements XQueryParserVisitor {
	
	Document root;
	

	@Override
	public Object visit(SimpleNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTStart node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtGetChild(0).jjtAccept(this, data);
		ArrayList<Node> init = new ArrayList<Node>();
		init.add(root);
		node.jjtGetChild(0).jjtAccept(this, init);
		return init;
	}

	@Override
	public Object visit(ASTDoc node, Object data) {
		// TODO Auto-generated method stub
		DOMParser parser = new DOMParser();
		try {
			parser.setFeature(
					"http://apache.org/xml/features/dom/defer-node-expansion",
					true);
			parser.setFeature("http://xml.org/sax/features/validation", false);
			parser.setFeature("http://xml.org/sax/features/namespaces", true);
			parser.setFeature(
					"http://apache.org/xml/features/validation/schema", true);
			parser.setFeature(
					"http://apache.org/xml/features/validation/schema-full-checking",
					false);
			String filePath = node.fileName.substring(1, node.fileName.length()-1);
			parser.parse(filePath);

		} catch (SAXNotRecognizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		root = parser.getDocument();
		node.childrenAccept(this, root);
		return root;
	}

	@Override
	public Object visit(ASTAbsSlash node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node>();
		SimpleNode second = (SimpleNode) node.jjtGetChild(1);
		
		for(Node n: (ArrayList<Node>) data){	
			NodeList nodelist = n.getChildNodes();
			int num = nodelist.getLength();
			for(int i = 0; i < num; ++i){
				Node cur = nodelist.item(i);
				if (cur instanceof ElementImpl)
				{
					resultSet.add(cur);
				}			
			}			
		}
		resultSet = (ArrayList<Node>) second.jjtAccept(this, resultSet);
		return resultSet;
	}

	@Override
	public Object visit(ASTAbsDSlash node, Object data) {
		// TODO Auto-generated method stub
//		node.childrenAccept(this, data);
		
		ArrayList<Node> resultSet = new ArrayList<Node>();
		SimpleNode second = (SimpleNode) node.jjtGetChild(1);
		
/*		if(second instanceof ASTStar){
			for(Node n: (ArrayList<Node>)data){
				NodeList children = n.getChildNodes();
				int l = children.getLength();
				for(int i = 0; i < l; ++i){
					resultSet.add(children.item(i));
				}		
			}			
		}
		else if(second instanceof ASTDdot){
			for(Node n: seq){	
				resultSet.add(n.getParentNode());	
			}
		}
		else if(second instanceof ASTDdot){
			for(Node n: seq){	
				resultSet.add(n.getParentNode());	
			}
		}
		SimpleNode leftMost = findLeftMost(node);
		
		String nodeName = (String) .tagName;*/
		

		for(Node n: (ArrayList<Node>) data){	
			resultSet = getDescendants(n, resultSet);
			/*NodeList decs = ((Element)n).getElementsByTagName(nodeName);
			int l = decs.getLength();
			for(int i = 0; i < l; ++i){
				if(!resultSet.contains(decs.item(i))){
					System.out.println(((Element)decs.item(i)).getTagName());
					resultSet.add(decs.item(i));
				}
			}		*/
		}
		resultSet = (ArrayList<Node>) second.jjtAccept(this, resultSet);
		return resultSet;
	}

	@Override
	public Object visit(ASTRelSlash node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node>();
		SimpleNode second = (SimpleNode) node.jjtGetChild(1);
		
		for(Node n: (ArrayList<Node>) data){	
			NodeList nodelist = n.getChildNodes();
			int num = nodelist.getLength();
			for(int i = 0; i < num; ++i){
				Node cur = nodelist.item(i);
				if (cur instanceof ElementImpl)
				{
					resultSet.add(cur);
				}			
			}			
		}
		resultSet = (ArrayList<Node>) second.jjtAccept(this, resultSet);
		return resultSet;
	}

	@Override
	public Object visit(ASTRelDSlash node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node>();
		SimpleNode second = (SimpleNode) node.jjtGetChild(1);

		for(Node n: (ArrayList<Node>) data){	
			resultSet = getDescendants(n, resultSet);	
		}
		resultSet = (ArrayList<Node>) second.jjtAccept(this, resultSet);
		return resultSet;
	}

	@Override
	public Object visit(ASTRelFilter node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node>();
		SimpleNode second = (SimpleNode) node.jjtGetChild(1);	
		resultSet = (ArrayList<Node>) second.jjtAccept(this, data);
		return resultSet;
	}

	@Override
	public Object visit(ASTComma node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet1 = new ArrayList<Node>();
		ArrayList<Node> resultSet2 = new ArrayList<Node>();
		return null;
	}

	@Override
	public Object visit(ASTRelComma node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTStar node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node>();
		for(Node n: (ArrayList<Node>)data){
			NodeList children = n.getChildNodes();
			int l = children.getLength();
			for(int i = 0; i < l; ++i){
				Node cur = children.item(i);
				if (cur instanceof ElementImpl)
				{
					resultSet.add(cur);
				}							
			}		
		}	
		node.childrenAccept(this, resultSet);
		return resultSet;
	}

	@Override
	public Object visit(ASTDot node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTDdot node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTText node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTParen node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTTagName node, Object data) {
		// TODO Auto-generated method stub
		return node.tagName;
	}

	@Override
	public Object visit(ASTFilterAnd node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTFilterOr node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTFilterEq node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTFilterIs node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTFilterParen node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTFilterNot node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTForClause node, Object data) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Object visit(ASTIn node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTLetClause node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTAssign node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTWhereClause node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTReturnClause node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTCondAnd node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTCondOr node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTCondEq node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTCondIs node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTCondEmpty node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTCondSome node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTVar node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTSlash node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTString node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTNewtag node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTFLWR node, Object data) {
		// TODO Auto-generated method stub
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTLX node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	SimpleNode findLeftMost(SimpleNode node){
		if(((SimpleNode) node).jjtGetNumChildren() != 0)
			return findLeftMost((SimpleNode)node.jjtGetChild(0));
		return node;
	}
	
/*	void addNodes(Node n, ArrayList<Node> result){
		NodeList nodelist = n.getChildNodes();
		int l = nodelist.getLength();
		for(int i = 0; i < l; ++i){
			result.add(nodelist.item(i));
			addNodes(nodelist.item(i), result);
		}
	}*/
	
	ArrayList<Node> getDescendants(Node n, ArrayList<Node> result){
		NodeList nodelist = n.getChildNodes();
		int num = nodelist.getLength();
		for(int i = 0; i < num; ++i){
			Node cur = nodelist.item(i);
			if (cur instanceof ElementImpl)
			{
				if(!result.contains(cur))
					result.add(cur);	
				result = getDescendants(cur, result);
			}			
		}
		return result;
	}
}
