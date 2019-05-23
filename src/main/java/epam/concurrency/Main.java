package epam.concurrency;

import epam.concurrency.relealise.FolderProcessor;
import epam.concurrency.relealise.FolderSizeNewThread;
import epam.concurrency.relealise.OneStreamThread;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\Dependency\\IdeaProjects\\FilesSizer");
        ExecutorService ex = Executors.newFixedThreadPool(4);
        OneStreamThread first = new OneStreamThread(path);
        FolderProcessor third = new FolderProcessor(ex);

        System.out.println(IFolderSizeUtil.clearView(first.folderSizer()));
//
        System.out.println(third.processFolder(path.toString()));


        FolderSizeNewThread newThread = new FolderSizeNewThread(path);
        Long aLong = newThread.folderSizer();
        System.out.println(IFolderSizeUtil.clearView(aLong));
    }
}
