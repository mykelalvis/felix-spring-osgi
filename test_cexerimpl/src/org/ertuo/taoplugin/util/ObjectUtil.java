package org.ertuo.taoplugin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * ������
 * 
 * @author mo.duanm
 * 
 */
public class ObjectUtil {

    // ѹ������,�Ѷ���ѹ�����ֽ�(���ѹ�������ַ���,ѹ���ɵ��ֽڲ���ֱ����new String��ת��)
    public static byte[] writeCompressObject(Object object_) {
        byte[] data_ = null;
        ByteArrayOutputStream o = null;
        GZIPOutputStream gzout = null;
        ObjectOutputStream out = null;
        try {
            // �����ֽ����������
            o = new ByteArrayOutputStream();
            // ����gzipѹ�������
            gzout = new GZIPOutputStream(o);
            // �����������л������
            out = new ObjectOutputStream(gzout);
            out.writeObject(object_);
            out.flush();
            out.close();
            gzout.close();
            // ����ѹ���ֽ���
            data_ = o.toByteArray();
            o.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return (data_);
    }

    // ��ѹ���ֽ����黹ԭΪ��Ӧ�������ݶ���
    public static Object readCompressObject(byte[] data_) {
        Object object_ = null;
        try {
            // �����ֽ�����������
            ByteArrayInputStream i = new ByteArrayInputStream(data_);
            // ����gzip��ѹ������
            GZIPInputStream gzin = new GZIPInputStream(i);
            // �����������л�������
            ObjectInputStream in = new ObjectInputStream(gzin);
            // ���ƶ����ͻ�ԭ����
            object_ = in.readObject();
            i.close();
            gzin.close();
            in.close();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);

        }
        return (object_);
    }
}
