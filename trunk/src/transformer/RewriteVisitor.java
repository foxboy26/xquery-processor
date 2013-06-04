package transformer;

import java.util.HashMap;

import parser.ASTAbsDSlash;
import parser.ASTAbsSlash;
import parser.ASTAssign;
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
import parser.ASTFilterRelPath;
import parser.ASTForClause;
import parser.ASTIn;
import parser.ASTJoin;
import parser.ASTJoinList;
import parser.ASTLX;
import parser.ASTLetClause;
import parser.ASTNewtag;
import parser.ASTParen;
import parser.ASTRelComma;
import parser.ASTRelDSlash;
import parser.ASTRelFilter;
import parser.ASTRelSlash;
import parser.ASTReturnClause;
import parser.ASTStar;
import parser.ASTStart;
import parser.ASTString;
import parser.ASTTagName;
import parser.ASTText;
import parser.ASTVar;
import parser.ASTWhereClause;
import parser.ASTXQueryComma;
import parser.ASTXQuerySlash;
import parser.SimpleNode;
import parser.XQueryParserVisitor;

public class RewriteVisitor implements XQueryParserVisitor {
	
	boolean isReturn = false;
	
	public Node root = new Node("input", Node.TAGNODE);
	
	HashMap<String, Node> context = new HashMap<String, Node> ();
	HashMap<String, SimpleNode> astContext = new HashMap<String, SimpleNode> ();
	
	@Override
	public Object visit(SimpleNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTStart node, Object data) {
		// TODO Auto-generated method stub
		return node.jjtGetChild(0).jjtAccept(this, data);
	}

	@Override
	public Object visit(ASTDoc node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTAbsSlash node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTAbsDSlash node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTRelSlash node, Object data) {
		// TODO Auto-generated method stub
		if (this.isReturn) {
			node.childrenAccept(this, data);
		}
		return null;
	}

	@Override
	public Object visit(ASTRelDSlash node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTRelFilter node, Object data) {
		// TODO Auto-generated method stub
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
		return null;
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
		return null;
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
	public Object visit(ASTFilterRelPath node, Object data) {
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
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTIn node, Object data) {
		// TODO Auto-generated method stub
		ASTVar var = (ASTVar) node.jjtGetChild(0);
		
		Node n = new Node(var.varName, Node.TAGNODE);
		context.put(var.varName, n);
		astContext.put(var.varName, node);
		
		SimpleNode first = (SimpleNode) node.jjtGetChild(1).jjtGetChild(0);

		if (first instanceof ASTDoc) {
			root.addChild(n);
		} else if (first instanceof ASTVar) {
			context.get(((ASTVar) first).varName).addChild(n);
		} else {
			System.out.println("Unknown core-syntax");
		}
		
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
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTReturnClause node, Object data) {
		// TODO Auto-generated method stub
		SimpleNode child = (SimpleNode) node.jjtGetChild(0);
		
		if (child instanceof ASTVar ||
				child instanceof ASTNewtag ||
				child instanceof ASTXQueryComma ||
				child instanceof ASTXQuerySlash) {
			this.isReturn = true;
			child.jjtAccept(this, data);
			this.isReturn = false;
		} else {
			System.out.println("Unknown core-syntax");
		}
		
		return null;
	}

	@Override
	public Object visit(ASTCondAnd node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTCondOr node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTCondEq node, Object data) {
		// TODO Auto-generated method stub
		ASTVar left = (ASTVar) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);
		
		if (right instanceof ASTVar) {
			context.get(left.varName).addPair(context.get(((ASTVar) right).varName));
			context.get(left.varName).isReturn = true;
			context.get(((ASTVar) right).varName).isReturn = true;
		} else if (right instanceof ASTString) {
			Node n = new Node(((ASTString) right).strName, Node.TEXTNODE);
			context.get(left.varName).addChild(n);
		}
		
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
		if (isReturn) {
			context.get(node.varName).isReturn = true;
		}
		return null;
	}

	@Override
	public Object visit(ASTXQueryComma node, Object data) {
		// TODO Auto-generated method stub
		if (isReturn) {
			node.jjtGetChild(0).jjtAccept(this, data);
			node.jjtGetChild(1).jjtAccept(this, data);
		}
		return null;
	}

	@Override
	public Object visit(ASTXQuerySlash node, Object data) {
		// TODO Auto-generated method stub
		if (this.isReturn) {
			node.childrenAccept(this, data);
		}
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
		if (this.isReturn) {
			node.childrenAccept(this, data);
		}
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

	@Override
  public Object visit(ASTJoin node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Object visit(ASTJoinList node, Object data) {
	  // TODO Auto-generated method stub
	  return null;
  }

}
