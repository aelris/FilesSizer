package epam.concurrency;

import epam.concurrency.realization.FolderExecutor;
import epam.concurrency.realization.FolderSizeNewThread;

import epam.concurrency.realization.OneThread.OneStreamThread;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        System.out.println("Please enter path (ex: C:\\Users\\Dependency\\IdeaProjects\\FilesSizer)");
        Scanner scanner = new Scanner(System.in);
        String scannedTxt = scanner.nextLine();
        Path path = Paths.get(scannedTxt);
//
        IFolderSizeUtil byOneThread = new OneStreamThread(path);
        IFolderSizeUtil byNThreads = new FolderSizeNewThread(path);
        IFolderSizeUtil byExecutorService = new FolderExecutor(path);

        System.out.println("One thread");
        //System.out.println(IFolderSizeUtil.clearViewKB(byOneThread.folderSizer()));
        System.out.println("With Threads");
       // System.out.println(IFolderSizeUtil.clearViewKB(byNThreads.folderSizer()));
        System.out.println("With Executor");
        System.out.println(IFolderSizeUtil.clearViewKB(byExecutorService.folderSizer()));
    }
}
