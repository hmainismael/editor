package org.ulco;

import java.util.Vector;

public class Layer {
    public Layer() {
        m_list = new Vector<GraphicsObject>();
        m_ID = ID.getInstance().incrementId();
    }

    public Layer(String json) {
        m_list= new Vector<GraphicsObject>();
        String str = json.replaceAll("\\s+","");
        int objectsIndex = str.indexOf("objects");
        int groupsIndex = str.indexOf("groups");
        int endIndex = str.lastIndexOf("}");

        if (groupsIndex != -1) {
            parseObjects(str.substring(objectsIndex + 9, groupsIndex - 2));
            parseGroups(str.substring(groupsIndex + 8, endIndex - 1));
        } else {
            parseObjects(str.substring(objectsIndex + 9, endIndex - 1));
        }
    }

    public void add(GraphicsObject o) {
        m_list.add(o);
    }

    public GraphicsObject get(int index) {
        return m_list.elementAt(index);
    }

    public int getObjectNumber() {
        return m_list.size();
    }

    public int getID() {
        return m_ID;
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
            m_list.add(JSON.parse(objectStr));
            if (separatorIndex == -1) {
                objectsStr = "";
            } else {
                objectsStr = objectsStr.substring(separatorIndex + 1);
            }
        }
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
            m_list.add(JSON.parseGroup(groupStr));
            if (separatorIndex == -1) {
                groupsStr = "";
            } else {
                groupsStr = groupsStr.substring(separatorIndex + 1);
            }
        }
    }

    private int searchSeparator(String str) {
        int index = 0;
        int level = 0;
        boolean found = false;

        while (!found && index < str.length()) {
            if (str.charAt(index) == '{') {
                ++level;
                ++index;
            } else if (str.charAt(index) == '}') {
                --level;
                ++index;
            } else if (str.charAt(index) == ',' && level == 0) {
                found = true;
            } else {
                ++index;
            }
        }
        if (found) {
            return index;
        } else {
            return -1;
        }
    }

    public Vector<GraphicsObject> getListe() {
        return m_list;
    }

    public String toJson() {
        String str = "{ type: layer, objects : { ";
        String strObjects = "";
        String strGroups = "";

        for (int i = 0; i < m_list.size(); ++i) {
            GraphicsObject element = m_list.elementAt(i);
            if(!element.isGroup()) {
                strObjects += element.toJson();
                if (i < m_list.size() - 1) strObjects += ", ";
            } else {
                strGroups += element.toJson();
            }
        }

        boolean presenceObjects = !strObjects.equals("");
        boolean presenceGroups = !strGroups.equals("");
        if(presenceObjects && presenceGroups) strObjects = strObjects.substring(0, strObjects.length() -2);
        if(presenceGroups) strGroups = " }, groups : { " + strGroups;

        return str + strObjects + strGroups + " } }";
    }

    private Vector<GraphicsObject> m_list;
    private int m_ID;
}
