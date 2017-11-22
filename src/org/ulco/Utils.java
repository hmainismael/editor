package org.ulco;

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
}
