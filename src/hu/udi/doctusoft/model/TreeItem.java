package hu.udi.doctusoft.model;

import java.util.ArrayList;
import java.util.List;

public class TreeItem {
		
	String name="";
	List<TreeItem> children=new ArrayList<>();
	
	public TreeItem() {
		super();
	}

	public TreeItem(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TreeItem> getChildren() {
		return children;
	}

	public void addChild(TreeItem child) {
		children.add(child);
	}
}
