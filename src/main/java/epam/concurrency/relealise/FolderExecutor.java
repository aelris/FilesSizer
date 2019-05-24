package epam.concurrency.relealise;

import epam.concurrency.FolderSizerEngine;
import epam.concurrency.IFolderSizeUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;


public class FolderExecutor extends FolderSizerEngine implements IFolderSizeUtil {
    AtomicLong sum = new AtomicLong(0L);

    public FolderExecutor(Path path) {
        super(path);
    }

    @Override
    public Long folderSizer() {
        Long aLong = null;
        try {
            aLong = processFolder(String.valueOf(super.path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aLong;
    }


    public Long processFolder(String inputPath) {
        ExecutorService ex = Executors.newFixedThreadPool(4);
        File inputFolder = new File(inputPath);
        List<Future<Long>> futureList = new ArrayList<>();

        for (String filename : inputFolder.list()) {
            filename = inputPath + "\\" + filename;

            if (new File(filename).isDirectory()) {
                Long aLong = processFolder(filename);
                sum.getAndSet(aLong);
            } else {

                Future<Long> future = ex.submit(new CallC(filename));
                futureList.add(future);
                sum.updateAndGet(v -> {
                    try {
                        return v + future.get();
                    } catch (InterruptedException | ExecutionException ignored) {
                    }
                    return sum.get();
                });

            }
        }

        ex.shutdown();
        return sum.get();
    }

    static class CallC implements Callable<Long> {

        String pathIn;

        CallC(String pathIn) {
            this.pathIn = pathIn;
        }

        @Override
        public Long call() throws Exception {
            Path path = Paths.get(this.pathIn);

            return Files.size(path);
        }
    }
}
