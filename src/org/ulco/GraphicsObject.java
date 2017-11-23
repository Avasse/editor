package org.ulco;

abstract public class GraphicsObject {

    private int m_ID;
    private String m_inside_color;
    private String m_border_color;

    public GraphicsObject() {
        m_ID = ID.getInstance().getID();
        this.m_inside_color = "TRANSPARENT";
        this.m_border_color = "TRANSPARENT";
    }

    abstract public GraphicsObject copy();

    public int getID() {
        return m_ID;
    }

    public String getM_inside_color() {
        return m_inside_color;
    }

    public String getM_border_color() {
        return m_border_color;
    }

    public void setM_inside_color(String m_inside_color) {
        this.m_inside_color = m_inside_color;
    }

    public void setM_border_color(String m_border_color) {
        this.m_border_color = m_border_color;
    }

    abstract boolean isClosed(Point pt, double distance);

    abstract void move(Point delta);

    abstract public String toJson();

    abstract public String toString();

    public boolean isGroup(){
        return false;
    }

    public int size() {
        return 1;
    }
}
