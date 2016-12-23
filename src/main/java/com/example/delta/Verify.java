package com.example.delta;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.SimpleVerifier;
import org.objectweb.asm.util.CheckClassAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by THINK on 2016/12/21.
 */
public class Verify extends SimpleVerifier {
    public static void main(String[] args) throws IOException {
        InputStream resourceAsStream = SimpleVerifier.class.getClassLoader().getResourceAsStream("com/example/delta/User.class");
        boolean dump=false;
        ClassReader cr = new ClassReader(resourceAsStream);
        ClassNode cn = new ClassNode();
        cr.accept(new CheckClassAdapter(cn, false), ClassReader.SKIP_DEBUG);

        Type syperType = cn.superName == null ? null : Type
                .getObjectType(cn.superName);
        List<MethodNode> methods = cn.methods;

        List<Type> interfaces = new ArrayList<Type>();
        for (Iterator<String> i = cn.interfaces.iterator(); i.hasNext();) {
            interfaces.add(Type.getObjectType(i.next()));
        }

        for (int i = 0; i < methods.size(); ++i) {
            MethodNode method = methods.get(i);
            System.out.println(method.name);
            SimpleVerifier verifier = new SimpleVerifier(
                    Type.getObjectType(cn.name), syperType, interfaces,
                    (cn.access & Opcodes.ACC_INTERFACE) != 0);
            Analyzer<BasicValue> a = new Analyzer<BasicValue>(verifier);

            try {
                a.analyze(cn.name, method);
                if (!dump) {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
