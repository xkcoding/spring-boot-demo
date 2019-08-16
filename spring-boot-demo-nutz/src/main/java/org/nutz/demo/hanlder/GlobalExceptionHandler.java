package org.nutz.demo.hanlder;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chinare.axe.Result;
import com.chinare.axe.auth.AuthException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	Log log = Logs.get();

	@ExceptionHandler(value = AuthException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Result auth(HttpServletResponse response, Exception e) {
		log.error("error=>", e);
		return Result.exception("没有权限进行操作!");
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result handle(ValidationException e) {
		log.error("error=>", e);
		if (e instanceof ConstraintViolationException) {

			final List<NutMap> errors = new ArrayList<>();
			final List<String> infos = Lang.list();

			Lang.each(((ConstraintViolationException) e).getConstraintViolations(), new Each<ConstraintViolation>() {

				@Override
				public void invoke(int index, ConstraintViolation error, int length)
						throws ExitLoop, ContinueLoop, LoopException {
					infos.add(error.getMessage());
					errors.add(NutMap.NEW().addv("msg", error.getMessage()).addv("obj", error.getConstraintDescriptor())
							.addv("arguments", error.getExecutableParameters()));
				}
			});
			return Result.fail(infos).addData("details", errors);
		}
		return Result.fail("参数不正确");
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result validate(HttpServletResponse response, MethodArgumentNotValidException e) {
		log.error("error=>", e);
		final List<NutMap> errors = new ArrayList<>();
		final List<String> infos = Lang.list();
		Lang.each(e.getBindingResult().getAllErrors(), new Each<ObjectError>() {

			@Override
			public void invoke(int index, ObjectError error, int length) throws ExitLoop, ContinueLoop, LoopException {
				infos.add(error.getDefaultMessage());
				errors.add(NutMap.NEW().addv("msg", error.getDefaultMessage()).addv("obj", error.getObjectName())
						.addv("arguments", error.getArguments()).addv("code", error.getCode())
						.addv("codes", error.getCodes()));
			}
		});
		return Result.fail(infos).addData("details", errors);
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result defaultErrorHandler(HttpServletResponse response, Exception e) {
		log.error("error=>", e);
		if (e instanceof RuntimeException) {
			log.infof("自定义异常", e);
			return Result.exception(e.getMessage());
		} else {
			return Result.exception("系统异常");
		}
	}
}
