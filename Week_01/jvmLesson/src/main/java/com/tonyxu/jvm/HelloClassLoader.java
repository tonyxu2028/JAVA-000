package com.tonyxu.jvm;

import com.tonyxu.jvm.util.FileUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

/**
 * @author Tony.xu
 * @version 1.0
 * @date 2020/10/16 10:39
 */
public class HelloClassLoader extends ClassLoader{

    private static final String HELLO_FILE_NAME = "Hello";

    private static final String HELLO_METHOD_NAME = "hello";

    private static final String HEllO_FILE_RELATIVE_NAME = "Hello.xlass";

    public static void main(String[] args){

        try{
           Object object = new HelloClassLoader().findClass(HELLO_FILE_NAME).newInstance();
           Method method = object.getClass().getMethod(HELLO_METHOD_NAME);
           method.invoke(object);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException{
        String filePath = HelloClassLoader.getSystemResource(HEllO_FILE_RELATIVE_NAME).getPath();
        try {
            String fileBase64Str = FileUtil.fileToBase64(filePath);
            byte[] bytes = decode(fileBase64Str);
            return defineClass(name,bytes,0,bytes.length);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private byte[] decode(String base64){
        return Base64.getDecoder().decode(base64);
    }

}
