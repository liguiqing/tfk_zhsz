package com.zhezhu.boot.config;


import com.zhezhu.commons.lang.Throwables;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class FreemarkerStaticModels extends HashMap<Object, Object> {
    private PropertiesConfiguration propertiesConfig = null;

    public FreemarkerStaticModels(PropertiesConfiguration propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
        Iterator it = this.propertiesConfig.getKeys();

        while (it.hasNext()) {
            Object key = it.next();
            put(key, getModel(propertiesConfig.getString(key + "")));
        }
    }

    public FreemarkerStaticModels(Map<String, String> classMap) {
        for (String key : classMap.keySet()) {
            put(key, getModel(classMap.get(key)));
        }
    }

    private TemplateHashModel getModel(String packageName) {
        BeansWrapper wrapper = new BeansWrapperBuilder(new Version("2.3.23")).build();
        TemplateHashModel staticModels = wrapper.getStaticModels();
        TemplateHashModel fileStatics;
        try {
            fileStatics = (TemplateHashModel) staticModels.get(packageName);
            return fileStatics;
        } catch (TemplateModelException e) {
            log.error(Throwables.toString(e));
        }
        return null;
    }

}