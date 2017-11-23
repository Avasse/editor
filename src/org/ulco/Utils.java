package org.ulco;

import java.util.Vector;

public final class Utils {

    public GraphicsObjects select(Point pt, double distance, Document document) {
        GraphicsObjects list = new GraphicsObjects();

        for (Layer layer : document.getM_layers()) {
            list.addAll(this.select(pt, distance, layer));
        }
        return list;
    }

    public GraphicsObjects select(Point pt, double distance, Layer layer) {
        GraphicsObjects list = new GraphicsObjects();

        for (GraphicsObject object : layer.getM_list()) {
            if (object.isClosed(pt, distance)) {
                list.add(object);
            }
        }
        return list;
    }

    public Document createDoc(Point origin, int line, int column, double length) {
        Document doc = new Document();
        Vector<Layer> m_layers = new Vector<Layer>();

        Layer layer = doc.createLayer();

        for (int indexX = 0; indexX < column; ++indexX) {
            for (int indexY = 0; indexY < line; ++indexY) {
                layer.add(new Square(new Point(origin.getX() + indexX * length, origin.getY() + indexY * length), length));
            }
        }
        return doc;
    }

    public Document createDoc(Point center, int number, double radius, double delta) {
        Document doc = new Document();
        Vector<Layer> m_layers = new Vector<Layer>();

        Layer layer = doc.createLayer();

        for (int index = 0; index < number; ++index) {
            layer.add(new Circle(center, radius + index * delta));
        }
        return doc;
    }

    public Vector<GraphicsObject> parseObjects(String objectsStr, Vector<GraphicsObject> list) {
        while (!objectsStr.isEmpty()) {
            Utils utils = new Utils();
            int separatorIndex = utils.searchSeparator(objectsStr);
            String objectStr;

            if (separatorIndex == -1) {
                objectStr = objectsStr;
            } else {
                objectStr = objectsStr.substring(0, separatorIndex);
            }
            list.add(JSON.parse(objectStr));
            if (separatorIndex == -1) {
                objectsStr = "";
            } else {
                objectsStr = objectsStr.substring(separatorIndex + 1);
            }
        }
        return list;
    }

    public Vector<GraphicsObject> parseGroups(String groupsStr, Vector<GraphicsObject> list) {
        while (!groupsStr.isEmpty()) {
            Utils utils = new Utils();
            int separatorIndex = utils.searchSeparator(groupsStr);
            String groupStr;

            if (separatorIndex == -1) {
                groupStr = groupsStr;
            } else {
                groupStr = groupsStr.substring(0, separatorIndex);
            }
            list.add(JSON.parseGroup(groupStr));
            if (separatorIndex == -1) {
                groupsStr = "";
            } else {
                groupsStr = groupsStr.substring(separatorIndex + 1);
            }
        }
        return list;
    }

    public int searchSeparator(String str) {
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

    public boolean isClosed(Point center, Point pt, double distance) {
        return Math.sqrt((center.getX() - pt.getX()) * (center.getX() - pt.getX()) +
                ((center.getY() - pt.getY()) * (center.getY() - pt.getY()))) <= distance;
    }

    public String toJSON (boolean layer, Vector<GraphicsObject> list) {
        if (layer) {
            String str = "{ type: layer, objects : { ";

            for (int i = 0; i < list.size(); ++i) {
                GraphicsObject element = list.elementAt(i);

                if (!element.isGroup()) {
                    str += element.toJson();
                    if (i < list.size() - 1) {
                        str += ", ";
                    }
                }
            }

            str += " }, groups : { ";

            for (int i = 0; i < list.size(); ++i) {
                GraphicsObject element = list.elementAt(i);
                if (element.isGroup()) {
                    str += element.toJson();
                }
            }
            return str + " } }";
        } else {
            String str = "{ type: group, objects : { ";

            for (int i = 0; i < list.size(); ++i) {
                GraphicsObject element = list.elementAt(i);
                int nbObjects = this.countObjects(list);
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

            for (int i = 0; i < list.size(); ++i) {
                GraphicsObject element = list.elementAt(i);
                if (element.isGroup()) {
                    str += element.toJson();
                }
            }
            return str + " } }";
        }
    }

    public int countObjects(Vector<GraphicsObject> list){
        int size = 0;
        for (GraphicsObject o : list) {
            if (!o.isGroup()) {
                size++;
            }
        }
        return size;
    }
}
