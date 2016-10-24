package module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by suvp on 10/24/2016.
 */
public class SampleClass {
    public static void main(String[] args){
        MyClass myClass = new MyClass();
        myClass.printLogger();
    }
}

class MyClass{

    private static final Logger myLogger = LoggerFactory.getLogger("moudule.MyClass");

    public void printLogger(){
        System.out.println(myLogger);
        for(int i =0; i<=20;i++){
            myLogger.debug("Printing value : "+i);
        }
    }
}
