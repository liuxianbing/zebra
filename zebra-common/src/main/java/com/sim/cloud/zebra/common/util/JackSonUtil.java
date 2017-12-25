package com.sim.cloud.zebra.common.util;

import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JackSonUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public static <T> T readValueAsObjFromStr(String str, Class<T> t) {
		T tt = null;
		try {
			tt = objectMapper.readValue(str, t);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tt;
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(DigestUtils.md5Hex("123"));
	}
}
