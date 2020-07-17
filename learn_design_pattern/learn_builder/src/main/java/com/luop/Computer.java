package com.luop;

/**
 * @Author: luoping
 * @Date: 2020/7/17 13:37
 * @Description: 建造者模式使用场景一：在对象属性较多时，可以对对象的赋值进行链式编程
 */
public class Computer {
    private String cpu;
    private String ram;
    private String usb;
    private String keyboard;
    private String mouse;
    private String display;

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.usb = builder.usb;
        this.keyboard = builder.keyboard;
        this.mouse = builder.mouse;
        this.display = builder.display;
    }

    public static class Builder {
        private String cpu;
        private String ram;
        private String usb;
        private String keyboard;
        private String mouse;
        private String display;

        public Builder setCpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder setRam(String ram) {
            this.ram = ram;
            return this;
        }

        public Builder setUsb(String usb) {
            this.usb = usb;
            return this;
        }

        public Builder setKeboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        public Builder setMouse(String mouse) {
            this.mouse = mouse;
            return this;
        }

        public Builder setDisplay(String display) {
            this.display = display;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }

    @Override
    public String toString() {
        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", ram='" + ram + '\'' +
                ", usb='" + usb + '\'' +
                ", keyboard='" + keyboard + '\'' +
                ", mouse='" + mouse + '\'' +
                ", display='" + display + '\'' +
                '}';
    }
}
