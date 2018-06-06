package hu.udi.foldersandacls.main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import hu.udi.doctusoft.model.AclItem;
import hu.udi.doctusoft.model.TreeItem;
import hu.udi.doctusoft.service.SearchAndAuthorize;

public class Main {
	
	// these should be parameters ofc, but let's go the lazy way for now
	private static final String START_DIR = "d:/test/";
	private static final String READ_ACL = "d:/test/read.txt";
	private static final String WRITE_ACL = "d:/test/write.txt";
	
	// let's assume Spring wires this for us ;)
	private static SearchAndAuthorize service = new SearchAndAuthorize();
	
	public static void main(String[] args) {
		
		File f =  new File(Main.START_DIR);
		Map<String, AclItem> acl = new HashMap<String, AclItem>();
		service.loadReadACL(Main.READ_ACL,acl);
		service.loadWriteACL(Main.WRITE_ACL,acl);
		
		TreeItem root = new TreeItem(f.getAbsolutePath());
		service.initializeTree(f, root);
		service.printTree(root,acl);
	}
	
}
