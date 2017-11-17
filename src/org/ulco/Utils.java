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
}
