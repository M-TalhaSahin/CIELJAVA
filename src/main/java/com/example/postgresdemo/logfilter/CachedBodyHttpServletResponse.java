package com.example.postgresdemo.logfilter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream cachedContent;
    private ServletOutputStream outputStream;
    private PrintWriter writer;

    public CachedBodyHttpServletResponse(HttpServletResponse response) throws IOException {
        super(response);
        this.cachedContent = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.outputStream == null) {
            this.outputStream = new CachedBodyServletOutputStream(super.getOutputStream(), this.cachedContent);
        }
        return this.outputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.writer == null) {
            this.writer = new PrintWriter(new OutputStreamWriter(this.cachedContent, this.getCharacterEncoding()));
        }
        return this.writer;
    }

    public byte[] getContentAsByteArray() {
        return this.cachedContent.toByteArray();
    }

    private static class CachedBodyServletOutputStream extends ServletOutputStream {

        private final ServletOutputStream outputStream;
        private final ByteArrayOutputStream cachedContent;

        public CachedBodyServletOutputStream(ServletOutputStream outputStream, ByteArrayOutputStream cachedContent) {
            this.outputStream = outputStream;
            this.cachedContent = cachedContent;
        }

        @Override
        public boolean isReady() {
            return this.outputStream.isReady();
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            this.outputStream.setWriteListener(writeListener);
        }

        @Override
        public void write(int b) throws IOException {
            this.outputStream.write(b);
            this.cachedContent.write(b);
        }
    }
}
