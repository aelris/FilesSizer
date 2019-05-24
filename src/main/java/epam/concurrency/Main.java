package epam.concurrency;

import epam.concurrency.relealise.FolderExecutor;
import epam.concurrency.relealise.FolderSizeNewThread;
import epam.concurrency.relealise.OneStreamThread;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        System.out.println("Please enter path (ex: C:\\Users\\Dependency\\IdeaProjects\\FilesSizer)");
        Scanner s = new Scanner(System.in);
        String sc = s.nextLine();
        Path path = Paths.get(sc);
//
        IFolderSizeUtil folderSizeUtil1 = new OneStreamThread(path);
        IFolderSizeUtil folderSizeUtil2 = new FolderSizeNewThread(path);
        IFolderSizeUtil folderSizeUtil3 = new FolderExecutor(path);

        System.out.println("One thread");
        System.out.println(IFolderSizeUtil.clearViewKB(folderSizeUtil1.folderSizer()));
        System.out.println("With Threads");
        System.out.println(IFolderSizeUtil.clearViewKB(folderSizeUtil2.folderSizer()));
        System.out.println("With Executor");
        System.out.println(IFolderSizeUtil.clearViewKB(folderSizeUtil3.folderSizer()));
    }
}
