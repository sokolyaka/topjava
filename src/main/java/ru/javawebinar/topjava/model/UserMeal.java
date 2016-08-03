package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.UserMeal.GET;
import static ru.javawebinar.topjava.model.UserMeal.GET_ALL;
import static ru.javawebinar.topjava.model.UserMeal.GET_BETWEEN;

/**
 * GKislin
 * 11.01.2015.
 */
@Entity
@Table(name = "meals")
@NamedQueries({
        @NamedQuery(name = GET_ALL,
                query = "SELECT um FROM UserMeal um WHERE um.user.id = :userId ORDER BY um.dateTime"),
        @NamedQuery(name = GET,
                query = "SELECT um FROM UserMeal um WHERE um.user.id = :userId AND um.id = :id"),
        @NamedQuery(name = GET_BETWEEN,
                query = "SELECT um FROM UserMeal um WHERE um.dateTime BETWEEN :startDate AND :endDate AND um.user.id = :userId")})
public class UserMeal extends BaseEntity {

    public static final String GET_ALL = "UserMeal.getAll";
    public static final String GET = "UserMeal.get";
    public static final String GET_BETWEEN = "UserMeal.getBetween";

    @Column(name = "datetime", nullable = false)
    @NotEmpty
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "calories", nullable = false)
    @NotEmpty
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
