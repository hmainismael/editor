package org.ulco;

public class MathUtils {

    public static boolean isClosed(Point center, Point pt, double distance){
       return Math.sqrt((center.getX() - pt.getX()) * (center.getX() - pt.getX()) +
                ((center.getY() - pt.getY()) * (center.getY() - pt.getY()))) <= distance;
    }
}
