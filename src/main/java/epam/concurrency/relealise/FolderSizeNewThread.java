package epam.concurrency.relealise;

import epam.concurrency.FolderSizerEngine;
import epam.concurrency.IFolderSizeUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;


public class FolderSizeNewThread extends FolderSizerEngine implements IFolderSizeUtil {

    public FolderSizeNewThread(Path path) {
        super(path);
    }

    @Override
    public Long folderSizer() {
        try {
            return countTotalSize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    private Long countTotalSize() throws IOException {
        final long[] totalSize = {0L};

        Files.walk(path)
                .forEach(path -> {
                    AtomicLong totalSizeThread = new AtomicLong(0L);

                    new Thread(() -> {
                        File file = new File(String.valueOf(path));
                        if (!file.isDirectory()) {
                            long size = 0;
                            try {
                                size = Files.size(path);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            totalSizeThread.set(totalSizeThread.get() + size);
                        }
                    }).run();
                    totalSize[0] = totalSize[0] + totalSizeThread.get();
                });
        return totalSize[0];
    }
}
