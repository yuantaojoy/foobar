package com.geek.foobar;

import org.junit.Test;
import org.springframework.http.HttpEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曲元涛
 * @date 2020/4/3 20:45
 */
public class CallByValue {

    @Test
    public void example1() {
        int num1 = 10;
        int num2 = 20;
        this.swap(num1, num2);
        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);
    }
    private void swap(int a, int b) {
        int tmp = a;
        a = b;
        b = tmp;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    @Test
    public void example2() {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println(arr[0]);
        change(arr);
        System.out.println(arr[0]);
    }
    private void change(int[] arr) {
        arr[0] = 0;
    }

    @Test
    public void example3() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("status", "05");
        requestBody.put("shop_no", 1);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody);
        this.changeBody(request);
        System.out.println("requestBody => " + requestBody);
        System.out.println("request => " + request);
    }
    private void changeBody(HttpEntity<Map<String, Object>> req) {
        Map<String, Object> requestBody = req.getBody();
        assert requestBody != null;
        requestBody.put("status", "10");
    }

    @Test
    public void example4() {
        Student s1 = new Student("张三");
        Student s2 = new Student("李四");
        swapObj(s1, s2);
        System.out.println("s1:" + s1.getName());
        System.out.println("s2:" + s2.getName());
    }
    private void swapObj(Student x1, Student x2) {
        Student tmp = x1;
        x1 = x2;
        x2 = tmp;
        System.out.println("x1:" + x1.getName());
        System.out.println("x2:" + x2.getName());
    }

    static class Student {

        String name;

        public Student(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
