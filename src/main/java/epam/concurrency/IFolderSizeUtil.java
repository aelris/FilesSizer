package epam.concurrency;

public interface IFolderSizeUtil {
    Long folderSizer();

    static String clearView(Long sizeInBytes) {
    return (sizeInBytes / 1024) + "KB";
    }
}

