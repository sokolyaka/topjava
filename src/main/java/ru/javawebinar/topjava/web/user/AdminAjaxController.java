package ru.javawebinar.topjava.web.user;

import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * User: grigory.kislin
 */
@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = POST)
    public void createOrUpdate(@RequestParam("id") int id,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {

        User user = new User(id, name, email, password, Role.ROLE_USER);
        if (id == 0) {
            super.create(user);
        } else {
            super.update(user, id);
        }
    }

    @RequestMapping(value = "enable", method = PUT, consumes = APPLICATION_JSON_VALUE)
    public void enabled(@RequestBody EnableDTO enableDTO) {
        super.enabled(enableDTO.getId(), enableDTO.isEnabled());
    }

    public static class EnableDTO {
        private int id;
        private boolean enabled;

        public EnableDTO() {
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getId() {
            return id;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }
}
