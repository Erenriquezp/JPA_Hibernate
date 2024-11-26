package org.example.hibernateapp.dto;

public class CustomerDto {
    private String name;
    private String lastame;

    public CustomerDto(String name, String lastame) {
        this.name = name;
        this.lastame = lastame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastame() {
        return lastame;
    }

    public void setLastame(String lastame) {
        this.lastame = lastame;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", lastame='" + lastame + '\'' +
                '}';
    }
}
