package io.pivotal.pal.tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.CharConversionException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler
        extends ResponseEntityExceptionHandler
{

// @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
//    @ResponseBody
//    public Map<String, Object> handleError() {
//        Map<String, Object> errorMap = new HashMap<>();
//        errorMap.put("message", "許可されていないメソッド");
//        errorMap.put("status", HttpStatus.METHOD_NOT_ALLOWED);
//        return errorMap;
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ IllegalArgumentException.class })
    @ResponseBody
    public Map<String, Object> handleError02() {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("message", "ハンドラ（MyExceptionHandler）でハンドリングした！（IllegalArgumentException）");
        errorMap.put("status", HttpStatus.BAD_REQUEST);
        return errorMap;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ CharConversionException.class })
    @ResponseBody
    public Map<String, Object> handleError03() {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("message", "ハンドラ（MyExceptionHandler）でハンドリングした！（CharConversionException）");
        errorMap.put("status", HttpStatus.CONFLICT);
        return errorMap;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ FileNotFoundException.class })
    @ResponseBody
    public Map<String, Object> handleError04() {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("message", "ハンドラ（MyExceptionHandler）でハンドリングした！（FileNotFoundException）");
        errorMap.put("status", HttpStatus.FORBIDDEN);
        return errorMap;
    }

    // 上記処理でハンドリングできなかったエラーは
    // Exceptionを継承しているクラスとしてハンドリングできる
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public Map<String, Object> handleError99(Exception e) {
        e.printStackTrace();
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("message", "ハンドラ（MyExceptionHandler）でハンドリングした！（Exception）");
        errorMap.put("status", HttpStatus.EXPECTATION_FAILED);
        return errorMap;
    }

}