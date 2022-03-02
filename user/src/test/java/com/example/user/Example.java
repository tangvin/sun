package com.example.user;

/**
 * @author TY
 * @description
 * @date 2021/10/21 23:06
 */
public class Example {
    String str = new String("good");
    char[] ch = {'a','b','c'};

    public void change(String str,char ch[]){
                str = "bad";
                ch[0] = 'x';
        System.out.println("change--->str======"+str);
    }

//    public static void main(String[] args) {
//        Example ex = new Example();
//        ex.change(ex.str,ex.ch);
//
//        System.out.println(new String(ex.str) + " and " + new String(ex.ch));
//    }

//    public static void main(String[] args){
//        String str1 = new String( "Hello" );
//        String str2 = "Hello";
//        String str3 = "Hello";
//        System.out.println( str1 == str2 );
//        System.out.println( str1 == str3 );
//        System.out.println( str2 == str3 );
//    }

    public static void main(String[] args) {
        StringBuffer s = new StringBuffer("hello");
        if(s.length() >= 5 && (s.append(" there").equals("false"))){
//            ;//do nothing
            System.out.println("value is :"+s);

        }
    }


}
