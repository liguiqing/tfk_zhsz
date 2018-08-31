package com.tfk.access.domain.model.wechat.xml;

import com.thoughtworks.xstream.XStream;

import java.io.InputStream;
import java.util.Map;

/**
 * 指定类的专属XStream
 * @author antgan
 *
 */
public class XStreamTransformer {

	protected static final Map<Class, XStream> CLASS_2_XSTREAM_INSTANCE =XStreamConfig.getConfig();

	/**
	 * xml -> pojo
	 *
	 * @param clazz
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXml(Class<T> clazz, String xml) {
		T object = (T) CLASS_2_XSTREAM_INSTANCE.get(clazz).fromXML(xml);
		return object;
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromXml(Class<T> clazz, InputStream is) {
		T object = (T) CLASS_2_XSTREAM_INSTANCE.get(clazz).fromXML(is);
		return object;
	}

	/**
	 * pojo -> xml
	 *
	 * @param clazz
	 * @param object
	 * @return
	 */
	public static <T> String toXml(Class<T> clazz, T object) {
		return CLASS_2_XSTREAM_INSTANCE.get(clazz).toXML(object);
	}


}
