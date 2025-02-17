import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.lang.CharSequence;

public static class asciiCharSequence implements CharSequence {
    private final byte[] data;
    private static final Charset WINDOWS_1251 = Charset.forName("Windows-1251");

    public asciiCharSequence(byte[] data) {
        this.data = data.clone();
    }

    @Override
    public int length() {
        return data.length;
    }

    @Override
    public char charAt(int index) {
        return (char) (data[index] & 0xff);
    }

    private byte[] getBytes(CharSequence sequence) {
        if (sequence instanceof asciiCharSequence) {
            return ((asciiCharSequence) sequence).data;
        } else {
            return sequence.toString().getBytes(WINDOWS_1251);
        }
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        int length = end - start;
        byte[] subsequence = new byte[length];
        System.arraycopy(data, start, subsequence, 0, length);
        return new asciiCharSequence(subsequence);
    }

    public CharSequence subSequence(int start) {
        return subSequence(start, data.length);
    }

    public CharSequence delete(int from, int to) {
        CharSequence left = subSequence(0, from);
        CharSequence right = subSequence(to, data.length);
        return concat(left, right);
    }

    public CharSequence concat(CharSequence first, CharSequence second) {
        byte[] firstBytes = getBytes(first);
        byte[] secondBytes = getBytes(second);
        if (first instanceof asciiCharSequence) {
            firstBytes = ((asciiCharSequence) first).data;
        } else {
            secondBytes = second.toString().getBytes(WINDOWS_1251);
        }

        byte[] combine = new byte[firstBytes.length + secondBytes.length];
        System.arraycopy(firstBytes, 0, combine, 0, firstBytes.length);
        System.arraycopy(secondBytes, 0, combine, firstBytes.length, secondBytes.length);
        return new asciiCharSequence(combine);
    }

    @Override
    public String toString() {
        return new String(data, WINDOWS_1251);
    }
}

        public void main(String[] args) {
            byte[] byteArray = "Ялюблюсобак".getBytes(Charset.forName("Windows-1251"));
            asciiCharSequence asciiSequence = new asciiCharSequence(byteArray);

            CharSequence subSeq = asciiSequence.subSequence(0);
            System.out.println(subSeq);

            CharSequence deletedSeq = asciiSequence.delete(1, 6);
            System.out.println(deletedSeq);
        }