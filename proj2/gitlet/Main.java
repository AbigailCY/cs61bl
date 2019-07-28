package gitlet;

import java.io.File;
import java.io.IOException;

/* Driver class for Gitlet, the tiny stupid version-control system.
   @author
*/

public class Main {
    /* Usage: java gitlet.Main ARGS, where ARGS contains
       <COMMAND> <OPERAND> .... */
    public static void logBlock(String[] args) throws IOException {
        File gitletFile = new File("./.gitlet/");
        if (!gitletFile.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            return;
        }

        Gitlet myGit = Gitlet.deserialize();
        myGit.log();
        Gitlet.serialize(myGit);
    }

    public static void statusBlock(String[] args) throws IOException {
        File gitletFile = new File("./.gitlet//");
        if (!gitletFile.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            return;
        }

        Gitlet myGit = Gitlet.deserialize();
        myGit.status();
        Gitlet.serialize(myGit);
    }

    public static void commitBlock(String[] args) throws IOException {
        File gitletFile = new File("./.gitlet/");
        if (!gitletFile.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            return;
        }
        if (args.length == 1 || args[1].equals("")) {
            System.out.println("Please enter a commit message.");
            return;
        }

        String message = args[1];
        Gitlet myGit = Gitlet.deserialize();
        myGit.commit(message);
        Gitlet.serialize(myGit);

    }

    public static void rmBlock(String[] args) throws IOException {
        File gitletFile = new File("./.gitlet/");
        if (!gitletFile.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            return;
        }
        if (args.length == 1 || args[1].equals("")) {
            System.out.println("Incorrect operands.");
            return;
        }

        String message = args[1];
        Gitlet myGit = Gitlet.deserialize();
        myGit.rm(message);
        Gitlet.serialize(myGit);
    }

    public static void findBlock(String[] args) throws IOException {
        File gitletFile = new File("./.gitlet/");
        if (!gitletFile.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            return;
        }
        if (args.length == 1 || args[1].equals("")) {
            System.out.println("Incorrect operands.");
            return;
        }

        String message = args[1];
        Gitlet myGit = Gitlet.deserialize();
        myGit.find(message);
        Gitlet.serialize(myGit);
    }

    public static void checkoutBlock(String[] args) throws IOException {
        File gitletFile = new File("./.gitlet/");
        if (!gitletFile.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            return;
        }
        if (args.length == 1 || args[1].equals("")) {
            System.out.println("Incorrect operands.");
            return;
        }


        if (args[1].equals("--") && args.length == 3) {
            String finalArgs = args[2];
            Gitlet myGit = Gitlet.deserialize();
            myGit.checkout(finalArgs, 0);
            Gitlet.serialize(myGit);

        } else if (args.length == 4 && args[2].equals("--")) {
            Gitlet myGit = Gitlet.deserialize();
            myGit.checkout(args[1], args[3]);
            Gitlet.serialize(myGit);
        } else if (args.length == 2 && !(args[1].equals("--"))) {
            Gitlet myGit = Gitlet.deserialize();
            myGit.checkout(args[1]);
            Gitlet.serialize(myGit);
        } else {
            System.out.println("Incorrect operands.");
            return;
        }
    }

    public static void resetBlock(String[] args) throws IOException {
        File gitletFile = new File("./.gitlet/");
        if (!gitletFile.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            return;
        }
        if (args.length == 1 || args[1].equals("")) {
            System.out.println("Incorrect operands.");
            return;
        }

        String message = args[1];
        Gitlet myGit = Gitlet.deserialize();
        myGit.reset(message);
        Gitlet.serialize(myGit);
    }

    public static void branchBlock(String[] args) throws IOException {
        File gitletFile = new File("./.gitlet/");
        if (!gitletFile.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            return;
        }
        if (args.length == 1 || args[1].equals("")) {
            System.out.println("Incorrect operands.");
            return;
        }

        String message = args[1];
        Gitlet myGit = Gitlet.deserialize();
        myGit.branch(message);
        Gitlet.serialize(myGit);
    }

    public static void mergeBlock(String[] args) throws IOException {
        File gitletFile = new File("./.gitlet/");
        if (!gitletFile.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            return;
        }
        if (args.length == 1 || args[1].equals("")) {
            System.out.println("Incorrect operands.");
            return;
        }

        String message = args[1];
        Gitlet myGit = Gitlet.deserialize();
        myGit.merge(message);
        Gitlet.serialize(myGit);
    }

    public static void main(String[] args) throws IOException {
//        args = args.strip();
        if (args.length < 1) {
            System.out.println("Please enter a command.");
        }
        if (args[0].equals("init")) {
            Gitlet myGit = new Gitlet();
            myGit.init();
            Gitlet.serialize(myGit);
            return;
        }
        if (args[0].equals("log")) {
            logBlock(args);
        } else if (args[0].equals("global-log")) {
            File gitletFile = new File("./.gitlet/");
            if (!gitletFile.exists()) {
                System.out.println("Not in an initialized gitlet directory.");
                return;
            }

            Gitlet myGit = Gitlet.deserialize();
            myGit.globalLog();
            Gitlet.serialize(myGit);

        } else if (args[0].equals("status")) {
            statusBlock(args);
        } else if (args[0].equals("add")) {
            File gitletFile = new File("./.gitlet/");
            if (!gitletFile.exists()) {
                System.out.println("Not in an initialized gitlet directory.");
                return;
            }
            if (args.length == 1 || args[1].equals("")) {
                System.out.println("Incorrect operands.");
                return;
            }
            String message = args[1];
            Gitlet myGit = Gitlet.deserialize();
            myGit.add(message);
            Gitlet.serialize(myGit);
        } else if (args[0].equals("commit")) {
            commitBlock(args);
        } else if (args[0].equals("rm")) {
            rmBlock(args);
        } else if (args[0].equals("find")) {
            findBlock(args);
        } else if (args[0].equals("checkout")) {
            checkoutBlock(args);
        } else if (args[0].equals("branch")) {
            branchBlock(args);
        } else if (args[0].equals("rm-branch")) {
            File gitletFile = new File("./.gitlet/");
            if (!gitletFile.exists()) {
                System.out.println("Not in an initialized gitlet directory.");
                return;
            }
            if (args.length == 1 || args[1].equals("")) {
                System.out.println("Incorrect operands.");
                return;
            }

            String message = args[1];
            Gitlet myGit = Gitlet.deserialize();
            myGit.rmBranch(message);
            Gitlet.serialize(myGit);
        } else if (args[0].equals("reset")) {
            resetBlock(args);
        } else if (args[0].equals("merge")) {
            mergeBlock(args);
        } else {
            System.out.println("No command with that name exists.");
        }
    }

}
