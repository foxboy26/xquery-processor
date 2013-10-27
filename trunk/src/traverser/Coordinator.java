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

		String fileName;
		if (args.length == 1) {
			fileName = args[0];
		} else {
			fileName = "query2";
		}
		try {
			XQueryParser t = new XQueryParser(new FileInputStream(new File(fileName)));
			ASTStart root = t.Start();
			root.dump(">");

			/* query rewritten */
			ASTStart newRoot = null;
			Transformer transformer = new Transformer(root);
			newRoot = transformer.rewrite();
			
			Context context = new Context();
			XQueryVisitor visitor = new XQueryVisitor();
			ArrayList<Node> resultSet;
			
			Date start = new Date();	
			
			if (newRoot != null) {
				System.out.println("Optimized plan: ");
				
				newRoot.jjtAccept(new PrinterVisitor(), 0);
				
				resultSet = (ArrayList<Node>) newRoot.jjtAccept(visitor, context);
			} else {
				System.out.println("Query cannot be rewritten, execute original plan.");
				
				resultSet = (ArrayList<Node>) root.jjtAccept(visitor, context);
			}

			printResult(resultSet);
			
		  double diffTime = ((double) (new Date().getTime() - start.getTime())) / 1000;
		  
		  System.out.println(resultSet.size() + " results in set (" + String.format("%.2f", diffTime) + " sec)");
		} catch (Exception e) {
			System.out.println("Oops.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void printResult(ArrayList<Node> result) {
		System.out.println("XQuery result:");
		
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
