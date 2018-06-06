# Having fun With Folders and ACL

The app will read a folder and subfolders recursively starting from in hu.udi.foldersandacls.main.Main.START_DIR and then will apply Read and Write ACL rules  defined in hu.udi.foldersandacls.main.Main.READ_ACL || WRITE_ACL.

How the ACL is applied:
1. When we have write on a folder, that folder is printed
2. When we have read, we have to make sure that at least one subfolder has write 
3. A subfolder can't be accessed when the parent has no rights

## How to run?

Update parameters in the Main, build, run!
