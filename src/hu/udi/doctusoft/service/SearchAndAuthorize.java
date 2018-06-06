package hu.udi.doctusoft.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import hu.udi.doctusoft.model.AclItem;
import hu.udi.doctusoft.model.AclType;
import hu.udi.doctusoft.model.TreeItem;

public class SearchAndAuthorize {
	
	public void initializeTree(File file, TreeItem item) {
		if (file.isDirectory()) {
			//System.out.println("Searching directory ... " + file.getAbsoluteFile());
			item.setName(file.getAbsoluteFile().toString());
			for (File temp : file.listFiles()) {
			    if (temp.isDirectory()) {
			    	TreeItem child = new TreeItem(temp.getAbsoluteFile().toString());
			    	item.addChild(child);
			    	initializeTree(temp, child);		    				    	
			    }				    
		    }
	
	      }
	}
	
	private Map<String, AclItem> loadACL(String aclFile,Map<String, AclItem> acl, AclType aclType){		
		//I'm not jet too experienced with Java 8 streams
		try (BufferedReader reader = new BufferedReader(new FileReader(aclFile))) {
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	        	if(acl.containsKey(line)) {
					switch (aclType) { //let's ignore duplicates in the ACL files ;)
					case READ:
						acl.get(line).setRead(true);
						break;
					case WRITE:
						acl.get(line).setWrite(true);
						break;
					}
				} else {
					acl.put(line, new AclItem(aclType));
				}
	        }
	    } catch (IOException e) {
	        System.out.println(e.getMessage());
	    }
		
		return acl;
	}
	
	public Map<String, AclItem> loadReadACL(String aclFile,Map<String, AclItem> acl){
		return loadACL(aclFile, acl,  AclType.READ);
	}
	
	public Map<String, AclItem> loadWriteACL(String aclFile,Map<String, AclItem> acl){
		return loadACL(aclFile, acl, AclType.WRITE);
	}
	
	/* We could go 
	 * 	2 ways. Either apply ACL when building the Tree, Or apply the ACL when reading the tree
	 *  In the 1st we have to maintain trees for each users. In the 2nd we use acl when acessing the item. 
	 *  In this case we need to make sure that the tree structure is protected well enough, so it is not leaked to unprivileged users  
	 */
	public void printTree(TreeItem node, Map<String, AclItem> acl) {
		//apply ACL
		if(acl.containsKey(node.getName())) {
			AclItem aclItem = acl.get(node.getName());
			boolean aclOK = false;
			if(aclItem.isWrite()) {
					aclOK = true;
				}
			else 
				if(aclItem.isRead()) {
					//there should be at least 1 subfolder with write, scan ahead... Not pretty. 
					for(TreeItem child: node.getChildren()) {
						if(acl.containsKey(child.getName())&&acl.get(child.getName()).isWrite()) {
							// we have a child with write, woohoo
							aclOK = true;
							continue;
						}
					}
				} //if there is ACLItem, but no privilege, probably it's an error :(
			if(aclOK) {
				System.out.println(node.getName()); // this should be replaced with some better action
			}
		}
		for(TreeItem child: node.getChildren()){
			printTree(child,acl);
		}
		
	}
	
}
