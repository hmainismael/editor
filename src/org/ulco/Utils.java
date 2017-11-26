package org.ulco;

import java.util.Vector;

public class Utils {

    public static GraphicsObjects selectLayer(Layer layer, Point pt, double distance) {
        GraphicsObjects list = new GraphicsObjects();
        for (GraphicsObject object : layer.getListe()) {
            if (object.isClosed(pt, distance)) {
                list.add(object);
            }
        }
        return list;
    }

    public static GraphicsObjects select(Document document, Point pt, double distance) {
        GraphicsObjects list = new GraphicsObjects();

        for (Layer layer : document.getLayers()) {
            list.addAll(Utils.selectLayer(layer, pt, distance));
        }
        return list;
    }

    public static Document createGridWithSquares(Point origin, int line, int column, double length) {
        Document document  = new Document();
        Layer layer = document.createLayer();

        for (int indexX = 0; indexX < column; ++indexX) {
            for (int indexY = 0; indexY < line; ++indexY) {
                layer.add(new Square(new Point(origin.getX() + indexX * length, origin.getY() + indexY * length), length));
            }
        }

        return document;
    }

    public static Document createGridWithConcentricCercles(Point center, int number, double radius, double delta) {
        Document document  = new Document();
        Layer layer = document.createLayer();

        for (int index = 0; index < number; ++index) {
            layer.add(new Circle(center, radius + index * delta));
        }

        return document;
    }
}
