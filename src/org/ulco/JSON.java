package org.ulco;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JSON {
    static public GraphicsObject parse(String json) {
        GraphicsObject o = null;
        String str = json.replaceAll("\\s+", "");
        String type = str.substring(str.indexOf("type") + 5, str.indexOf(","));

        String classToInstance = type.substring(0, 1).toUpperCase() + type.substring(1);
        Object o2 = null;
        try {
            Class cl = Class.forName("org.ulco." + classToInstance);
            Constructor ct = cl.getConstructor(String.class);
            o2 = ct.newInstance(json);
        } catch (InvocationTargetException | IllegalArgumentException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (GraphicsObject) o2;
    }

    static public Group parseGroup(String json) {
        return new Group(json);
    }

    static public Layer parseLayer(String json) {
        return new Layer(json);
    }

    static public Document parseDocument(String json) {
        return new Document(json);
    }
}
