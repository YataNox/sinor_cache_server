package com.sinor.main.expression;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sinor.main.expression.model.Expression;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpressionService {
	private final ExpressionRepository expressionRepository;

	public List<Expression> getExpression() {
		return expressionRepository.findAll();
	}
}
