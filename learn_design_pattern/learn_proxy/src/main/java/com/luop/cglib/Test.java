package com.luop.cglib;

public class Test {
    public static void main(String[] args) {
        //创建目标对象
        TeacherDaoImpl target = new TeacherDaoImpl();
        //获取代理对象，并且将目标对象传给代理对象
        TeacherDaoImpl proxy = (TeacherDaoImpl) new ProxyFactory(target).getProxyInstance();
        //执行代理对象的方法，触发intercept方法，进而实现对目标对象的调用
        proxy.teach();
    }
}
