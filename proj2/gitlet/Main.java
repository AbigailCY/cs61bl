package gitlet;

import java.io.File;
import java.io.IOException;

/* Driver class for Gitlet, the tiny stupid version-control system.
   @author
*/

public class Main {
    /* Usage: java gitlet.Main ARGS, where ARGS contains
       <COMMAND> <OPERAND> .... */
    public static void log_block(String[] args) throws IOException {
        File gitletFile = new File("./.gitlet/");
        if (!gitletFile.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            return;
        }

        Gitlet myGit = Gitlet.deserialize();
        myGit.log();
        Gitlet.serialize(myGit);
    }

    public static void status_block(String[] args) throws IOException {
        File gitletFile = new File("./.gitlet//");
        if (!gitletFile.exists()) {
            System.out.println("Not in an initialized gitlet directory.");
            return;
        }

        Gitlet myGit = Gitlet.deserialize();
        myGit.status();
        Gitlet.serialize(myGit);
    }

    public static void commit_block(String[] args) throws IOException {
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

    public static void rm_block(String[] args) throws IOException {
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

    public static void find_block(String[] args) throws IOException {
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

    public static void checkout_block(String[] args) throws IOException {
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

    public static void reset_block(String[] args) throws IOException {
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

    public static void branch_block(String[] args) throws IOException {
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

    public static void merge_block(String[] args) throws IOException {
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
            log_block(args);
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
            status_block(args);
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
            commit_block(args);
        } else if (args[0].equals("rm")) {
            rm_block(args);
        } else if (args[0].equals("find")) {
            find_block(args);
        } else if (args[0].equals("checkout")) {
            checkout_block(args);
        } else if (args[0].equals("branch")) {
            branch_block(args);
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
            reset_block(args);
        } else if (args[0].equals("merge")) {
            merge_block(args);
        } else {
            System.out.println("No command with that name exists.");
        }
    }

}