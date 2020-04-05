package com.geek.foobar.serializable;

import java.io.*;

/**
 * @author 曲元涛
 * @date 2020/4/5 15:51
 */
public class MySerializable {

    static class Employee implements Serializable {
        public String name;
        public String address;
        public transient int SSN = 10;
        public int number;
        public void mailCheck() {
            System.out.println("Mailing a check to " + name + " " + address);
        }
    }

    private static void serializableDemo() {
        Employee e = new Employee();
        e.name = "quyuantao";
        e.address = "地球村X001公社";
        e.SSN = 7;
        e.number = 12345;
        try {
            FileOutputStream fos = new FileOutputStream("/Users/quyuantao/Downloads/Employee.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(e);
            oos.close();
            fos.close();
            System.out.println("Employee Object had saved to disk");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void deserializableDemo() {
        Employee e = null;
        try {
            FileInputStream fis = new FileInputStream("/Users/quyuantao/Downloads/Employee.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            e = (Employee) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        System.out.println("Deserializable Employee...");
        System.out.println(e.name);
        System.out.println(e.address);
        System.out.println(e.SSN);
        System.out.println(e.number);
        e.mailCheck();
    }

    public static void main(String[] args) {
        MySerializable.serializableDemo();
        MySerializable.deserializableDemo();
    }
}
