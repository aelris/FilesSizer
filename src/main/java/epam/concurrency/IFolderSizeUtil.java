package epam.concurrency;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface IFolderSizeUtil {

  Long folderSizer();

  @NotNull
  @Contract(pure = true)
  static String clearViewMB(Long sizeInBytes) {
      return (sizeInBytes / 1024 / 1024) + "MB";
  }

  @NotNull
  @Contract(pure = true)
  static String clearViewKB(Long sizeInBytes) {
    return (sizeInBytes ) + "bytes";
  }
}
