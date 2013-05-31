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
		
		Context old = new Context();
		
		old.c.putAll(this.c);
				
		c.put(varName, value);
		
		return old;
	}
	
	public ArrayList<Node> find(String varName) {
		return c.get(varName);
	}
}
