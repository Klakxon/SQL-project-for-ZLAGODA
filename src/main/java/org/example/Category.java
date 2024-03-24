package org.example;

public class Category {
    private final int id;
    private String name;

    public Category(int id, String name) {
        if (id < 0) {
            throw new IllegalArgumentException("Invalid value of id");
        }
        if (name == null || name.length() > 50) {
            throw new IllegalArgumentException("Invalid value of name");
        }
        this.id = id;
        this.name = name;
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
}
