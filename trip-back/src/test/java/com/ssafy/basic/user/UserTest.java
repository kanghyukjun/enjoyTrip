package com.ssafy.basic.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class UserTest {

    @Test
    void 회원_가입_성공() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "john", "john@ssafy.com", "ssafy", "password1"));
        userList.add(new User(2, "max", "max@ssafy.com", "wowowowo", "password2"));

        User newUser = new User(3, "john", "john@ssafy.com", "capybara", "password3");

        boolean isOk = true;
        for (User user : userList) {
            if (newUser.getId() == user.getId()) {
                isOk = false;
                break;
            }
        }

        assertThat(isOk).isTrue();
    }

    @Test
    void 회원_가입_실패() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "john", "john@ssafy.com", "ssafy", "password1"));
        userList.add(new User(2, "max", "max@ssafy.com", "wowowowo", "password2"));

        User newUser = new User(3, "john", "john@ssafy.com", "ssafy", "password3");

        boolean isOk = true;
        for (User user : userList) {
            if (newUser.getId() == user.getId()) {
                isOk = false;
                break;
            }
        }

        assertThat(isOk).isFalse();
    }

    @Test
    void 회원_정보_조회_성공() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "john", "john@ssafy.com", "ssafy", "password1"));
        userList.add(new User(2, "max", "max@ssafy.com", "wowowowo", "password2"));

        String searchId = "ssafy";
        User searched = null;
        for (User user : userList) {
            if (user.getLoginId().equals(searchId)) {
                searched = user;
            }
        }

        assertThat(searched).isNotNull();
    }

    @Test
    void 회원_정보_조회_실패() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "john", "john@ssafy.com", "ssafy", "password1"));
        userList.add(new User(2, "max", "max@ssafy.com", "wowowowo", "password2"));

        String searchId = "bootcamp";
        User searched = null;
        for (User user : userList) {
            if (user.getLoginId().equals(searchId)) {
                searched = user;
            }
        }

        assertThat(searched).isNull();
    }

    @Test
    void 회원_정보_수정() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "john", "john@ssafy.com", "ssafy", "password1"));
        userList.add(new User(2, "max", "max@ssafy.com", "wowowowo", "password2"));

        UserUpdateDto userUpdateDto = new UserUpdateDto(1, "taekyng", "teakyung@ssafy.com", "newpassword");
        User targetUser = null;

        for (User user : userList) {
            if (user.getId() == userUpdateDto.getId()) {
                targetUser = user;
                break;
            }
        }

        if (targetUser != null) {
            targetUser.setName(userUpdateDto.getName());
            targetUser.setEmail(userUpdateDto.getEmail());
            targetUser.setPassword(userUpdateDto.getPassword());
        }
    }

    @Test
    void 회원_정보_삭제_성공() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "john", "john@ssafy.com", "ssafy", "password1"));
        userList.add(new User(2, "max", "max@ssafy.com", "wowowowo", "password2"));
        int listSize = userList.size();

        String deleteId = "ssafy";
        User deleteUser = null;
        for (User user : userList) {
            if (user.getLoginId().equals(deleteId)) {
                deleteUser = user;
                break;
            }
        }

        if(deleteUser != null) {
            userList.remove(deleteUser);
        }

        assertThat(listSize).isEqualTo(userList.size() + 1);
    }

    @Test
    void 회원_로그인_실패() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "john", "john@ssafy.com", "ssafy", "password1"));
        userList.add(new User(2, "max", "max@ssafy.com", "wowowowo", "password2"));

        String id = "ssafy";
        String password = "password3";

        String savedPassword = "";
        for (User user:userList) {
            if (user.getLoginId().equals(id)) {
                savedPassword = user.getPassword();
            }
        }

        assertThat(password).isNotEqualTo(savedPassword);
    }

    @Test
    void 회원_로그인_성공() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "john", "john@ssafy.com", "ssafy", "password1"));
        userList.add(new User(2, "max", "max@ssafy.com", "wowowowo", "password2"));

        String id = "ssafy";
        String password = "password1";

        String savedPassword = "";
        for (User user:userList) {
            if (user.getLoginId().equals(id)) {
                savedPassword = user.getPassword();
            }
        }

        assertThat(password).isEqualTo(savedPassword);
    }

    static class UserUpdateDto {
        int id;
        String name;
        String email;
        String password;

        public UserUpdateDto(int id, String name, String email, String password) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    static class User {
        int id;
        String name;
        String email;
        String loginId;
        String password;

        public User(int id, String name, String email, String loginId, String password) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.loginId = loginId;
            this.password = password;
        }

        public User() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
