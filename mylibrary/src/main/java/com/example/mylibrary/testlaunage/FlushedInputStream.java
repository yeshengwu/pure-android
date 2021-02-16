package com.example.mylibrary.testlaunage;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * https://github.com/nostra13/Android-Universal-Image-Loader/blob/458df4da2e23ba9ad76c79241a948cdfcccf72ae/library/src/main/java/com/nostra13/universalimageloader/core/assist/FlushedInputStream.java
 * https://issuetracker.google.com/issues/36912546 BitmapFactory.decodeStream() fails if InputStream.skip() does not skip fully
 */
public class FlushedInputStream extends FilterInputStream {

    public FlushedInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public long skip(long n) throws IOException {
        long totalBytesSkipped = 0L;
        while (totalBytesSkipped < n) {
            long bytesSkipped = in.skip(n - totalBytesSkipped);
            if (bytesSkipped == 0L) {
                int by_te = read();
                if (by_te < 0) {
                    break; // we reached EOF
                } else {
                    bytesSkipped = 1; // we read one byte
                }
            }
            totalBytesSkipped += bytesSkipped;
        }
        return totalBytesSkipped;
    }
}
