package com.sinor.main.expression;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinor.main.common.BaseResponse;
import com.sinor.main.expression.model.Expression;
import com.sinor.main.expression.model.ExpressionResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/expression")
public class ExpressionController {

	private final ExpressionService expressionService;

	@GetMapping
	public ExpressionResponse getExpression() {
		log.info("getPopular 실행");
		List<Expression> expressionList = expressionService.getExpression();
		return new ExpressionResponse(true, expressionList);
	}
}
