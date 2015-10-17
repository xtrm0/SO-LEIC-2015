package edt.core;

import java.util.Map;
import java.util.HashMap;

abstract class Node {
	private String id;
	protected IdentificationFactory factory;
	private int length;
	private Node parent;

	public Node(Node parent, IdentificationFactory factory) {
		id = null;
		length = 0;
		this.parent = parent;
		if (factory == null)
			factory = new IdentificationFactory();
		this.factory = factory;
	}

	public Node switchUniqueId(String id) {
		return factory.addNodeId(id, this);
	}

	void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	private void notifyLength(Node son, int oldLength) {
		int currLength = this.length;
		length += son.length - oldLength;
		if (getParent() != null)
			getParent().notifyLength(this, currLength);
	}

	public void updateLength() {
		int currLength = this.length;
		this.length = calcLength();
		if (getParent() != null)
			getParent().notifyLength(this, currLength);
	}

	public int getLength() {
		return length;
	}
	
	public void delete() {
		if (id!=null)
			factory.removeNodeId(this.id);
	}
	
	protected abstract int calcLength();

	public Node getParent() {
		return parent;
	}
}

class IdentificationFactory {
	private Map<String, Node> nodes;

	public IdentificationFactory() {
		nodes = new HashMap<String, Node>();
	}

	public Node findByUniqueId(String id) {
		return nodes.get(id);
	}

	/*
	 * Adds a <id,node> pair to the map. Returns the previous node with that id,
	 * or null if there was none.
	 */
	public Node addNodeId(String id, Node node) {
		Node rv = findByUniqueId(id);
		if (rv != null) {
			rv.setId(null);
		}
		nodes.put(id, node);
		node.setId(id);
		return rv;
	}
	
	public void removeNodeId(String id) {
		
	}
}