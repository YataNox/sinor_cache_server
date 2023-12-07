package com.sinor.main.expression;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinor.main.expression.model.Expression;

public interface ExpressionRepository extends JpaRepository<Expression, Long> {
}
