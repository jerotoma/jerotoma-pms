package com.jerotoma.common.utils;

import com.github.slugify.Slugify;

public class SlugifyUtil {
	
	/*
	 * Now you're able to use it:
	 *  Slugify slg = new Slugify();
		String result = slg.slugify("Hello, world!");
		// result: hello-world
	 * 
	 * 
	 * You can set custom replacements for Slugify:
	 * 
	 *  Slugify slg = slg.withCustomReplacement("hello", "world").withCustomReplacement("foo", "bar");
		// or multiple at once
		slg = slg.withCustomReplacements(new HashMap<String, String>() {{
			put("hello", "world");
			put("foo", "bar");
		}});
		
		String result = slg.slugify("hello foo");
		// result: world-bar
	 * 
	 * Or if you want case sensitivity:
	 * Slugify slg = new Slugify().withLowerCase(false);
	   String result = slg.slugify("Hello, World!");
	   // result: Hello-World
	 * 
	 * */
	
	public static String slugify(String s) {
		
		return new Slugify().slugify(s);
	}
	
    public static Slugify getSlugify() {
		
		return new Slugify();
	}
  
 
}
