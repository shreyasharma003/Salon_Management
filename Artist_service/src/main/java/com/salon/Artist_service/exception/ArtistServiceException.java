package com.salon.Artist_service.exception;

public class ArtistServiceException extends RuntimeException {

    public ArtistServiceException(String message) {
        super(message);
    }

    public ArtistServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
