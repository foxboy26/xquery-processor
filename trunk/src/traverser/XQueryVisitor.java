package traverser;

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
import parser.ASTXQueryComma;
import parser.ASTXQuerySlash;
import parser.SimpleNode;
import parser.XQueryParserVisitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.dom.TextImpl;
import org.apache.xerces.parsers.DOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class XQueryVisitor implements XQueryParserVisitor {

	public ArrayList<Node> finalSet = new ArrayList<Node>();

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
		checkNumOfChildren(node, 1, "[Start]");

		return node.jjtGetChild(0).jjtAccept(this, data);
	}

	@Override
	public Object visit(ASTDoc node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 0, "[Doc]");

		return root(node.fileName);
	}

	@Override
	// doc(filename)/rp
	public Object visit(ASTAbsSlash node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 2, "[AbsSlash]");

		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);

		ArrayList<Node> resultSet = (ArrayList<Node>) left
				.jjtAccept(this, data);
		resultSet = (ArrayList<Node>) right.jjtAccept(this, resultSet);

		return resultSet;
	}

	@Override
	public Object visit(ASTAbsDSlash node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node>();
		data = node.jjtGetChild(0).jjtAccept(this, data);

		resultSet.addAll((ArrayList<Node>) data);
		for (Node n : (ArrayList<Node>) data) {
			ArrayList<Node> descendants = new ArrayList<Node>();
			descendants = getDescendants(n, descendants);
			resultSet.addAll(descendants);
		}

		resultSet = (ArrayList<Node>) node.jjtGetChild(1).jjtAccept(this,
				resultSet);

		return unique(resultSet);
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
		resultSet = (ArrayList<Node>) node.jjtGetChild(1).jjtAccept(this,
				resultSet);

		return unique(resultSet);
	}

	@Override
	public Object visit(ASTRelDSlash node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node>();

		int childNum = node.jjtGetNumChildren();
		if (childNum == 0) {
			System.err.println("[RelDSlash] Error!");
			System.exit(1);
		}

		data = (ArrayList<Node>) node.jjtGetChild(0).jjtAccept(this, data);

		resultSet.addAll((ArrayList<Node>) data);
		for (Node n : (ArrayList<Node>) data) {
			ArrayList<Node> descendants = new ArrayList<Node>();
			descendants = getDescendants(n, descendants);
			resultSet.addAll(descendants);
		}

		if (childNum == 2)
			resultSet = (ArrayList<Node>) node.jjtGetChild(1).jjtAccept(this,
					resultSet);

		return unique(resultSet);
	}

	@Override
	public Object visit(ASTRelFilter node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node>();

		int childNum = node.jjtGetNumChildren();

		data = (ArrayList<Node>) node.jjtGetChild(0).jjtAccept(this, data);

		for (int i = 1; i < childNum - 1; i++) {
			SimpleNode second = (SimpleNode) node.jjtGetChild(i);
			for (Node n : (ArrayList<Node>) data) {
				ArrayList<Node> temp = new ArrayList<Node>();
				temp.add(n);
				Boolean accept = (Boolean) second.jjtAccept(this, temp);
				if (accept)
					resultSet.add(n);
			}
			data = resultSet;
		}

		SimpleNode last = (SimpleNode) node.jjtGetChild(childNum - 1);
		if (last instanceof ASTFilterAnd || last instanceof ASTFilterOr
				|| last instanceof ASTFilterNot || last instanceof ASTFilterEq
				|| last instanceof ASTFilterIs
				|| last instanceof ASTFilterParen
				|| last instanceof ASTFilterRelPath) {
			for (Node n : (ArrayList<Node>) data) {
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

		ArrayList<Node> resultSet = new ArrayList<Node>();

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
		ArrayList<Node> resultSet = new ArrayList<Node>();

		for (Node n : (ArrayList<Node>) data) {
			resultSet.add(n.getParentNode());
		}

		return resultSet;
	}

	@Override
	// TODO figure out what to return
	public Object visit(ASTText node, Object data) {
		// TODO Auto-generated method stub
		ArrayList<Node> resultSet = new ArrayList<Node>();

		for (Node n : (ArrayList<Node>) data) {
			resultSet.add(n.getFirstChild());
		}

		return resultSet;
	}

	@Override
	public Object visit(ASTParen node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 1, "[Paren]");
		// TODO check numOfChild;
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTTagName node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 0, "TagName");

		ArrayList<Node> nodeList = (ArrayList<Node>) data;
		ArrayList<Node> resultSet = new ArrayList<Node>();

		for (Node n : nodeList) {
			NodeList children = n.getChildNodes();
			int numOfChildren = children.getLength();
			for (int i = 0; i < numOfChildren; i++) {
				if (children.item(i).getNodeName().equalsIgnoreCase(node.tagName))
					resultSet.add(children.item(i));
			}
		}

		return resultSet;
	}

	@Override
	public Object visit(ASTFilterRelPath node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 1, "FilterRelPath");

		ArrayList<Node> resultSet = (ArrayList<Node>) node.jjtGetChild(0)
				.jjtAccept(this, data);
		return resultSet.size() > 0;
	}

	@Override
	public Object visit(ASTFilterAnd node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 2, "FilterAnd");

		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);

		Boolean leftRes = (Boolean) left.jjtAccept(this, data);
		Boolean rightRes = (Boolean) right.jjtAccept(this, data);

		return leftRes && rightRes;
	}

	@Override
	public Object visit(ASTFilterOr node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 2, "FilterOr");

		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);

		Boolean leftRes = (Boolean) left.jjtAccept(this, data);
		Boolean rightRes = (Boolean) right.jjtAccept(this, data);

		return leftRes || rightRes;
	}

	@Override
	public Object visit(ASTFilterEq node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 2, "FilterEq");

		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);

		ArrayList<Node> leftRes = (ArrayList<Node>) left.jjtAccept(this, data);
		ArrayList<Node> rightRes = (ArrayList<Node>) right
				.jjtAccept(this, data);

		for (Node l : leftRes)
			for (Node r : rightRes)
				if (equals(l, r))
					return true;

		return false;
	}

	@Override
	public Object visit(ASTFilterIs node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 2, "FilterIs");

		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);

		ArrayList<Node> leftRes = (ArrayList<Node>) left.jjtAccept(this, data);
		ArrayList<Node> rightRes = (ArrayList<Node>) right
				.jjtAccept(this, data);

		for (Node l : leftRes)
			for (Node r : rightRes)
				if (l == r)
					return true;

		return false;
	}

	@Override
	public Object visit(ASTFilterParen node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 1, "FilterParen");

		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTFilterNot node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 2, "FilterNot");

		Boolean leafRes = (Boolean) node.jjtGetChild(0).jjtAccept(this, data);

		return !leafRes;
	}

	@Override
	public Object visit(ASTForClause node, Object data) {
		// TODO Auto-generated method stub

		Context context = (Context) data;
		return node.jjtGetChild(0).jjtAccept(this, context);
	}

	@Override
	public Object visit(ASTIn node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 2, "[In]");

		Context context = (Context) data;
		ASTVar varNode = (ASTVar) node.jjtGetChild(0);

		ArrayList<Node> resultSet = (ArrayList<Node>) node.jjtGetChild(1)
				.jjtAccept(this, context);

		// Context newContext = context.add(varNode.varName, resultSet);
		int size = resultSet.size();

		// Find out the index if current node

		SimpleNode parent = (SimpleNode) ((SimpleNode) node).jjtGetParent();
		int childNum = ((SimpleNode) parent).jjtGetNumChildren();
		int index = 0;
		for (; index < childNum; ++index) {
			if (((SimpleNode) parent).jjtGetChild(index) == node)
				break;
		}

		if (parent instanceof ASTForClause) {
			for (int i = 0; i < size; ++i) {
				ArrayList<Node> value = new ArrayList<Node>();

				value.add(resultSet.get(i));
				Context newContext = context.add(varNode.varName, value);

				if (index != childNum - 1) {
					((SimpleNode) parent).jjtGetChild(index + 1).jjtAccept(
							this, newContext);

				} else {
					// FLWR
					SimpleNode grandParent = (SimpleNode) ((SimpleNode) node)
							.jjtGetParent().jjtGetParent();
					grandParent.jjtGetChild(1).jjtAccept(this, newContext);
				}

			}

			if (index == 0)
				return finalSet;
			return null;
		} else {
			for (int i = 0; i < size; ++i) {
				ArrayList<Node> value = new ArrayList<Node>();

				value.add(resultSet.get(i));
				Context newContext = context.add(varNode.varName, value);

				if ((Boolean) parent.jjtGetChild(index + 1).jjtAccept(this,
						newContext))
					return true;


			}
			return false;
		}
	}

	@Override
	public Object visit(ASTLetClause node, Object data) {
		// TODO Auto-generated method stub
		Context context = (Context) data;
		return (Context) node.jjtGetChild(0).jjtAccept(this, context);

	}

	@Override
	public Object visit(ASTAssign node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 2, "[Assign]");

		Context context = (Context) data;
		ASTVar varNode = (ASTVar) node.jjtGetChild(0);

		ArrayList<Node> resultSet = (ArrayList<Node>) node.jjtGetChild(1)
				.jjtAccept(this, context);

		Context newContext = context.add(varNode.varName, resultSet);

		// Find out the index if current node
		SimpleNode parent = (SimpleNode) ((SimpleNode) node).jjtGetParent();
		int childNum = ((SimpleNode) parent).jjtGetNumChildren();
		int index = 0;
		for (; index < childNum; ++index) {
			if (((SimpleNode) parent).jjtGetChild(index) == node)
				break;
		}
		if (index != childNum - 1) {
			++index;
			((SimpleNode) parent).jjtGetChild(index)
					.jjtAccept(this, newContext);
		} else {
			// FLWR
			SimpleNode grandParent = (SimpleNode) ((SimpleNode) node)
					.jjtGetParent().jjtGetParent();
			grandParent.jjtGetChild(2).jjtAccept(this, newContext);
		}
		return null;
	}

	@Override
	public Object visit(ASTWhereClause node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 1, "[WhereClause]");
		Context context = (Context) data;
		Boolean b = (Boolean) node.jjtGetChild(0).jjtAccept(this, data);
		if (b) {
			SimpleNode parent = (SimpleNode) ((SimpleNode) node).jjtGetParent();
			int childNum = parent.jjtGetNumChildren();
			return parent.jjtGetChild(childNum - 1).jjtAccept(this, context);
		}
		return null;
	}

	@Override
	public Object visit(ASTReturnClause node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 1, "[ReturnClause]");

		finalSet.addAll((ArrayList<Node>) node.jjtGetChild(0).jjtAccept(this,
				data));
		return finalSet;
	}

	@Override
	public Object visit(ASTCondAnd node, Object data) {
		// TODO Auto-generated method stub
		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);

		Boolean leftRes = (Boolean) left.jjtAccept(this, data);
		Boolean rightRes = (Boolean) right.jjtAccept(this, data);

		return leftRes && rightRes;
	}

	@Override
	public Object visit(ASTCondOr node, Object data) {
		// TODO Auto-generated method stub
		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);

		Boolean leftRes = (Boolean) left.jjtAccept(this, data);
		Boolean rightRes = (Boolean) right.jjtAccept(this, data);

		return leftRes || rightRes;
	}

	@Override
	public Object visit(ASTCondEq node, Object data) {
		// TODO Auto-generated method stub
		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);

		Context context = (Context) data;
		ArrayList<Node> leftRes = (ArrayList<Node>) left.jjtAccept(this,
				context);
		ArrayList<Node> rightRes = (ArrayList<Node>) right.jjtAccept(this,
				context);

		for (Node l : leftRes)
			for (Node r : rightRes)
				if (equals(l, r))
					return true;

		return false;
	}

	@Override
	public Object visit(ASTCondIs node, Object data) {
		// TODO Auto-generated method stub
		SimpleNode left = (SimpleNode) node.jjtGetChild(0);
		SimpleNode right = (SimpleNode) node.jjtGetChild(1);

		Context context = (Context) data;
		ArrayList<Node> leftRes = (ArrayList<Node>) left.jjtAccept(this,
				context);
		ArrayList<Node> rightRes = (ArrayList<Node>) right.jjtAccept(this,
				context);

		for (Node l : leftRes)
			for (Node r : rightRes)
				if (l == r)
					return true;

		return false;
	}

	@Override
	public Object visit(ASTCondEmpty node, Object data) {
		// TODO Auto-generated method stub
		Context context = (Context) data;
		ArrayList<Node> resultSet = (ArrayList<Node>) node.jjtGetChild(0)
				.jjtAccept(this, context);
		return resultSet.isEmpty();
	}

	@Override
	public Object visit(ASTCondSome node, Object data) {
		// TODO Auto-generated method stub

		return node.jjtGetChild(0).jjtAccept(this, data);
	}

	@Override
	public Object visit(ASTVar node, Object data) {
		// TODO Auto-generated method stub
		Context context = (Context) data;

		return context.find(node.varName);
	}

	@Override
	public Object visit(ASTXQuerySlash node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 2, "[QuerySlash]");

		ArrayList<Node> resultSet;
		Context context = (Context) data;

		resultSet = (ArrayList<Node>) node.jjtGetChild(0).jjtAccept(this,
				context);
		resultSet = (ArrayList<Node>) node.jjtGetChild(1).jjtAccept(this,
				resultSet);

		return unique(resultSet);
	}

	@Override
	public Object visit(ASTXQueryComma node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 2, "[QueryComma]");

		Context context = (Context) data;
		ArrayList<Node> lhsResultSet = (ArrayList<Node>) node.jjtGetChild(0)
				.jjtAccept(this, context);
		ArrayList<Node> rhsResultSet = (ArrayList<Node>) node.jjtGetChild(1)
				.jjtAccept(this, context);

		return concat(lhsResultSet, rhsResultSet);
	}

	@Override
	public Object visit(ASTString node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 0, "[String]");

		Document doc = new DocumentImpl();
		Text newText = doc.createTextNode(node.strName.substring(1,
				node.strName.length() - 1));
		ArrayList<Node> resultSet = new ArrayList<Node>();
		resultSet.add(newText);

		return resultSet;
	}

	@Override
	public Object visit(ASTNewtag node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 1, "[Newtag]");

		Context context = (Context) data;
		ArrayList<Node> resultSet = (ArrayList<Node>) node.jjtGetChild(0)
				.jjtAccept(this, context);

		Document doc = new DocumentImpl();
		Element newTag = doc.createElement(node.tagName);
		for (Node n : resultSet) {
			newTag.appendChild(createNode(doc, n));
		}

		resultSet.clear();
		resultSet.add(newTag);

		return resultSet;
	}

	@Override
	public Object visit(ASTFLWR node, Object data) {
		// TODO Auto-generated method stub
		return node.jjtGetChild(0).jjtAccept(this, data);
	}

	@Override
	public Object visit(ASTLX node, Object data) {
		// TODO Auto-generated method stub
		checkNumOfChildren(node, 2, "[LX]");

		Context context = (Context) data;
		Context newContext = (Context) node.jjtGetChild(0).jjtAccept(this,
				context);

		return node.jjtAccept(this, newContext);
	}

	ArrayList<Node> getDescendants(Node n, ArrayList<Node> result) {
		NodeList nodelist = n.getChildNodes();
		int num = nodelist.getLength();
		for (int i = 0; i < num; ++i) {
			Node cur = nodelist.item(i);
			if (cur instanceof ElementImpl) {
				if (!result.contains(cur))
					result.add(cur);
				result = getDescendants(cur, result);
			}
		}
		return result;
	}

	ArrayList<Node> root(String fileName) {
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
			parser.setFeature(
					"http://apache.org/xml/features/dom/include-ignorable-whitespace",
					false);

			String filePath = fileName.substring(1, fileName.length() - 1);
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
		root.add(document);
		return root;
	}

	ArrayList<Node> unique(ArrayList<Node> list) {
		HashSet<Node> set = new HashSet<Node>(list);
		list.clear();
		list.addAll(set);
		return list;
	}

	ArrayList<Node> concat(ArrayList<Node> lhs, ArrayList<Node> rhs) {
		ArrayList<Node> re = new ArrayList<Node>();
		re.addAll(lhs);
		re.addAll(rhs);
		return re;
	}

	public boolean equals(Node lhs, Node rhs) {
		if (lhs.getNodeType() != rhs.getNodeType())
			return false;
		if (!lhs.getNodeName().equals(rhs.getNodeName()))
			return false;
		if (lhs instanceof TextImpl && rhs instanceof TextImpl) {
			//System.out.println("lvalue:" + lhs.getNodeValue());
			//System.out.println("rvalue:" + rhs.getNodeValue());
			if (!((TextImpl) lhs).getNodeValue().trim()
					.equals(((TextImpl) rhs).getNodeValue().trim()))
				return false;
		}

		NodeList leftChildren = lhs.getChildNodes();
		NodeList rightChildren = rhs.getChildNodes();
		int leftNum = leftChildren.getLength();
		int rightNum = rightChildren.getLength();
		int i = 0, j = 0;
		while (i < leftNum && j < rightNum) {

			while (i < leftNum
					&& (((leftChildren.item(i) instanceof TextImpl) && ((TextImpl) leftChildren
							.item(i)).getNodeValue().trim().length() == 0) || (!(leftChildren
							.item(i) instanceof TextImpl) && !(leftChildren
							.item(i) instanceof ElementImpl)))) {
				++i;
			}
			while (j < rightNum
					&& (((rightChildren.item(j) instanceof TextImpl) && ((TextImpl) rightChildren
							.item(j)).getNodeValue().trim().length() == 0) || (!(rightChildren
							.item(j) instanceof TextImpl) && !(rightChildren
							.item(j) instanceof ElementImpl)))) {
				++j;
			}

			if (i < leftNum && j < rightNum) {
				Node left = leftChildren.item(i);
				Node right = rightChildren.item(j);
				if (!equals(left, right))
					return false;
				else {
					++i;
					++j;
				}
			} else if (i == leftNum && j == rightNum)
				return true;
			else
				return false;
		}
		while (i < leftNum) {
			if ((leftChildren.item(i) instanceof ElementImpl)
					|| ((leftChildren.item(i) instanceof TextImpl) && ((TextImpl) leftChildren
							.item(i)).getNodeValue().trim().length() != 0))
				return false;
		}
		while (j < rightNum) {
			if ((rightChildren.item(j) instanceof ElementImpl)
					|| ((rightChildren.item(j) instanceof TextImpl) && ((TextImpl) rightChildren
							.item(j)).getNodeValue().trim().length() != 0))
				return false;
		}
		return true;
	}

	void checkNumOfChildren(SimpleNode node, int num, String errMsg) {
		int numOfChild = node.jjtGetNumChildren();
		if (numOfChild != num) {
			System.err.println("Error: " + errMsg + " should have " + num
					+ " children, but only have " + numOfChild);
		}
	}

	public Node createNode(Document doc, Node n) {
		Node newNode = null;
		if (n instanceof ElementImpl)
			newNode = doc.createElement(((ElementImpl) n).getTagName());
		else if (n instanceof TextImpl && n.getNodeValue().trim().length() > 0)
			newNode = doc.createTextNode(n.getNodeValue());

		NodeList children = n.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = createNode(doc, children.item(i));
			newNode.appendChild(child);
		}

		return newNode;
	}
}
