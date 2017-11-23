package org.ulco;

import java.util.Vector;

public class Layer {
    public Layer() {
        m_list = new Vector<GraphicsObject>();
        m_ID = ID.getInstance().getID();
    }

    public Layer(String json) {
        m_list = new Vector<GraphicsObject>();
        String str = json.replaceAll("\\s+","");
        int objectsIndex = str.indexOf("objects");
        int groupsIndex = str.indexOf("groups");
        int endIndex = str.lastIndexOf("}");

        parseObjects(str.substring(objectsIndex + 9, groupsIndex - 2));
        parseGroups(str.substring(groupsIndex + 8, endIndex - 1));
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

    public void move(Point delta) {
        Group g = new Group();

        for (GraphicsObject o : m_list) {
            o.move(delta);
        }
    }

    public int size() {
        int size = 0;
        for (GraphicsObject o : m_list) {
            size += o.size();
        }
        return size;
    }

    private void parseObjects(String objectsStr) {
        Utils utils = new Utils();
        setM_list(utils.parseObjects(objectsStr, m_list));
    }

    private void parseGroups(String groupsStr) {
        Utils utils = new Utils();
        setM_list(utils.parseGroups(groupsStr, m_list));
    }

    public Vector<GraphicsObject> getM_list() {
        return m_list;
    }

    public void setM_list(Vector<GraphicsObject> m_list) {
        this.m_list = m_list;
    }

    public String toJson() {
        String str = "{ type: layer, objects : { ";

        for (int i = 0; i < m_list.size(); ++i) {
            GraphicsObject element = m_list.elementAt(i);

            if (!element.isGroup()) {
                str += element.toJson();
                if (i < m_list.size() - 1) {
                    str += ", ";
                }
            }
        }

        str += " }, groups : { ";

        for (int i = 0; i < m_list.size(); ++i) {
            GraphicsObject element = m_list.elementAt(i);
            if (element.isGroup()) {
                str += element.toJson();
            }
        }
        return str + " } }";
    }

    public int countObjects(){
        int size = 0;
        for (GraphicsObject o : m_list) {
            if (!o.isGroup()) {
                size++;
            }
        }
        return size;
    }


    private Vector<GraphicsObject> m_list;
    private int m_ID;
}
