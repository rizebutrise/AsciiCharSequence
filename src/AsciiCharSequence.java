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
       return (char)(data[index] & 0xff);
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
        int newLength = data.length - (to - from);
        byte[] result = new byte[newLength];
        System.arraycopy(data, 0, result, 0, from);
        System.arraycopy(data, to, result, from, data.length - to);
        return new asciiCharSequence(result);
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