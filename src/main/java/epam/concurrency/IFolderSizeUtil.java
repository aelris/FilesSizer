package epam.concurrency;

public interface IFolderSizeUtil {
  Long folderSizer();

  static String clearViewMD(Long sizeInBytes) {
      return (sizeInBytes / 1024 / 1024) + "MD";
  }

  static String clearViewKB(Long sizeInBytes) {
    return (sizeInBytes / 1024) + "KB";
  }
}
