package ru.fazziclay.fazziclaylibs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteUtils {
    public static String byteToHex(int value) {
        String hex = "00".concat(Integer.toHexString(value));
        return hex.substring(hex.length()-2);
    }

    public static short getShort(byte byte1, byte byte2) {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(byte1);
        bb.put(byte2);
        return bb.getShort(0);
    }


    public static byte[] getBytes(short value) {
        return new byte[]{(byte)(value & 0x00FF),(byte)((value & 0xFF00)>>8)};
        //ByteBuffer buffer = ByteBuffer.allocate(2);
        //buffer.putShort(value);
        //return buffer.array();
    }
}
