package traverser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

public class Context {
	
	Map<String, ArrayList<Node>> c = null;
	
	public Context() {
		c = new HashMap<String, ArrayList<Node>> ();
	}
	
	public Context add(String varName, ArrayList<Node> value) {
		
		Context newContext = new Context();
		
		newContext.c.putAll(this.c);
				
		newContext.c.put(varName, value);
		
		return newContext;
	}
	
	public ArrayList<Node> find(String varName) {
		return c.get(varName);
	}
}
