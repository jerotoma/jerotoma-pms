package com.jerotoma.common.utils;

import java.beans.PropertyEditorSupport;

public class UserTypeConverter extends PropertyEditorSupport {
	public void setAsText(final String text) throws IllegalArgumentException {
        setValue(UserType.fromValue(text));
    }
}
