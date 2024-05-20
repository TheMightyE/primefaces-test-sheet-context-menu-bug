package org.primefaces.test;

import java.io.Serializable;
import java.util.UUID;

public class Item implements Serializable {
    /** Serial version */
    private static final long serialVersionUID = 1723876414086L;
    private String id;
    private String name;
    private String type;

    public Item(String name, String type) {
        this.name = name;
        this.type = type;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
