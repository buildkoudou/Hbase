package HbaseData.FileUtilClass;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class FileSize {

    private   ForkJoinPool forkJoinPool = new ForkJoinPool();

    private  class FileSizeFinder extends RecursiveTask<Long> {

        final File file;

        public FileSizeFinder(File theFile) {
            file = theFile;
        }

        @Override
        public Long compute() {
            long size = 0;
            if (file.isFile()) {
                size = file.length();
            } else {
                final File[] children = file.listFiles();
                if (children != null) {
                    List<ForkJoinTask<Long>> tasks = new ArrayList<>();
                    for ( File child : children) {
                        if (child.isFile()) {
                            size += child.length();
                        } else {
                            tasks.add(new FileSizeFinder(child));
                        }
                    }
                    for ( ForkJoinTask<Long> task : invokeAll(tasks)) {
                        size += task.join();
                    }
                }
            }
            return size;
        }
    }

    public  long getfilesize(String  path) {
      //  final long start = System.nanoTime();
        final long total = forkJoinPool.invoke(new FileSizeFinder(new File(path)));
  //     final long end = System.nanoTime();
        System.out.println("Total Size: " + total / 1024 / 1024 + "mb");
       // System.out.println("Time taken: " + (end - start) / 1.0e9);
        return total;
    }
}
