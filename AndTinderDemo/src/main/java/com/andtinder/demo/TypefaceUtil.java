package com.andtinder.demo;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

public class TypefaceUtil {

	public static final String TYPEFACE_MUSEO_SLAB_100 = "Museo_Slab_100.ttf";
	public static final String TYPEFACE_MUSEO_SLAB_300 = "Museo_Slab_300.ttf";

	public static void defineTextStyle(TextView textView, String textStyle) {
		textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), textStyle));
	}

	public static void defineTextStyle(Activity activity, int resId, String textStyle) {

		if (activity != null) {
			TextView textView = (TextView) activity.findViewById(resId);

			if (textView != null) {
				Typeface typeface = typefaces.get(textStyle);

				if (typeface == null) {
					typeface = Typeface.createFromAsset(activity.getAssets(), textStyle);
					typefaces.put(textStyle, typeface);
				}

				textView.setTypeface(typeface);
			}
		}
	}

	static Map<String, Typeface> typefaces = new HashMap<String, Typeface>();

	public static void defineTextStyle(Activity activity, View view, int resId, String textStyle) {

		if (activity != null && view != null) {
			TextView textView = (TextView) view.findViewById(resId);

			if (textView != null) {
				Typeface typeface = typefaces.get(textStyle);

				if (typeface == null) {
					typeface = Typeface.createFromAsset(activity.getAssets(), textStyle);
					typefaces.put(textStyle, typeface);
				}

				textView.setTypeface(typeface);
			}
		}
	}

	public static Typeface getTypeface(Context context, String typefaceId) {
		Typeface typeface = typefaces.get(typefaceId);

		if (typeface == null) {
			typeface = Typeface.createFromAsset(context.getAssets(), typefaceId);
			typefaces.put(typefaceId, typeface);
		}

		return typeface;
	}
}
