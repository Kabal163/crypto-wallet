package com.github.kabal163.web.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

import static org.apache.commons.lang3.StringUtils.isAllBlank;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WebResponse<T> {

    private final boolean succeeded;

    /**
     * Can be {@code null} if no message was provided by the system.
     */
    @Nullable
    private final String message;

    /**
     * Can be {@code null} if no error occurred during an operation
     */
    @Nullable
    private final String error;

    @Nullable
    private final T data;

    public static WebResponse<Void> succeeded() {
        return new Builder<Void>()
                .succeeded(true)
                .build();
    }

    public static <T> WebResponse<T> succeeded(T data) {
        return new Builder<T>()
                .succeeded(true)
                .data(data)
                .build();
    }

    public static WebResponse<Void> failed(String message, String error) {
        return new Builder<Void>()
                .succeeded(false)
                .message(message)
                .error(error)
                .build();
    }

    public static <T> WebResponse<T> failed(String message, String error, T data) {
        return new Builder<T>()
                .succeeded(false)
                .message(message)
                .error(error)
                .data(data)
                .build();
    }

    public static <R> Builder<R> builder() {
        return new Builder<>();
    }

    public static final class Builder<T> {

        private boolean succeeded;
        private String message;
        private String error;
        private T data;

        public Builder<T> succeeded(boolean succeeded) {
            this.succeeded = succeeded;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> error(String error) {
            this.error = error;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public WebResponse<T> build() {
            if (!succeeded && isAllBlank(message, error)) {
                throw new IllegalStateException("Web response which has no success must contain message or error");
            }

            return new WebResponse<>(succeeded, message, error, data);
        }
    }
}
