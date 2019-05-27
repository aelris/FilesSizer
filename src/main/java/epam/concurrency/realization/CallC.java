package epam.concurrency.realization;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

class CallC implements Callable<Long> {

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