package traverser;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.w3c.dom.Node;

import parser.ASTStart;
import parser.XQueryParser;
import parser.XQueryParserVisitor;

public class Coordinator {
	public static void main(String args[]) {
		// System.out.println("Reading from standard input...");
		// String str = "doc(\"test.xml\")//book";
		XQueryParser t;
		try {
			t = new XQueryParser(new FileInputStream(new File("xpath.txt")));
			ArrayList<Node> data = new ArrayList<Node>();
			ASTStart n = t.Start();
			n.dump(">");
			
			XQueryParserVisitor visitor = new XQueryVisitor();
			data = (ArrayList<Node>) n.jjtAccept(visitor, data);
			System.out.println(data);
		} catch (Exception e) {
			System.out.println("Oops.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
