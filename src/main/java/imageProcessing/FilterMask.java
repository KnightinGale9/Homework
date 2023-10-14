package imageProcessing;

import imageProcessing.filterProcessing.*;

import java.util.Arrays;

public class FilterMask {
    private Filter filter;
    public  FilterMask(String type,int ... var)
    {
        if(type.equals("box"))
        {
            filter = new BoxFilter(var[0]);
        }
        else if(type.equals("weightedAverage"))
        {
            filter = new WeightedAverageFilter(var[0],var[1]);
        }
        else if(type.equals("median"))
        {
            filter = new MedianFilter(var[0]);
        }
        else if(type.equals("sharpening"))
        {
            filter = new SharpeningFilter(var[0],var[1],var[2]);
        }
        System.out.println(type+ ":"+Arrays.toString(var));
    }
    public int[][] padding(int[][] original, int pad)
    {
        int[][] padImage = new int[original.length+(2*pad)][original.length+(2*pad)];
        for(int i =0;i< original.length;i++)
        {
            System.arraycopy(original[i],0,padImage[i+pad],pad,original[i].length);
        }
        for(int i =0;i< pad;i++) {
            System.arraycopy(original[0],0,padImage[i],pad,original[0].length);
            System.arraycopy(original[original.length-1],0,padImage[original.length+i+pad],pad,original[original.length-1].length);
        }
        for(int i=0;i<padImage.length;i++)
        {
            for(int j=0;j<pad;j++)
            {
                padImage[i][j]=padImage[i][pad];
                padImage[i][padImage.length-pad+j]=padImage[i][padImage.length-pad-1];
            }
        }
        System.out.println(padImage.toString());
        return padImage;
    }
    public void runFilter(int[][] original, int[][]transform,int mask)
    {
        int[][] localMask = new int[mask][mask];
        int[][] temp = padding(original, mask/2);
        for(int i=mask/2,i2=0;i< temp.length-mask/2;i++,i2++) {
            for (int j = mask / 2, j2 = 0; j < temp.length - mask / 2; j++, j2++) {
                for(int mi=0,offset=-mask/2; mi< localMask.length;mi++,offset++) {
                    System.arraycopy(temp[i+offset],j-mask/2,localMask[mi],0,localMask.length);
                }
                transform[i2][j2]=filter.calculate(localMask);
            }
        }
    }
}