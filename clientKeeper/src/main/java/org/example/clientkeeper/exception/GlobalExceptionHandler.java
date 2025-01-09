//package org.example.clientkeeper.exception;
//
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.ErrorResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//import java.time.LocalDateTime;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                ex.getMessage(),
//                HttpStatus.NOT_FOUND.value(),
//                "RESOURCE_NOT_FOUND",
//                "The resource you are looking for does not exist.",
//                request.getDescription(false)
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                "An unexpected error occurred.",
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "INTERNAL_SERVER_ERROR",
//                ex.getMessage(),
//                request.getDescription(false)
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                ex.getMessage(),
//                HttpStatus.BAD_REQUEST.value(),
//                "BAD_REQUEST",
//                "The request contains invalid arguments.",
//                request.getDescription(false)
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                "Malformed JSON request.",
//                HttpStatus.BAD_REQUEST.value(),
//                "BAD_REQUEST",
//                "The request body is malformed or cannot be read.",
//                request.getDescription(false)
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(UsernameAlreadyExistsException.class)
//    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                ex.getMessage(),
//                HttpStatus.CONFLICT.value(),
//                "USERNAME_ALREADY_EXISTS",
//                "The username provided is already taken.",
//                request.getDescription(false)
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(EmptyException.class)
//    public ResponseEntity<ErrorResponse> handleEmptyUsernameException(EmptyException ex, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                LocalDateTime.now(),
//                ex.getMessage(),
//                HttpStatus.BAD_REQUEST.value(),
//                "USERNAME_EMPTY",
//                "The field is required and cannot be empty.",
//                request.getDescription(false)
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//
////    @ExceptionHandler(AccessDeniedException.class)
////    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
////        ErrorResponse errorResponse = new ErrorResponse(
////                LocalDateTime.now(),
////                "Access Denied.",
////                HttpStatus.FORBIDDEN.value(),
////                "ACCESS_DENIED",
////                "You do not have permission to access this resource.",
////                request.getDescription(false)
////        );
////        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
////    }
//
//
//
//}
