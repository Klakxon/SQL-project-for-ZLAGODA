package org.example.Entity;

import java.util.Date;

public class Employee {
    private final String id;
    private double payment;
    private String surname, name, patronymic, position, telephone, city, street, zipCode;
    private int building;
    private Date startWork, birthday;

    public Employee(String id, String surname, String name, String patronymic, String position, double payment,
                    Date startWork, Date birthday, String telephone, String city, String street, int building, String zipCode) {
        if (id == null || id.length() == 0 || id.length() > 10) {
            throw new IllegalArgumentException("Invalid value of ID");
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
        if (payment < 0) {
            throw new IllegalArgumentException("Invalid value of payment");
        }
        if (startWork == null || birthday == null) {
            throw new IllegalArgumentException("Invalid value of startWork and/or birthday");
        }
        else if (startWork.before(birthday)) {
            throw new IllegalArgumentException("Invalid values of dates");
        }
        if (telephone == null || telephone.length() > 13) {
            throw new IllegalArgumentException("Invalid value of telephone");
        }
        if (position == null || position.length() > 10) {
            throw new IllegalArgumentException("Invalid value of position");
        }
        if (city == null || city.length() > 50) {
            throw new IllegalArgumentException("Invalid value of city");
        }
        if (street == null || street.length() > 50) {
            throw new IllegalArgumentException("Invalid value of street");
        }
        if (building <= 0) {
            throw new IllegalArgumentException("Invalid value of building");
        }
        if (zipCode == null || zipCode.length() > 9) {
            throw new IllegalArgumentException("Invalid value of zipCode");
        }
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.position = position;
        this.payment = payment;
        this.startWork = startWork;
        this.birthday = birthday;
        this.telephone = telephone;
        this.city = city;
        this.street = street;
        this.building = building;
        this.zipCode = zipCode;
    }

    public String getId() {
        return id;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getStartWork() {
        return startWork;
    }

    public void setStartWork(Date startWork) {
        this.startWork = startWork;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }
}
