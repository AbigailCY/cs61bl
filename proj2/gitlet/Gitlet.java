package gitlet;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Gitlet implements Serializable {
    //    StagingArea: filename -- file SHA1
    private HashMap<String, String> stagingArea = new HashMap<>();
    //    Heads: branch -- head(commits)
    private HashMap<String, Commit> heads = new HashMap<>();
    //   Commits: commit SHA1 -- commit
    private HashMap<String, Commit> commits = new HashMap<>();
//    Removes: remove file name--SHA
    private HashMap<String, HashSet<String>> removes = new HashMap<>();
    private String currHeadID;
    private String currBranch;
    //    split points: (branch1, branch2) -- commitID
    private HashMap<List<String>, String> isSplit = new HashMap<>();

    public Gitlet() {
    }

//    "./.gitlet/"
//    "./.gitlet/StagingArea/"
//    "./.gitlet/commit/"
//    "./.gitlet/temp"

//    "./.gitlet/gitClass"

    public void init() throws IOException {
        try {
            Path pathGit = Paths.get("./.gitlet/");
            Files.createDirectory(pathGit);
            Files.createDirectory(Paths.get("./.gitlet/StagingArea/"));
            Files.createDirectory(Paths.get("./.gitlet/commit/"));
            Files.createDirectory(Paths.get("./.gitlet/temp/"));

            Commit initCommit = new Commit("initial commit", stagingArea, "./.gitlet/");
            currHeadID = initCommit.initializeID("./.gitlet/");
            heads.put("master", initCommit);
            currBranch = "master";
            commits.put(currHeadID, initCommit);
            removes.put("master", new HashSet<>());
        } catch (IOException ex) {
            System.out.println(
                    "A gitlet version-control system already exists in the current directory.");
        }
    }

    public void add(String filename) {
        List<String> names = Utils.plainFilenamesIn("./");
        if (!names.contains(filename)) {
            System.out.println("File does not exist.");
            return;
        } else {
            String path = "./" + filename;
            if (removes.get(currBranch).contains(filename)) {
                removes.get(currBranch).remove(filename);
            }

            Blob currFile = new Blob(new File(path));
            Commit mycom = Commit.deserialize("./.gitlet/", currHeadID);
            if (mycom.getContents().containsValue(currFile.getSHA())) {
                return;
            }
            File myFile = currFile.serialize("./.gitlet/StagingArea/");
            stagingArea.put(filename, myFile.getName());
        }
    }

    public void commit(String message) throws IOException {
        if (stagingArea.isEmpty()) {
            boolean rm = false;
//            boolean workingRm = false;
            Commit myCommit = Commit.deserialize("./.gitlet/", currHeadID);
            for (String name : myCommit.getContents().keySet()) {
                if (!new File("./" + name).exists()) {
                    removes.get(currBranch).add(name);
                }
                if (removes.get(currBranch).contains(name)) {
                    rm = true;
                }
            }
            if (!rm) {
                System.out.println("No changes added to the commit.");
                return;
            }
        }
        Commit mycom = Commit.deserialize("./.gitlet/", currHeadID);
        if (!mycom.getContents().isEmpty()) {
            for (String name : heads.get(currBranch).getContents().keySet()) {
                if (!new File("./" + name).exists()) {
                    removes.get(currBranch).add(name);
                    if (stagingArea.containsKey(name)) {
                        stagingArea.remove(name);
                    }
                }
            }
        }
        Commit newCom = new Commit(currHeadID, message,
                stagingArea, commits, "./.gitlet/", removes.get(currBranch));
        removes.get(currBranch).clear();
        currHeadID = newCom.initializeID("./.gitlet/");
        stagingArea.clear();
        heads.put(currBranch, newCom);
        commits.put(newCom.getID(), newCom);

    }

    public void rm(String fileName) {
        Commit mycom = Commit.deserialize("./.gitlet/", currHeadID);
        if (mycom.getContents().containsKey(fileName)) {
//            String targetID = heads.get(currBranch).getContents().get(fileName);
//            Blob targetBlob = Blob.deserialize("./.gitlet/" + targetID);
//            targetBlob.setToRemove(true);
//            targetBlob.serialize("./.gitlet/");
            removes.get(currBranch).add(fileName);
            File targetFile = new File("./" + fileName);
            targetFile.delete();
            if (stagingArea.containsKey(fileName)) {
                stagingArea.remove(fileName);
                File rmStage = new File("./.gitlet/StagingArea/" + stagingArea.get(fileName));
                rmStage.delete();
            }
        } else if (stagingArea.containsKey(fileName)) {
            stagingArea.remove(fileName);
            File rmStage = new File("./.gitlet/StagingArea/" + stagingArea.get(fileName));
            rmStage.delete();
        } else {
            System.out.println("No reason to remove the file.");
        }
    }

    public void branch(String branchName) {
        if (heads.containsKey(branchName)) {
            System.out.println("A branch with that name already exists.");
            return;
        }
        heads.put(branchName, Commit.deserialize("./.gitlet/", currHeadID));
        isSplit.put(Arrays.asList(currBranch, branchName), heads.get(currBranch).getID());
        removes.put(branchName, removes.get(currBranch));
    }

    public void rmBranch(String branchName) {
        if (!(heads.keySet().contains(branchName))) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        if (commits.get(currHeadID) == heads.get(branchName)) {
            System.out.println("Cannot remove the current branch.");
            return;
        }
        heads.remove(branchName);
        removes.remove(branchName);

        for (List<String> i : isSplit.keySet()) {
            if (i.contains(branchName)) {
                isSplit.remove(i);
            }
        }
    }

    public void checkout(String fileName, int num) throws IOException {
        Commit mycom = Commit.deserialize("./.gitlet/", currHeadID);
        if (!mycom.getContents().containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
        } else {
            File targetFile = new File("./" + fileName);
            if (targetFile.exists()) {
                targetFile.delete();
            }
            targetFile.createNewFile();
            String fileID = mycom.getContents().get(fileName);
            byte[] myContent = Blob.deserialize("./.gitlet/" + fileID).getContent();
            Utils.writeContents(targetFile, myContent);
        }
    }

    public void checkout(String commitID, String fileName) throws IOException {
        boolean find = false;
        for (String i : Utils.plainFilenamesIn("./.gitlet/commit/")) {
            if (i.startsWith(commitID)) {
                commitID = i;
                find = true;
                break;
            }
        }
        if (!find) {
            System.out.println("No commit with that id exists.");
        } else {
            Commit myCommit = Commit.deserialize("./.gitlet/", commitID);
            if (!myCommit.getContents().containsKey(fileName)) {
                System.out.println("File does not exist in that commit.");
            } else {
                File targetFile = new File("./" + fileName);
                if (targetFile.exists()) {
                    targetFile.delete();
                }
                targetFile.createNewFile();
                String fileID = myCommit.getContents().get(fileName);
                byte[] myContent = Blob.deserialize("./.gitlet/" + fileID).getContent();
                Utils.writeContents(targetFile, myContent);
            }
        }
    }

    public void checkout(String branchName) throws IOException {
        if (!heads.containsKey(branchName)) {
            System.out.println("No such branch exists.");

        } else if (currBranch.equals(branchName)) {
            System.out.println("No need to checkout the current branch.");
        } else {
            Commit toCommit = Commit.deserialize("./.gitlet/", heads.get(branchName).getID());
            Commit myCommit = Commit.deserialize("./.gitlet/", currHeadID);

            for (String blobName : toCommit.getContents().keySet()) {
                File targetFile = new File("./" + blobName);
                String blobID = toCommit.getContents().get(blobName);
                if (targetFile.exists()) {
                    if (!new Blob(targetFile).getSHA().equals(blobID)) {
                        if (!myCommit.getContents().containsKey(blobName)) {
                            System.out.println("There is "
                                    + "an untracked file in the way; delete it or add it first.");
                            return;
                        } else {
                            targetFile.delete();
                        }
                    } else {
                        continue;
                    }
                }
                targetFile.createNewFile();
                byte[] myContent = Blob.deserialize("./.gitlet/" + blobID).getContent();
                Utils.writeContents(targetFile, myContent);
            }

            for (String j : myCommit.getContents().keySet()) {
                if (!toCommit.getContents().containsKey(j)) {
                    File rmFile = new File("./" + j);
                    if (rmFile.exists()) {
                        rmFile.delete();
                    }

                }
            }
            currBranch = branchName;
            currHeadID = heads.get(currBranch).getID();
            stagingArea.clear();
        }
    }

    public void reset(String commitID) throws IOException {
//        boolean contain = false;
//        for (String commitId : Utils.plainFilenamesIn("./.gitlet/commit/")) {
//            if (commitID.length() >= 6 && commitId.startsWith(commitID)) {
//                commitID = commitId;
//                contain = true;
//            }
//        }
//        if (!contain) {
//            System.out.println("No commit with that id exists.");
//            return;
//        }
        if (!Utils.plainFilenamesIn("./.gitlet/commit/").contains(commitID)) {
            System.out.println("No commit with that id exists.");
        } else {
            List<String> currFiles = Utils.plainFilenamesIn("./");
            Commit currCommit = Commit.deserialize("./.gitlet/", commitID);
            Commit headCommit = Commit.deserialize("./.gitlet/", currHeadID);
            for (String i : currFiles) {
                String iD = new Blob(new File("./" + i)).getSHA();
                if (!headCommit.getContents().containsValue(iD) && !stagingArea.containsValue(iD)) {
                    if (currCommit.getContents().containsKey(i)
                            && currCommit.getContents().get(i) != iD) {
                        System.out.println("There is an untracked file "
                                + "in the way; delete it or add it first.");
                        return;
                    } else {
                        new File("./" + i).delete();
                    }
                }
            }

            for (String blobName : currCommit.getContents().keySet()) {
                checkout(commitID, blobName);
                File targetFile = new File("./" + blobName);
                if (targetFile.exists()) {
                    targetFile.delete();
                }
                targetFile.createNewFile();
                String fileID = currCommit.getContents().get(blobName);
                byte[] myContent = Blob.deserialize("./.gitlet/" + fileID).getContent();
                Utils.writeContents(targetFile, myContent);
            }



            for (String j : headCommit.getContents().keySet()) {
                if (!currCommit.getContents().containsKey(j)) {
                    File rmFile = new File("./" + j);
                    if (rmFile.exists()) {
                        rmFile.delete();
                    }
                }
            }
            heads.put(currBranch, currCommit);
            currHeadID = heads.get(currBranch).getID();
            commits.put(currHeadID, currCommit);
            stagingArea.clear();
            removes.get(currBranch).clear();
        }
    }

    public void merge(String branchName) throws IOException {
        if (!heads.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist");
            return;
        } else if (branchName.equals(currBranch)) {
            System.out.println("Cannot merge a branch with itself.");
            return;
        }
//        split point finded
        String splitID = isSplit.get(Arrays.asList(branchName, currBranch));
        if (splitID == null) {
            splitID = isSplit.get(Arrays.asList(currBranch, branchName));
        }
//        error
        if (splitID.equals(heads.get(branchName).getID())) {
            System.out.println("Given branch is an ancestor of the current branch");
            return;
        } else if (splitID.equals(currHeadID)) {
            System.out.println("Current branch fast-forward");
            return;
        }
//
        List<String> currFiles = Utils.plainFilenamesIn("./");
        Commit currCommit = Commit.deserialize("./.gitlet/", currHeadID);
        Commit split = Commit.deserialize("./.gitlet/", splitID);
        Commit givenCommit = Commit.deserialize("./.gitlet/", heads.get(branchName).getID());
        HashMap<String, String> currCon = currCommit.getContents();
        HashMap<String, String> splitCon = split.getContents();
        HashMap<String, String> givenCon = givenCommit.getContents();
        HashSet<String> toMerge1 = new HashSet<>();
        HashSet<String> toMerge2 = new HashSet<>();
        HashSet<String> toMerge3 = new HashSet<>();
        HashSet<String> toRemove = new HashSet<>();
        HashSet<String> toCheckout = new HashSet<>();
        for (String file : splitCon.keySet()) {
            if (currCon.containsKey(file) && givenCon.containsKey(file)) {
                if (!currCon.get(file).equals(splitCon.get(file))
                        && !givenCon.get(file).equals(splitCon.get(file))
                        && !currCon.get(file).equals(givenCon.get(file))) {

                    toMerge1.add(file);
                } else if (currCon.get(file).equals(splitCon.get(file))
                        && !givenCon.get(file).equals(splitCon.get(file))) {
                    toCheckout.add(file);
                }
            } else if (!currCon.containsKey(file) && givenCon.containsKey(file)) {
                if (!givenCon.get(file).equals(splitCon.get(file))) {
                    toMerge2.add(file);
                }
            } else if (currCon.containsKey(file) && !givenCon.containsKey(file)) {
                if (!currCon.get(file).equals(splitCon.get(file))) {
                    toMerge3.add(file);
                } else {
                    toRemove.add(file);
                }
            }
        }
        for (String file : givenCon.keySet()) {
            if (!splitCon.containsKey(file)) {
                if (!currCon.containsKey(file)) {
                    toCheckout.add(file);
                } else if (!currCon.get(file).equals(givenCon.get(file))) {
                    toMerge1.add(file);
                }
            }
        }

//        for (String i : currFiles) {
//            String iD = new Blob(new File("./" + i)).getSHA();
//            if (toMerge1.contains(i) || toMerge3.contains(i) || toCheckout.contains(i)) {
//                if (!currCon.containsKey(i)) {
//                    System.out.println(
//                            "There is an untracked file in the way; delete it or add it first.");
//                    return;
//                }
//            }
//        }

        for (String fileName : toCheckout) {
            checkout(givenCommit.getID(), fileName);
            add(fileName);
        }
        for (String fileName : toRemove) {
            rm(fileName);
        }
        if (toMerge1.isEmpty() && toMerge2.isEmpty() && toMerge3.isEmpty()) {
            String message = "Merged [" + currBranch + "] with [" + branchName + "].";
            commit(message);
            return;
        } else {
            System.out.println("Encountered a merge conflict.");
            for (String fileName : toMerge1) {
                File myFile = new File("./" + fileName);


                System.out.println(Utils.plainFilenamesIn("./.gitlet/"));
                System.out.println(Utils.plainFilenamesIn("./"));
                System.out.println(Utils.plainFilenamesIn("./StagingArea"));
                System.out.println(currCon.get(fileName));
                System.out.println(givenCon.get(fileName));


                byte[] sp = System.getProperty("line.separator").getBytes();
                byte[] current = Blob.deserialize("./.gitlet/"
                        + currCon.get(fileName)).getContent();
                byte[] given = Blob.deserialize("./.gitlet/"
                        + givenCon.get(fileName)).getContent();
                Utils.writeContents(myFile, "<<<<<<< HEAD".getBytes());
                Utils.writeContents(myFile, sp);
                Utils.writeContents(myFile, current);
                Utils.writeContents(myFile, "=======".getBytes());
                Utils.writeContents(myFile, sp);
                Utils.writeContents(myFile, given);
                Utils.writeContents(myFile, ">>>>>>>".getBytes());
                add(fileName);
            }

            for (String fileName : toMerge2) {
                System.out.println(fileName);
                System.out.println(givenCon.get(fileName));
                System.out.println(Utils.plainFilenamesIn("./gitlet/"));
                File myFile = new File("./" + fileName);
                myFile.createNewFile();
                byte[] given = Blob.deserialize("./gitlet/"
                        + givenCon.get(fileName)).getContent();
                byte[] sp = System.getProperty("line.separator").getBytes();
                Utils.writeContents(myFile, "<<<<<<< HEAD".getBytes());
                Utils.writeContents(myFile, sp);
                Utils.writeContents(myFile, sp);
                Utils.writeContents(myFile, "=======".getBytes());
                Utils.writeContents(myFile, sp);
                Utils.writeContents(myFile, given);
                Utils.writeContents(myFile, ">>>>>>>".getBytes());
                add(fileName);
            }
            for (String fileName : toMerge3) {
                File myFile = new File("./" + fileName);
                byte[] current = Blob.deserialize("./gitlet/"
                        + currCon.get(fileName)).getContent();
                byte[] sp = System.getProperty("line.separator").getBytes();
                Utils.writeContents(myFile, "<<<<<<< HEAD".getBytes());
                Utils.writeContents(myFile, sp);
                Utils.writeContents(myFile, current);
                Utils.writeContents(myFile, "=======".getBytes());
                Utils.writeContents(myFile, sp);
                Utils.writeContents(myFile, sp);
                Utils.writeContents(myFile, ">>>>>>>".getBytes());
                add(fileName);
            }
            commit("Encountered a merge conflict.");

        }
    }

    public void log() {
        for (String pointer = currHeadID; pointer != null;
             pointer = commits.get(pointer).getParent()) {
            System.out.println(commits.get(pointer).toString());
            System.out.println();
        }
    }

    public void globalLog() {
        for (String i : commits.keySet()) {
            System.out.println(commits.get(i).toString());
            System.out.println();
        }
    }

    public void find(String commitMessage) {
        boolean find = false;
        for (String i : commits.keySet()) {
            if (commits.get(i).getMessage().equals(commitMessage)) {
                System.out.println(commits.get(i).getID());
                find = true;
            }
        }
        if (!find) {
            System.out.println("Found no commit with that message.");
        }
    }

    public void status() {


        System.out.println("=== Branches ===");
        for (String i : heads.keySet()) {
            if (i.equals(currBranch)) {
                System.out.println("*" + i);
            } else {
                System.out.println(i);
            }
        }
        System.out.println();

        Commit myCommit = Commit.deserialize("./.gitlet/", currHeadID);

        System.out.println("=== Staged Files ===");
        for (String j : stagingArea.keySet()) {
            System.out.println(j);
        }
        System.out.println();

        HashMap<String, String> comCon = myCommit.getContents();

        System.out.println("=== Removed Files ===");

        for (String fileName :removes.get(currBranch)) {
            System.out.println(fileName);
        }
        System.out.println();

        HashSet<String> untrack = new HashSet<>();

        System.out.println("=== Modifications Not Staged For Commit ===");
        for (String fileName : Utils.plainFilenamesIn("./")) {
            String fileID = new Blob(new File("./" + fileName)).getSHA();

            if (!comCon.containsKey(fileName) && !stagingArea.containsKey(fileName)) {
                untrack.add(fileName);
            } else if (comCon.containsKey(fileName) && !comCon.get(fileName).equals(fileID)) {
                if (!stagingArea.containsKey(fileName)) {
                    System.out.println(fileName + " (modified)");
                }
            } else if (stagingArea.containsKey(fileName)
                    && !stagingArea.get(fileName).equals(fileID)) {
                System.out.println(fileName + " (modified)");
            }
        }

        for (String fileName : comCon.keySet()) {
            File myFile = new File("./" + fileName);
            if (!myFile.exists() && !removes.get(currBranch).contains(fileName)) {
                System.out.println(fileName + " (deleted)");
            }
        }

        for (String fileName : stagingArea.keySet()) {
            File myFile = new File("./" + fileName);
            if (!myFile.exists()) {
                System.out.println(fileName + " (deleted)");
            }
        }

        System.out.println();

        System.out.println("=== Untracked Files ===");
        for (String i : untrack) {
            System.out.println(i);
        }
    }


    public static void serialize(Gitlet myGit) {
        File outFile = new File("./.gitlet/gitClass");
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outFile));
            out.writeObject(myGit);
            out.close();
        } catch (IOException excp) {
            System.out.println("5Error!");
            return;
        }
    }

    public static Gitlet deserialize() {
        try {
            Gitlet myGit = null;
            FileInputStream fileIn = new FileInputStream("./.gitlet/gitClass");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            myGit = (Gitlet) in.readObject();
            in.close();
            return myGit;
        } catch (IOException i) {
            i.printStackTrace();
            System.out.println("6Error!");
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Git class not found");
//            c.printStackTrace();
            return null;
        }
    }

}
