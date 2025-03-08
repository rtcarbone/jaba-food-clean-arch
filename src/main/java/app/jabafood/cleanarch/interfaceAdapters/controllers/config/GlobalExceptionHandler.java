package app.jabafood.cleanarch.interfaceAdapters.controllers.config;

import app.jabafood.cleanarch.domain.exceptions.*;
import app.jabafood.cleanarch.interfaceAdapters.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(Exception ex, WebRequest request, HttpStatus status) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();
        String requestPath = httpRequest.getRequestURI();
        String httpMethod = httpRequest.getMethod();

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ex.getMessage(),
                httpMethod,
                requestPath,
                LocalDateTime.now().toString(),
                status.value()
        );
        return new ResponseEntity<>(errorResponse, status);
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
            MenuItemRestaurantInvalidException.class
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
        return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
