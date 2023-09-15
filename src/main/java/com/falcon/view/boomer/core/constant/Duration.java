package com.falcon.view.boomer.core.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Duration {
	SECOND_5(5), SECOND_15(15), SECOND_30(30), SECOND_60(60), SECOND_90(90);
	private final int value;
}
