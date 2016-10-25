package module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by suvp on 10/24/2016.
 */
public class SampleClass {
    public static void main(String[] args){
        ReceiverTopic receiver = new ReceiverTopic();
        receiver.receiveMessageTopic();
        MyClass myClass = new MyClass();
        myClass.printLogger();
    }
}

class MyClass{

    private static final Logger myLogger = LoggerFactory.getLogger("module.MyClass");
    private static final Logger myLoggerNew= LoggerFactory.getLogger("module.MyClass.NewClass");

    public void printLogger(){
        boolean isEnable =true;
        for(int i =0; i<=200000;i++){
            if(isEnable){
                myLogger.debug("Printing value : "+i); //even
                isEnable = false;
            }else{
                myLoggerNew.debug("Printing value : "+i); //odd
                isEnable = true;
            }
        }
    }
}
