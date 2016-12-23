package com.example.delta;


import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.analysis.SimpleVerifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by THINK on 2016/12/21.
 */
public class Oops {
    public static void main(String[] args) throws IOException {
        ScanResult scan = new FastClasspathScanner("com.example")
                .strictWhitelist()
                .scan();
        List<String> classes = scan.getNamesOfAllClasses();
        for (String clazz : classes) {
            String s = clazz.replaceAll("\\.", "/")+".class";
            InputStream resourceAsStream = SimpleVerifier.class.getClassLoader().getResourceAsStream(s);

            ClassReader cr = new ClassReader(resourceAsStream);
            cr.accept(new CV(),0);
        }

    }
}
