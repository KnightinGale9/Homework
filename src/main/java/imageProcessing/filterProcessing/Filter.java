package imageProcessing.filterProcessing;

public interface Filter {

    public int[][] getMask();
    public int getPixel(int i,int j);
    public int calculate(int[][] mask);
}
