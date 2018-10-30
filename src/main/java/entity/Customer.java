package entity;

import java.util.ArrayList;
import java.util.Collection;

public class Customer {

    private String name;

    private String surname;

    private String phone;

    private Collection<String> roles = new ArrayList<>();

    private Collection<Card> cards = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public void setRole(String role) {
        roles.add(role);
    }

    public Collection<Card> getCards() {
        return cards;
    }

    public void setCards(Collection<Card> cards) {
        this.cards = cards;
    }

    public void setCard(Card card) {
        cards.add(card);
    }


}
