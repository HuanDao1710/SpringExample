package com.falcon.view.boomer.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

@UtilityClass
public class CommonUtils {
	public Pageable from(long skip, int limit) {
		return PageRequest.of((int) (skip / limit), limit);
	}

	public long extractRemainView(Date start, Date end, Date current, long total) {
		var totalTime = end.getTime() - start.getTime();
		var remainTime = end.getTime() - current.getTime();
		// remainView / totalView = remainTime / totalTime
		return remainTime * total / totalTime;
	}

	public boolean isValidUrl(String url) {
		try {
			new URL(url);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}
	}
}
