package com.example.delta;





import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ASM4;

/**
 * Created by THINK on 2016/12/21.
 */
public class CV extends ClassVisitor {
    public CV() {
        super(ASM4);
    }
    @Override
    public MethodVisitor visitMethod(int access, String name,
                                     String desc, String signature, String[] exceptions) {
        MethodVisitor mv= new MV();
        return mv;
    }
}
