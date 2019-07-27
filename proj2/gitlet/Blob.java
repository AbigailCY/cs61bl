package gitlet;

import java.io.Serializable;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Blob implements Serializable {
    private byte[] content;
    private String filename;
//    private File file;

    public Blob(File file) {
        content = Utils.readContents(file);
        filename = file.getName();
//        this.file = file;
    }

    public byte[] getContent() {
        return content;
    }

    public String getFilename() {
        return filename;
    }

    public String getSHA() {
//        return Utils.sha1(content);
        File newFile = serialize("./.gitlet/temp/");
        String hash = newFile.getName();
        newFile.delete();
        return hash;
    }

//    public File getFile() {
//        return this.file;
//    }

    public File serialize(String gitletDirectory) {
        try {
            String path = gitletDirectory + Utils.sha1(content);
            File outFile = new File(path);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(this);
            out.close();
            File newOut = new File(gitletDirectory + Utils.sha1(Utils.readContents(outFile)));
            newOut.createNewFile();
            Utils.writeContents(newOut, Utils.readContents(outFile));
            outFile.delete();
            return newOut;
        } catch (IOException excp) {
            System.out.println("1Error!");
            return null;
        }
    }

    public static Blob deserialize(String path) {
        try {
            Blob myBlob;
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(path)));
            myBlob = (Blob) in.readObject();
            in.close();
            return myBlob;
        } catch (IOException i) {
//            i.printStackTrace();
            System.out.println("2Error!");
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Blob class not found");
//            c.printStackTrace();
            return null;
        }
    }

}
