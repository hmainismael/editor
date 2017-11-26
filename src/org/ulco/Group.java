package org.ulco;

import java.util.Vector;

public class Group extends GraphicsObject {

    public Group() {
        super();
        m_objectList = new Vector<>();
    }

    public Group(String json) {
        m_objectList = new Vector<>();
        String str = json.replaceAll("\\s+","");
        int objectsIndex = str.indexOf("objects");
        int groupsIndex = str.indexOf("groups");
        int endIndex = str.lastIndexOf("}");

        parseObjects(str.substring(objectsIndex + 9, groupsIndex - 2));
        parseGroups(str.substring(groupsIndex + 8, endIndex - 1));
    }

    public void add(Object object) {
        if (object instanceof GraphicsObject) {
            m_objectList.add((GraphicsObject) object);
        }
    }

    public Group copy() {
        Group g = new Group();

        for (Object o : m_objectList) {
            GraphicsObject element = (GraphicsObject) (o);

            g.add(element.copy());
        }

        return g;
    }

    @Override
    public void move(Point delta) {
        for (Object o : m_objectList) {
            GraphicsObject element = (GraphicsObject) (o);

            element.move(delta);
        }
    }

    private int searchSeparator(String str) {
        return Utils.searchSeparator(str);
    }

    private void parseGroups(String groupsStr) {
        while (!groupsStr.isEmpty()) {
            int separatorIndex = searchSeparator(groupsStr);
            String groupStr;

            if (separatorIndex == -1) {
                groupStr = groupsStr;
            } else {
                groupStr = groupsStr.substring(0, separatorIndex);
            }
            m_objectList.add(JSON.parseGroup(groupStr));
            if (separatorIndex == -1) {
                groupsStr = "";
            } else {
                groupsStr = groupsStr.substring(separatorIndex + 1);
            }
        }
    }

    private void parseObjects(String objectsStr) {
        while (!objectsStr.isEmpty()) {
            int separatorIndex = searchSeparator(objectsStr);
            String objectStr;

            if (separatorIndex == -1) {
                objectStr = objectsStr;
            } else {
                objectStr = objectsStr.substring(0, separatorIndex);
            }
            m_objectList.add(JSON.parse(objectStr));
            if (separatorIndex == -1) {
                objectsStr = "";
            } else {
                objectsStr = objectsStr.substring(separatorIndex + 1);
            }
        }
    }

    @Override
    public int size() {
        int size = 0;
        int iteration;
        for(iteration = 0; iteration < m_objectList.size(); iteration++){
            size += m_objectList.elementAt(iteration).size();
        }
        return size;
    }

    @Override
    public String toJson() {
        String str = "{ type: group, objects : { ";

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            if(!element.isGroup()) {
                str += element.toJson();
                if (i < m_objectList.size() - 1) {
                    str += ", ";
                }
            }
        }
        str += " }, groups : { ";

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            if(element.isGroup()) {
                str += element.toJson();
            }
        }
        return str + " } }";
    }

    @Override
    public String toString() {
        int nbGroup = 0;
        for(int i = 0; i<m_objectList.size(); i++){
            if(m_objectList.elementAt(i).isGroup()){
                nbGroup += 1;
            }
        }

        String str = "group[[";

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            if(!element.isGroup()) {
                str += element.toString();
                if (i < m_objectList.size() - nbGroup - 1) {
                    str += ", ";
                }
            }
        }
        str += "],[";

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            if(element.isGroup()) {
                str += element.toString();
            }
        }
        return str + "]]";
    }

    @Override
    public boolean isGroup() {
        return !super.isGroup();
    }

    @Override
    public boolean isClosed(Point pt, double distance){
        int iteration = 0;
        boolean isClosed = false;

        while( !isClosed || iteration != m_objectList.size()){
            isClosed = m_objectList.elementAt(iteration).isClosed(pt,distance);
            iteration += 1;
        }
        return true;
    }

    private Vector<GraphicsObject> m_objectList;
}
