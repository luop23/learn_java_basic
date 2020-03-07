package com.luop.dynamic;

public class Test {

    public static void main(String[] args) {
        //创建目标对象
        TeacherDao target = new TeacherDaoImpl();
        //给目标对象，创建代理对象
        TeacherDao proxy = (TeacherDao) new ProxyFactory(target).getProxyInstance();
//        System.out.println("proxy=" + proxy.getClass());//proxy=class com.sun.proxy.$Proxy0   内存中动态生成了代理对象
        //通过代理对象，调用目标对象方法
        proxy.teach();
    }
}
