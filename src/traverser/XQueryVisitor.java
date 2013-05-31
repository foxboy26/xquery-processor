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
import java.util.Collection;
import java.util.HashSet;

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

	@Override
	public Object visit(SimpleNode node, Object data) {
		// TODO Auto-generated method stub
		System.err.println("you should not come here!");
		System.exit(1);
		return null;
	}

	@Override
	public Object visit(ASTStart node, Object data) {
		// TODO Auto-generated method stub
		int numOfChild = node.jjtGetNumChildren();
		if (numOfChild == 0) {
			System.err.println("[Start]: error");
			System.exit(1);
		}
		
	  return node.jjtGetChild(0).jjtAccept(this, data);
	}

	@Override
	public Object visit(ASTDoc node, Object data) {
		// TODO Auto-generated method stub
		return root(node.fileName);
	}

	@Override
	// doc(filename)/rp
	public Object visit(ASTAbsSlash node, Object data) {
		// TODO Auto-generated method stub
		int numOfChild = node.jjtGetNumChildren();
		if (numOfChild != 2) {
			System.err.println("[AbsSlash] Error: AbsSlash must have 2 children.");
			System.exit(1);
		}
				
		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);
		
		data = left.jjtAccept(this, data);
		data = right.jjtAccept(this, data); 
		
		return data;
	}

	@Override
	public Object visit(ASTAbsDSlash node, Object data) {
		// TODO Auto-generated method stub
		
		ArrayList<Node> resultSet = new ArrayList<Node>();
		data = node.jjtGetChild(0).jjtAccept(this, data);

		resultSet.addAll((ArrayList<Node>) data);
		for(Node n: (ArrayList<Node>) data){
			ArrayList<Node> descendants = new ArrayList<Node> ();
			descendants = getDescendants(n, descendants);
			resultSet.addAll(descendants);
		}
		
		resultSet = (ArrayList<Node>) node.jjtGetChild(1).jjtAccept(this, resultSet);
				
		return data = unique(resultSet);
	}

	@Override
	public Object visit(ASTRelSlash node, Object data) {
		// TODO Auto-generated method stub
		int childNum = node.jjtGetNumChildren();

		if (childNum == 0) {
			System.out.println("[RelSlash] Error: 0 child.");
			System.exit(1);
		}
		
		ArrayList<Node> resultSet = null;
		resultSet = (ArrayList<Node>) node.jjtGetChild(0).jjtAccept(this, data);
		resultSet = (ArrayList<Node>) node.jjtGetChild(1).jjtAccept(this, resultSet);
		
		data  = unique(resultSet);
		
		return data;
	}

	@Override
	public Object visit(ASTRelDSlash node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node>();
		
		int childNum = node.jjtGetNumChildren();
		
		SimpleNode second;
		if (childNum > 1) {
			data = (ArrayList<Node>) node.jjtGetChild(0).jjtAccept(this, data);
			second = (SimpleNode) node.jjtGetChild(1);
		} else {
			second = (SimpleNode) node.jjtGetChild(0);
		}
		
		for(Node n: (ArrayList<Node>) data){
			resultSet = getDescendants(n, resultSet);	
		}
		
		resultSet = (ArrayList<Node>) second.jjtAccept(this, resultSet);
		
		return unique(resultSet);
	}

	@Override
	public Object visit(ASTRelFilter node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node>();
		
		int childNum = node.jjtGetNumChildren();
		
		data = (ArrayList<Node>) node.jjtGetChild(0).jjtAccept(this, data);
		
		for (int i = 1; i < childNum -1; i++) {
			SimpleNode second = (SimpleNode) node.jjtGetChild(i);	
			for(Node n: (ArrayList<Node>) data){	
	      ArrayList<Node> temp = new ArrayList<Node>();
	      temp.add(n);
	      Boolean accept = (Boolean) second.jjtAccept(this, temp);
	      if (accept)
	        resultSet.add(n);
			}
			data = resultSet;
		}
		
		SimpleNode last = (SimpleNode) node.jjtGetChild(childNum-1);	
		if (last instanceof ASTRelFilter) {
			for(Node n: (ArrayList<Node>) data){	
	      ArrayList<Node> temp = new ArrayList<Node>();
	      temp.add(n);
	      Boolean accept = (Boolean) last.jjtAccept(this, temp);
	      if (accept)
	        resultSet.add(n);
			}
		} else {
			resultSet = (ArrayList<Node>) last.jjtAccept(this, resultSet);
		}
		
		return resultSet;
	}

	@Override
	public Object visit(ASTComma node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> left = new ArrayList<Node>();
		ArrayList<Node> right = new ArrayList<Node>();
		
		left = (ArrayList<Node>) node.jjtGetChild(0).jjtAccept(this, data);
		right = (ArrayList<Node>) node.jjtGetChild(1).jjtAccept(this, data);
		
		return concat(left, right);
	}

	@Override
	public Object visit(ASTRelComma node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> left = new ArrayList<Node>();
		ArrayList<Node> right = new ArrayList<Node>();
		
		left = (ArrayList<Node>) node.jjtGetChild(0).jjtAccept(this, data);
		right = (ArrayList<Node>) node.jjtGetChild(1).jjtAccept(this, data);
		
		return concat(left, right);
	}

	@Override
	public Object visit(ASTStar node, Object data) {
		// TODO Auto-generated method stub
		int numOfChild = node.jjtGetNumChildren();
		if (numOfChild != 0) {
			System.err.println("[Star] Error: star is not leaf.");
		}

		ArrayList<Node> resultSet = new ArrayList<Node> ();
		
		ArrayList<Node> nodeList = (ArrayList<Node>) data;
		for (Node n : nodeList) {
			NodeList children = n.getChildNodes();
			int numOfChildren = children.getLength();
			for (int i = 0; i < numOfChildren; i++) {
				if (children.item(i) instanceof ElementImpl)
					resultSet.add(children.item(i));
			}
		}
		
		return resultSet;
	}

	@Override
	public Object visit(ASTDot node, Object data) {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public Object visit(ASTDdot node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node> ();
		
		for (Node n : (ArrayList<Node>) data) {
			resultSet.add(n.getParentNode());
		}
		
		return data = resultSet;
	}

	@Override
	//TODO figure out what to return
	public Object visit(ASTText node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node> ();
		
		for (Node n : (ArrayList<Node>) data) {
			resultSet.add(n.getFirstChild());
		}
		
		data = resultSet;
		
		return data;
	}

	@Override
	public Object visit(ASTParen node, Object data) {
		// TODO Auto-generated method stub
		int numOfChild = node.jjtGetNumChildren();
		
		//TODO check numOfChild;
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTTagName node, Object data) {
		// TODO Auto-generated method stub
		int numOfChild = node.jjtGetNumChildren();
	  //TODO check numOfChild;
		ArrayList<Node> resultSet = new ArrayList<Node>();
		
		ArrayList<Node> nodeList = (ArrayList<Node>) data;
		for (Node n : nodeList) {
			NodeList children = n.getChildNodes();
			int numOfChildren = children.getLength();
			for (int i = 0; i < numOfChildren; i++) {
				if (children.item(i).getNodeName().equals(node.tagName))
					resultSet.add(children.item(i));
			}
		}
		
		return data = resultSet;
	}

	@Override
	public Object visit(ASTFilterAnd node, Object data) {
		// TODO Auto-generated method stub
		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);
		
		Boolean leftRes = (Boolean) left.jjtAccept(this, data);
		Boolean rightRes = (Boolean) right.jjtAccept(this, data);
		
		return leftRes && rightRes;
	}

	@Override
	public Object visit(ASTFilterOr node, Object data) {
		// TODO Auto-generated method stub
		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);
		
		Boolean leftRes = (Boolean) left.jjtAccept(this, data);
		Boolean rightRes = (Boolean) right.jjtAccept(this, data);
		
		return leftRes || rightRes;
	}

	@Override
	public Object visit(ASTFilterEq node, Object data) {
		// TODO Auto-generated method stub
		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);
    
		ArrayList<Node> leftRes = (ArrayList<Node>) left.jjtAccept(this, data);
		ArrayList<Node> rightRes = (ArrayList<Node>) right.jjtAccept(this, data);

    //TODO: change equal function later.
    for (Node l : leftRes) {
      for (Node r : rightRes) {
        if (l.equals(r)) {
          return true;
        }
      }
    }

		return false;
	}

	@Override
	public Object visit(ASTFilterIs node, Object data) {
		// TODO Auto-generated method stub
		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);
    
		ArrayList<Node> leftRes = (ArrayList<Node>) left.jjtAccept(this, data);
		ArrayList<Node> rightRes = (ArrayList<Node>) right.jjtAccept(this, data);

    //TODO: change equal function later.
    for (Node l : leftRes) {
      for (Node r : rightRes) {
        if (l == r) {
          return true;
        }
      }
    }

		return false;
	}

	@Override
	public Object visit(ASTFilterParen node, Object data) {
		// TODO Auto-generated method stub
		int numOfChild = node.jjtGetNumChildren();
		
		//TODO check numOfChild;
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTFilterNot node, Object data) {
		// TODO Auto-generated method stub
		SimpleNode leaf = (SimpleNode) node.jjtGetChild(0);
		
		Boolean leafRes = (Boolean) leaf.jjtAccept(this, data);
		
		return !leafRes;
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
	
	ArrayList<Node> root(String fileName) {
		DOMParser parser = new DOMParser();
		try {
			parser.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", true);
			parser.setFeature("http://xml.org/sax/features/validation", false);
			parser.setFeature("http://xml.org/sax/features/namespaces", true);
			parser.setFeature("http://apache.org/xml/features/validation/schema", true);
			parser.setFeature("http://apache.org/xml/features/validation/schema-full-checking", false);
			parser.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", false);
			
			String filePath = fileName.substring(1, fileName.length()-1);
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

		Document document = parser.getDocument();
		
		ArrayList<Node> root = new ArrayList<Node>();
		root.add(document.getFirstChild());

		return root;
	}
		
	ArrayList<Node> unique(ArrayList<Node> list) {
		HashSet<Node> set = new HashSet<Node>(list);
		list.clear();
		list.addAll(set);
		return list;
	}
	
	ArrayList<Node> concat(ArrayList<Node> lhs, ArrayList<Node> rhs) {
		for (Node n : rhs)
			lhs.add(n);
		return lhs;
	}
}
