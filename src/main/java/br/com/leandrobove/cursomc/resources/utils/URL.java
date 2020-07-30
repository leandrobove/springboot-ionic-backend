package br.com.leandrobove.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	public static String decodeParam(String param) {
		try {
			return URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static List<Integer> decodeIntList(String s) {

		String[] ids = s.split(",");

		List<Integer> listaDeIds = new ArrayList<Integer>();

		for (String str : ids) {
			listaDeIds.add(Integer.parseInt(str));
		}
		return listaDeIds;

	}

}
