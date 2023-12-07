package com.sinor.main.expression.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpressionResponse {
	private boolean ok;
	private List<Expression> data;

}
