package gitlet;

import javax.lang.model.element.NestingKind;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class Commit implements Serializable {
    private final String parent;
    private final String message;
    private String commitDate;
    //    Change: Contents: filename -- SHA1
    private HashMap<String, String> contents = new HashMap<>();
    private String ID;
    private boolean isSplit;

    public Commit(String message, HashMap<String, String> StagingArea, String gitletDirectory) {
        parent = null;
        this.message = message;
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        commitDate = ft.format(date);
        isSplit = false;
    }

    public Commit(String parentId, String message, HashMap<String, String> StagingArea, HashMap<String, Commit> commits, String gitletDirectory) throws IOException {
        parent = parentId;
        this.message = message;
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        commitDate = ft.format(date);
        isSplit = false;
        if (parentId != null) {
            contents = commits.get(parentId).contents;
        } else {
            contents = new HashMap<>();
        }

        HashMap<String, String> temp1 = copyHash(contents);
        for (String checkName : temp1.keySet()) {
            Blob toCheck = Blob.deserialize(gitletDirectory + contents.get(checkName));
            if (toCheck.isToRemove()) {
                contents.remove(checkName);
            }
        }

        HashMap<String, String> temp2 = copyHash(StagingArea);
        for (String keyName : temp2.keySet()) {
            if (contents.containsKey(keyName) && contents.get(keyName).equals(StagingArea.get(keyName))) {
                File file = new File(gitletDirectory + "StagingArea/" + StagingArea.get(keyName));
                file.delete();
            } else {
                contents.put(keyName, StagingArea.get(keyName));
                File moveFile = new File(gitletDirectory + "commit/" + StagingArea.get(keyName));
                Files.move(Paths.get(gitletDirectory + "StagingArea/" + StagingArea.get(keyName)),
                        Paths.get(gitletDirectory + StagingArea.get(keyName)));
                moveFile.delete();
            }
        }
    }

    public static HashMap<String, String> copyHash(HashMap<String, String> hash) {
        HashMap<String, String> newHash = new HashMap<>();
        for (String keyName : hash.keySet()) {
            newHash.put(keyName, hash.get(keyName));
        }
        return newHash;
    }

    public File serialize(String gitletDirectory) throws IOException {
        File outFile = new File(gitletDirectory + "commit/" + "a");
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outFile));
            out.writeObject(this);
            out.close();
        } catch (IOException excp) {
            System.out.println("3Error!");
            return null;
        }
        File newOut = new File(gitletDirectory + "commit/" + Utils.sha1(Utils.readContents(outFile)));
        newOut.createNewFile();
        Utils.writeContents(newOut, Utils.readContents(outFile));
        outFile.delete();
        return newOut;
    }

    public static Commit deserialize(String myDirectory, String commitID) {
        try {
            Commit myCommit;
            String path = myDirectory + "commit/" + commitID;
            File inFile = new File(path);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(inFile));
            myCommit = (Commit) in.readObject();
            myCommit.initializeID(myDirectory);
            in.close();
            return myCommit;
        } catch (IOException i) {
            System.out.println("4Error!");
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Commit class not found");
            return null;
        }
    }

    public String initializeID(String gitletDirectory) throws IOException {
        ID = serialize(gitletDirectory).getName();
        return ID;
    }

    public String getID() {
        return ID;
    }

    public String getMessage() {
        return message;
    }


    public String getParent() {
        return parent;
    }

    public HashMap<String, String> getContents() {
        return contents;
    }


    public String toString() {
        return "===\nCommit " + ID + "\n" + commitDate + "\n" + message;
    }
}
