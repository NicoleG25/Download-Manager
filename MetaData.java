public class MetaData {
    private long[] progress;
    private long fileSize;
    public String fileName;


    public MetaData() {

    }

    public void updateProgress(int index, int bytes) {
        this.progress[index] += bytes;


    }

    public long getFileSize() {
        return this.fileSize;
    }

}
