package com.luop;

/**
 * @Author: luoping
 * @Date: 2020/7/17 13:47
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        Computer computer = new Computer.Builder()
                .setCpu("i7")
                .setRam("16G")
                .setUsb("3.0")
                .setKeboard("机械键盘")
                .setMouse("无线鼠标")
                .setDisplay("三星显示器")
                .build();
        System.out.println(computer);
    }
}
