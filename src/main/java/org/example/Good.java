package org.example;

public class Good {
    private final int id, idCategory;
    private String name, producer, description;
    private boolean isPromotional;

    public Good(int id, int idCategory, String name, String producer, String description, boolean isPromotional) {
        if (id < 0) {
            throw new IllegalArgumentException("Invalid value of id");
        }
        if (idCategory < 0) {
            throw new IllegalArgumentException("Invalid value of idCategory");
        }
        if (name == null || name.length() > 50) {
            throw new IllegalArgumentException("Invalid value of name");
        }
        if (producer == null || producer.length() > 50) {
            throw new IllegalArgumentException("Invalid value of producer");
        }
        if (description == null || description.length() > 100) {
            throw new IllegalArgumentException("Invalid value of description");
        }
        this.id = id;
        this.idCategory = idCategory;
        this.name = name;
        this.producer = producer;
        this.description = description;
        this.isPromotional = isPromotional;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPromotional() {
        return isPromotional;
    }

    public void setPromotional(boolean promotional) {
        isPromotional = promotional;
    }

    public int getIdCategory() {
        return idCategory;
    }
}
