package transformer;

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

public class PrinterVisitor implements XQueryParserVisitor {

	@Override
	public Object visit(SimpleNode node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTStart node, Object data) {
		// TODO Auto-generated method stub
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTDoc node, Object data) {
		// TODO Auto-generated method stub
		System.out.print("doc(" + node.fileName + ")");
		return null;
	}

	@Override
	public Object visit(ASTAbsSlash node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print("/");
		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTAbsDSlash node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print("//");
		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTRelSlash node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print("/");
		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTRelDSlash node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print("//");
		node.jjtGetChild(1).jjtAccept(this, data);
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
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println(",");
		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTStar node, Object data) {
		// TODO Auto-generated method stub
		System.out.print("*");
		return null;
	}

	@Override
	public Object visit(ASTDot node, Object data) {
		// TODO Auto-generated method stub
		System.out.print(".");
		return null;
	}

	@Override
	public Object visit(ASTDdot node, Object data) {
		// TODO Auto-generated method stub
		System.out.print("..");
		return null;
	}

	@Override
	public Object visit(ASTText node, Object data) {
		// TODO Auto-generated method stub
		System.out.print("text()");
		return null;
	}

	@Override
	public Object visit(ASTParen node, Object data) {
		// TODO Auto-generated method stub
		System.out.print("(");
		node.childrenAccept(this, data);
		System.out.print(")");
		return null;
	}

	@Override
	public Object visit(ASTTagName node, Object data) {
		// TODO Auto-generated method stub
		System.out.print(node.tagName);
		return null;
	}

	@Override
	public Object visit(ASTFilterAnd node, Object data) {
		// TODO Auto-generated method stub
		System.out.print(node.jjtGetChild(0).jjtAccept(this, data));
		System.out.print(" and ");
		System.out.print(node.jjtGetChild(1).jjtAccept(this, data));
		return null;
	}

	@Override
	public Object visit(ASTFilterOr node, Object data) {
		// TODO Auto-generated method stub
		System.out.print(node.jjtGetChild(0).jjtAccept(this, data));
		System.out.print(" or ");
		System.out.print(node.jjtGetChild(1).jjtAccept(this, data));
		return null;
	}

	@Override
	public Object visit(ASTFilterEq node, Object data) {
		// TODO Auto-generated method stub
		System.out.print(node.jjtGetChild(0).jjtAccept(this, data));
		System.out.print(" eq ");
		System.out.print(node.jjtGetChild(1).jjtAccept(this, data));
		return null;
	}

	@Override
	public Object visit(ASTFilterIs node, Object data) {
		// TODO Auto-generated method stub
		System.out.print(node.jjtGetChild(0).jjtAccept(this, data));
		System.out.print(" is ");
		System.out.print(node.jjtGetChild(1).jjtAccept(this, data));
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
		System.out.print("(");
		node.childrenAccept(this, data);
		System.out.print(")");
		return null;
	}

	@Override
	public Object visit(ASTFilterNot node, Object data) {
		// TODO Auto-generated method stub
		System.out.print("not ");
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTForClause node, Object data) {
		// TODO Auto-generated method stub
		System.out.print("for ");
		int numOfChild = node.jjtGetNumChildren();
		boolean first = true;
		for (int i = 0; i < numOfChild; i++) {
			if (first)
				first = false;
			else
				System.out.println(",");
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		System.out.println();
		return null;
	}

	@Override
	public Object visit(ASTIn node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" in ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTLetClause node, Object data) {
		// TODO Auto-generated method stub
		System.out.print("let ");
		int numOfChild = node.jjtGetNumChildren();
		for (int i = 0; i < numOfChild; i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
			System.out.println(",");
		}
		return null;
	}

	@Override
	public Object visit(ASTAssign node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" := ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTWhereClause node, Object data) {
		// TODO Auto-generated method stub
		System.out.print("where ");
		node.childrenAccept(this, data);
		System.out.println();
		return null;
	}

	@Override
	public Object visit(ASTReturnClause node, Object data) {
		// TODO Auto-generated method stub
		System.out.print("return ");
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTCondAnd node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" and ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTCondOr node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" or ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTCondEq node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" eq ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTCondIs node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" is ");
		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTCondEmpty node, Object data) {
		// TODO Auto-generated method stub
		System.out.print("empty (");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(")");
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
		System.out.print(node.varName);
		return null;
	}

	@Override
	public Object visit(ASTXQueryComma node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println(",");
		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTXQuerySlash node, Object data) {
		// TODO Auto-generated method stub
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print("/");
		node.jjtGetChild(1).jjtAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTString node, Object data) {
		// TODO Auto-generated method stub
		System.out.print(node.strName);
		return null;
	}

	@Override
	public Object visit(ASTNewtag node, Object data) {
		// TODO Auto-generated method stub
		System.out.println("<" + node.tagName + "> {");
		node.childrenAccept(this, data);
		System.out.println("\n} </" + node.tagName + ">");
		return null;
	}

	@Override
	public Object visit(ASTJoin node, Object data) {
		// TODO Auto-generated method stub
		System.out.println("join (");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println(",");
		node.jjtGetChild(1).jjtAccept(this, data);
		System.out.println(",");
		node.jjtGetChild(2).jjtAccept(this, data);
		System.out.println(",");
		node.jjtGetChild(3).jjtAccept(this, data);
		System.out.print("\n)");
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
		node.childrenAccept(this, data);
		return null;
	}

	@Override
  public Object visit(ASTJoinList node, Object data) {
	  // TODO Auto-generated method stub
		System.out.print("[");
		int numOfChild = node.jjtGetNumChildren();
		boolean first = true;
		for (int i = 0; i < numOfChild; i++) {
			if (first)
				first = false;
			else
				System.out.print(", ");
			node.jjtGetChild(i).jjtAccept(this, data);
		}
		System.out.print("]");
	  return null;
  }
}
