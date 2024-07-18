package kr.contec.fromto.cam.safran.util;

import com.google.common.primitives.UnsignedInteger;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 파싱 공동 유틸 모듈
 *
 * @author skjeong
 */
@UtilityClass
public class ConverterUtils {

    public static UnsignedInteger bytesToUnsignedInt(final byte[] array, final int start)
    {
        return UnsignedInteger.fromIntBits(toInt(array, start));
    }
    public static UnsignedInteger bytesToUnsignedShort(final byte[] array, final int start)
    {
        return UnsignedInteger.fromIntBits(toShort(array, start));
    }
    /**
     * bytebuffer to int
     *
     * @param buffer 파싱할 buffer
     * @param offset 파싱 시작 위치
     * @return 파싱 결과
     */
    public int toInt(ByteBuffer buffer, int offset) {
        try {
            byte[] tmp = new byte[4];

            buffer.get(offset, tmp);

            if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
                ArrayUtils.reverse(tmp);
            }
            long ret = 0;
            for (int i = 0; i < 4; i++) {
                ret |= ((tmp[i] & 0xff) << (8 * i));
            }
            return (int) ret;
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * bytearray to int
     *
     * @param tmp 파싱할 buffer
     * @return 파싱 결과
     */
    public int toInt(byte[] tmp) {
        try {
            if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
                ArrayUtils.reverse(tmp);
            }
            long ret = 0;
            for (int i = 0; i < 4; i++) {
                ret |= ((tmp[i] & 0xff) << (8 * i));
            }
            return (int) ret;
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * bytebuffer to int
     *
     * @param tmp    파싱할 buffer
     * @param offset 파싱 시작 위치
     * @return 파싱 결과
     */
    public int toInt(byte[] tmp, int offset) {
        try {
            if (tmp.length - 4 < offset) {
                return -1;
            }
            if (offset < 0) {
                return -1;
            }
            byte[] parsingBuffer = {tmp[offset], tmp[offset + 1], tmp[offset + 2], tmp[offset + 3]};
            return toInt(parsingBuffer);
        } catch (Exception ex) {
            return 0;
        }
    }


    /**
     * bytebuffer to int
     *
     * @param tmp    파싱할 buffer
     * @param offset 파싱 시작 위치
     * @return 파싱 결과
     */
    public int toInt(byte[] tmp, int offset, int size) {
        try {
            if (tmp.length - 4 < offset) {
                return -1;
            }
            if (offset < 0) {
                return -1;
            }
            if (size >= 4) {
                return -1;
            }
            byte[] parsingBuffer = {tmp[offset], tmp[offset + 1], tmp[offset + 2], tmp[offset + 3]};

            for(int i = 0; i <4-size; i++){
                parsingBuffer[i] = 0x00;
            }
            for(int i = 4-size; i <4; i++){
                parsingBuffer[i] = tmp[offset + i - (4-size)];
            }

            return toInt(parsingBuffer);
        } catch (Exception ex) {
            return 0;
        }
    }
    /**
     * bytebuffer to int
     *
     * @param tmp    파싱할 buffer
     * @param offset 파싱 시작 위치
     * @return 파싱 결과
     */
    public short toShort(byte[] tmp, int offset) {
        try {
            if (tmp.length - 4 < offset) {
                return -1;
            }
            if (offset < 0) {
                return -1;
            }
            byte[] parsingBuffer = {tmp[offset], tmp[offset + 1]};
            return toShort(parsingBuffer);
        } catch (Exception ex) {
            return 0;
        }
    }


    /**
     * bytearray to int
     *
     * @param tmp 파싱할 buffer
     * @return 파싱 결과
     */
    public short toShort(byte[] tmp) {
        try {
            if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
                ArrayUtils.reverse(tmp);
            }
            long ret = 0;
            for (int i = 0; i < 2; i++) {
                ret |= ((tmp[i] & 0xff) << (8 * i));
            }
            return (short) ret;
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * bytebuffer to float
     *
     * @param tmp    파싱할 buffer
     * @param offset 파싱 시작 위치
     * @return 파싱 결과
     */
    public float toFloat(byte[] tmp, int offset) {
        try {
            if (tmp.length - 4 < offset) {
                return -1;
            }
            if (offset < 0) {
                return -1;
            }
            byte[] parsingBuffer = {tmp[offset], tmp[offset + 1], tmp[offset + 2], tmp[offset + 3]};
            return Float.intBitsToFloat(toInt(parsingBuffer));
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * int to byte array
     *
     * @param value 입력값
     * @return 파싱결과
     */
    public byte[] toByteArray(int value) {
        byte[] byteArray = new byte[4];
        byteArray[0] = (byte) (value >> 24);
        byteArray[1] = (byte) (value >> 16);
        byteArray[2] = (byte) (value >> 8);
        byteArray[3] = (byte) (value);
        return byteArray;
    }

    public static byte[] toByteArray(String hex) {
        hex = hex.replaceAll("\"", "\\\""); /*" */
        if (hex == null || hex.length() == 0) {
            return null;
        }
        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }
    /**
     * int to byte array
     *
     * @param byteArray 파싱할 buffer
     * @param offset    파싱 시작 위치
     * @param value     입력값
     * @return 파싱결과
     */
    public boolean put(byte[] byteArray, int offset, int value) {
        if (byteArray.length < offset) {
            return false;
        }
        if (0 > offset) {
            return false;
        }
        byteArray[offset + 0] = (byte) (value >> 24);
        byteArray[offset + 1] = (byte) (value >> 16);
        byteArray[offset + 2] = (byte) (value >> 8);
        byteArray[offset + 3] = (byte) (value);
        return true;
    }

    /**
     * float to byte array
     *
     * @param value 입력값
     * @return 파싱 결과
     */
    public byte[] toByteArray(float value) {
        int intBits = Float.floatToIntBits(value);
        return new byte[]{
                (byte) (intBits >> 24),
                (byte) (intBits >> 16),
                (byte) (intBits >> 8),
                (byte) (intBits)};
    }

    /**
     * hex byte array to string
     * @param buffer byte array
     * @return string
     */
    public String toHexString(byte [] buffer){
        int parseOffset = 0;
        int length = buffer.length;
        StringBuilder stringBuffer = new StringBuilder(length);
        String hexNumber;
        for (int i = parseOffset; i < parseOffset + length; i++) {
            hexNumber = "0" + Integer.toHexString(0xff & buffer[i]);

            String substring = hexNumber.substring(hexNumber.length() - 2);
            substring = substring.toUpperCase();
            if (i != parseOffset + length - 1) {
                stringBuffer.append(substring + " ");
            } else {
                stringBuffer.append(substring);
            }
        }
        return stringBuffer.toString();
    }
}

