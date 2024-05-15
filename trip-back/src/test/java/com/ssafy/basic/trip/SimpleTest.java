package com.ssafy.basic.trip;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class SimpleTest {

    static class A {
        int a = 0;
    }

    @Test
    @DisplayName("간단한 테스트")
    void simpleTest(){
        String name = "kang";
        assertThat(name).isEqualTo("king");
    }
    @Test
    @DisplayName("간단한 테스트2")
    void simpleTest2(){
        A a = null;
        assertThrows(NullPointerException.class , () -> System.out.println(a.a));
    }
}
