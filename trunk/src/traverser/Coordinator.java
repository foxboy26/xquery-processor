package traverser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

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
import transformer.PrinterVisitor;
import transformer.Transformer;

public class Coordinator {
	public static void main(String args[]) {
		// System.out.println("Reading from standard input...");
		// String str = "doc(\"test.xml\")//book";
		XQueryParser t;
		try {
			t = new XQueryParser(new FileInputStream(new File("shake.txt")));
			ASTStart root = t.Start();
			root.dump(">");

			Transformer transformer = new Transformer(root);
			//if (transformer.isRewrittable) {
				root = transformer.rewrite();
				System.out.println("Optimized plan: ");
				root.jjtAccept(new PrinterVisitor(), 0);
			//}
				
			Date start = new Date();	
				
			Context context = new Context();
			XQueryParserVisitor visitor = new XQueryVisitor();
			ArrayList<Node> resultSet = 
					(ArrayList<Node>) root.jjtAccept(visitor, context);

			System.out.println("XQuery result:");
			printResult(resultSet);
		  
		  double time = ((double) (new Date().getTime() - start.getTime())) / 1000;
		  
		  System.out.println(resultSet.size() + " results in set (" + String.format("%.2f", time) + " sec)");
		} catch (Exception e) {
			System.out.println("Oops.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void printResult(ArrayList<Node> result) {
		PrintWriter writer = new PrintWriter(System.out);
		XMLSerializer serializer = new XMLSerializer(
				writer, new OutputFormat(Method.XML, "UTF-8", true));
		for (Node n : result) {
			try {
				if (n instanceof DocumentImpl)
					serializer.serialize((Document) n);
				else if (n instanceof ElementImpl)
					serializer.serialize((Element) n);
				else if (n instanceof TextImpl)
					System.out.println(n.getNodeValue());
				System.out.println("------------------------");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void printNode(Node node) {
		PrintWriter writer = new PrintWriter(System.out);
		XMLSerializer serializer = new XMLSerializer(
				writer, new OutputFormat(Method.XML, "UTF-8", true));
		try {
			if (node instanceof DocumentImpl)
				serializer.serialize((Document) node);
			else if (node instanceof ElementImpl)
				serializer.serialize((Element) node);
			else if (node instanceof TextImpl)
				System.out.println(node.getNodeValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
