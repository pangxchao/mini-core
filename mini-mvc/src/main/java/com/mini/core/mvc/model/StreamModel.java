package com.mini.core.mvc.model;

import com.mini.core.util.ThrowableKt;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Path;

@SuppressWarnings("UnusedReturnValue")
public final class StreamModel extends IModel<ResponseEntity<Resource>, StreamModel> {
    private Resource resource;

    public StreamModel(HttpServletRequest request, HttpServletResponse response) {
        super(request, response, MediaType.APPLICATION_OCTET_STREAM);
    }

    @Override
    protected StreamModel getThis() {
        return this;
    }

    public final Resource getResource() {
        return resource;
    }

    @Override
    public final StreamModel setStatus(@NotNull HttpStatus status) {
        return super.setStatus(status);
    }

    @Override
    public final StreamModel setMessage(String message) {
        return super.setMessage(message);
    }

    @Override
    public final StreamModel setCode(Integer code) {
        return super.setCode(code);
    }

    public final StreamModel setResource(Resource resource) {
        this.resource = resource;
        return getThis();
    }

    public final StreamModel setInputStreamResource(InputStream stream) {
        return setResource(new InputStreamResource(stream));
    }

    public final StreamModel setFileSystemResource(FileSystem fileSystem, String path) {
        return setResource(new FileSystemResource(fileSystem, path));
    }

    public final StreamModel setFileSystemResource(String path) {
        return setResource(new FileSystemResource(path));
    }

    public final StreamModel setFileSystemResource(File file) {
        return setResource(new FileSystemResource(file));
    }

    public final StreamModel setFileSystemResource(Path path) {
        return setResource(new FileSystemResource(path));
    }

    public final StreamModel setFileUrlResource(URL url) {
        return setResource(new FileUrlResource(url));
    }

    public final StreamModel setByteArrayResource(byte[] byteArray, @Nullable String description) {
        return setResource(new ByteArrayResource(byteArray, description));
    }

    public final StreamModel setByteArrayResource(byte[] byteArray) {
        return setResource(new ByteArrayResource(byteArray));
    }

    public final StreamModel setUriResource(URI uri) {
        try {
            return setResource(new UrlResource(uri));
        } catch (MalformedURLException e) {
            throw ThrowableKt.hidden(e);
        }
    }

    public final StreamModel setUrlResource(URL url) {
        return setResource(new UrlResource(url));
    }

    @Nonnull
    @Override
    public final ResponseEntity<Resource> build() {
        return ResponseEntity.status(getStatus())
                .headers(this.getHeaders())
                .body(resource);
    }

    @Override
    protected final String getDispatcherPath() {
        return "/h/stream";
    }
}
