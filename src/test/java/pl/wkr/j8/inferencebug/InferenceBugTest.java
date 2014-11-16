package pl.wkr.j8.inferencebug;

import org.junit.Test;

import java.util.Iterator;

import static pl.wkr.j8.inferencebug.A.*;
import static pl.wkr.j8.inferencebug.B.*;

class A {
    public static <T> void methodA(Iterable<T> actual) {}
    public static <T> void methodA(Iterator<T> actual) {}
    public static void methodA(Throwable actual) {}
}

class B {
    public static <E extends Throwable> E methodB() {
        return null;
    }
}


public class InferenceBugTest {

    @Test
    public void should_infer_method() {
        methodA(methodB());

        /*

        Java 1.7 compiles this, but in 1.8(1.8.0_25) I get error:

          error: reference to methodA is ambiguous
                methodA(methodB());
                ^
          both method <T#1>methodA(Iterator<T#1>) in A and method <T#2>methodA(Iterable<T#2>) in A match
          where T#1,T#2 are type-variables:
            T#1 extends Object declared in method <T#1>methodA(Iterator<T#1>)
            T#2 extends Object declared in method <T#2>methodA(Iterable<T#2>)

          -------------
           (change 'sourceCompatibility' in build.gradle to see behavior in java 1.7 and 1.8)
         */
    }


}



