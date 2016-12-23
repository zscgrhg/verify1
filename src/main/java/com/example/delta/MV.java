package com.example.delta;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


import static org.objectweb.asm.Opcodes.ASM4;


/**
 * Created by THINK on 2016/12/21.
 */
public class MV extends MethodVisitor {


    public MV() {
        super(ASM4);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
        if ("<cinit>".equals(name)) {
            return;
        }


        if (owner.startsWith("com/example/")) {
            System.out.print(owner + "  |   ");
            Type type = Type.getType(owner);
            System.out.print(type + "  |   ");
            try {

                Class<?> target = loadClass(owner);
                boolean b = false;
                if ("<init>".equals(name)) {
                    b = existConstructor(desc, target);
                } else {
                    b = existMethod(name, desc, target);
                }
                if (!b) {
                    System.out.println("method not found: " + name + "     " + desc);
                } else {
                    System.out.println(name + desc);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        try {
            Class<?> target = loadClass(owner);
            Field field = target.getField(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static Class loadClass(String owner) throws ClassNotFoundException {
        String className = owner.replaceAll("/", ".");
        System.out.print(className + "  |   ");
        Class<?> target = Class.forName(className);
        return target;
    }

    public static boolean existConstructor(String desc, Class clazz) {
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            String constructorDescriptor = Type.getConstructorDescriptor(constructor);
            if (constructorDescriptor.equals(desc)) {
                return true;
            }
        }
        return false;
    }

    public static boolean existMethod(String name, String desc, Class clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(name)) {
                String methodDescriptor = Type.getMethodDescriptor(method);
                if (methodDescriptor.equals(desc)) {
                    return true;
                }
            }
        }
        return false;
    }
}
