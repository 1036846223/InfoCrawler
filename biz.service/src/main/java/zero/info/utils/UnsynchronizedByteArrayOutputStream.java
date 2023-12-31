
package zero.info.utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import zero.info.utils.UnsynchronizedByteArrayInputStream;


public final class UnsynchronizedByteArrayOutputStream extends AbstractByteArrayOutputStream {

    /**
     * Creates a new byte array output stream. The buffer capacity is initially
     * {@value AbstractByteArrayOutputStream#DEFAULT_SIZE} bytes, though its size increases if necessary.
     */
    public UnsynchronizedByteArrayOutputStream() {
        this(DEFAULT_SIZE);
    }

    /**
     * Creates a new byte array output stream, with a buffer capacity of the specified size, in bytes.
     *
     * @param size the initial size
     * @throws IllegalArgumentException if size is negative
     */
    public UnsynchronizedByteArrayOutputStream(final int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Negative initial size: " + size);
        }
        needNewBuffer(size);
    }

    @Override
    public void write(final byte[] b, final int off, final int len) {
        if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException(String.format("offset=%,d, length=%,d", off, len));
        }
        if (len == 0) {
            return;
        }
        writeImpl(b, off, len);
    }

    @Override
    public void write(final int b) {
        writeImpl(b);
    }

    @Override
    public int write(final InputStream in) throws IOException {
        return writeImpl(in);
    }

    @Override
    public int size() {
        return count;
    }

    /**
     * @see java.io.ByteArrayOutputStream#reset()
     */
    @Override
    public void reset() {
        resetImpl();
    }

    @Override
    public void writeTo(final OutputStream out) throws IOException {
        writeToImpl(out);
    }

    /**
     * Fetches entire contents of an {@code InputStream} and represent same data as result InputStream.
     * <p>
     * This method is useful where,
     * </p >
     * <ul>
     * <li>Source InputStream is slow.</li>
     * <li>It has network resources associated, so we cannot keep it open for long time.</li>
     * <li>It has network timeout associated.</li>
     * </ul>
     * It can be used in favor of {@link #toByteArray()}, since it avoids unnecessary allocation and copy of byte[].<br>
     * This method buffers the input internally, so there is no need to use a {@code BufferedInputStream}.
     *
     * @param input Stream to be fully buffered.
     * @return A fully buffered stream.
     * @throws IOException if an I/O error occurs.
     */
    public static InputStream toBufferedInputStream(final InputStream input) throws IOException {
        return toBufferedInputStream(input, DEFAULT_SIZE);
    }

    /**
     * Fetches entire contents of an {@code InputStream} and represent same data as result InputStream.
     * <p>
     * This method is useful where,
     * </p >
     * <ul>
     * <li>Source InputStream is slow.</li>
     * <li>It has network resources associated, so we cannot keep it open for long time.</li>
     * <li>It has network timeout associated.</li>
     * </ul>
     * It can be used in favor of {@link #toByteArray()}, since it avoids unnecessary allocation and copy of byte[].<br>
     * This method buffers the input internally, so there is no need to use a {@code BufferedInputStream}.
     *
     * @param input Stream to be fully buffered.
     * @param size the initial buffer size
     * @return A fully buffered stream.
     * @throws IOException if an I/O error occurs.
     */
    public static InputStream toBufferedInputStream(final InputStream input, final int size) throws IOException {
        // It does not matter if a ByteArrayOutputStream is not closed as close() is a no-op
        try (final UnsynchronizedByteArrayOutputStream output = new UnsynchronizedByteArrayOutputStream(size)) {
            output.write(input);
            return output.toInputStream();
        }
    }

    @Override
    public InputStream toInputStream() {
        return toInputStream(UnsynchronizedByteArrayInputStream::new);
    }

    @Override
    public byte[] toByteArray() {
        return toByteArrayImpl();
    }
}