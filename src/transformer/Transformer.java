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
import org.w3c.dom.Node;
import org.w3c.dom.Document;

import parser.ASTStart;
import parser.XQueryParser;
import parser.XQueryParserVisitor;

public class Transformer {
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
				ArrayList<Node> resultSet = (ArrayList<Node>) n.jjtAccept(visitor, null);
				System.out.println();
				
				((RewriteVisitor)visitor).root.dump();
				
			} catch (Exception e) {
				System.out.println("Oops.");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
}
