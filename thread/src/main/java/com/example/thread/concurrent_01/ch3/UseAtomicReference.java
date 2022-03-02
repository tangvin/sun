package com.example.thread.concurrent_01.ch3;

import java.util.concurrent.atomic.AtomicReference;

/*演示引用类型的原子操作类*/
public class UseAtomicReference {

    static AtomicReference<UserInfo> atomicReference;

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo("Bruce", 20);
        atomicReference = new AtomicReference<>(userInfo);
        UserInfo updateUser = new UserInfo("mark", 26);
        UserInfo getAndSet = atomicReference.getAndSet(updateUser);
        System.out.println("getAndSet=="+getAndSet);
        UserInfo get = atomicReference.get();
        System.out.println("get=="+get);
        boolean b = atomicReference.compareAndSet(userInfo, updateUser);
        System.out.println("atomicReference.get()+:" + atomicReference.get());

    }

    //定义实体类
    static class UserInfo {
        private String name;
        private int age;

        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
