package epam.concurrency.relealise;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public class FolderProcessor {
    public ExecutorService ex;
    private long size;

    public FolderProcessor(ExecutorService ex){

        this.ex= ex;
    }

    public Long processFolder(String inputPath){
        File inputFolder = new File(inputPath);
        AtomicReference<Long> totalSizeThread = new AtomicReference<>(0L);

        for(String filename : inputFolder.list()){
            final String filePath = inputFolder.toPath().resolve(filename).toString();

            if (new File(filePath).isDirectory()){

                ex.execute(() -> processFolder(filePath));
            }else {
                ex.execute(()->{
                    Path path = Paths.get(filePath);
                    try {
                        size = Files.size(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    totalSizeThread.set(totalSizeThread.get() + size);
                });
            }
        }return totalSizeThread.get();
    }
}
