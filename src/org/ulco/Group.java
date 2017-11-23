package org.ulco;

import java.util.Vector;

public class Group extends GraphicsObject {

    public Group() {
        m_objectList = new Vector<GraphicsObject>();
        m_ID = ID.getInstance().getID();
    }

    public Group(String json) {
        m_objectList = new Vector<GraphicsObject>();
        String str = json.replaceAll("\\s+","");
        int objectsIndex = str.indexOf("objects");
        int groupsIndex = str.indexOf("groups");
        int endIndex = str.lastIndexOf("}");

        parseObjects(str.substring(objectsIndex + 9, groupsIndex - 2));
        parseGroups(str.substring(groupsIndex + 8, endIndex - 1));
    }

    public void add(GraphicsObject object) {
        m_objectList.add(object);
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    public Group copy() {
        Group g = new Group();

        for (GraphicsObject o : m_objectList) {
            g.add(o.copy());
        }
        return g;
    }

    public int getID() {
        return m_ID;
    }

    @Override
    boolean isClosed(Point pt, double distance) {
        //TODO/Check if vector contain at least an object
        return false;
    }

    public void move(Point delta) {
        Group g = new Group();

        for (GraphicsObject o : m_objectList) {
            o.move(delta);
        }
    }

    private void parseGroups(String groupsStr) {
        Utils utils = new Utils();
        setM_objectList(utils.parseGroups(groupsStr, m_objectList));
    }

    private void parseObjects(String objectsStr) {
        Utils utils = new Utils();
        setM_objectList(utils.parseObjects(objectsStr, m_objectList));
    }

    public void setM_objectList(Vector<GraphicsObject> m_objectList) {
        this.m_objectList = m_objectList;
    }

    @Override
    public int size() {
        int size = 0;
        for (GraphicsObject o : m_objectList) {
            size += o.size();
        }
        return size;
    }

    public String toJson() {
        String str = "{ type: group, objects : { ";

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            int nbObjects = countObjects();
            if (!element.isGroup()) {
                str += element.toJson();
                if (i < nbObjects - 1) {
                    str += ", ";
                }
            }
            if (!element.isGroup()) {

            }
        }
        str += " }, groups : { ";

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            if (element.isGroup()) {
                str += element.toJson();
            }
        }
        return str + " } }";
    }

    public String toString() {
        String str = "group[[";

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            int nbObject = countObjects();
            if (!element.isGroup()) {
                str += element.toString();
                if (i < nbObject - 1) {
                    str += ", ";
                }
            }
        }
        str += "],[";

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            if (element.isGroup()) {
                str += element.toString();
            }
        }
        return str + "]]";
    }

    public int countObjects(){
        int size = 0;
        for (GraphicsObject o : m_objectList) {
            if (!o.isGroup()) {
                size++;
            }
        }
        return size;
    }

    private Vector<GraphicsObject> m_objectList;
    private int m_ID;
}
