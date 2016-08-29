package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static ru.javawebinar.topjava.web.meal.UserMealRestController.REST_URL;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(REST_URL)
public class UserMealRestController extends AbstractUserMealController {
    static final String REST_URL = "/rest/meals";


    @Override
    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public UserMeal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @RequestMapping(value = "/{id}", method = PUT, consumes = APPLICATION_JSON_VALUE)
    public void update(@RequestBody UserMeal userMeal, @PathVariable("id") int id) {
        super.update(userMeal, id);
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMeal> createOne(@RequestBody UserMeal userMeal) {
        UserMeal created = super.create(userMeal);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @RequestMapping(value = "/{id}", method = DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @RequestMapping(value = "/filter", method = GET, produces = APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getBetween(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
            @RequestParam(name = "startTime", required = false) LocalTime startTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(name = "endDate", required = false) LocalDate endDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
            @RequestParam(name = "endTime", required = false) LocalTime endTime) {

        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}