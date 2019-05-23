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
import java.util.concurrent.atomic.AtomicReference;

public class FolderExecutor extends FolderSizerEngine implements IFolderSizeUtil {
  AtomicReference<Long> sum = new AtomicReference<>(0L);
  Long sumLong = 0L;
  static String pathStat = "";

  public FolderExecutor(Path path) {
    super(path);
    pathStat = String.valueOf(path);
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
        sumLong += aLong;
      } else {
        Future<Long> future = ex.submit(new CallC(filename));
        futureList.add(future);
      }
    }

    futureList.forEach(
        longFuture -> {
          if (longFuture != null)
            try {
              Long aLong = longFuture.get();
              sum.updateAndGet(v -> v + aLong);
            } catch (InterruptedException | ExecutionException e) {
              e.printStackTrace();
            }
        });

    ex.shutdown();
    return sum.get();
  }

  static class CallC implements Callable<Long> {

    String pathIn;

    public CallC(String pathIn) {
      this.pathIn = pathIn;
    }

    @Override
    public Long call() throws Exception {
      Path path = Paths.get(this.pathIn);

      return Files.size(path);
    }
  }
}
