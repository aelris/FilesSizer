package epam.concurrency;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\Dependency\\IdeaProjects\\FilesSizer");
        OneStreamThread first = new OneStreamThread(path);

        System.out.println(IFolderSizeUtil.clearView(first.folderSizer()));
    }
}
