package blob.happypetsy.studentmanagementportal.Helpers;

// Written by Prem Mali
// http://burnignorance.com/java-web-development-tips/store-java-class-object-in-database/

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import blob.happypetsy.studentmanagementportal.Wrappers.Task;

/*
 * creating a class which have two function getByteArrayObject and getJavaObject.
 *
 * getByteArrayObject convert java object into byte[] and return the byte[].
 *
 * getJavaObject convert byte[] to java object. and return SimpleExample object.
 *
 */

public class ConvertObject {
    //converting SimpleExample object to byte[].
    public byte[] getByteArrayObject(Task object){
        byte[] byteArrayObject = null;
        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);

            oos.close();
            bos.close();
            byteArrayObject = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return byteArrayObject;
        }
        return byteArrayObject;
    }

    //converting byte[] to SimpleExample
    public Task getJavaObject(byte[] convertObject){
        Task objSimpleExample = null;

        ByteArrayInputStream bais;
        ObjectInputStream ins;
        try {

            bais = new ByteArrayInputStream(convertObject);

            ins = new ObjectInputStream(bais);
            objSimpleExample =(Task)ins.readObject();

            ins.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return objSimpleExample;
    }

}