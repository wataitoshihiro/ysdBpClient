package io.pivotal.pal.tracker.exception;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class MyExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
//        if (e instanceof MultipartException && e.getCause() instanceof IllegalStateException
//                && e.getCause().getCause() instanceof FileUploadBase.FileSizeLimitExceededException) {
//            modelAndView.addObject("status", "500");
//            modelAndView.addObject("error", e);
//            modelAndView.addObject("message", "ファイルサイズ超過");
//            modelAndView.setViewName("error");
//            modelAndView.addObject("timestamp", new Date());
//        } else {
//            modelAndView.setViewName("error");
//        }
        modelAndView.addObject("status", "599");
        modelAndView.addObject("error", e);
        modelAndView.addObject("message", "[system error occurred.]\n" +
                e.getMessage());
        modelAndView.setViewName("error");
        modelAndView.addObject("timestamp", new Date());
        return modelAndView;
    }
}