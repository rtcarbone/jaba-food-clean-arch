package app.jabafood.cleanarch.infrastructure.config;

import app.jabafood.cleanarch.application.dto.ErrorResponseDTO;
import app.jabafood.cleanarch.domain.exceptions.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

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
                        .format(formatter),
                status.value()
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidEnum(HttpMessageNotReadableException ex, WebRequest request) {
        Throwable rootCause = ex.getCause();

        if (rootCause instanceof InvalidFormatException invalidFormatException) {
            Class<?> targetType = invalidFormatException.getTargetType();
            if (targetType.isEnum()) {
                String acceptedValues = Arrays.stream(targetType.getEnumConstants())
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));

                String errorMessage = String.format("Invalid value '%s' for enum %s. Accepted values: [%s]",
                                                    invalidFormatException.getValue(), targetType.getSimpleName(), acceptedValues);

                return buildErrorResponse(new InvalidEnumException(errorMessage), request, HttpStatus.BAD_REQUEST);
            }
        }

        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmailAlreadyInUseException.class, LoginAlreadyInUseException.class})
    public ResponseEntity<ErrorResponseDTO> handleAlreadyInUseException(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            AddressNotFoundException.class,
            UserNotFoundException.class,
            RestaurantNotFoundException.class,
            MenuItemNotFoundException.class
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
            RestaurantOwnerInvalidException.class,
            MenuItemMandatoryFieldException.class,
            MenuItemRestaurantInvalidException.class,
            InvalidClosingTimeException.class,
            InvalidPriceException.class
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
