package org.ulco;

public class ID {
    private int ID = 0;

    private ID() { }

    public int getID() {
        return ID++;
    }

    /** Instance unique pré-initialisée */
    private static ID INSTANCE = new ID();

    /** Point d'accès pour l'instance unique du singleton */
    public static ID getInstance() {
        return INSTANCE;
    }
}