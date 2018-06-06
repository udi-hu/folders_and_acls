package hu.udi.doctusoft.model;

public class AclItem {
	
	boolean read = false;
	boolean write = false;
	
	
	public AclItem(AclType aclType) {
		super();
		switch (aclType) {
		case READ:
			read = true;
			break;
		case WRITE:
			write = true;
			break;
		
		}
	}
	
	public boolean hasRights() {
		return read||write;
	}
	
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public boolean isWrite() {
		return write;
	}
	public void setWrite(boolean write) {
		this.write = write;
	}
	
}
