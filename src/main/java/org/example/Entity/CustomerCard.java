package org.example.Entity;

public class CustomerCard {
    private final String id;
    private String surname, name, patronymic, telephone, city, street, zipCode;
    private int percent;

    public CustomerCard(String id, String surname, String name, String patronymic, String telephone, String city,
                        String street, String zipCode, int percent) {
        if (id == null || id.length() > 13) {
            throw new IllegalArgumentException("Invalid value of id");
        }
        if (surname == null || surname.length() > 50) {
            throw new IllegalArgumentException("Invalid value of surname");
        }
        if (name == null || name.length() > 50) {
            throw new IllegalArgumentException("Invalid value of name");
        }
        if (patronymic.length() > 50) {
            throw new IllegalArgumentException("Invalid value of patronymic");
        }
        if (telephone == null || telephone.length() > 13) {
            throw new IllegalArgumentException("Invalid value of telephone");
        }
        if (city.length() > 50) {
            throw new IllegalArgumentException("Invalid value of city");
        }
        if (street.length() > 50) {
            throw new IllegalArgumentException("Invalid value of street");
        }
        if (zipCode.length() > 9) {
            throw new IllegalArgumentException("Invalid value of zipCode");
        }
        if (percent < 0) {
            throw new IllegalArgumentException("Invalid value of percent");
        }
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.telephone = telephone;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.percent = percent;
    }

    public String getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
