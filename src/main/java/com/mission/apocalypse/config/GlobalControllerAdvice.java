package com.mission.apocalypse.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;

@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 处理所有未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理特定的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>("Runtime error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理验证异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleBindException(BindException ex) {
        return new ResponseEntity<>("Validation error: " + ex.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 初始化数据绑定器，应用于所有控制器的方法参数绑定
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 禁用特定字段的数据绑定
        binder.setDisallowedFields("id");
    }

    /**
     * 在所有控制器的方法中添加全局模型属性
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("globalAttribute", "This is a global attribute");
    }
}
