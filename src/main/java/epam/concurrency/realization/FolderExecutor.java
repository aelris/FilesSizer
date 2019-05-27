package epam.concurrency.realization;

import epam.concurrency.FolderSizerEngine;
import epam.concurrency.IFolderSizeUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;


public class FolderExecutor extends FolderSizerEngine implements IFolderSizeUtil {
   private Long sum = 0L;

    public FolderExecutor(Path path) {
        super(path);
    }

    @Override
    public Long folderSizer() {
        Long aLong = null;
        try {
            aLong = processFolder(super.path.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aLong;
    }

    private Long processFolder(String inputPath) {
        ExecutorService ex = Executors.newFixedThreadPool(8);
        File inputFolder = new File(inputPath);
        System.out.println(inputFolder.list().length);


        for (String filename : inputFolder.list()) {
            System.out.println(filename);
            filename = inputPath + "\\" + filename;
            System.out.println(filename);
            if (new File(filename).isDirectory()) {
                Long aLong = processFolder(filename);
                sum+= aLong ;

            }

            else {
                Future<Long> future = ex.submit(new CallC(filename));
               // sum.updateAndGet(v -> {
                    try {
                        return sum += future.get();
                    } catch (InterruptedException | ExecutionException ignored) {
                        ignored.printStackTrace();
                        return sum;
                    }
              //  });
            }

        }

        ex.shutdown();
        return sum;
    }


}
