package app.jabafood.cleanarch.interfaceAdapters.controllers.config;

import app.jabafood.cleanarch.domain.exceptions.*;
import app.jabafood.cleanarch.interfaceAdapters.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(Exception ex, WebRequest request, HttpStatus status) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();
        String requestPath = httpRequest.getRequestURI();
        String httpMethod = httpRequest.getMethod();

        logger.error("Exception: {} | Path: {} | Method: {}", ex.getMessage(), requestPath, httpMethod, ex);

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ex.getMessage(),
                httpMethod,
                requestPath,
                LocalDateTime.now()
                        .format(formatter), // Data mais legível
                status.value()
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponseDTO> handleDatabaseConstraintViolation(Exception ex, WebRequest request) {
        return buildErrorResponse(
                new EmailAlreadyInUseException("O e-mail informado já está em uso."),
                request,
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler({EmailAlreadyInUseException.class, LoginAlreadyInUseException.class})
    public ResponseEntity<ErrorResponseDTO> handleAlreadyInUseException(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            AddressNotFoundException.class,
            UserNotFoundException.class,
            RestaurantNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFound(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            AddressMandatoryFieldException.class,
            EmailFormatException.class,
            InvalidPageValueException.class,
            InvalidPasswordException.class,
            InvalidSizeValueException.class,
            MissingPasswordException.class,
            PasswordNotMatchException.class,
            UserMandatoryFieldException.class,
            RestaurantMandatoryFieldException.class,
            RestaurantOwnerInvalidException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            SaveUserException.class,
            SaveAddressException.class,
            UpdatePasswordException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleSaveException(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unhandled exception caught: ", ex);
        return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
