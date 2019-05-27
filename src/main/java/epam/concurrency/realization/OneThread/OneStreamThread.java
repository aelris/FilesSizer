package epam.concurrency.realization.OneThread;

import epam.concurrency.FolderSizerEngine;
import epam.concurrency.IFolderSizeUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class OneStreamThread extends FolderSizerEngine implements IFolderSizeUtil {


    public OneStreamThread(Path path) {
        super(path);
    }

    @Override
    public Long folderSizer() {
        return sizeOfFiles();
    }

    private Stream<Path> listOfFiles() {
        Stream<Path> pathStream = null;
        try {
            pathStream = Files.walk(path).filter(Files::isRegularFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathStream;
    }

    private Long sizeOfFiles() {
        Comparable<? extends Comparable<?>> reduce = listOfFiles().map(x -> {
            try {
                return Files.size(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return x;
        }).reduce((aLong, aLong2) -> (Long) aLong + (Long) aLong2)
                .get();
        return (Long) reduce;
    }
}

