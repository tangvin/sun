package com.example.designmode.singleton;

import javax.swing.*;

/**
 * @Description
 * @Author TY
 * @Date 2021/2/27 17:40
 */
public class Bajie extends JPanel {
    private static Bajie instance = new Bajie();

    private Bajie() {
        JLabel jLabel = new JLabel(new ImageIcon("Bajie.jpg"));
        this.add(jLabel);
    }


    public static Bajie getInstance() {
        return instance;
    }

}
