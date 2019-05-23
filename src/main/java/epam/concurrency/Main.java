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
        System.out.println(third.processFolder(path.toString()));


        FolderSizeNewThread newThread = new FolderSizeNewThread(path);
        Long aLong = newThread.folderSizer();
        System.out.println(IFolderSizeUtil.clearView(aLong));
    }
}
