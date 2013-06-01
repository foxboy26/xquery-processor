package traverser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Document;

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
			Context context = new Context();
			ASTStart n = t.Start();
			n.dump(">");
			
			XQueryParserVisitor visitor = new XQueryVisitor();
			n.jjtAccept(visitor, context);
			System.out.println();
			
			System.out.println("Result:");

			print(((XQueryVisitor)visitor).finalSet);
			

		} catch (Exception e) {
			System.out.println("Oops.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void print(ArrayList<Node> result) {
    PrintWriter writer= new PrintWriter(System.out);
		XMLSerializer serializer = new XMLSerializer(writer, new OutputFormat(Method.XML, "UTF-8", true));
    boolean first = true;
		for (Node n : result) {
			try {
				if (first) {
					if (n instanceof DocumentImpl)
						serializer.serialize((Document) n);
					else if (n instanceof ElementImpl)
						serializer.serialize((Element) n);
					first = false;
				} else {
					System.out.println("------------------------");
					if (n instanceof DocumentImpl)
						serializer.serialize((Document) n);
					else if (n instanceof ElementImpl)
						serializer.serialize((Element) n);
				}
      } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
		}
	}
}
