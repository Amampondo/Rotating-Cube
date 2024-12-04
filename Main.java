import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        // some points pairs
        double[][][] lines = {
                {
                        {-1.0,-1.0,1.0}, {1.0,-1.0,1.0}
                },
                {
                        {-1.0,-1.0,-1.0}, {1.0,-1.0,-1.0}
                },
                {
                        {-1.0,1.0,1.0} ,{1.0,1.0,1.0}
                },
                {
                        {-1.0,1.0,-1.0},{1.0,1.0,-1.0}
                },
                //
                {
                        {-1.0,-1.0,1.0}, {-1.0,-1.0,-1.0}
                },
                {
                        {1.0,-1.0,1.0} , {1.0,-1.0,-1.0}
                },
                {
                        {-1.0,1.0,1.0} ,{-1.0,1.0,-1.0}
                },
                {
                        {1.0,1.0,1.0},{1.0,1.0,-1.0}
                },
                //
                {
                        {-1.0,-1.0,1.0}, {-1.0,1.0,1.0}
                },
                {
                        {1.0,-1.0,1.0} , {1.0,1.0,1.0}
                },
                {
                        {-1.0,-1.0,-1.0} ,{-1.0,1.0,-1.0}
                },
                {
                        {1.0,-1.0,-1.0},{1.0,1.0,-1.0}
                },
        };

        printLines(lines);
    }


    public static void printLines(double[][][] lines){
        // tranform points
        final double delta = Math.PI/100.0 ;
        double a = delta ;

        double[][][] newLines = lines;

        int start = -10 , end = 10 ;
        StdDraw.setScale(start,end);

        Random random = new Random();

        Color[] colors = {StdDraw.GRAY,StdDraw.BLACK,StdDraw.BOOK_RED,StdDraw.YELLOW} ;

        while (a < 10000){
            double z = a , x = a ;
            double mX = StdDraw.mouseX() ;
            double mY = StdDraw.mouseX() ;
            System.out.println(mX);
            if ( mX == 0 && mY == 0){

            }else{

                if(mX < -1.0){
                    z = a*-2.0 ;
                    x = 0 ;
                }
                if(mX > 1.0){
                    z = a*2.0 ;
                    x= 0 ;
                }
            }

            newLines = transform(newLines , x , 0 , z);
            //a +=delta ;

            // display
            int color = 0 ;
            try {
                for (double[][] line : newLines){

                    StdDraw.setPenRadius(0.003);
                    StdDraw.setPenColor(colors[color]);
                    StdDraw.line(line[0][0], line[0][1], line[1][0], line[1][1]);
                    if (color < 3){
                        color ++ ;
                    }
                    else{
                        color = 0 ;
                    }
                }
                TimeUnit.MILLISECONDS.sleep(10);
                StdDraw.clear();
            }catch (InterruptedException e){
                System.err.format("IOException: %s%n", e);
            }


        }
    }

    public static double[][][] transform(double[][][] vectors , double xRotation , double yRotation , double zRotation ){

        double[][][] newVectors = vectors.clone() ;

        int i = 0 ;

        for(double[][] pair : vectors){
             int j = 0 ;
            for ( double[] v : pair){
                double[] vector = v ;

                if ( xRotation != 0 ){
                    vector = rotateX(vector, xRotation);
                }
                if ( yRotation != 0 ){
                    vector = rotateY(vector , yRotation);
                }
                if ( zRotation != 0 ){
                    vector = rotateZ(vector , zRotation);
                }
                newVectors[i][j] = vector ;

                j++ ;
            };
            i++ ;
        };
        return newVectors ;
    }

    public static double[] mxv(double[][] A , double[] B){

        double x , y , z ;
        double[] newV = {0.0,0.0,0.0} ;
        int j = 0 ;
        for (double s : B){
            int row = 0 ;
            for (double[] r : A){
                newV[row] += r[j] * s ;
                //System.out.println(r[j]);
                row ++ ;
            }
            j ++ ;
        }
        return newV ;
    }
    public static double[] rotateX(double[] vector , double theta){

        double rAlpha = Math.PI/2.0 + theta ;
        double[][] transformationM = {
                {1.0 , 0 , 0},
                {0.0 , Math.sin(rAlpha) ,Math.sin(theta)},
                {0.0 ,Math.cos(rAlpha) , Math.cos(theta)}
        };
        double[] t = mxv(transformationM,vector);
        return t;
    }

    public static double[] rotateY(double[] vector , double theta){

        double rAlpha = Math.PI/2.0 + theta ;
        double[][] transformationM = {
                { Math.cos(theta) , 0.0 ,Math.cos(rAlpha) },
                {0.0 , 1.0 ,0.0},
                {Math.sin(theta), 0.0 , Math.sin(rAlpha)}

        };

        return mxv(transformationM,vector);
    }
    public static double[] rotateZ(double[] vector , double theta){

        double rAlpha = Math.PI/2.0 + theta ;
        double[][] transformationM = {
                {Math.sin(rAlpha),Math.sin(theta),0.0},
                {Math.cos(rAlpha),Math.cos(theta),0.0},
                {0.0,0.0,1.0},
        };

        return mxv(transformationM,vector);
    }
}